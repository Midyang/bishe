package com.shop.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.shop.mapper.CategoryMapper;
import com.shop.mapper.OrderitemMapper;
import com.shop.mapper.OrdersMapper;
import com.shop.mapper.ProductMapper;
import com.shop.mapper.SecondcategoryMapper;
import com.shop.mapper.UserMapper;
import com.shop.pojo.Category;
import com.shop.pojo.CategoryExample;
import com.shop.pojo.CategoryExample.Criteria;
import com.shop.pojo.Orderitem;
import com.shop.pojo.OrderitemExample;
import com.shop.pojo.Orders;
import com.shop.pojo.OrdersExample;
import com.shop.pojo.Product;
import com.shop.pojo.ProductExample;
import com.shop.pojo.Secondcategory;
import com.shop.pojo.SecondcategoryExample;
import com.shop.pojo.User;

@Service
@Transactional
public class UserShopService {
	
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private SecondcategoryMapper secondcategoryMapper;
	@Autowired
	private OrdersMapper orderMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private OrderitemMapper orderitemMapper;
	
	/**
	 * 	返回所有的一级分类信息
	 * */
	public List<Category> getOneClass() {
		CategoryExample example = new CategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andNameIsNotNull();
		return categoryMapper.selectByExample(example);
	}
	/**
	 * 	返回所有的的一级分类对应的二级分类信息
	 * */
	public List<Secondcategory> getTwoClass(Category one) {
		SecondcategoryExample example = new SecondcategoryExample();
		com.shop.pojo.SecondcategoryExample.Criteria criteria = example.createCriteria();
		criteria.andCidEqualTo(one.getId());
		return secondcategoryMapper.selectByExample(example);
	}
	/**
	 * 	根据给的二级分类信息，找寻商品
	 * */
	public List<Product> getProductInfo(Secondcategory two) {
		ProductExample example = new ProductExample();
		com.shop.pojo.ProductExample.Criteria criteria = example.createCriteria();
		criteria.andScidEqualTo(two.getId());
		return productMapper.selectByExample(example);
	}
	/**
	 *	 根据给定的二级分类信息，返回该分类下的所有商品数量
	 * */
	public int getProductNum(Secondcategory secondcategory) {
		ProductExample example = new  ProductExample();
		com.shop.pojo.ProductExample.Criteria criteria = example.createCriteria();
		criteria.andScidEqualTo(secondcategory.getId());
		return productMapper.countByExample(example);
	}
	/**
	 * 	根据Id 查找商品
	 * */
	public Product productInfoById(String shopId) {
		try {
			int id=Integer.parseInt(shopId);
			return productMapper.selectByPrimaryKey(id);
		}catch (Exception e) {
			System.err.println("假装是日志 { UserShopService类productInfoById方法-操作动作 : 根据ID查找商品，然后显示到页面 ; 位置 :	; 类型 : 可能为数据类型转化异常或查询数据库报错 ; 处理 : 提示日志!");
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 	根据Id 查找商品
	 * */
	public Product productInfoById(Integer shopId) {
		try {
			return productMapper.selectByPrimaryKey(shopId);
		}catch (Exception e) {
			System.err.println("假装是日志 { UserShopService类productInfoById方法-操作动作 : 根据ID查找商品，然后显示到页面 ; 位置 :	; 类型 : 可能为数据类型转化异常或查询数据库报错 ; 处理 : 提示日志!");
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 	根据传入的参数，查找商品
	 **/
	public List<Product> getProductList(Map<String, Object> parameter) {
		try {
			return productMapper.getList(parameter);
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("UserShopService类/getProductList()方法	异常");
			return null;
		}
		
	}
	/**
	 * 	根据传入的参数，查找商品记录数
	 **/
	public int getProductCount(Map<String, Object> parameter) {
		try {
			return productMapper.listCount(parameter);
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("UserShopService类/getProductCount()方法	异常");
			return 0;
		}
		
	}
	/**
	 * 	验证该商品的ID是否存在
	 * 	存在 ：true
	 * 	不存在：false
	 **/
	public boolean isProductID(Integer pdtID) {
		return productMapper.selectByPrimaryKey(pdtID)==null?false:true;
	}
	/**
	  *  根据用户ID 查询该用户的所有订单信息
	 * */
	public List<Orders> getOrderList(Map<String, Integer> map, Model model) {
		return orderMapper.getPointOrder(map);
	}
	/**
	  * 测试使用 
	 **/
	public User getUser(int id) {
		return userMapper.selectByPrimaryKey(id);
	}
	/**
	  *  获取添加下订单数量
	 * */
	public int getOrderCount(Integer id, int i, int j) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("valOne", id);
		map.put("valTwo", i);
		map.put("valThr", j);
		return orderMapper.getPointOrderCount(map);
	}
	/**
	 * 查询订单项详情信息 
	 **/
	public List<Orderitem> selectOrderItemInfo(String uuid) {
		OrderitemExample example = new OrderitemExample();
		com.shop.pojo.OrderitemExample.Criteria criteria = example.createCriteria();
		criteria.andOrderIdEqualTo(uuid);
		List<Orderitem> list = orderitemMapper.selectByExample(example );
		for(Orderitem item:list) {
			item.setProduct(productMapper.selectByPrimaryKey(item.getProductId()));
		}
		return list;
	}
	/**
	  * 查询订单详情信息 
	 **/
	public Orders selectOrderInfo(String uuid) {
		return orderMapper.selectByPrimaryKey(uuid);
	}
	/**
	 *保存订单信息 
	 **/
	public void addOrderInfo(Orders order, List<Orderitem> orderItem) {
		orderMapper.insert(order);
		for(Orderitem item:orderItem) {	//订单项赋值订单号
			item.setOrderId(order.getId());
			item.setId(null);
			orderitemMapper.insert(item);
		}
	}
	//获取购物车中订单项的信息
	public List<Orderitem> getShopCarItm(User user) {
		String orderID = addOrGetOrderId(user);
		OrderitemExample example2 = new OrderitemExample();
		com.shop.pojo.OrderitemExample.Criteria createCriteria2 = example2.createCriteria();
		createCriteria2.andOrderIdEqualTo(orderID);
		List<Orderitem> selectByExample2 = orderitemMapper.selectByExample(example2 );
		return selectByExample2 == null?null:(selectByExample2.size()<1?null:selectByExample2);
	}
	//获取该用户的购物车的订单ID
	public String addOrGetOrderId(User user) {
		if(user == null) return null;
		String orderID=null;
		OrdersExample example = new OrdersExample();
		com.shop.pojo.OrdersExample.Criteria createCriteria = example.createCriteria();
		createCriteria.andUidEqualTo(user.getId());
		createCriteria.andStateEqualTo(0);
		List<Orders> selectByExample = orderMapper.selectByExample(example );
		if( selectByExample==null || selectByExample.size()<1){//第一次初始化
			Orders order = new Orders();
			orderID = UUID.randomUUID().toString();
			order.setId(orderID);
			order.setUid(user.getId());
			order.setState(6);
			order.setAddr(user.getAddr());
			order.setName(user.getName());
			order.setOrdertime(new Date());
			order.setPhone(user.getPhone());
			order.setTotal(0.0);
			order.setWay("购物车信息");
		}else{
			orderID = selectByExample.get(0).getId();
		}
		return orderID;
	}
	//更新购物车订单项
	public void updateShopCarItem(User user, Orderitem orderItm) {
		if(user == null || orderItm ==null || orderItm.getProductId()==null)return;
		OrderitemExample example=new OrderitemExample();
		example.createCriteria().andOrderIdEqualTo(addOrGetOrderId(user)).andProductIdEqualTo(orderItm.getProductId());
		orderitemMapper.updateByExample(orderItm, example);
	}
	//购物车项更新到数据库
	public void updateShopCarItem(Orderitem orderItm) {
		if(orderItm==null || orderItm.getId()==null|| orderItm.getProductId()==null || orderItm.getOrderId()==null) return;
		orderitemMapper.updateByPrimaryKey(orderItm);
	}
	//添加购物车项/订单项
	public void addShopCarItem(Orderitem orderItm) {
		if(orderItm==null || orderItm.getProductId()==null || orderItm.getOrderId()==null) return;
		orderitemMapper.insert(orderItm);
	}
	public void addShopCarItem(User user, Orderitem orderItm) {
		orderItm.setOrderId(addOrGetOrderId(user));
		orderitemMapper.insert(orderItm);
	}
	//删除指定购物车订单项
	public void deleteShopCarItem(User user, Orderitem orderItm) {
		if(user == null || orderItm ==null)return;
		System.out.println("开始删除：");
		OrderitemExample example=new OrderitemExample();
		example.createCriteria().andOrderIdEqualTo(addOrGetOrderId(user)).andProductIdEqualTo(orderItm.getProductId());
		orderitemMapper.deleteByExample(example);
	}
	/**
	 *根据订单项中的ID 删除订单项 
	 **/
	public void deleteShopCarItem(Orderitem orderItm) {
		if(orderItm==null || orderItm.getId()==null) return;
		orderitemMapper.deleteByPrimaryKey(orderItm.getId());
	}
	/**
	 * 
	 * 检索购物车中同一种商品
	 **/
	public List<Orderitem> selectOrderInfo(Orderitem orderItem,String orderId) {
		if( orderId==null || orderItem==null || orderItem.getProductId()==null ) return null;
		OrderitemExample example2 = new OrderitemExample();
		com.shop.pojo.OrderitemExample.Criteria createCriteria2 = example2.createCriteria();
		createCriteria2.andOrderIdEqualTo(orderId).andProductIdEqualTo(orderItem.getProductId());
		return orderitemMapper.selectByExample(example2);
	}
	/**
	 * 清空该用户的购物车 
	 **/
	public void delShopCarItemAll(User user) {
		if(user == null) return;
		OrderitemExample example2 = new OrderitemExample();
		com.shop.pojo.OrderitemExample.Criteria createCriteria2 = example2.createCriteria();
		createCriteria2.andOrderIdEqualTo(addOrGetOrderId(user));
		List<Orderitem> orderItems = orderitemMapper.selectByExample(example2);
		if(orderItems == null || orderItems.size()<1) return ;
		for (Orderitem orderitem : orderItems) {
			try{
				orderitemMapper.deleteByPrimaryKey(orderitem.getId());
			}catch(Exception exception){
				//不做任何处理
			}
		}
	}
	/**
	 *根据订单Id 更新订单信息 
	 **/
	public void updateOrderByKey(Orders orderInfo) {
		if(orderInfo == null || orderInfo.getId() == null) return;
		orderMapper.updateByPrimaryKey(orderInfo);
	}
	
}
