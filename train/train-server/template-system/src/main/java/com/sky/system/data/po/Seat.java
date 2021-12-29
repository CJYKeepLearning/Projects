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
 * Created by skyyemperor on 2021-08-26
 * Description :
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "seat")
public class Seat implements Serializable {
    /**
     * 座位id
     */
    @TableId(value = "seat_id", type = IdType.AUTO)
    private Long seatId;

    /**
     * 车次id
     */
    @TableField(value = "train_id")
    private String trainId;

    /**
     * 车厢
     */
    @TableField(value = "carriage")
    private Integer carriage;

    /**
     * 座位号
     */
    @TableField(value = "seat_num")
    private Integer seatNum;

    /**
     * 座位类型
     */
    @TableField(value = "seat_type")
    private String seatType;

    public Seat(String trainId, Integer carriage, Integer seatNum, String seatType) {
        this.trainId = trainId;
        this.carriage = carriage;
        this.seatNum = seatNum;
        this.seatType = seatType;
    }

    private static final long serialVersionUID = 1L;
}