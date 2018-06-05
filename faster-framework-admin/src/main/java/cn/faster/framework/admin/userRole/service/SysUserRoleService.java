package cn.faster.framework.admin.userRole.service;

import cn.faster.framework.admin.userRole.entity.SysUserRole;
import cn.faster.framework.admin.userRole.mapper.SysUserRoleMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import java.util.List;

/**
 * @author zhangbowen 2018/6/5 11:29
 */
@Service
@Transactional
@AllArgsConstructor
public class SysUserRoleService {
    private SysUserRoleMapper sysUserRoleMapper;


    /**
     * 用户角色列表
     *
     * @param sysUserRole
     * @return
     */
    public List<SysUserRole> list(SysUserRole sysUserRole) {
        return sysUserRoleMapper.select(sysUserRole);
    }

    /**
     * 根据角色id删除用户角色关系
     *
     * @param roleId
     */
    public void deleteByRoleId(Long roleId) {
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setDeleted(1);
        sysUserRole.preUpdate();
        sysUserRoleMapper.updateByExampleSelective(sysUserRole,
                new Example.Builder(SysUserRole.class)
                        .where(WeekendSqls.<SysUserRole>custom()
                                .andEqualTo(SysUserRole::getRoleId, roleId)
                        ).build()
        );
    }

    /**
     * 根据用户id删除用户角色关系
     *
     * @param userId
     */
    public void deleteByUserId(Long userId) {
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setDeleted(1);
        sysUserRole.preUpdate();
        sysUserRoleMapper.updateByExampleSelective(sysUserRole,
                new Example.Builder(SysUserRole.class)
                        .where(WeekendSqls.<SysUserRole>custom()
                                .andEqualTo(SysUserRole::getUserId, userId)
                        ).build()
        );
    }

    /**
     * 批量选择用户的角色
     *
     * @param userId
     */
    public void batchChoose(List<SysUserRole> list, Long userId) {
        this.deleteByUserId(userId);
        if (list.isEmpty()) {
            return;
        }
        list.forEach(item -> {
            item.setUserId(userId);
            item.preInsert();
        });
        sysUserRoleMapper.insertList(list);
    }
}
