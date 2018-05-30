package cn.faster.framework.admin.rolePermission.entity;

import cn.faster.framework.core.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;

/**
 * @author zhangbowen 2018/5/28 15:29
 */
@Getter
@Setter
@Table
public class SysRolePermission extends BaseEntity {
    private Long roleId;
    private Long permissionId;
}
