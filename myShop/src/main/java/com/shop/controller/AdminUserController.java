package com.shop.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.pojo.User;
import com.shop.service.AdminUserService;
import com.shop.utils.JsonUtil;
import com.shop.utils.Page;
import com.shop.utils.ValidateValue;

@Controller
@RequestMapping("/user")
public class AdminUserController {

	@Autowired
	AdminUserService service;
	
	
	@RequestMapping("/list")
	public String list(String mohuName,String currPage,Model model ) {
		
		mohuName=ValidateValue.isEmpty(mohuName)?"":mohuName.trim();
		int curPage=ValidateValue.isInteger(currPage)?Integer.valueOf(currPage.trim()):1;
		int pageSize=9;

		List<User> list = service.list(mohuName);
		PageHelper.startPage(curPage, pageSize);
		PageInfo<User> pageInfo = new PageInfo<User>(list);
		Page page = new Page(curPage,(int)pageInfo.getTotal(),pageSize);
		
		model.addAttribute("list",list);
		model.addAttribute("mohu",mohuName);
		model.addAttribute("page",page);
		model.addAttribute("fromLocation", "0");
		return "back_page/user/user_List";
	}
	@RequestMapping("/del")
	public String del(Integer id,String mohuName,String currPage) {
		
		try {
			if(id != null) {
				if( service.del(id)>0 ) {
					return "redirect:/user/list?mohuName="+mohuName+"&curPage="+currPage;
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.err.println("假装是日志 { user-操作动作 : 删除 ; 位置 : user/del; 类型 : 非正常操作 ; 处理 : 强制下线!");
		return "redirect:/login/exitLogin";
	}
	@SuppressWarnings("unchecked")
	@RequestMapping("/searchUser")
	public String searchOrder(User user,String fromLocation,String currPage,HttpServletRequest request,HttpServletResponse respose, Model model){
		HashMap<String, Object> parameter = null;//查询参数
		List<User> list = null;					//查询结果
		Page page = null;						//分页对象
		boolean returnFlag = false;
		try {
			if(fromLocation != null && "1".equals(fromLocation)){//来源于详情查询页面
				 parameter = new HashMap<String,Object>();
				 if(user != null){
					 if(user.getUsername() != null && !"".equals(user.getUsername())){//账号,优先级最高
						 parameter.put("username", user.getUsername());
					 }else{
						 if(user.getName()!=null && !"".equals(user.getName())){
							 parameter.put("name", user.getName());
						 }
						 if(user.getPhone()!=null && !"".equals(user.getPhone())){
							 parameter.put("phone", user.getPhone());
						 }
						 if(user.getEmail()!=null && !"".equals(user.getEmail())){
							 parameter.put("email", user.getEmail());
						 }
						 if(user.getAddr()!=null && !"".equals(user.getAddr())){
							 parameter.put("addr", user.getAddr());
						 }
					 }
				 }
				Integer curPage = 1;
				if(ValidateValue.isInteger(currPage)){
					curPage=Integer.valueOf(currPage);
				}
				page = new Page(curPage, service.searchOrderCount(parameter), 10);
				
				parameter.put("currPage", page.getStartIndex());
				parameter.put("pageSize", page.getSizePage());
				list = service.searchOrder(parameter);
				
				String objToByteStr = JsonUtil.objToByteStr(parameter);
				
				Cookie newcookie = new Cookie("searchUserParameter", objToByteStr);
				newcookie.setMaxAge(60*30);
				respose.addCookie(newcookie);
				returnFlag = true ;
			}else if(fromLocation != null && "0".equals(fromLocation)){
				boolean flag=false;// false 未找到，true 找到了
				Cookie[] cookies = request.getCookies();
				for (Cookie cookie : cookies) {
					if("searchUserParameter".equals(cookie.getName())){
						flag = true;
						String value = cookie.getValue();
						parameter=JsonUtil.byteStrToObj(value, HashMap.class);//得到参数
					}
				}
				if(!flag || parameter == null){//找了该cookie，并且拿到了参数 除外 的情况
					parameter=new HashMap<String, Object>();
				}
				Integer curPage = 1;
				if(ValidateValue.isInteger(currPage)){
					curPage=Integer.valueOf(currPage);
				}
				page = new Page(curPage, service.searchOrderCount(parameter), 10);
				
				parameter.put("currPage", page.getStartIndex());
				parameter.put("pageSize", page.getSizePage());
				list = service.searchOrder(parameter);
				returnFlag = true ;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("   LOG: 查询用户信息失败！");
		}
		System.out.println("  当前查询客户参数："+JSON.toJSONString(parameter));
		if(returnFlag){
			model.addAttribute("list",list);
			model.addAttribute("fromLocation", "1");
			model.addAttribute("page",page);
			return "back_page/user/user_List";
		}
		return "redirect:/user/list";
		
	}
	@SuppressWarnings("unchecked")
	@RequestMapping("/searchUserBefore")
	public String searchUserBefore(HttpServletRequest request,HttpServletResponse respose, Model model){
		HashMap<String,Object> parameter=null;
		boolean flag=false;// false 未找到，true 找到了
		try {
			Cookie[] cookies = request.getCookies();
			for (Cookie cookie : cookies) {
				if("searchUserParameter".equals(cookie.getName())){
					flag = true;
					String value = cookie.getValue();
					parameter=JsonUtil.byteStrToObj(value, HashMap.class);//得到参数
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("   LOG:跳转到用户详细查询页，获取查询参数失败！");
		}
		System.out.println("    LOG:当前获取到的参数："+JSON.toJSONString(parameter));
		if(!flag || parameter == null){//找了该cookie，并且拿到了参数 除外 的情况
			parameter=new HashMap<String, Object>();
		}
		model.addAttribute("parameter", parameter);
		return "back_page/user/searchUserPage";
	}
	
}
