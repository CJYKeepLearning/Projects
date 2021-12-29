package com.sky.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sky.system.data.po.Seat;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by skyyemperor on 2021-08-26
 * Description :
 */
@Mapper
public interface SeatMapper extends BaseMapper<Seat> {
    void deleteSeat(@Param("trainId") String trainId,
                    @Param("carriage") Integer carriage);

    Seat selectSeat(@Param("trainId") String trainId,
                    @Param("carriage") Integer carriage,
                    @Param("seatNum") Integer seatNum);

    List<Seat> selectSeatByTrainId(String trainId);

    List<Seat> selectSeatList(@Param("trainId") String trainId,
                              @Param("carriage") Integer carriage);
}