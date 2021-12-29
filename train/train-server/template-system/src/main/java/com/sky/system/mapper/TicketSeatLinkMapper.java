package com.sky.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sky.system.data.po.TicketSeatLink;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by skyyemperor on 2021-08-25
 * Description :
 */
@Mapper
public interface TicketSeatLinkMapper extends BaseMapper<TicketSeatLink> {

    TicketSeatLink selectOneTicket(Long ticketId);

    void update(@Param("ticketId") Long ticketId,
                @Param("seatId") Long seatId,
                @Param("sold") Boolean sold);



}