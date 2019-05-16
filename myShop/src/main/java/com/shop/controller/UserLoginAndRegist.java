package com.shop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.pojo.User;
import com.shop.service.UserService;
import com.shop.utils.Message;
import com.shop.utils.ValidateValue;

@Controller
@RequestMapping("/userLR")
public class UserLoginAndRegist {
	@Autowired
	private UserService userService;
	
	@RequestMapping("/userRegist")
	@ResponseBody
	public Message<Object> userRegist(User user, String yzm, String passwordTwo ,HttpServletRequest request) {
		Message<Object> msg = new Message<Object>("A0");//约定A0的为注册成功的状态码.
		//验证码
		if( !ValidateValue.valiCode(yzm) ){
			msg.setCode("A1");//约定A1的为验证码格式不通过.
			msg.setMsg("*请输入四位有效字符。");
			return msg;
		}
		//账号
		if( !ValidateValue.valiUserName(user.getUsername()) ) {
			msg.setCode("A2");//约定A2的为账号格式不通过
			msg.setMsg("*账号格式不正确!");
			return msg;
		}
		//密码
		if( !ValidateValue.valiPWD(user.getPassword()) ) {
			msg.setCode("A3");//约定A3的为密码格式不通过
			msg.setMsg("*密码格式不正确!");
			return msg;
		}
		//确认密码
		if( !ValidateValue.valiPWD(user.getPassword()) ) {
			msg.setCode("A4");//约定A4的确认密码格式不通过
			msg.setMsg("*确认密码格式不正确!");
			return msg;
		}
		//姓名
		if( ValidateValue.isEmpty(user.getName()) ) {
			msg.setCode("A5");//约定A5的申请人姓名不能为空
			msg.setMsg("*姓名不能为空!");
			return msg;
		}
		//手机号
		if( !ValidateValue.valiPhone(user.getPhone()) ) {
			msg.setCode("A6");//约定A6的申请人手机号格式不通过
			msg.setMsg("*手机号码格式不正确!");
			return msg;
		}
		//地址
		if( ValidateValue.isEmpty(user.getAddr()) ) {
			msg.setCode("A7");//约定A7的地址不能为空
			msg.setMsg("*地址不能为空!");
			return msg;
		}
		//邮箱
		if( !ValidateValue.valiEmail(user.getEmail()) ) {
			msg.setCode("A8");//约定A8的邮箱格式不正确
			msg.setMsg("*邮箱格式不能正确!");
			return msg;
		}
		//验证验证码是否正确
		Object admin_codeValid=null;
		if( (admin_codeValid=request.getSession().getAttribute("admin_codeValid")) != null && ((String)admin_codeValid).equalsIgnoreCase(yzm) ) {
			
			//验证账号是否被使用
			if(userService.isExistUser(user.getUsername())) {
				msg.setCode("A9");//约定A9 该用户名已经被使用
				msg.setMsg("*该用户名已经被使用了!");
			}
			//验证两次密码是否相等
			if( !passwordTwo.equals(user.getPassword()) ) {
				msg.setCode("A10");//约定A10 为 验证两次密码是否相等
				msg.setMsg("*两次密码不一致!");
				return msg;
			}
			user.setState(1);//新申请的账号,给定状态码
			//验证是否添加成功
			System.out.println(user.toString());
			if( !userService.addRegist(user) ) {
				msg.setCode("A11");//约定A11 为遇到数据库异常添加失败
				msg.setMsg("*添加失败!请检查你的输入信息!");
				return msg;
			}
		}else {
			msg.setCode("A12");//约定A12 为验证码不正确
			msg.setMsg("*验证码输入错误!");
			return msg;
		}
		
		return msg;//当前返回为注册成功
	}
	
	@ResponseBody
	@RequestMapping("/isExistUser")
	public Message<Object> isExistUser(String username){
		System.out.println("public Message<Object> isExistUser:"+username);
		Message<Object> msg = new Message<Object>("B0");
		
		if( !ValidateValue.valiUserName(username) ) {
			msg.setCode("B1");//约定B1 为账号格式不正确
			msg.setMsg("*账号格式不正确!");
			return msg;
		}
		if( userService.isExistUser(username) ) {
			msg.setCode("B2");//约定B2 为 该用户已经存在了
			msg.setMsg("*该用户名已经被使用了!");
			return msg;
		}
		msg.setMsg("*可以使用!");
		return msg;
	}
	
	@ResponseBody
	@RequestMapping("/userLogin")
	public Message<Object> login( User user, String yzm, HttpServletRequest request ){
		Message<Object> msg = new Message<Object>("A0");//约定A0的为注册成功的状态码.
		//验证码
		if( !ValidateValue.valiCode(yzm) ){
			msg.setCode("A1");//约定A1的为验证码格式不通过.
			msg.setMsg("*请输入四位有效字符");
			return msg;
		}
		//账号
		if( !ValidateValue.valiUserName(user.getUsername()) ) {
			msg.setCode("A2");//约定A2的为账号格式不通过
			msg.setMsg("*账号格式不正确!");
			return msg;
		}
		//密码
		if( !ValidateValue.valiPWD(user.getPassword()) ) {
			msg.setCode("A3");//约定A3的为密码格式不通过
			msg.setMsg("*密码格式不正确!");
			return msg;
		}
		
		//验证验证码是否正确
		Object admin_codeValid=null;
		if( (admin_codeValid=request.getSession().getAttribute("admin_codeValid")) != null && ((String)admin_codeValid).equalsIgnoreCase(yzm) ) {
			//验证账号是否存在
			User users = userService.selectByUserName(user.getUsername());
			if( users == null ) {
				msg.setCode("A4");//约定A4 为 验证账号是否存在
				msg.setMsg("*该用户名不存在! ");
				return msg;
			}
			//验证密码是否相等
			if( !users.getPassword().equals(user.getPassword()) ) {
				msg.setCode("A5");//约定A5  密码是否正确
				msg.setMsg("*密码或账号错误!");
				return msg;
			}
			request.getSession().setAttribute("user", users);
		}else {
			msg.setCode("A12");//约定A12 为验证码不正确
			msg.setMsg("*验证码错误!.");
			return msg;
		}
		return msg;
	}
	@RequestMapping("/exitLogin")
	public String exitLogin(HttpServletRequest request){
		HttpSession session = request.getSession();
		Object admin = session.getAttribute("user");
		if (admin != null) {
			session.removeAttribute("user");
			session.invalidate();//使该会话无效，然后解除绑定到该会话的任何对象的绑定
		}
		return "redirect:/userShop/index";
	}
}
