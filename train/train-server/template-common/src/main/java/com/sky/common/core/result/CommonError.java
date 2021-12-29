package com.sky.common.core.result;

import com.sky.common.constant.Constants;
import com.sky.common.core.result.ResultError;

public enum CommonError implements ResultError {
    PARAM_WRONG(40000, "参数范围或格式错误"),
    NETWORK_WRONG(40001, "网络错误"),
    REQUEST_NOT_ALLOW(40002, "当前条件或时间不允许〒▽〒"),
    REQUEST_FREQUENTLY(40003, "请求繁忙，请稍后再试"),
    CONTENT_NOT_FOUND(40004, "你要找的东西好像走丢啦X﹏X"),
    METHOD_NOT_ALLOW(40005, "方法不允许"),
    THIS_IS_LAST_PAGE(40006, "这是最后一页，再怎么找也没有啦"),
    THIS_IS_FIRST_PAGE(40007, "没有上一页啦"),
    PIC_FORMAT_ERROR(40008, "图片格式只能为jpg, jpeg, png, gif, bmp, webp"),
    VERIFY_WRONG(40009, "参数校验失败"),
    ;


    private int code;

    private String message;


    private CommonError(int code, String message) {
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
