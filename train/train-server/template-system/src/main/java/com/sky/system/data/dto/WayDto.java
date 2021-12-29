package com.sky.system.data.dto;

import com.sky.system.data.po.Ticket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by skyyemperor on 2021-09-05
 * Description :
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WayDto {

    private List<TicketDto> tickets;

    private Integer transfer;

}
