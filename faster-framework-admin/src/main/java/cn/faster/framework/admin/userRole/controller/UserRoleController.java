package cn.faster.framework.admin.userRole.controller;

import cn.faster.framework.admin.userRole.entity.SysUserRole;
import cn.faster.framework.admin.userRole.service.SysUserRoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhangbowen 2018/6/5 17:27
 */
@RestController
@RequestMapping("/sys/userRole")
@AllArgsConstructor
public class UserRoleController {
    private SysUserRoleService sysUserRoleService;

    /**
     * 批量选择
     *
     * @param list
     * @param userId
     * @return
     */
    @PutMapping("/{userId}")
    public ResponseEntity batchChoose(@RequestBody List<SysUserRole> list, @PathVariable Long userId) {
        sysUserRoleService.batchChoose(list, userId);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
