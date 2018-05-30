package cn.faster.framework.admin.userRole.entity;

import cn.faster.framework.core.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;

/**
 * @author zhangbowen 2018/5/28 15:30
 */
@Getter
@Setter
@Table
public class SysUserRole extends BaseEntity {
    private Long userId;
    private Long roleId;
}
