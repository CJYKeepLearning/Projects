package com.sky.system.data.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by skyyemperor on 2021-08-31
 * Description :
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "`order`")
public class Order implements Serializable {
    /**
     * 订单id
     */
    @TableId(value = "order_id", type = IdType.AUTO)
    private Long orderId;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 车票id
     */
    @TableField(value = "ticket_id")
    private Long ticketId;

    /**
     * 座位id
     */
    @TableField(value = "seat_id")
    private Long seatId;

    /**
     * 下单时间
     */
    @TableField(value = "buy_time")
    private LocalDateTime buyTime;

    /**
     * 订单状态。待支付，已支付，已退票。
     */
    @TableField(value = "status")
    private Integer status;

    public Order(Long userId, Long ticketId, Long seatId, LocalDateTime buyTime, Integer status) {
        this.userId = userId;
        this.ticketId = ticketId;
        this.seatId = seatId;
        this.buyTime = buyTime;
        this.status = status;
    }

    private static final long serialVersionUID = 1L;
}