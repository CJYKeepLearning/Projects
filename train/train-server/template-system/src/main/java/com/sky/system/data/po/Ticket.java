package com.sky.system.data.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by skyyemperor on 2021-08-30
 * Description :
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ticket")
public class Ticket implements Serializable {
    /**
     * 车票id
     */
    @TableId(value = "ticket_id", type = IdType.AUTO)
    private Long ticketId;

    /**
     * 车次id
     */
    @TableField(value = "train_id")
    private String trainId;

    /**
     * 出发日期
     */
    @TableField(value = "start_day")
    private LocalDate startDay;

    /**
     * 始发站
     */
    @TableField(value = "start_station")
    private String startStation;

    /**
     * 终点站
     */
    @TableField(value = "end_station")
    private String endStation;

    /**
     * 始发时间
     */
    @TableField(value = "start_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    /**
     * 终点时间
     */
    @TableField(value = "end_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    /**
     * 车票类型
     */
    @TableField(value = "ticket_type")
    private String ticketType;

    /**
     * 车票价格
     */
    @TableField(value = "ticket_price")
    private BigDecimal ticketPrice;

    /**
     * 车票数量
     */
    @TableField(value = "ticket_num")
    private Integer ticketNum;

    private static final long serialVersionUID = 1L;
}