package com.sky.system.data.enums;

import lombok.Getter;

import java.math.BigDecimal;

/**
 * Created by skyyemperor on 2021-08-26
 * Description :
 */
@Getter
public enum OrderStatusEnum {
    HAD_PAYED(0, "未出行"),
    WAIT_PAY(1, "待支付"),
    HAD_REFUND(2, "已退票"),
    CHANGE(3, "已改签"),
    COMPLETED(4, "已完成"),
    CANCEL(5, "已取消"),
    ;

    private Integer code;
    private String remark;

    private OrderStatusEnum(Integer code, String remark) {
        this.code = code;
        this.remark = remark;
    }

    public static String getRemark(Integer code) {
        for (OrderStatusEnum e : OrderStatusEnum.values()) {
            if (e.code.equals(code))
                return e.remark;
        }
        return "";
    }
}
