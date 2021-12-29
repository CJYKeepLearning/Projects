package com.sky.system.data.enums;

import com.sky.common.enums.CampusEnum;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * Created by skyyemperor on 2021-08-26
 * Description :
 */
@Getter
public enum SeatPriceEnum {
    HARD_SEAT("硬座", BigDecimal.valueOf(12.5)),
    HARD_WO("硬卧", BigDecimal.valueOf(58.5)),
    SOFT_WO("软卧", BigDecimal.valueOf(89.5)),
    NO_SEAT("无座", BigDecimal.valueOf(12.5)),
    BUSINESS("商务座", BigDecimal.valueOf(188)),
    FIRST("一等座", BigDecimal.valueOf(98)),
    SECOND("二等座", BigDecimal.valueOf(48)),
    ;

    private String type;
    private BigDecimal price;

    private SeatPriceEnum(String type, BigDecimal price) {
        this.type = type;
        this.price = price;
    }

    public static BigDecimal getPrice(String key) {
        for (SeatPriceEnum enums : SeatPriceEnum.values()) {
            if (enums.type.equals(key)) return enums.price;
        }
        return null;
    }
}
