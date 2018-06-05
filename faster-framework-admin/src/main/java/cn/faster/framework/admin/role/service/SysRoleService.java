package cn.faster.framework.admin.role.service;

import cn.faster.framework.admin.role.entity.SysRole;
import cn.faster.framework.admin.role.mapper.SysRoleMapper;
import cn.faster.framework.admin.rolePermission.service.SysRolePermissionService;
import cn.faster.framework.admin.userRole.service.SysUserRoleService;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

/**
 * @author zhangbowen 2018/6/5 9:15
 */
@Service
@Transactional
@AllArgsConstructor
public class SysRoleService {
    private SysRoleMapper sysRoleMapper;
    private SysUserRoleService sysUserRoleService;
    private SysRolePermissionService sysRolePermissionService;

    /**
     * 列表
     *
     * @return
     */
    public PageInfo<SysRole> list(SysRole sysRole) {
        return sysRoleMapper.selectPageByExample(new Example.Builder(SysRole.class)
                .where(WeekendSqls.<SysRole>custom()
                        .andEqualTo(SysRole::getDeleted, 0)
                        .andLike(SysRole::getName, "%" + sysRole.getName() + "%")
                ).orderByAsc("createDate")
                .build()).toPageInfo();
    }

    /**
     * 详情
     *
     * @param roleId
     * @return
     */
    public SysRole infoById(Long roleId) {
        return sysRoleMapper.selectByPrimaryKey(roleId);
    }

    /**
     * 添加
     *
     * @return
     */
    public void insert(SysRole sysRole) {
        sysRole.preInsert();
        sysRoleMapper.insertSelective(sysRole);
    }

    /**
     * 编辑
     *
     * @return
     */
    public void update(SysRole sysRole) {
        sysRole.preUpdate();
        sysRoleMapper.updateByPrimaryKeySelective(sysRole);
    }

    /**
     * 删除
     *
     * @return
     */
    public void deleteById(Long id) {
        //超级管理员不可删除
        if (id == 0) {
            return;
        }
        SysRole delete = new SysRole();
        delete.setId(id);
        delete.setDeleted(1);
        delete.preUpdate();
        sysRoleMapper.updateByPrimaryKeySelective(delete);
        //删除角色用户关系
        sysUserRoleService.deleteByRoleId(id);
        //删除角色权限关系
        sysRolePermissionService.deleteByRoleId(id);
    }

}
