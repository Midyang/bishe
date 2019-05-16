package com.shop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.constant.AdminMessage;
import com.shop.pojo.Admin;
import com.shop.service.AdminService;
import com.shop.utils.ValidateValue;

@Controller
@RequestMapping("/login")
public class LoginController {
	
		@Autowired
		private AdminService adminService;
		/*
		 * 登录 页面
		 * 
		 * */
		@RequestMapping("/adminLogin")
		@ResponseBody
		public AdminMessage getById(Admin admin,String codeValid,HttpServletRequest request) {
			
			if(!ValidateValue.valiUserName(admin.getUsername())) {
				return getMessage(1,"*账号不符合规则!",null);
			}

			if(!ValidateValue.valiPWD(admin.getPassword())) {
				return getMessage(2,"*密码不符合规则!",null);
			}
			
			if(!ValidateValue.valiCode(codeValid)) {
				return getMessage(0,"*验证码不符合规则!",null);
			}
			
			Object admin_codeValid=null;
			/*
			 *
			 * 系统戏中用于验证的 验证码不为空! 且  输入值与 系统中验证码的值 相等
			 * 
			 * */
			if( (admin_codeValid=request.getSession().getAttribute("admin_codeValid")) != null && ((String)admin_codeValid).equalsIgnoreCase(codeValid) ) {
				
					Admin locationAdmin=adminService.getByUsername(admin.getUsername());
					if( locationAdmin==null ){
						return getMessage(1,"*该用户名不存在!",null);
					}else if( ( admin.getUsername().equals( locationAdmin.getUsername() ) && admin.getPassword().equals( locationAdmin.getPassword() ) ) ){
						request.getSession().setAttribute("admin", locationAdmin);
						return getMessage(3,null,null);
					}else {
						return getMessage(1,"*账号或密码错误!",null);
					}
				}else {
					return getMessage(0,"*验证码不正确!",null);
				}
		}
		/*
		 * 获取登录信息
		 * 
		 * */
		@RequestMapping("/getAdminName")
		@ResponseBody
		public AdminMessage getAdminName(HttpServletRequest request) {
			
			Object object = request.getSession().getAttribute("admin");
			if( object != null) {
				Admin admin=(Admin)object;
				return getMessage(1,null,admin.getUsername() );
			}else {
				return getMessage(2, "请登录!" , null);
			}
		}
		/*
		 * 退出登录
		 * */
		@RequestMapping("/exitLogin")
		public String exitLogin(HttpServletRequest request) {
			HttpSession session = request.getSession();
			Object admin = session.getAttribute("admin");
			if (admin != null) {
				session.removeAttribute("admin");
				session.invalidate();//使该会话无效，然后解除绑定到该会话的任何对象的绑定
			}
			return "AdminLogin";
		}

		/*
		 * 
		 * 响应消息体
		 * 
		 * */
		private AdminMessage getMessage(int stateCode , String message , String adminName) {
			
			AdminMessage adminMessage = new AdminMessage(stateCode , message);
			if( adminName != null) {
				adminMessage.setUsername(adminName);
			}
			return adminMessage;
		}
}
