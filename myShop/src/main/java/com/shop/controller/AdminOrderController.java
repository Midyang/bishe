package com.shop.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.shop.pojo.Orderitem;
import com.shop.pojo.Orders;
import com.shop.service.AdminOrderService;
import com.shop.utils.JsonUtil;
import com.shop.utils.Message;
import com.shop.utils.Page;
import com.shop.utils.ValidateValue;

@Controller
@RequestMapping("/orders")
public class AdminOrderController {

	@Autowired
	AdminOrderService service;
	
	
	@RequestMapping("/list")
	public String list(String mohuName,String currPage,String orderState,Model model ) {
		/*
		 *	 数据入口处理
		 * */
		mohuName=ValidateValue.isEmpty(mohuName)?"":mohuName.trim();
		int curPage=ValidateValue.isInteger(currPage)?Integer.valueOf(currPage.trim()):1;
		int state=ValidateValue.isInteger(orderState)?Integer.valueOf(orderState.trim()):0;//传入有值,或者无值的时候进行处理
		int pageSize=9;
		/*
		 * 	初始化判断条件map
		 * */
		Map<String, Object> map= new HashMap<String, Object>();
		
		map.put("name", mohuName);
		if(state != 0) {//值为0时,不需要添加该筛选条件
			map.put("state", state);
			model.addAttribute("list_state",state);
		}
		/*
		 * 	初始化分页
		 * */
		Page page = new Page(curPage,service.getCount(map),pageSize);
		/*
		 * 	分页设置
		 * */
		map.put("currPage", page.getStartIndex());
		map.put("pageSize", pageSize);
		/*
		 * 	获取列表信息
		 * */
		List<Orders> list = service.list(map);
	
		model.addAttribute("list",list);
		model.addAttribute("mohu",mohuName);
		model.addAttribute("page",page);
		model.addAttribute("fromLocation", "0");
		
		
		return "back_page/order/orders_List";
	}
	/*
	 *	 根据ID查询
	 * */
	@RequestMapping("/selectById")
	public String selectById(String id,Model model) {
		try {
			if(!ValidateValue.isEmpty(id)) {//不为空
				List<Orderitem> orderitem = service.listOfOrderItem(id);
				if( orderitem != null ) {
					model.addAttribute("list",orderitem);
					Orders order = service.getOrder(id);
					model.addAttribute("order",order);
					return "back_page/order/orders_List_item";
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.err.println("假装是日志 { order-操作动作 : 根据ID查询 ; 位置 : order/selectById; 类型 : 非正常操作 ; 处理 : 强制下线!");
		return "redirect:/login/exitLogin";
	}
	/*
	 *	 根据修改订单状态
	 * */
	@RequestMapping("/updateStateById")
	@ResponseBody
	public Message<Object> updateStateById(String id) {
		boolean flag = false;
		Message<Object> message = new Message<>();
		try {
			if(!ValidateValue.isEmpty(id)) {//不为空
				Orders order = service.getOrder(id);
				if( order != null ) {
					int state = order.getState();
					if(state<5) {
						order.setState(state+1);
						order.setWay("0");//支付方式暂时省略
						flag = service.updateState(order);
					}
				}
			}
		}catch (Exception e) {
			System.err.println("假装是日志 { order-操作动作 : 根据ID查询 ; 位置 : order/selectById; 类型 : 非正常操作 ; 处理 : 强制下线!");
			e.printStackTrace();
		}
		if(flag) {
			message.setCode("1");//1 成功状态代码
			message.setMsg(service.getStateInfo(id));
			return message;
		}else {
			message.setCode("0");//0 失败状态代码
			message.setMsg("修改订单状态失败!");
			return message;
		}
		
	}
	/*
	 * 详细查询订单信息
	 * fromLocation 来源于哪个页面	0：来源于了列表	1：来源于跟多查询页面
	 * 
	 * */
	@SuppressWarnings("unchecked")
	@RequestMapping("/searchOrder")
	public String searchOrder(Orders order, Double minPrice,Double maxPrice,String fromLocation,String currPage,HttpServletRequest request,HttpServletResponse respose, Model model){
		boolean returnFlag=false;
		Page page = null;
		List<Orders> list = null;
		HashMap<String,Object> parameter = null;
		try {
			if(fromLocation!=null&&"0".equals(fromLocation)){//从Cookie 中去参数 然后查询
				boolean flag=false;// false 未找到，true 找到了
				Cookie[] cookies = request.getCookies();
				for (Cookie cookie : cookies) {
					if("searchOrderParameter".equals(cookie.getName())){
						flag = true;
						String value = cookie.getValue();
						parameter=JsonUtil.byteStrToObj(value, HashMap.class);//得到参数
					}
				}
				if(!flag || parameter == null){//找了该cookie，并且拿到了参数 除外 的情况
					parameter=new HashMap<String, Object>();
				}
				//分页参数
				Integer curPage=1;
				if( ValidateValue.isInteger(currPage) ){
					curPage=Integer.valueOf(currPage);
				}
				page = new Page(curPage, service.getCount(parameter), 9);
				parameter.put("currPage", page.getStartIndex());
				parameter.put("pageSize", page.getSizePage());
				list = service.list(parameter);//查询结果
				returnFlag=true;
			}else if(fromLocation!=null&&"1".equals(fromLocation)){//查询的同时保存参数到Cookie
				parameter = new HashMap<String,Object>();//查询参数
				boolean flag = true;//是否要进行价格区间
				if(order != null){
					if(order.getId() != null && !"".equals(order.getId())){
						parameter.put("id",order.getId());//订单号
						flag = false;
					}else{//二选一，因为订单号为主见，优先订单号来查询
						if(order.getName() != null && !"".equals( order.getName())){
							parameter.put("name",order.getName());//收件人姓名
						}
						if(order.getPhone() !=null && !"".equals(order.getPhone())){
							parameter.put("phone",order.getPhone());//收件人手机号
						}
						if(order.getState() !=null && (order.getState()==0 || order.getState()==1 || order.getState()==2 || order.getState()==3 || order.getState()==4 || order.getState()==5)){
							parameter.put("state",order.getState());//订单状态
						}
					}
				}
				//价格区间
				if(flag){
					if(minPrice != null && minPrice>=0){
						parameter.put("minPrice", minPrice);
					}
					if(maxPrice != null && maxPrice>0){
						if(minPrice == null){
							parameter.put("maxPrice", maxPrice);
						}else if(maxPrice>minPrice){
							parameter.put("maxPrice", maxPrice);
						}
					}
				}
				//分页参数
				Integer curPage=1;
				if( ValidateValue.isInteger(currPage) ){
					curPage=Integer.valueOf(currPage);
				}
				page = new Page(curPage, service.getCount(parameter), 9);
				parameter.put("currPage", page.getStartIndex());
				parameter.put("pageSize", page.getSizePage());
				list = service.list(parameter);//查询结果
				// 参数放在cookie中
				String objToByteStr = JsonUtil.objToByteStr(parameter);
				Cookie newCookie= new Cookie("searchOrderParameter", objToByteStr);
				newCookie.setMaxAge(60*30);//生命为半个小时
				respose.addCookie(newCookie);
				returnFlag=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("   LOG: 当前查询订单信息失败");
		}
		System.out.println("   LOG: 当前订单查询参数为："+JSON.toJSONString(parameter));
		if(returnFlag){
			model.addAttribute("list",list);
			model.addAttribute("page",page);
			model.addAttribute("fromLocation", "1");// 0: 来源于普通查询	1：来源于跟多查询	
			return "back_page/order/orders_List";
		}else{
			return "redirect:/orders/list";
		}
	}
	@SuppressWarnings("unchecked")
	@RequestMapping("searchOrderBefore")
	public String searchOrderBefore(HttpServletRequest request,Model model){
		HashMap<String,Object> parameter = null;
		boolean flag=false;// false 未找到，true 找到了
		try {
			Cookie[] cookies = request.getCookies();
			for (Cookie cookie : cookies) {
				if("searchOrderParameter".equals(cookie.getName())){
					flag = true;
					String value = cookie.getValue();
					System.out.println("上次参数："+JSON.toJSONString(parameter));
					parameter=JsonUtil.byteStrToObj(value, HashMap.class);//得到参数
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("   LOG：获取查询订单参数失败");
		}
		if(!flag || parameter == null){//找了该cookie，并且拿到了参数 除外 的情况
			parameter=new HashMap<String, Object>();
		}
		model.addAttribute("parameter", parameter);
		return "back_page/order/searchOrderPage";
	}
}
