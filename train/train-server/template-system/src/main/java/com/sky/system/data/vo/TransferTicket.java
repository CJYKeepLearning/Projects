package com.sky.system.data.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Created by skyyemperor on 2021-08-26
 * Description :
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferTicket {

    private String firstTrainId;

    private String secondTrainId;

    private String secondStartStation;

    private LocalDate secondStartDay;

}
