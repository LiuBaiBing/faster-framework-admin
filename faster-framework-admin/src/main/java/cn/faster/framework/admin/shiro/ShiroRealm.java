package cn.faster.framework.admin.shiro;

import cn.faster.framework.admin.permission.entity.SysPermission;
import cn.faster.framework.admin.permission.service.SysPermissionService;
import cn.faster.framework.admin.rolePermission.entity.SysRolePermission;
import cn.faster.framework.admin.rolePermission.service.SysRolePermissionService;
import cn.faster.framework.admin.user.entity.SysUser;
import cn.faster.framework.admin.user.service.SysUserService;
import cn.faster.framework.admin.userRole.entity.SysUserRole;
import cn.faster.framework.admin.userRole.service.SysUserRoleService;
import cn.faster.framework.core.auth.JwtService;
import cn.faster.framework.core.cache.context.CacheFacade;
import cn.faster.framework.core.exception.model.BasisErrorCode;
import cn.faster.framework.core.web.context.RequestContext;
import cn.faster.framework.core.web.context.WebContextFacade;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangbowen 2018/5/30 10:00
 */
@Service
public class ShiroRealm extends AuthorizingRealm {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysPermissionService sysPermissionService;
    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token != null;
    }

    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String jwtToken = (String) super.getAvailablePrincipal(principalCollection);
        Claims claims = jwtService.parseToken(jwtToken);
        if (claims == null) {
            throw new AuthenticationException(BasisErrorCode.TOKEN_INVALID.getDescription());
        }
        SysUser user = sysUserService.infoById(Long.parseLong(claims.getAudience()));
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if (user == null) {
            return info;
        }

        List<String> permissionCodeList;
        SysUserRole sysUserRoleQuery = new SysUserRole();
        sysUserRoleQuery.setUserId(user.getId());
        //根据用户id，获取当前用户的角色
        List<SysUserRole> sysUserRoleList = sysUserRoleService.list(sysUserRoleQuery);
        boolean isAdmin = sysUserRoleList.stream().anyMatch(userRole -> userRole.getRoleId() == 0);
        info.setRoles(sysUserRoleList.stream().map(userRole -> userRole.getRoleId().toString()).collect(Collectors.toSet()));
        //如果是管理员用户，查询全部权限
        if (isAdmin) {
            permissionCodeList = sysPermissionService.selectAll().stream().map(SysPermission::getCode).collect(Collectors.toList());
        } else {
            //如果不是管理员用户，查询该用户所有角色id
            List<Long> roleIds = sysUserRoleList.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
            //根据角色id列表查询所有权限id
            List<Long> permissionIds = sysRolePermissionService.selectByRoleIdList(roleIds).stream().map(SysRolePermission::getPermissionId).collect(Collectors.toList());
            //根据权限id列表查询所有权限code
            permissionCodeList = sysPermissionService.selectByIdList(permissionIds).stream().map(SysPermission::getCode).collect(Collectors.toList());
        }
        info.addStringPermissions(permissionCodeList);
        return info;
    }

    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        try {
            String token = (String) authenticationToken.getCredentials();
            Claims claims = jwtService.parseToken(token);
            if (claims == null) {
                throw new AuthenticationException(BasisErrorCode.TOKEN_INVALID.getDescription());
            }
            String userId = claims.getAudience();
            if (!jwtService.isMultipartTerminal()) {
                String cacheToken = CacheFacade.get(JwtService.JWT_TOKEN_PREFIX + userId);
                if (StringUtils.isEmpty(cacheToken) || !token.equals(cacheToken)) {
                    throw new AuthenticationException(BasisErrorCode.TOKEN_INVALID.getDescription());
                }
            }
            RequestContext requestContext = WebContextFacade.getRequestContext();
            requestContext.setUserId(Long.parseLong(userId));
            WebContextFacade.setRequestContext(requestContext);
        } catch (Exception e) {
            throw new AuthenticationException(BasisErrorCode.TOKEN_INVALID.getDescription());
        }
        return new SimpleAuthenticationInfo(authenticationToken.getPrincipal(), authenticationToken.getCredentials(), getName());
    }
}
