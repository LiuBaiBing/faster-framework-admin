package cn.faster.framework.admin.auth.service;

import cn.faster.framework.admin.auth.error.LoginError;
import cn.faster.framework.admin.auth.model.LoginReq;
import cn.faster.framework.admin.auth.model.LoginRes;
import cn.faster.framework.admin.auth.model.PermissionRes;
import cn.faster.framework.admin.permission.service.SysPermissionService;
import cn.faster.framework.admin.user.entity.SysUser;
import cn.faster.framework.admin.user.mapper.SysUserMapper;
import cn.faster.framework.core.auth.JwtService;
import cn.faster.framework.core.exception.model.ErrorResponseEntity;
import cn.faster.framework.core.utils.Utils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangbowen 2018/5/29 11:03
 */
@Service
public class AuthService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysPermissionService sysPermissionService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthorizingRealm authorizingRealm;

    /**
     * 登录
     *
     * @param loginReq
     * @return
     */
    public ResponseEntity login(LoginReq loginReq) {
        SysUser query = new SysUser();
        query.setAccount(loginReq.getAccount());
        SysUser existUser = sysUserMapper.selectOne(query);
        if (existUser == null) {
            return ErrorResponseEntity.error(LoginError.USER_NOT_EXIST, HttpStatus.NOT_FOUND);
        }
        if (!existUser.getPassword().equals(Utils.md5(loginReq.getPassword()))) {
            return ErrorResponseEntity.error(LoginError.PASSWORD_ERROR, HttpStatus.NOT_FOUND);
        }
        LoginRes loginRes = new LoginRes(jwtService.createToken(existUser.getId(), 0));
        Subject subject = SecurityUtils.getSubject();
        subject.login(new AuthenticationToken() {
            @Override
            public Object getPrincipal() {
                return loginRes.getToken();
            }

            @Override
            public Object getCredentials() {
                return loginRes.getToken();
            }
        });
        return ResponseEntity.ok(loginRes);
    }

    /**
     * 获取当前用户所有权限code
     *
     * @return
     */
    public PermissionRes permissions() {
        Cache<Object, AuthorizationInfo> cache = authorizingRealm.getAuthorizationCache();
        //说明没有缓存，刷新缓存
        if (cache.size() == 0) {
            SecurityUtils.getSubject().isPermitted("permissions");
        }
        return new PermissionRes(cache.get(SecurityUtils.getSubject().getPrincipals()).getStringPermissions());
    }
}
