package com.sky.system.data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sky.system.data.po.Ticket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by skyyemperor on 2021-08-26
 * Description :
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDto {

    private List<Ticket> items;

    public static TicketDto fromTicket(Ticket t) {
        return new TicketDto(new ArrayList<>(Collections.singletonList(t)));
    }

    public void addTicket(Ticket ticket) {
        items.add(ticket);
    }


}
