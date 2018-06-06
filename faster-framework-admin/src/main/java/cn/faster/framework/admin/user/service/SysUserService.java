package cn.faster.framework.admin.user.service;

import cn.faster.framework.admin.user.entity.SysUser;
import cn.faster.framework.admin.user.error.UserError;
import cn.faster.framework.admin.user.mapper.SysUserMapper;
import cn.faster.framework.admin.user.model.SysUserChangePwdReq;
import cn.faster.framework.admin.userRole.service.SysUserRoleService;
import cn.faster.framework.core.exception.model.ErrorResponseEntity;
import cn.faster.framework.core.utils.Utils;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity insert(SysUser sysUser) {
        //判断当前用户是否存在
        SysUser query = new SysUser();
        query.setAccount(sysUser.getAccount());
        SysUser exist = sysUserMapper.selectOne(query);
        if (exist != null) {
            return ErrorResponseEntity.error(UserError.USER_EXIST, HttpStatus.BAD_REQUEST);
        }
        sysUser.setPassword(Utils.md5(sysUser.getPassword()));
        sysUser.preInsert();
        sysUserMapper.insertSelective(sysUser);
        return new ResponseEntity(HttpStatus.CREATED);
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

    /**
     * 修改密码
     *
     * @param sysUserChangePwdReq
     * @param userId
     * @return
     */
    public ResponseEntity changePwd(SysUserChangePwdReq sysUserChangePwdReq, Long userId) {
        SysUser existUser = sysUserMapper.selectByPrimaryKey(userId);
        if (existUser == null) {
            return ErrorResponseEntity.error(UserError.USER_NOT_EXIST, HttpStatus.NOT_FOUND);
        }
        if (!existUser.getPassword().equals(sysUserChangePwdReq.getOldPwd())) {
            return ErrorResponseEntity.error(UserError.OLD_PASSWORD_ERROR, HttpStatus.NOT_FOUND);
        }
        SysUser update = new SysUser();
        update.setPassword(Utils.md5(sysUserChangePwdReq.getPassword()));
        update.setId(userId);
        update.preUpdate();
        sysUserMapper.updateByPrimaryKeySelective(update);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * 重置密码
     *
     * @param userId
     * @return
     */
    public void resetPassword(Long userId) {
        SysUser update = new SysUser();
        update.setPassword(Utils.md5("123456"));
        update.setId(userId);
        this.update(update);
    }
}
