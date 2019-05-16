package com.shop.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.mapper.OrdersMapper;
import com.shop.mapper.ProductMapper;
import com.shop.pojo.Orderitem;
import com.shop.pojo.Orders;

@Service
@Transactional
public class AdminOrderService {
	@Autowired
	OrdersMapper ordersMapper;
	@Autowired
	ProductMapper productMapper;
	/**
	 * 	返回查询到的列表信息
	 **/
	public List<Orders> list(Map<String,Object> map){
		return ordersMapper.list(map);
	}
	/**
	 * getCount 根据Map中的值,返回查找到的记录数
	 **/
	public int getCount(Map<String,Object> map){
		return ordersMapper.getCount(map);
	}
	/**
	 * 	根据ID查询订单详细信息
	 **/
	public List<Orderitem> listOfOrderItem(String id) {
		List<Orderitem> items = ordersMapper.listOfOrderItem(id);
		if(items != null ) {
			for(Orderitem i:items) {
				i.setProduct( productMapper.selectByPrimaryKey(i.getProductId()) );
			}
		}
		return items;
	}
	/**
	 * 	根据ID查询订单详细信息
	 **/
	public Orders getOrder(String id) {
		
		return ordersMapper.selectByPrimaryKey(id);
	}
	/**
	 * 	修改订单状态
	 **/
	public boolean updateState(Orders order) {
		System.out.println("目前阶段测试用:"+order.toString());
		return ordersMapper.changeState(order)>0;
		//return ordersMapper.updateByPrimaryKey(order)>0;
		
	}
	/**
	 * 
	 * 	根据ID查询状态信息
	 **/
	public String getStateInfo(String id) {
		return ordersMapper.getStateInfo(id);
	}
}
