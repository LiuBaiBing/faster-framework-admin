package cn.faster.framework.admin.user.controller;

import cn.faster.framework.admin.user.entity.SysUser;
import cn.faster.framework.admin.user.model.request.SysUserRequest;
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
     * @param sysUserRequest
     * @return
     */
    @PostMapping
    public ResponseEntity insert(@RequestBody @Validated SysUserRequest sysUserRequest) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserRequest, sysUser);
        sysUserService.insert(sysUser);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * 编辑用户
     *
     * @param sysUserRequest
     * @param userId
     * @return
     */
    @PutMapping("/{userId}")
    public ResponseEntity update(@RequestBody SysUserRequest sysUserRequest, @PathVariable Long userId) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserRequest, sysUser);
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
}
