package cn.faster.framework.admin.permission.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author zhangbowen 2018/6/4 16:12
 */
@Data
public class SysPermissionRequest {
    @NotBlank(message = "请输入权限名称")
    private String name;
    @NotBlank(message = "请输入权限编码")
    private String code;
    @NotNull(message = "请选择上级id")
    private Long parentId;
}
