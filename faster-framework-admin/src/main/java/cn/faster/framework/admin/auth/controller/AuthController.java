package cn.faster.framework.admin.auth.controller;

import cn.faster.framework.admin.auth.model.LoginReq;
import cn.faster.framework.admin.auth.service.AuthService;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangbowen 2018/5/29 10:59
 */
@RestController
@AllArgsConstructor
public class AuthController {
    private AuthService authService;

    /**
     * 登录
     *
     * @param loginReq
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity login(@Validated @RequestBody LoginReq loginReq) {
        return authService.login(loginReq);
    }

    /**
     * 获取权限列表
     *
     * @return
     */
    @GetMapping("/permissions")
    public ResponseEntity permissions() {
        return ResponseEntity.ok(authService.permissions());
    }
    /**
     * 退出登录
     *
     * @return
     */
    @PostMapping("/logout")
    public ResponseEntity logout() {
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
