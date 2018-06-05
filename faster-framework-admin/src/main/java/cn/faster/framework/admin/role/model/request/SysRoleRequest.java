package cn.faster.framework.admin.role.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author zhangbowen 2018/6/5 11:20
 */
@Data
public class SysRoleRequest {
    @NotBlank(message = "请输入角色名称")
    private String name;
}
