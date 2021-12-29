package com.sky.admin.controller;

import com.sky.common.core.result.Result;
import com.sky.common.util.TaskExecutorUtil;
import com.sky.system.data.po.Train;
import com.sky.system.service.SeatService;
import com.sky.system.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Created by skyyemperor on 2021-08-26
 * Description :
 */
@RestController
@RequestMapping("/train")
public class TrainController {

    @Autowired
    private TrainService trainService;

    @Autowired
    private SeatService seatService;

    @Autowired
    private TaskExecutorUtil<?> taskExecutorUtil;

    @PreAuthorize("@pms.hasPerm('admin:train:*')")
    @GetMapping("/list")
    public Result getTrainList() {
        return trainService.getTrainList();
    }

    @PreAuthorize("@pms.hasPerm('admin:train:*')")
    @PostMapping("/save")
    public Result saveOrUpdateTrain(@RequestBody Train train) {
        return trainService.saveTrain(train);
    }

    @PreAuthorize("@pms.hasPerm('admin:train:*')")
    @PostMapping("/delete")
    public Result deleteTrain(@RequestParam String trainId) {
        return trainService.deleteTrain(trainId);
    }

    @GetMapping("/info")
    public Result getTrainInfo(@RequestParam String trainId) {
        return trainService.getTrainInfo(trainId);
    }

    @PostMapping("/add")
    public Result addTrain(@RequestParam(required = false) String trainId) {
        if (trainId == null) {
            taskExecutorUtil.run(() -> {
                for (int i = 0; i < 500; i++)
                    if (i % 20 == 0)
                        addTrain0("G" + i);
            });
            taskExecutorUtil.run(() -> {
                for (int i = 0; i < 1000; i++)
                    if (i % 20 == 0)
                        addTrain0("K" + i);
            });
            taskExecutorUtil.run(() -> {
                for (int i = 1001; i < 2000; i++)
                    if (i % 20 == 0)
                        addTrain0("K" + i);
            });
        } else {
            addTrain0(trainId);
        }

        return Result.success();
    }

    private void addTrain0(String trainId) {
        try {
            trainService.addTrain(trainId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
