package cn.faster.framework.admin.user.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author zhangbowen 2018/6/5 16:33
 */
@Data
public class SysUserRequest {
    @NotBlank(message = "请输入账号")
    private String account;
    @NotBlank(message = "请输入密码")
    private String password;
    @NotBlank(message = "请输入姓名")
    private String name;
}
