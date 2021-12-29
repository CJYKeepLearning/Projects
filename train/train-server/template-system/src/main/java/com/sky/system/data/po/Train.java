package com.sky.system.data.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by skyyemperor on 2021-08-26
 * Description :
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "train")
public class Train implements Serializable {
    /**
     * 车次号
     */
    @TableId(value = "train_id", type = IdType.INPUT)
    private String trainId;

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
    @JsonFormat(timezone = "GMT+8", pattern = "HH:mm:ss")
    private LocalTime startTime;

    /**
     * 终点时间
     */
    @TableField(value = "end_time")
    @JsonFormat(timezone = "GMT+8", pattern = "HH:mm:ss")
    private LocalTime endTime;

    /**
     * 跨越天数
     */
    @TableField(value = "across_days")
    private Integer acrossDays;

    /**
     * 途径站点信息
     */
    @TableField(value = "station_info")
    private String stationInfo;

    /**
     * 座位信息
     */
    @TableField(value = "seat_info")
    private String seatInfo;

    private static final long serialVersionUID = 1L;
}