package cn.faster.framework.admin.user.controller;

import cn.faster.framework.admin.user.entity.SysUser;
import cn.faster.framework.admin.user.model.SysUserAddReq;
import cn.faster.framework.admin.user.model.SysUserChangePwdReq;
import cn.faster.framework.admin.user.model.SysUserUpdateReq;
import cn.faster.framework.admin.user.service.SysUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangbowen 2018/5/29 11:45
 */
@RestController
@RequestMapping("/sys/users")
@AllArgsConstructor
public class SysUserController {
    private SysUserService sysUserService;

    /**
     * 用户列表
     *
     * @param sysUser
     * @return
     */
    @GetMapping
    public ResponseEntity list(SysUser sysUser) {
        return ResponseEntity.ok(sysUserService.list(sysUser));
    }

    /**
     * 用户详情
     *
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    public ResponseEntity infoById(@PathVariable Long userId) {
        return ResponseEntity.ok(sysUserService.infoById(userId));
    }

    /**
     * 添加用户
     *
     * @param sysUserAddReq
     * @return
     */
    @PostMapping
    public ResponseEntity insert(@RequestBody @Validated SysUserAddReq sysUserAddReq) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserAddReq, sysUser);
        return sysUserService.insert(sysUser);
    }

    /**
     * 编辑用户
     *
     * @param sysUserUpdateReq
     * @param userId
     * @return
     */
    @PutMapping("/{userId}")
    public ResponseEntity update(@RequestBody SysUserUpdateReq sysUserUpdateReq, @PathVariable Long userId) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserUpdateReq, sysUser);
        sysUser.setId(userId);
        sysUserService.update(sysUser);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * 删除用户
     *
     * @param userId
     * @return
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity delete(@PathVariable Long userId) {
        sysUserService.deleteById(userId);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * 修改密码
     *
     * @param sysUserChangePwdReq
     * @param userId
     * @return
     */
    @PutMapping("/{userId}/password/change")
    public ResponseEntity changePwd(@RequestBody SysUserChangePwdReq sysUserChangePwdReq, @PathVariable Long userId) {
        return sysUserService.changePwd(sysUserChangePwdReq, userId);
    }

    /**
     * 重置密码
     *
     * @param userId
     * @return
     */
    @PutMapping("/{userId}/password/reset")
    public ResponseEntity resetPassword(@PathVariable Long userId) {
        sysUserService.resetPassword(userId);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
