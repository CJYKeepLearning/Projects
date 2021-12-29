package com.sky.common.core.exception;

import com.sky.common.core.result.CommonError;
import com.sky.common.core.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public Result handleBaseException(BaseException e) {
        log.error(e.getMessage(), e);
        return Result.getResult(e.getCode(), e.getMessage(), e.getData());
    }

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        log.error(e.getMessage(), e);
        return Result.fail();
    }


    /**
     * 参数校验错误异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result validationBodyException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        String message = CommonError.PARAM_WRONG.getMessage();
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            if (errors.size() > 0) {
                FieldError fieldError = (FieldError) errors.get(0);
                message = fieldError.getDefaultMessage();
            }
        }
        return Result.getResult(CommonError.PARAM_WRONG.getCode(), message);
    }


    /**
     * 参数类型转换错误
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return Result.getResult(CommonError.PARAM_WRONG.getCode(),
                e.getName() + "参数类型转换错误");
    }


    /**
     * 参数转换JSON出错
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result httpMessageNotReadableException(HttpMessageNotReadableException e) {
        return Result.getResult(CommonError.PARAM_WRONG.getCode(),
                CommonError.PARAM_WRONG.getMessage());
    }

    /**
     * 请求方法不允许
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result methodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return Result.getResult(CommonError.METHOD_NOT_ALLOW.getCode(),
                e.getMethod() + "方法不允许");
    }

    /**
     * 缺少请求参数
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result missingParameterException(MissingServletRequestParameterException e) {
        return Result.getResult(CommonError.PARAM_WRONG.getCode(), e.getParameterName() + "不能为空");
    }

    /**
     * 请求参数格式错误
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result ConstraintViolationException(ConstraintViolationException e) {
//        String message = CommonError.PARAM_WRONG.getMessage();
//        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
//        if (constraintViolations.size() >= 1) {
//            message = constraintViolations.iterator().next().getMessageTemplate();
//        }
        String message = e.getMessage();
        message = "参数" + message.substring(message.indexOf(".") + 1);
        return Result.getResult(CommonError.PARAM_WRONG.getCode(), message);
    }

}