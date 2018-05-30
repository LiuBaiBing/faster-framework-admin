package cn.faster.framework.admin.user.controller;

import cn.faster.framework.admin.user.mapper.SysUserMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangbowen 2018/5/29 11:45
 */
@RestController
@RequestMapping("/sys//users")
@AllArgsConstructor
public class SysUserController {
    private SysUserMapper sysUserMapper;

    @GetMapping
    public ResponseEntity user(){
        return ResponseEntity.ok(sysUserMapper.selectAll());
    }
}
