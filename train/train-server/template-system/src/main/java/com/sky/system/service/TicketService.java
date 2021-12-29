package com.sky.system.service;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.sky.common.core.exception.ticket.TicketSoldOutError;
import com.sky.common.core.result.CommonError;
import com.sky.common.core.result.Result;
import com.sky.common.core.result.error.TicketError;
import com.sky.common.util.MapBuildUtil;
import com.sky.common.util.StringUtil;
import com.sky.common.util.uuid.IdUtil;
import com.sky.system.data.dto.TicketDto;
import com.sky.system.data.dto.WayDto;
import com.sky.system.data.enums.OrderStatusEnum;
import com.sky.system.data.enums.SeatPriceEnum;
import com.sky.system.data.po.*;
import com.sky.system.data.vo.TrainInfo;
import com.sky.system.data.vo.TransferTicket;
import com.sky.system.mapper.OrderMapper;
import com.sky.system.mapper.SeatMapper;
import com.sky.system.mapper.TicketSeatLinkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.system.mapper.TicketMapper;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by skyyemperor on 2021-08-25
 * Description :
 */
@Service
public class TicketService extends ServiceImpl<TicketMapper, Ticket> {

    @Autowired
    private TicketMapper ticketMapper;

    @Autowired
    private TrainService trainService;

    @Autowired
    private SeatMapper seatMapper;

    @Autowired
    private TicketSeatLinkMapper ticketSeatLinkMapper;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMapper orderMapper;

    public Result getTicketInfo(Long ticketId) {
        Ticket ticket = ticketMapper.selectById(ticketId);
        Train train = trainService.getById(ticket.getTrainId());
        return Result.success(MapBuildUtil.builder()
                .data("ticket", ticket)
                .data("train", train)
                .get());
    }

    public Result searchTicket(String startCity, String endCity, LocalDate day, Integer transfer) {
        List<String> startStations = ticketMapper.selectStationByCity(startCity);
        List<String> endStations = ticketMapper.selectStationByCity(endCity);

        List<WayDto> wayDtos = new ArrayList<>();

        for (String startStation : startStations) {
            for (String endStation : endStations) {
                if (transfer == 0) {
                    List<String> trainIds = ticketMapper.selectTicketDirect(startStation, endStation, day);
                    for (String trainId : trainIds) {
                        wayDtos.add(new WayDto(
                                Collections.singletonList(selectByTrainId(trainId, day, startStation, endStation)),
                                0
                        ));
                    }
                }

                if (transfer > 0) {
                    List<TransferTicket> transferTickets = ticketMapper.selectTicketTransfer(startStation, endStation, day);
                    for (TransferTicket transferTicket : transferTickets) {
                        wayDtos.add(new WayDto(
                                Arrays.asList(
                                        selectByTrainId(transferTicket.getFirstTrainId(), day, startStation, transferTicket.getSecondStartStation()),
                                        selectByTrainId(transferTicket.getSecondTrainId(), transferTicket.getSecondStartDay(), transferTicket.getSecondStartStation(), endStation)
                                ),
                                1
                        ));
                    }
                }
            }
        }

        return Result.success(wayDtos);
    }

    public Result getCityInfo(String city) {
        if (StringUtil.isBlank(city))
            return Result.success(new ArrayList<>());
        return Result.success(ticketMapper.selectCity("%" + city + "%"));
    }

    @Transactional
    public void initTicketPerDay(LocalDate day) {
        List<Train> trains = trainService.list();
        for (Train train : trains) {
            TrainInfo trainInfo = new TrainInfo(train);
            List<TrainInfo.StationInfo> stationInfoList = trainInfo.getStationInfoList();
            List<TrainInfo.SeatInfo> seatInfoList = trainInfo.getSeatInfoList();

            //初始化途径站点信息
            Ticket ticket = new Ticket();
            ticket.setTrainId(train.getTrainId());
            ticket.setStartDay(day);
            for (int i = 0; i < stationInfoList.size(); i++) {
                for (int j = i + 1; j < stationInfoList.size(); j++) {
                    TrainInfo.StationInfo startStation = stationInfoList.get(i);
                    TrainInfo.StationInfo endStation = stationInfoList.get(j);
                    ticket.setStartStation(startStation.getStation());
                    ticket.setStartTime(LocalDateTime.of(day, startStation.getTime()).plusDays(startStation.getAcrossDays()));
                    ticket.setEndStation(endStation.getStation());
                    ticket.setEndTime(LocalDateTime.of(day, endStation.getTime()).plusDays(endStation.getAcrossDays()));

                    for (TrainInfo.SeatInfo seatInfo : seatInfoList) {
                        ticket.setTicketType(seatInfo.getType());
                        ticket.setTicketNum(seatInfo.getNum());
                        ticket.setTicketPrice(SeatPriceEnum.getPrice(seatInfo.getType()).multiply(BigDecimal.valueOf((j - i + 1) / 2)));
                        ticketMapper.insert(ticket);
                    }
                }
            }

            //初始化座位信息
            List<Seat> seats = seatMapper.selectSeatByTrainId(train.getTrainId());
            List<Ticket> tickets = ticketMapper.selectTicketByTrainIdAndDay(train.getTrainId(), day);
            for (Ticket t : tickets) {
                for (Seat s : seats) {
                    if (t.getTicketType().equals(s.getSeatType()))
                        ticketSeatLinkMapper.insert(new TicketSeatLink(t.getTicketId(), s.getSeatId(), false));
                }
            }

            //更新车票数量
            ticketMapper.updateTicketNum(train.getTrainId());
        }
    }

    @Transactional
    public Result buyTicket(Long userId, Long ticketId, Boolean change) {
        Ticket ticket = ticketMapper.selectById(ticketId);
        if (ticket == null || ticket.getTicketNum() <= 0)
            throw new TicketSoldOutError();
        if (ticket.getStartTime().minusMinutes(20).isBefore(LocalDateTime.now()))
            return Result.getResult(TicketError.TIME_NOT_ALLOW);

        TicketSeatLink ticketSeatLink = ticketSeatLinkMapper.selectOneTicket(ticket.getTicketId());
        //处理购买的票
        dealBoughtOrRefundTicket(ticketSeatLink, true);
        //保存订单
        orderService.save(new Order(userId, ticketSeatLink.getTicketId(), ticketSeatLink.getSeatId(), LocalDateTime.now(),
                change ? OrderStatusEnum.CHANGE.getCode() : OrderStatusEnum.WAIT_PAY.getCode()));
        Order order = orderMapper.selectLatestOrder(userId, ticket.getTicketId());

        try {
            AlipayTradePagePayResponse response = Factory.Payment.Page()
                    .asyncNotify("http://skyemperor.top:6066/train/order/pay?orderId=" + order.getOrderId())
                    .pay("火车票订单支付", IdUtil.fastSimpleUUID(),
                            String.format("%.2f", ticket.getTicketPrice()),
                            "http://skyemperor.top/front_project/train/#/order");
            if (ResponseChecker.success(response)) {
                return Result.success(MapBuildUtil.builder()
                        .data("order", order)
                        .data("form", response.getBody())
                        .get());
            } else throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 处理买票或退票
     */
    public void dealBoughtOrRefundTicket(TicketSeatLink ticketSeatLink, Boolean sold) {
        Ticket ticket = ticketMapper.selectById(ticketSeatLink.getTicketId());
        Train train = trainService.getById(ticket.getTrainId());
        TrainInfo trainInfo = new TrainInfo(train);
        List<TrainInfo.StationInfo> stationInfoList = trainInfo.getStationInfoList();

        int start = findStationIdx(stationInfoList, ticket.getStartStation());
        int end = findStationIdx(stationInfoList, ticket.getEndStation());
        int len = stationInfoList.size();

        for (int i = 0; i < start; i++) {
            for (int j = start + 1; j < len; j++) {
                Ticket t = ticketMapper.selectByTrainIdAndStationAndTicketType(
                        train.getTrainId(),
                        stationInfoList.get(i).getStation(),
                        stationInfoList.get(j).getStation(),
                        ticket.getTicketType(),
                        ticket.getStartDay());
                ticketSeatLinkMapper.update(t.getTicketId(), ticketSeatLink.getSeatId(), sold);
            }
        }

        for (int i = start; i < end; i++) {
            for (int j = i + 1; j < len; j++) {
                Ticket t = ticketMapper.selectByTrainIdAndStationAndTicketType(
                        train.getTrainId(),
                        stationInfoList.get(i).getStation(),
                        stationInfoList.get(j).getStation(),
                        ticket.getTicketType(),
                        ticket.getStartDay());
                ticketSeatLinkMapper.update(t.getTicketId(), ticketSeatLink.getSeatId(), sold);
            }
        }

        //更新ticket表中的票数
        ticketMapper.updateTicketNum(train.getTrainId());
    }

    private int findStationIdx(List<TrainInfo.StationInfo> stationInfoList, String stationName) {
        for (int i = 0; i < stationInfoList.size(); i++) {
            if (stationInfoList.get(i).getStation().equals(stationName))
                return i;
        }
        return -1;
    }

    private TicketDto selectByTrainId(String trainId, LocalDate day, String startStation, String endStation) {
        List<Ticket> tickets = ticketMapper.selectTicketByConcreteInfo(trainId, startStation, endStation, day);
        if (tickets.size() == 0) return null;

        TicketDto ticketDto = null;

        for (Ticket ticket : tickets) {
            if (ticketDto == null) {
                ticketDto = TicketDto.fromTicket(ticket);
            } else {
                ticketDto.addTicket(ticket);
            }
        }

        return ticketDto;
    }
}



