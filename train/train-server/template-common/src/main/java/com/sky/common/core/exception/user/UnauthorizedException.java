package com.sky.common.core.exception.user;

import com.sky.common.core.exception.BaseException;
import com.sky.common.core.result.CommonError;
import com.sky.common.core.result.error.AuthError;

/**
 * Created by skyyemperor on 2021-02-03 22:55
 * Description : 认证失败的异常类
 */
public class UnauthorizedException extends BaseException {

    public UnauthorizedException() {
        super(AuthError.PASSWORD_WRONG.getCode(),
                AuthError.PASSWORD_WRONG.getMessage());
    }
}
