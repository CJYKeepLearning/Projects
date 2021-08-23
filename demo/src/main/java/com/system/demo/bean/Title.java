package com.system.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Title {
    private String id;
    private String titleName;
    private Long wage;
    private Long accumulationFund;
    private Long oldPension;
}
