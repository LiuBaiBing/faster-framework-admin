package cn.faster.framework.admin.permission.entity;

import cn.faster.framework.core.entity.BaseEntity;
import cn.faster.framework.core.utils.tree.TreeNode;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * @author zhangbowen 2018/5/28 15:17
 */
@Getter
@Setter
@Table
public class SysPermission extends BaseEntity implements TreeNode {
    private String name;
    private String code;
    private Long parentId;
    private Long parentIds;
    private List<TreeNode> children;
    @Transient
    private Integer isLeaf = 0;
}
