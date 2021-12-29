package com.sky.admin.controller;

import com.sky.common.core.result.Result;
import com.sky.system.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Created by skyyemperor on 2021-09-07
 * Description :
 */
@RestController
@RequestMapping("/seat")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @PreAuthorize("@pms.hasPerm('admin:train:*')")
    @PostMapping("/save")
    public Result saveSeat(@RequestParam String trainId,
                           @RequestParam Integer carriage,
                           @RequestParam Integer seatNum,
                           @RequestParam String seatType) {
        return seatService.saveSeat(trainId, carriage, seatNum, seatType);
    }

    @PreAuthorize("@pms.hasPerm('admin:train:*')")
    @GetMapping("/list")
    public Result getSeatList(@RequestParam String trainId,
                              @RequestParam Integer carriage) {
        return seatService.getSeatList(trainId, carriage);
    }

}
