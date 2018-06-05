package cn.faster.framework.admin.permission.controller;

import cn.faster.framework.admin.permission.entity.SysPermission;
import cn.faster.framework.admin.permission.model.request.SysPermissionRequest;
import cn.faster.framework.admin.permission.service.SysPermissionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangbowen 2018/5/30 11:49
 */
@RestController
@RequestMapping("/sys/permissions")
@AllArgsConstructor
public class SysPermissionController {
    private SysPermissionService sysPermissionService;

    /**
     * 权限列表
     *
     * @return
     */
    @GetMapping
    public ResponseEntity list() {
        return ResponseEntity.ok(sysPermissionService.treeList());
    }

    /**
     * 权限详情
     *
     * @return
     */
    @GetMapping("/{permissionId}")
    public ResponseEntity infoById(@PathVariable Long permissionId) {
        return ResponseEntity.ok(sysPermissionService.infoById(permissionId));
    }

    /**
     * 新增权限
     *
     * @param request
     * @return
     */
    @PostMapping
    public ResponseEntity add(@Validated @RequestBody SysPermissionRequest request) {
        SysPermission insert = new SysPermission();
        BeanUtils.copyProperties(request, insert);
        sysPermissionService.insert(insert);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 更新权限
     *
     * @param request
     * @param permissionId
     * @return
     */
    @PutMapping("/{permissionId}")
    public ResponseEntity update(@RequestBody SysPermissionRequest request, @PathVariable Long permissionId) {
        SysPermission update = new SysPermission();
        BeanUtils.copyProperties(request, update);
        update.setId(permissionId);
        sysPermissionService.update(update);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 删除权限
     *
     * @param permissionId
     * @return
     */
    @DeleteMapping("/{permissionId}")
    public ResponseEntity delete(@PathVariable Long permissionId) {
        sysPermissionService.deleteById(permissionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
