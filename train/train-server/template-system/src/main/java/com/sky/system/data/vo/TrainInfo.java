package com.sky.system.data.vo;

import com.alibaba.fastjson.JSON;
import com.sky.system.data.po.Train;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by skyyemperor on 2021-08-26
 * Description :
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainInfo {

    List<StationInfo> stationInfoList;

    List<SeatInfo> seatInfoList;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StationInfo {
        String station;
        LocalTime time;
        Integer acrossDays;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SeatInfo {
        String type;
        Integer num;
    }

    public TrainInfo(String stationInfoJson, String seatInfoJson) {
        try {
            this.stationInfoList = JSON.parseArray(stationInfoJson, StationInfo.class);
            this.seatInfoList = JSON.parseArray(seatInfoJson, SeatInfo.class);
        } catch (Exception e) {
            //
        }
    }

    public TrainInfo(Train train) {
        this(train.getStationInfo(), train.getSeatInfo());
    }

    public static void main(String[] args) {
        List<StationInfo> stationInfos = new ArrayList<>();
        stationInfos.add(new StationInfo("深圳", LocalTime.of(7, 0), 0));
        stationInfos.add(new StationInfo("济南", LocalTime.of(10, 0), 0));
        stationInfos.add(new StationInfo("北京", LocalTime.of(23, 0), 0));
        System.out.println(JSON.toJSONString(stationInfos));

        List<SeatInfo> seatInfos = new ArrayList<>();
        seatInfos.add(new SeatInfo("硬座", 200));
        seatInfos.add(new SeatInfo("硬卧", 100));
        seatInfos.add(new SeatInfo("软卧", 100));
        System.out.println(JSON.toJSONString(seatInfos));

        String s = "[{\"acrossDays\":0,\"station\":\"深圳\",\"time\":\"07:00\"},{\"acrossDays\":0,\"station\":\"济南\",\"time\":\"10:00\"},{\"acrossDays\":0,\"station\":\"北京\",\"time\":\"23:00\"}]";
    }

}
