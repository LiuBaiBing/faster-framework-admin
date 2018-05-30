package cn.faster.framework.admin.rolePermission.mapper;

import cn.faster.framework.admin.rolePermission.entity.SysRolePermission;
import cn.faster.framework.core.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author zhangbowen 2018/5/28 17:03
 */
public interface SysRolePermissionMapper extends BaseMapper<SysRolePermission> {
    @Select("select p.code from sys_role_permission rp inner join sys_permission p on rp.permission_id = p.id where rp.role_id in ${collect}")
    List<String> findPermissionCodeListByRoleIds(@Param("collect") String collect);
}
