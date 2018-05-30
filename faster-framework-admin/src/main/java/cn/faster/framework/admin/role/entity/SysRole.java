package cn.faster.framework.admin.role.entity;

import cn.faster.framework.core.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;

/**
 * @author zhangbowen 2018/5/28 15:28
 */
@Getter
@Setter
@Table
public class SysRole extends BaseEntity {
    private String name;

}
