package com.foling.community.exception;

/**
 * @Author foling
 * @Date2021-07-25 11:37
 * @Version 1.0
 * @Other Be happy~
 **/
public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND(2001,"问题不存在了~"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何评论或回复"),
    NO_LOGIN(2003,"未登录哦"),
    GET_INFO_FAILED(2004,"信息错误"),
    SYS_ERROR(2005,"服务器冒烟了"),
    TYPE_PARAM_WRONG(2006,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2007,"回复的评论不存在");
    private String message;
    private Integer code;

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
