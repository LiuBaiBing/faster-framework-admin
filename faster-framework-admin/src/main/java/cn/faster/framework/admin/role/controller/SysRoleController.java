package cn.faster.framework.admin.role.controller;

import cn.faster.framework.admin.role.entity.SysRole;
import cn.faster.framework.admin.role.model.request.SysRoleRequest;
import cn.faster.framework.admin.role.service.SysRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangbowen 2018/6/5 9:15
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController {
    private SysRoleService sysRoleService;

    /**
     * 角色列表
     *
     * @return
     */
    @GetMapping
    public ResponseEntity list(SysRole sysRole) {
        return ResponseEntity.ok(sysRoleService.list(sysRole));
    }

    /**
     * 角色详情
     *
     * @return
     */
    @GetMapping("/{roleId}")
    public ResponseEntity infoById(@PathVariable Long roleId) {
        return ResponseEntity.ok(sysRoleService.infoById(roleId));
    }

    /**
     * 新增角色
     *
     * @param request
     * @return
     */
    @PostMapping
    public ResponseEntity insert(@Validated @RequestBody SysRoleRequest request) {
        SysRole insert = new SysRole();
        BeanUtils.copyProperties(request, insert);
        sysRoleService.insert(insert);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 更新角色
     *
     * @param request
     * @param roleId
     * @return
     */
    @PutMapping("/{roleId}")
    public ResponseEntity update(@RequestBody SysRoleRequest request, @PathVariable Long roleId) {
        SysRole update = new SysRole();
        BeanUtils.copyProperties(request, update);
        update.setId(roleId);
        sysRoleService.update(update);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 删除角色
     *
     * @param roleId
     * @return
     */
    @DeleteMapping("/{roleId}")
    public ResponseEntity delete(@PathVariable Long roleId) {
        sysRoleService.deleteById(roleId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
