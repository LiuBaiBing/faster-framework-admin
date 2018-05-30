package cn.faster.framework.admin.permission.service;

import cn.faster.framework.admin.permission.entity.SysPermission;
import cn.faster.framework.admin.permission.mapper.SysPermissionMapper;
import cn.faster.framework.core.utils.tree.TreeNode;
import cn.faster.framework.core.utils.tree.TreeUtils;
import com.github.pagehelper.Page;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.Weekend;
import tk.mybatis.mapper.weekend.WeekendSqls;

import java.util.List;

/**
 * @author zhangbowen 2018/5/30 11:50
 */
@Service
@Transactional
@AllArgsConstructor
public class SysPermissionService {
    private SysPermissionMapper sysPermissionMapper;

    /**
     * 查询权限树
     *
     * @return
     */
    public List<TreeNode> treeList() {
        List<SysPermission> list = sysPermissionMapper.selectByExample(
                Example.builder(SysPermission.class)
                        .where(WeekendSqls.<SysPermission>custom().andEqualTo(SysPermission::getDeleted, 0))
                        .orderBy(
                                "parentIds", "sort"
                        ).build()
        );
        return TreeUtils.convertToTree(list);
    }

    /**
     * 添加权限
     *
     * @param sysPermission
     */
    public void insert(SysPermission sysPermission) {
        sysPermission.preInsert();
        sysPermissionMapper.insertSelective(sysPermission);
    }

    /**
     * 编辑权限
     *
     * @param sysPermission
     */
    public void update(SysPermission sysPermission) {
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
    }
}
