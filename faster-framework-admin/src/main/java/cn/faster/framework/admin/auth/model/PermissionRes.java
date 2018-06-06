package cn.faster.framework.admin.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;

/**
 * @author zhangbowen 2018/6/6 17:24
 */
@Data
@AllArgsConstructor
public class PermissionRes {
    private Collection<String> permissions;
}
