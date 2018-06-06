package cn.faster.framework.admin.user.error;

import cn.faster.framework.core.exception.model.ErrorCode;
import lombok.AllArgsConstructor;

/**
 * @author zhangbowen 2018/6/6 10:41
 */
@AllArgsConstructor
public enum UserError implements ErrorCode {
    USER_EXIST(2100, "用户已存在"),
    USER_NOT_EXIST(2101, "用户不存在"),
    OLD_PASSWORD_ERROR(2103, "旧密码输入错误"),

    ;

    private int value;
    private String description;

    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}
