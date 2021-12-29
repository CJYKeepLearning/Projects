package com.sky.common.core.result.error;

import com.sky.common.core.result.ResultError;

/**
 * Created by skyyemperor on 2021-02-02 23:27
 * Description :
 */
public enum TicketError implements ResultError {
    TICKET_SOLD_OUT(40300, "这张票已经售空了"),
    TIME_NOT_ALLOW(40301, "该时间不能购买该票"),
    CHANGE_STATION_MUST_SAME(40302, "改签车票始终车站不同"),
    CHANGE_NOT_ALLOW(40303, "当前订单不支持改签"),
    ;

    private int code;

    private String message;


    private TicketError(int code, String message) {
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