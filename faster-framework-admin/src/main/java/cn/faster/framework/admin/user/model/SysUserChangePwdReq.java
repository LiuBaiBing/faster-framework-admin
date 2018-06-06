package cn.faster.framework.admin.user.model;

import lombok.Data;

/**
 * @author zhangbowen 2018/6/6 11:17
 */
@Data
public class SysUserChangePwdReq {
    private String oldPwd;
    private String password;
}
