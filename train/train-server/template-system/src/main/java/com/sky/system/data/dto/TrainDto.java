package com.sky.system.data.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sky.system.data.po.Train;
import com.sky.system.data.vo.TrainInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

/**
 * Created by skyyemperor on 2021-09-06
 * Description :
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainDto {

    private String trainId;

    private String startStation;

    private String endStation;

    private LocalTime startTime;

    private LocalTime endTime;

    private Integer acrossDays;

    private List<TrainInfo.StationInfo> stationInfo;

    private List<TrainInfo.SeatInfo> seatInfo;

    public static TrainDto fromTrain(Train train) {
        TrainDto trainDto = new TrainDto(
                train.getTrainId(),
                train.getStartStation(),
                train.getEndStation(),
                train.getStartTime(),
                train.getEndTime(),
                train.getAcrossDays(),
                null, null
        );
        TrainInfo trainInfo = new TrainInfo(train.getStationInfo(), train.getSeatInfo());
        trainDto.setStationInfo(trainInfo.getStationInfoList());
        trainDto.setSeatInfo(trainInfo.getSeatInfoList());
        return trainDto;
    }
}
