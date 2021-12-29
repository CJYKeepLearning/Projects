package com.sky.system.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sky.common.core.result.CommonError;
import com.sky.common.core.result.Result;
import com.sky.common.util.MapBuildUtil;
import com.sky.common.util.OkHttpUtil;
import com.sky.system.data.dto.TrainDto;
import com.sky.system.data.po.Seat;
import com.sky.system.data.vo.TrainInfo;
import com.sky.system.mapper.SeatMapper;
import com.sky.system.mapper.TicketMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.system.mapper.TrainMapper;
import com.sky.system.data.po.Train;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by skyyemperor on 2021-08-25
 * Description :
 */
@Service
public class TrainService extends ServiceImpl<TrainMapper, Train> {

    @Autowired
    private TrainMapper trainMapper;

    @Autowired
    private TicketMapper ticketMapper;

    @Autowired
    private SeatMapper seatMapper;

    public Result getTrainList() {
        return Result.success(this.list().stream().map(TrainDto::fromTrain).collect(Collectors.toList()));
    }

    public Result saveTrain(Train train) {
        TrainInfo trainInfo = new TrainInfo(train.getStationInfo(), train.getSeatInfo());
        if (trainInfo.getStationInfoList() == null || trainInfo.getSeatInfoList() == null)
            return Result.getResult(CommonError.PARAM_WRONG);

        this.saveOrUpdate(train);
        return Result.success();
    }

    public Result deleteTrain(String trainId) {
        trainMapper.deleteById(trainId);
        return Result.success();
    }

    public Result getTrainInfo(String trainId) {
        return Result.success(trainMapper.selectById(trainId));
    }

    private int getAcrossDays(String arriveDay) {
        switch (arriveDay) {
            case "当日到达":
                return 0;
            case "次日到达":
                return 1;
            case "第三日到达":
                return 2;
            case "第四日到达":
                return 3;
            case "第五日到达":
                return 4;
            case "第六日到达":
                return 5;
            default:
                return 0;
        }
    }

    private LocalTime parseLocalTime(String time) {
        String[] nums = time.split(":");
        return LocalTime.of(Integer.parseInt(nums[0]), Integer.parseInt(nums[1]));
    }

    private String searchKeyWord(String trainId) {
        String res = OkHttpUtil.getInstance().get(
                "https://search.12306.cn/search/v1/train/search?keyword=" + trainId + "&date=20210911"
        );
        JSONArray arr = JSON.parseObject(res).getJSONArray("data");
        if (arr.size() >= 1) {
            JSONObject o = arr.getJSONObject(0);
            if (o.getString("station_train_code").equals(trainId))
                return o.getString("train_no");
        }
        return null;
    }

    @Transactional
    public Result addTrain(String trainId) {
        String queryTrainId = searchKeyWord(trainId);
        if (queryTrainId == null)
            return Result.getResult(CommonError.CONTENT_NOT_FOUND);

        String res = OkHttpUtil.getInstance().get(
                "https://kyfw.12306.cn/otn/queryTrainInfo/query?" +
                        "leftTicketDTO.train_no=" + queryTrainId
                        + "&leftTicketDTO.train_date=2021-09-11&rand_code=");
        JSONArray jsonArray = JSON.parseObject(res).getJSONObject("data").getJSONArray("data");
        int len = jsonArray.size();

        Train train = new Train();
        train.setTrainId(trainId);
        train.setStartStation(jsonArray.getJSONObject(0).getString("station_name"));
        train.setEndStation(jsonArray.getJSONObject(len - 1).getString("station_name"));
        train.setStartTime(parseLocalTime(jsonArray.getJSONObject(0).getString("start_time")));
        train.setEndTime(parseLocalTime(jsonArray.getJSONObject(len - 1).getString("start_time")));
        train.setAcrossDays(getAcrossDays(jsonArray.getJSONObject(len - 1).getString("arrive_day_str")));

        List<TrainInfo.StationInfo> stationInfoList = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            JSONObject o = jsonArray.getJSONObject(i);
            if (ticketMapper.selectCityOnly(o.getString("station_name")) == null)
                ticketMapper.insertCity(o.getString("station_name"));
            stationInfoList.add(new TrainInfo.StationInfo(
                    o.getString("station_name"),
                    parseLocalTime(o.getString("start_time")),
                    getAcrossDays(o.getString("arrive_day_str"))
            ));
        }

        List<TrainInfo.SeatInfo> seatInfoList = new ArrayList<>();
        seatInfoList.add(new TrainInfo.SeatInfo("硬座", 3));
        seatInfoList.add(new TrainInfo.SeatInfo("硬卧", 2));
        seatInfoList.add(new TrainInfo.SeatInfo("软卧", 1));

        train.setStationInfo(JSON.toJSONString(stationInfoList));
        train.setSeatInfo(JSON.toJSONString(seatInfoList));

        this.saveOrUpdate(train);

        insertSeat(trainId);

        return Result.success(this.getById(trainId));
    }

    private void insertSeat(String trainId) {
        seatMapper.insert(new Seat(trainId, 1, 1, "硬座"));
        seatMapper.insert(new Seat(trainId, 1, 2, "硬座"));
        seatMapper.insert(new Seat(trainId, 1, 3, "硬座"));
        seatMapper.insert(new Seat(trainId, 2, 1, "硬卧"));
        seatMapper.insert(new Seat(trainId, 2, 2, "硬卧"));
        seatMapper.insert(new Seat(trainId, 3, 1, "软卧"));
    }

}

