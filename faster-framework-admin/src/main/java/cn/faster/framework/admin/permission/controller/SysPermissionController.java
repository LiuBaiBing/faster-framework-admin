package cn.faster.framework.admin.permission.controller;

import cn.faster.framework.admin.permission.service.SysPermissionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangbowen 2018/5/30 11:49
 */
@RestController
@RequestMapping("/sys/permissions")
@AllArgsConstructor
public class SysPermissionController {
    private SysPermissionService sysPermissionService;

    @GetMapping
    public ResponseEntity list(){
        return ResponseEntity.ok(sysPermissionService.treeList());
    }
}
