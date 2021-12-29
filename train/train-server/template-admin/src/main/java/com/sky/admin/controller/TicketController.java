package com.sky.admin.controller;

import com.sky.common.core.result.CommonError;
import com.sky.common.core.result.Result;
import com.sky.system.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

/**
 * Created by skyyemperor on 2021-08-26
 * Description :
 */
@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/info")
    public Result getTicketInfo(@RequestParam Long ticketId) {
        return ticketService.getTicketInfo(ticketId);
    }

    @GetMapping("/search")
    public Result searchTicket(@RequestParam String startCity,
                               @RequestParam String endCity,
                               @RequestParam Long day,
                               @RequestParam(defaultValue = "0") Integer transfer) {
        LocalDate localDate = Instant.ofEpochMilli(day).atZone(ZoneOffset.ofHours(8)).toLocalDate();
        return ticketService.searchTicket(startCity, endCity, localDate, transfer);
    }

    @GetMapping("/city")
    public Result getCityInfo(@RequestParam String city) {
        return ticketService.getCityInfo(city);
    }

    @PostMapping("/init")
    public Result initTicketPerDay(@RequestParam Long day) {
        ticketService.initTicketPerDay(Instant.ofEpochMilli(day).atZone(ZoneOffset.ofHours(8)).toLocalDate());
        return Result.success();
    }

    @PostMapping("/buy")
    public Result buyTicket(@RequestParam("_uid_") Long userId,
                            @RequestParam Long ticketId) {
        return ticketService.buyTicket(userId, ticketId, false);
    }

}
