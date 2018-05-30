package cn.faster.framework.admin.user.entity;

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
public class SysUser extends BaseEntity {
    private String account;
    private String password;
    private String name;
    private String avatar;
}