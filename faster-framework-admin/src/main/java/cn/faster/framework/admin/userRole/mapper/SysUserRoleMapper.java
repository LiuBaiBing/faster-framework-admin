package cn.faster.framework.admin.userRole.mapper;

import cn.faster.framework.admin.userRole.entity.SysUserRole;
import cn.faster.framework.admin.userRole.model.SysUserRoleInfo;
import cn.faster.framework.core.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author zhangbowen 2018/5/28 17:02
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
    @Select("select r.id as roleId,r.name as roleName from sys_user_role ur inner join sys_role r on ur.role_id = r.id where ur.user_id=#{userId}")
    List<SysUserRoleInfo> findRolesByUser(Long userId);
}
