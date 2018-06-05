package cn.faster.framework.admin.user.service;

import cn.faster.framework.admin.user.entity.SysUser;
import cn.faster.framework.admin.user.mapper.SysUserMapper;
import cn.faster.framework.admin.userRole.service.SysUserRoleService;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

/**
 * @author zhangbowen 2018/5/29 11:01
 */
@Service
@Transactional
@AllArgsConstructor
public class SysUserService {
    private SysUserMapper sysUserMapper;
    private SysUserRoleService sysUserRoleService;

    /**
     * 列表
     *
     * @param sysUser
     * @return
     */
    public PageInfo<SysUser> list(SysUser sysUser) {
        return sysUserMapper.selectPageByExample(new Example.Builder(SysUser.class)
                .where(WeekendSqls.<SysUser>custom()
                        .andEqualTo(SysUser::getDeleted, 0)
                        .andLike(SysUser::getName, "%" + sysUser.getName() + "%")
                ).orderByAsc("createDate")
                .build()).toPageInfo();
    }

    /**
     * 用户详情
     *
     * @param id
     * @return
     */
    public SysUser infoById(Long id) {
        return sysUserMapper.selectByPrimaryKey(id);
    }

    /**
     * 新增用户
     *
     * @param sysUser
     */
    public void insert(SysUser sysUser) {
        sysUser.preInsert();
        sysUserMapper.insertSelective(sysUser);
    }

    /**
     * 更新用户
     *
     * @param sysUser
     */
    public void update(SysUser sysUser) {
        sysUser.preUpdate();
        sysUserMapper.updateByPrimaryKeySelective(sysUser);
    }

    /**
     * 删除用户
     *
     * @param userId
     */
    public void deleteById(Long userId) {
        SysUser delete = new SysUser();
        delete.setId(userId);
        delete.setDeleted(1);
        delete.preUpdate();
        sysUserMapper.updateByPrimaryKeySelective(delete);
        //删除用户角色关系
        sysUserRoleService.deleteByUserId(userId);
    }
}
