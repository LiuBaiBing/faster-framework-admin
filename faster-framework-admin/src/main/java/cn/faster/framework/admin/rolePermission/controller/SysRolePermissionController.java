package cn.faster.framework.admin.rolePermission.controller;

import cn.faster.framework.admin.rolePermission.entity.SysRolePermission;
import cn.faster.framework.admin.rolePermission.service.SysRolePermissionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhangbowen 2018/6/5 16:53
 */
@RestController
@RequestMapping("/sys/rolePermission")
@AllArgsConstructor
public class SysRolePermissionController {
    private SysRolePermissionService sysRolePermissionService;

    /**
     * 批量为角色选择权限
     *
     * @param list
     * @return
     */
    @PutMapping("/{roleId}")
    public ResponseEntity batchChoose(@RequestBody List<SysRolePermission> list, @PathVariable Long roleId) {
        sysRolePermissionService.batchChoose(list, roleId);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
