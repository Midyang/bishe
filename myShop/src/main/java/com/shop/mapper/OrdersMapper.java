package com.shop.mapper;

import com.shop.pojo.Orderitem;
import com.shop.pojo.Orders;
import com.shop.pojo.OrdersExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface OrdersMapper {
    int countByExample(OrdersExample example);

    int deleteByExample(OrdersExample example);

    int deleteByPrimaryKey(String id);

    int insert(Orders record);

    int insertSelective(Orders record);

    List<Orders> selectByExample(OrdersExample example);
    
    List<Orders> list(Map<String, Object> map);

    Orders selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Orders record, @Param("example") OrdersExample example);

    int updateByExample(@Param("record") Orders record, @Param("example") OrdersExample example);

    int updateByPrimaryKeySelective(Orders record);

    int updateByPrimaryKey(Orders record);
    /**
     * 	获取总数
     * */
	int getCount(Map<String, Object> map);

	List<Orderitem> listOfOrderItem(String id);

	String getStateInfo(String id);
	
	int changeState(Orders o);
	
	List<Orders> getPointOrder(Map<String, Integer> map);
	
	int getPointOrderCount(Map<String, Integer> map);
}