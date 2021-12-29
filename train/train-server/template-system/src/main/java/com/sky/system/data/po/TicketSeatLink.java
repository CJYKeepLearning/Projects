package com.sky.system.data.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by skyyemperor on 2021-08-25
 * Description : 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ticket_seat_link")
public class TicketSeatLink implements Serializable {
    @TableField(value = "ticket_id")
    private Long ticketId;

    @TableField(value = "seat_id")
    private Long seatId;

    @TableField(value = "sold")
    private Boolean sold;

    public TicketSeatLink(Long ticketId, Long seatId) {
        this.ticketId = ticketId;
        this.seatId = seatId;
    }

    private static final long serialVersionUID = 1L;
}