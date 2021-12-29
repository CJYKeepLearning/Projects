package com.sky.common.core.exception.user;

import com.sky.common.core.exception.BaseException;
import com.sky.common.core.result.CommonError;
import com.sky.common.core.result.error.AuthError;

/**
 * Created by skyyemperor on 2021-02-03 22:58
 * Description : 无权限的异常类
 */
public class NoPermissionException extends BaseException {
    public NoPermissionException() {
        super(AuthError.PERM_NOT_ALLOW.getCode(),
                AuthError.PERM_NOT_ALLOW.getMessage());
    }
}
