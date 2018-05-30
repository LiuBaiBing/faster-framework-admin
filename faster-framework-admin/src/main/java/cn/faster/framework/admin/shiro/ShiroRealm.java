package cn.faster.framework.admin.shiro;

import cn.faster.framework.admin.rolePermission.mapper.SysRolePermissionMapper;
import cn.faster.framework.admin.user.entity.SysUser;
import cn.faster.framework.admin.user.mapper.SysUserMapper;
import cn.faster.framework.admin.userRole.mapper.SysUserRoleMapper;
import cn.faster.framework.admin.userRole.model.SysUserRoleInfo;
import cn.faster.framework.core.auth.JwtService;
import cn.faster.framework.core.exception.model.BasicError;
import cn.faster.framework.core.web.context.RequestContext;
import cn.faster.framework.core.web.context.WebContextFacade;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangbowen 2018/5/30 10:00
 */
@Service
public class ShiroRealm extends AuthorizingRealm {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;

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
            throw new AuthenticationException(BasicError.TOKEN_INVALID.getDescription());
        }
        SysUser user = sysUserMapper.selectByPrimaryKey(claims.getAudience());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if (user == null) {
            return info;
        }
        List<SysUserRoleInfo> sysUserRoleInfoList = sysUserRoleMapper.findRolesByUser(user.getId());
        info.setRoles(sysUserRoleInfoList.stream().map(SysUserRoleInfo::getRoleName).collect(Collectors.toSet()));
        List<String> permissionCodeList = sysRolePermissionMapper.findPermissionCodeListByRoleIds(sysUserRoleInfoList.stream().map(role -> role.getRoleId().toString()).collect(Collectors.joining(",", "(", ")")));
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
            Claims claims = jwtService.parseToken((String) authenticationToken.getCredentials());
            if (claims == null) {
                throw new AuthenticationException(BasicError.TOKEN_INVALID.getDescription());
            }
            RequestContext requestContext = WebContextFacade.getRequestContext();
            requestContext.setUserId(Long.parseLong(claims.getAudience()));
            WebContextFacade.setRequestContext(requestContext);
        } catch (Exception e) {
            throw new AuthenticationException(BasicError.TOKEN_INVALID.getDescription());
        }
        return new SimpleAuthenticationInfo(authenticationToken.getPrincipal(), authenticationToken.getCredentials(), getName());
    }
}
