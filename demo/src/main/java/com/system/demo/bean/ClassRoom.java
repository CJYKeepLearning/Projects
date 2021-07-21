package com.system.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClassRoom {
    private Long id;
    private Long spNo;
    private Long peopleCount;
}
