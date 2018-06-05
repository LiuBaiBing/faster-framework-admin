package cn.faster.framework.admin.rolePermission.service;

import cn.faster.framework.admin.rolePermission.entity.SysRolePermission;
import cn.faster.framework.admin.rolePermission.mapper.SysRolePermissionMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import java.util.List;

/**
 * @author zhangbowen 2018/6/5 11:42
 */
@Service
@Transactional
@AllArgsConstructor
public class SysRolePermissionService {
    private SysRolePermissionMapper sysRolePermissionMapper;

    /**
     * 根据角色id列表查询角色权限关系列表
     *
     * @param roleIdList
     * @return
     */
    public List<SysRolePermission> selectByRoleIdList(List<Long> roleIdList) {
        return sysRolePermissionMapper.selectByExample(new Example
                .Builder(SysRolePermission.class)
                .selectDistinct("permissionId")
                .where(WeekendSqls.<SysRolePermission>custom()
                        .andEqualTo(SysRolePermission::getDeleted, 0)
                        .andIn(SysRolePermission::getRoleId, roleIdList)
                )
                .build()
        );
    }

    /**
     * 根据角色删除角色权限关系
     *
     * @param roleId
     */
    public void deleteByRoleId(Long roleId) {
        SysRolePermission sysRolePermission = new SysRolePermission();
        sysRolePermission.setDeleted(1);
        sysRolePermission.preUpdate();
        sysRolePermissionMapper.updateByExampleSelective(sysRolePermission,
                new Example.Builder(SysRolePermission.class)
                        .where(WeekendSqls.<SysRolePermission>custom()
                                .andEqualTo(SysRolePermission::getRoleId, roleId)
                        ).build()
        );
    }

    /**
     * 根据权限删除角色权限关系
     *
     * @param permissionId
     */
    public void deleteByPermissionId(Long permissionId) {
        SysRolePermission sysRolePermission = new SysRolePermission();
        sysRolePermission.setDeleted(1);
        sysRolePermission.preUpdate();
        sysRolePermissionMapper.updateByExampleSelective(sysRolePermission,
                new Example.Builder(SysRolePermission.class)
                        .where(WeekendSqls.<SysRolePermission>custom()
                                .andEqualTo(SysRolePermission::getPermissionId, permissionId)
                        ).build()
        );
    }

    /**
     * 批量为角色选择权限
     *
     * @param list   权限列表
     * @param roleId 角色id
     */
    public void batchChoose(List<SysRolePermission> list, @PathVariable Long roleId) {
        //根据角色删除所有的关系
        this.deleteByRoleId(roleId);
        if (list.isEmpty()) {
            return;
        }
        //新增所有的权限
        list.forEach(item -> {
            item.setRoleId(roleId);
            item.preInsert();
        });
        sysRolePermissionMapper.insertList(list);
    }
}
