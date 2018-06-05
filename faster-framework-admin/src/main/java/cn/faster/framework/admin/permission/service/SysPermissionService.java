package cn.faster.framework.admin.permission.service;

import cn.faster.framework.admin.permission.entity.SysPermission;
import cn.faster.framework.admin.permission.mapper.SysPermissionMapper;
import cn.faster.framework.admin.rolePermission.service.SysRolePermissionService;
import cn.faster.framework.core.entity.BaseEntity;
import cn.faster.framework.core.utils.tree.TreeNode;
import cn.faster.framework.core.utils.tree.TreeUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangbowen 2018/5/30 11:50
 */
@Service
@Transactional
@AllArgsConstructor
public class SysPermissionService {
    private SysPermissionMapper sysPermissionMapper;
    private SysRolePermissionService sysRolePermissionService;

    /**
     * 查询权限树
     *
     * @return
     */
    public List<TreeNode> treeList() {
        List<SysPermission> list = sysPermissionMapper.selectByExample(
                Example.builder(SysPermission.class)
                        .where(WeekendSqls.<SysPermission>custom().andEqualTo(SysPermission::getDeleted, 0))
                        .orderByAsc(
                                "parentIds"
                        ).build()
        ).stream().sorted(Comparator.comparing(BaseEntity::getSort)).collect(Collectors.toList());
        return TreeUtils.convertToTree(list);
    }

    /**
     * 查询全部权限列表
     *
     * @return
     */
    public List<SysPermission> selectAll() {
        return sysPermissionMapper.select(new SysPermission());
    }

    /**
     * 权限详情
     *
     * @param permissionId
     * @return
     */
    public SysPermission infoById(Long permissionId) {
        return sysPermissionMapper.selectByPrimaryKey(permissionId);
    }

    /**
     * 添加权限
     *
     * @param sysPermission
     */
    public void insert(SysPermission sysPermission) {
        if (sysPermission.getParentId() != 0) {
            //获取parentIds
            SysPermission existParent = sysPermissionMapper.selectByPrimaryKey(sysPermission.getParentId());
            if (existParent == null) {
                return;
            }
            sysPermission.setParentIds(existParent.getParentIds().concat(",").concat("[").concat(existParent.getParentId().toString()).concat("]"));
        } else {
            sysPermission.setParentIds("[0]");
        }
        sysPermission.preInsert();
        sysPermissionMapper.insertSelective(sysPermission);
    }

    /**
     * 编辑权限
     *
     * @param sysPermission
     */
    public void update(SysPermission sysPermission) {
        if (sysPermission.getParentId() != null) {
            if (sysPermission.getParentId() != 0) {
                //获取parentIds
                SysPermission existParent = sysPermissionMapper.selectByPrimaryKey(sysPermission.getParentId());
                if (existParent == null) {
                    return;
                }
                sysPermission.setParentIds(existParent.getParentIds().concat(",").concat("[").concat(existParent.getId().toString()).concat("]"));
            } else {
                sysPermission.setParentIds("[0]");
            }
        }
        sysPermission.preUpdate();
        sysPermissionMapper.updateByPrimaryKeySelective(sysPermission);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(Long id) {
        SysPermission delete = new SysPermission();
        delete.setId(id);
        delete.setDeleted(1);
        delete.preUpdate();
        sysPermissionMapper.updateByPrimaryKeySelective(delete);
        //删除角色权限关系
        sysRolePermissionService.deleteByPermissionId(id);
    }

    /**
     * 根据id列表查询
     *
     * @return
     */
    public List<SysPermission> selectByIdList(List<Long> idList) {
        return sysPermissionMapper.selectByIdList(idList);
    }
}
