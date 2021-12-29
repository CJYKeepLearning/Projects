package com.sky.system.service;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.sky.common.core.exception.ticket.TicketSoldOutError;
import com.sky.common.core.result.CommonError;
import com.sky.common.core.result.Result;
import com.sky.common.core.result.error.AuthError;
import com.sky.common.core.result.error.TicketError;
import com.sky.common.util.MapBuildUtil;
import com.sky.common.util.uuid.IdUtil;
import com.sky.system.data.dto.OrderDto;
import com.sky.system.data.enums.OrderStatusEnum;
import com.sky.system.data.po.Ticket;
import com.sky.system.data.po.TicketSeatLink;
import com.sky.system.mapper.TicketMapper;
import com.sky.system.mapper.TicketSeatLinkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.system.data.po.Order;
import com.sky.system.mapper.OrderMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by skyyemperor on 2021-08-25
 * Description :
 */
@Service
public class OrderService extends ServiceImpl<OrderMapper, Order> {

    @Autowired
    private TicketMapper ticketMapper;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private SeatService seatService;

    @Autowired
    private OrderMapper orderMapper;

    static {
        Factory.setOptions(getOptions());
    }


    public Result getOrderInfo(Long userId) {
        List<OrderDto> orderDtos = new ArrayList<>();
        List<Order> orders = orderMapper.selectByUserId(userId);

        for (Order order : orders) {
            OrderDto orderDto = OrderDto.fromOrder(order);
            orderDto.setTicket(ticketService.getById(order.getTicketId()));
            orderDto.setSeat(seatService.getById(order.getSeatId()));
            orderDtos.add(orderDto);
        }

        return Result.success(orderDtos);
    }

    public Result getOrderList() {
        List<OrderDto> orderDtos = new ArrayList<>();
        List<Order> orders = this.list();

        for (Order order : orders) {
            OrderDto orderDto = OrderDto.fromOrder(order);
            orderDto.setTicket(ticketService.getById(order.getTicketId()));
            orderDto.setSeat(seatService.getById(order.getSeatId()));
            orderDtos.add(orderDto);
        }

        return Result.success(orderDtos);
    }

    public Result buyOrder(Long userId, Long orderId) {
        Order order = this.getById(orderId);
        if(!order.getStatus().equals(OrderStatusEnum.WAIT_PAY.getCode()))
            return Result.getResult(CommonError.REQUEST_NOT_ALLOW.getCode(),
                    OrderStatusEnum.getRemark(order.getStatus()) + "状态下无需支付");

        Ticket ticket = ticketMapper.selectById(order.getTicketId());
        try {
            AlipayTradePagePayResponse response = Factory.Payment.Page()
                    .asyncNotify("http://skyemperor.top:6066/train/order/pay?orderId=" + order.getOrderId())
                    .pay("火车票订单支付", IdUtil.fastSimpleUUID(),
                            String.format("%.2f", ticket.getTicketPrice()),
                            "http://localhost:3025/#/order");
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

    @Transactional
    public Result refundOrder(Long userId, Long orderId) {
        Order order = this.getById(orderId);
        if (order == null)
            return Result.getResult(CommonError.CONTENT_NOT_FOUND);
        if (order.getStatus().equals(OrderStatusEnum.WAIT_PAY.getCode()) ||
                order.getStatus().equals(OrderStatusEnum.COMPLETED.getCode()) ||
                order.getStatus().equals(OrderStatusEnum.CANCEL.getCode()))
            return Result.getResult(CommonError.REQUEST_NOT_ALLOW.getCode(),
                    OrderStatusEnum.getRemark(order.getStatus()) + "状态下不能退票");

        TicketSeatLink ticketSeatLink = new TicketSeatLink(order.getTicketId(), order.getSeatId());
        //处理需退的票
        ticketService.dealBoughtOrRefundTicket(ticketSeatLink, false);
        //更新订单状态为已退票
        this.saveOrUpdate(new Order(orderId, userId, ticketSeatLink.getTicketId(), ticketSeatLink.getSeatId(),
                LocalDateTime.now(), OrderStatusEnum.HAD_REFUND.getCode()));

        return Result.success();
    }


    @Transactional
    public Result changeOrder(Long userId, Long orderId, Long ticketId) {
        Order order = this.getById(orderId);
        Ticket ticket = ticketService.getById(ticketId);

        if (order == null || ticket == null)
            return Result.getResult(CommonError.CONTENT_NOT_FOUND);
        if (!order.getStatus().equals(OrderStatusEnum.HAD_PAYED.getCode()))
            return Result.getResult(TicketError.CHANGE_NOT_ALLOW);

        Ticket oldTicket = ticketService.getById(order.getTicketId());

        if (!order.getUserId().equals(userId))
            return Result.getResult(AuthError.PERM_NOT_ALLOW);
        if (ticket.getTicketNum() <= 0)
            throw new TicketSoldOutError();
        if (ticket.getStartTime().minusMinutes(20).isBefore(LocalDateTime.now()))
            return Result.getResult(TicketError.TIME_NOT_ALLOW);
        if (!ticketMapper.selectCityByStation(oldTicket.getStartStation()).equals(ticketMapper.selectCityByStation(ticket.getStartStation()))
                || !ticketMapper.selectCityByStation(oldTicket.getEndStation()).equals(ticketMapper.selectCityByStation(ticket.getEndStation())))
            return Result.getResult(TicketError.CHANGE_STATION_MUST_SAME);

        //买新票
        ticketService.buyTicket(userId, ticketId, true);
        //退票
        this.refundOrder(userId, orderId);

        return Result.success();
    }

    public Result payOrder(Long orderId) {
        Order order = orderMapper.selectById(orderId);
        if(!order.getStatus().equals(OrderStatusEnum.WAIT_PAY.getCode())){
            return Result.getResult(CommonError.REQUEST_NOT_ALLOW.getCode(),
                    OrderStatusEnum.getRemark(order.getStatus()) + "状态下不能支付");
        }
        order.setStatus(OrderStatusEnum.HAD_PAYED.getCode());
        orderMapper.updateById(order);
        return Result.success();
    }

    private static Config getOptions() {
        Config config = new Config();
        config.protocol = "https";
        config.gatewayHost = "openapi.alipaydev.com";
        config.signType = "RSA2";

        config.appId = "2021000118613812";

        config.merchantPrivateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCVQUAcqcmM1Aj9QFhP2AszyeyZXZhcN6YO3p391zhlEckUxg3/NY/p+IL+mzYABEjZuB9zWMknVyeKrG5u8mlw8tBMfJ3Liu1/Dj6NIpsAR8goRQ31tw130d7zwyUfxbBptPwq0avED9EOBUORt9n3YYEehjrXGjOp+3fIN5bjuEDY4fbPxLSB0+0CtIRELpwc7nswVGD9Gt8SIAZBP4mHEEYZvlTHkFC2hQEhnnFw44yJPNlTqdvei9Xg4Y1hZo5Oiz16/CXY1yNhhXDYMBU2zgCB9Qhd/2hMItUXjdAy5HLzHgKd8DIYNnaESyr239dHtNeuutznS/w3mX3JdxlPAgMBAAECggEAbTKMAWyHNS18VizZb7EUoRM+swYzDmT4bTCaTrlDXU6h2zz7yJgbboaO8FAGNgTsd72p5O40XT/6gg6dzTY1xpkfpJYXrG88SxTU1tVWqeHaovvCtt7yT5OQlq9TXed4ph3v0DQOgyc/QA7B3L6yDbecjASQ0AcvfVGp1SY2kut9rMNYYMMQxZvACLJHYaBU8P/FI7yXpuO/r0fzVNieygoEOvHEmR3tizhmlYZIMRbpupn+6lQFCm+9n8oRpiylc79xBf9hs9cRjF5V6CACeGlqlDvnO2hlSmXIQGJCFDvQU4OXr98G3/yxnb7qb3Mpx8PeU2rLFQGoT5/pcuQ9YQKBgQDkRmjGtkzKFlRvQztJiFCa72VI9ZUqLEQUkF4A/Mm1+PTaCYnmUI+OgD8FW13tPcBV9HnnrDZPcWniPivzp/pFZQqo1c3ZqXMtWHggg7jeFbBOXYg01bK944B55k0W3Mc0bUt+z2t3kfGnmEOxc5wDjXTaHokWXALkSYjXQ4Ch5QKBgQCnYey4ZVnWbTmgG62uNXN8di/UTm2oMzXKz6AioNk+IYxB8XZRk1Ra4h5WFKWhT93le4UGAU3APs5npXzDY7UMXUrSgdrm9ajp6GuBSYVcX29N6FYKsyn1Ka4m9tnQutI8wPV1VHOmbu2vyucIgMdwvvt3v1fylQqeZ5/3z/mrIwKBgGS7jVDi8U/cOrY+fNMeDDPSoGdpt8E8d257pn/ZDUn36lStEOVJd4/yl9zNTM18Cnvjvke1fuHfslHcDZkf7xjgVavbbVM3q1O/ToNKnTGjcIGkVI68HnwdAHzKXApSsrU35bC35zyEcpb6YiTUV2xZ58642tW7FKuYp4IWj5ThAoGAUqsarzffVvg7Lch9zzy2/RGiLclOf3RLbQ9+l5u5r+YBz8/iEAgj8X8cXId2BmXbwitvV7ftHrXmW2pwZFAqayDog29/HfLWqjAQaJpXM3gvENtfI4m5f0xGpxfkHCMpErh6gqhjNiAzZE9iK+Dun9CzgkEOvtC9nd6wM0SUmuECgYEAwmQSa+H0T/rUCvbbQMow8SpCZvEFh40ItAMDEjseOVscf/1hC8C9WXcsHgooo9u6EBSoTah0+bpcg8sVO1e69kySVWcyZiaC8uyRhEP9hRs4v3EQLT7blBzs8GTw6OvAqY4kIBsbVyE57WvgpAWN+KOmv202jl1heGedgGTfXP4=";

        config.alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvKiIpWiFtr06dTkCo5MTej9+A8Ya7yRnHMa+f9L19Mb2bN6OAZgqmP8QZsEdcI1ez2Qwd/7ne6h/bAQD1Ca8pZyjbRLNMlMNfUoI59cJZZw0OuicddrnZRsZjJ0YvkkaMPYkNIc9aNiAAKNSB755h/P0QG7iJv4ADOSbgYBFi/GPlzFPoZSg6jvoX13yIbQQRts8MXFbbIri7YhrO5kd2cBd+8wmrP6XXUHc6InBF2bbBhdSyUh8gO3LJZ0sgibJ46S3KjyXCOBszlhyjmH4eIWYO3O4Y2JC1XDRZa2iGDOSV7hQOIZqKE4q044NL/nBwO+qWbQaQbj9wXjcn0lniwIDAQAB";

        return config;
    }

}


