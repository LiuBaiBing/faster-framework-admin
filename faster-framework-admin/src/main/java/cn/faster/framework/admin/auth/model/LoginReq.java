package cn.faster.framework.admin.auth.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author zhangbowen 2018/5/29 11:04
 */
@Data
public class LoginReq {
    @NotBlank(message = "请填写用户名")
    private String account;
    @NotBlank(message = "请填写密码")
    private String password;

}
