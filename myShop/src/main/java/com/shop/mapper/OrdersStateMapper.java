package com.shop.mapper;

import com.shop.pojo.OrdersState;
import com.shop.pojo.OrdersStateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrdersStateMapper {
    int countByExample(OrdersStateExample example);

    int deleteByExample(OrdersStateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OrdersState record);

    int insertSelective(OrdersState record);

    List<OrdersState> selectByExample(OrdersStateExample example);

    OrdersState selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OrdersState record, @Param("example") OrdersStateExample example);

    int updateByExample(@Param("record") OrdersState record, @Param("example") OrdersStateExample example);

    int updateByPrimaryKeySelective(OrdersState record);

    int updateByPrimaryKey(OrdersState record);
}