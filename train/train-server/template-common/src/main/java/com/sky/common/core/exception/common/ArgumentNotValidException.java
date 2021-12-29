package com.sky.common.core.exception.common;

import com.sky.common.core.exception.BaseException;
import com.sky.common.core.result.CommonError;

/**
 * Created by skyyemperor on 2021-02-03 23:40
 * Description : 参数校验错误的异常类
 */
public class ArgumentNotValidException extends BaseException {
    public ArgumentNotValidException(String message) {
        super(CommonError.PARAM_WRONG.getCode(), message);
    }
}
