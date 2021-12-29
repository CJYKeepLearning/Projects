package com.sky.system.service;

import com.sky.common.core.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.system.data.po.Seat;
import com.sky.system.mapper.SeatMapper;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by skyyemperor on 2021-08-25
 * Description :
 */
@Service
public class SeatService extends ServiceImpl<SeatMapper, Seat> {

    @Autowired
    private SeatMapper seatMapper;

    public Result saveSeat(String trainId, Integer carriage, Integer seatNum, String seatType) {
        Seat seat = seatMapper.selectSeat(trainId, carriage, seatNum);
        if (seat == null)
            seat = new Seat(trainId, carriage, seatNum, seatType);
        seat.setSeatType(seatType);
        this.saveOrUpdate(seat);

        return Result.success(seat);
    }

    public Result getSeatList(String trainId, Integer carriage) {
        return Result.success(seatMapper.selectSeatList(trainId, carriage));
    }
}




