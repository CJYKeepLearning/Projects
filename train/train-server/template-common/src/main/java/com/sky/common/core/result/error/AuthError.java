package com.sky.common.core.result.error;

import com.sky.common.core.result.ResultError;

/**
 * Created by skyyemperor on 2020-12-18 2:05
 * Description :
 */
public enum AuthError implements ResultError {
    PASSWORD_WRONG(40100, "账号或密码错误"),
    LOGIN_STATUS_IS_INVALID(40101, "登录状态已失效"),
    LOGIN_STATUS_WRONG(40102, "登录状态错误，请重新登录"),
    PERM_NOT_ALLOW(40103, "无权限访问"),
    REFRESH_TOKEN_IS_NOT_VALID(40104,"refresh_token已失效"),
    USER_NOT_EXIST(40105,"该用户不存在"),
    USERNAME_HAS_EXIST(40106, "用户名已存在"),

    ;

    private int code;

    private String message;


    private AuthError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
