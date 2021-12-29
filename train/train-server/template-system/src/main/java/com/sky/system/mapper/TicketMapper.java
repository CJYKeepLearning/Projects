package com.sky.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sky.system.data.po.Ticket;
import com.sky.system.data.vo.TransferTicket;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by skyyemperor on 2021-08-30
 * Description :
 */
@Mapper
public interface TicketMapper extends BaseMapper<Ticket> {
    List<String> selectStationByCity(String city);

    List<String> selectCityByStation(String station);

    List<String> selectCity(String city);

    void insertCity(String city);

    String selectCityOnly(String city);

    List<String> selectTicketDirect(@Param("startStation") String startStation,
                                    @Param("endStation") String endStation,
                                    @Param("day") LocalDate day);

    List<TransferTicket> selectTicketTransfer(@Param("startStation") String startStation,
                                              @Param("endStation") String endStation,
                                              @Param("day") LocalDate day);

    List<Ticket> selectTicketByConcreteInfo(@Param("trainId") String trainId,
                                            @Param("startStation") String startStation,
                                            @Param("endStation") String endStation,
                                            @Param("day") LocalDate day);

    List<Ticket> selectTicketByTrainIdAndDay(@Param("trainId") String trainId,
                                             @Param("day") LocalDate day);

    Ticket selectByTrainIdAndStationAndTicketType(@Param("trainId") String trainId,
                                                  @Param("startStation") String startStation,
                                                  @Param("endStation") String endStation,
                                                  @Param("ticketType") String ticketType,
                                                  @Param("day") LocalDate day);

    void updateTicketNum(String trainId);
}