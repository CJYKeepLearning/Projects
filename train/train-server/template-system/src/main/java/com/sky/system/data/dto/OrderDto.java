package com.sky.system.data.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sky.system.data.enums.OrderStatusEnum;
import com.sky.system.data.po.Order;
import com.sky.system.data.po.Seat;
import com.sky.system.data.po.Ticket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by skyyemperor on 2021-08-25
 * Description :
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto implements Serializable {
    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 车次id
     */
    private Ticket ticket;

    /**
     * 座位
     */
    private Seat seat;

    /**
     * 下单时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime buyTime;

    /**
     * 订单状态。待支付，已支付，已退票。
     */
    private String orderStatus;

    public static OrderDto fromOrder(Order order) {
        return new OrderDto(
                order.getOrderId(),
                order.getUserId(),
                null,
                null,
                order.getBuyTime(),
                OrderStatusEnum.getRemark(order.getStatus())
        );
    }

    private static final long serialVersionUID = 1L;
}
