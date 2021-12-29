package com.sky.common.core.exception.ticket;

import com.sky.common.core.exception.BaseException;
import com.sky.common.core.result.error.TicketError;

/**
 * Created by skyyemperor on 2021-08-30
 * Description :
 */
public class TicketSoldOutError extends BaseException {
    public TicketSoldOutError() {
        super(TicketError.TICKET_SOLD_OUT);
    }
}
