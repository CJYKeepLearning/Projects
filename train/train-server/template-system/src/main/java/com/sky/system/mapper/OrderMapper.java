package com.sky.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sky.system.data.po.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by skyyemperor on 2021-08-31
 * Description :
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    List<Order> selectByUserId(Long userId);

    Order selectLatestOrder(@Param("userId") Long userId, @Param("ticketId") Long ticketId);

}