package com.sky.admin.controller;

import com.sky.common.core.result.Result;
import com.sky.system.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Created by skyyemperor on 2021-08-31
 * Description :
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @GetMapping("/list")
    public Result getOrderInfo(@RequestParam("_uid_") Long userId) {
        return orderService.getOrderInfo(userId);
    }

    @PostMapping("/buy")
    public Result buyOrder(@RequestParam("_uid_") Long userId,
                           @RequestParam Long orderId) {
        return orderService.buyOrder(userId, orderId);
    }

    @PostMapping("/pay")
    public Result payOrder(@RequestParam Long orderId) {
        return orderService.payOrder(orderId);
    }

    @PostMapping("/refund")
    public Result refundOrder(@RequestParam("_uid_") Long userId,
                              @RequestParam Long orderId) {
        return orderService.refundOrder(userId, orderId);
    }

    @PostMapping("/change")
    public Result changeOrder(@RequestParam("_uid_") Long userId,
                              @RequestParam Long orderId,
                              @RequestParam Long ticketId) {
        return orderService.changeOrder(userId, orderId, ticketId);
    }

    @PreAuthorize("@pms.hasPerm('admin:order:*')")
    @GetMapping("/query")
    public Result queryOrderList() {
        return orderService.getOrderList();
    }


}
