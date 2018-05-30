package cn.faster.framework.admin.auth.error;

import cn.faster.framework.core.exception.model.ErrorCode;
import lombok.AllArgsConstructor;

/**
 * @author zhangbowen 2018/5/29 11:06
 */
@AllArgsConstructor
public enum LoginError implements ErrorCode {
    USER_NOT_EXIST(2000, "用户未注册"),
    PASSWORD_ERROR(2001, "密码错误"),

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
