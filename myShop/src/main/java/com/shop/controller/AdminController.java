package com.shop.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.constant.AdminConstant;
import com.shop.constant.AdminMessage;
import com.shop.pojo.Admin;
import com.shop.pojo.AdminLimit;
import com.shop.pojo.Menu;
import com.shop.service.AdminLimitService;
import com.shop.service.AdminService;
import com.shop.service.MenuService;
import com.shop.utils.Page;
import com.shop.utils.ValidateValue;
/**
 * session中
 * admin_codeValid 验证码
 * admin 登录凭证(用户信息)
 **/
@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	@Autowired 
	private AdminLimitService adminLimitService; 
	@Autowired 
	private MenuService menuService;

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
	/*
	 * 响应登录后的首页
	 * */
	@RequestMapping("/index")
	public String indexPage() {
		return "/back_page/a_index";
	}
	
	 /*
	  * 账户列表查询
	  * */
	@RequestMapping("/list")
	public String list(Model model,String mohuName,String curPage){
		
		Integer currPage=ValidateValue.isInteger(curPage)?Integer.parseInt(curPage.trim()):1;
		mohuName=ValidateValue.isEmpty(mohuName)?"":mohuName.trim();
		
		int pageSize=AdminConstant.PAGE_SIZE;//每页显示的信息的数量
		PageHelper.startPage(currPage, pageSize);
		
		List<Admin> list=adminService.list(mohuName);
		
		PageInfo<Admin> pageInfo = new PageInfo<Admin>(list);
		Page page = new Page(currPage,(int)pageInfo.getTotal(),pageSize);
		
		model.addAttribute("list",list);
		model.addAttribute("mohu",mohuName);
		model.addAttribute("page",page);
		
		return "back_page/admin/AdminList";
	}
	/*
	 * 通过账号ID注销账号
	 * */
	@RequestMapping("/del")
	public String del(Integer id,String curPage,String mohuName){
		try {
			if(id != null) {
				if( adminService.del(id)>0 ) {
					return "redirect:/admin/list?mohuName="+mohuName+"&curPage="+curPage;
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.err.println("假装是日志 { product-操作动作 : 删除 ; 位置 : admin/del; 类型 : 非正常操作 ; 处理 : 强制下线!");
		return "redirect:/login/exitLogin";
	}
	/*
	 *	 通过账号ID查询账户信息
	 * */
	@RequestMapping("/selectById")
	public String selectById(Integer id,Model model){
		try{
			if(id != null) {
				Admin admin=adminService.getById(id);
				if(admin != null) {
					model.addAttribute("admin",admin);
					return "back_page/admin/AdminUpdate";
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.err.println("假装是日志 { admin-操作动作 : 修改信息前查询; 位置 : admin/selectById; 类型 : 非正常操作 ; 处理 : 强制下线!");
		return "redirect:/login/exitLogin";
	}
	/*
	 * 更改账号或密码
	 * 
	 * */
	@RequestMapping("/update")
	public String update(Admin admin,String comfirmWord, String oldUsername,String oldPassword,Model model ){
		
		
		if( !ValidateValue.valiUserName(oldUsername) || !ValidateValue.valiPWD(oldPassword) ) {
			System.err.println("假装是日志 { admin-操作动作 : 修改信息; 位置 : admin/update; 类型 : 非正常操作 ; 处理 : 强制下线!");
			return "redirect:/login/exitLogin";
		}
		
		if(!ValidateValue.valiUserName(admin.getUsername() ) ) {
			updateIsFailure(admin, oldUsername, oldPassword, "nameMsg",admin.getUsername()+":不合法!", model);
			return "back_page/admin/AdminUpdate";
		}
		
		if(!ValidateValue.valiPWD(admin.getPassword() ) ) {
			updateIsFailure(admin, oldUsername, oldPassword, "pwdMsg",admin.getPassword()+":不合法!", model);
			return "back_page/admin/AdminUpdate";
		}
		
		if(ValidateValue.isEmpty(comfirmWord)) {
			updateIsFailure(admin, oldUsername, oldPassword, "comfirmMsg","*只能为数字/字母/下划线!", model);
			return "back_page/admin/AdminUpdate";
		}else if( !admin.getPassword().equals(comfirmWord) ){
			updateIsFailure(admin, oldUsername, oldPassword, "pwdMsg","*两次密码不一致!", model);
			return "back_page/admin/AdminUpdate";
		}
		
		if(admin.getUsername().equals(oldUsername)){//账号不变动
				if (admin.getPassword().equals(oldPassword)) {//密码不变动
					updateIsFailure(admin, oldUsername, oldPassword, "nameMsg","*您未修改任何信息!如果不修改,请返回上一页!", model);
					return "back_page/admin/AdminUpdate";
				}else{//密码变动
					try {
						if( adminService.update(admin) > 0)
						return "redirect:/admin/list";
					}catch (Exception e){
						e.printStackTrace();
						System.err.println("假装是日志 { admin-操作动作 : 修改信息; 位置 : admin/update ; 类型 : 数据库操作捕捉到异常 ; 处理 : 返回到修改页面!");
					}
				}
		}else{//账号变动
				Admin value=adminService.getByUsername(admin.getUsername());
				if(value==null){//该用户名可用
					try {
						if( adminService.update(admin) > 0);//根据ID
						return "redirect:/admin/list";
					}catch (Exception e){
						e.printStackTrace();
						System.err.println("假装是日志 { admin-操作动作 : 修改信息; 位置 : admin/update ; 类型 : 数据库操作捕捉到异常 ; 处理 : 返回到修改页面!");
					}
				}else{//用户名不可用
					updateIsFailure(admin, oldUsername, oldPassword, "nameMsg",admin.getUsername()+" : 该用户名已经被占用!", model);
					return "back_page/admin/AdminUpdate";
				}
		}
		updateIsFailure(admin, oldUsername, oldPassword, "nameMsg","*修改失败!", model);
		return "back_page/admin/AdminUpdate";
	}
	/*
	 * 修改数据响应
	 * 
	 * */
	private void updateIsFailure(Admin admin, String oldUsername, String oldPassword,String msgName, String msg, Model model) {
		
		model.addAttribute(msgName,msg);
		if( admin != null ) {
			admin.setUsername(oldUsername);
			admin.setPassword(oldPassword);
			model.addAttribute(admin);
		}
	}
	/*
	 * 添加新的后台账号
	 * */
	@RequestMapping("/add")
	@ResponseBody
	public AdminMessage add(Admin admin,String comfirmWord,String codeValid,HttpServletRequest request) {
		
		if(!ValidateValue.valiUserName(admin.getUsername())) {
			return getMessage(1,"*账号不符合规则!",null);
		}
		if(!ValidateValue.valiPWD(admin.getPassword())) {
			return getMessage(2,"*密码不符合规则!",null);
		}
		if(ValidateValue.isEmpty(comfirmWord)) {
			return getMessage(3,"*密码不符合规则!",null);
		}else if( !admin.getPassword().equals(comfirmWord) ){
			return getMessage(2,"*密码不一致!",null);
		}
		if(!ValidateValue.valiCode(codeValid)) {
			return getMessage(0,"*验证码不符合规则!",null);
		}
		
		Object admin_codeValid=null;
		if( (admin_codeValid=request.getSession().getAttribute("admin_codeValid")) != null && ((String)admin_codeValid).equalsIgnoreCase(codeValid) ) {
			Admin locationAdmin=adminService.getByUsername(admin.getUsername());
			if(locationAdmin==null){//可用
				if ( adminService.add(admin) > 0 ) {
					return getMessage(5,"*注册成功!",null);
				}else {
					return getMessage(4,"*注册失败!",null);
				}
			}else{
				return getMessage(1,"*该用户名已经被占用!",null);
			}
			
		}else {
			return getMessage(0,"*验证码不正确!",null);
		}
	}
	@RequestMapping("/selectLimitById")
	public String selectLimitById(Integer targetAdminId, Model model,HttpServletRequest request){
		System.out.println("id:"+targetAdminId);
		Admin targetAdmin = adminService.getById(targetAdminId);
		if(targetAdmin == null){//如果不存在，当前操作为非法操作，直接退出
			request.setAttribute("exit", "<script type=\"text/javascript\">window.parent.location.href=\"login/exitLogin\";</script>");
			return "back_page/admin/AdminList";
		}
		model.addAttribute("targetAdmin", targetAdmin);
		try{
			if(targetAdminId != null) {
				AdminLimit adminLimit = adminLimitService.selectByUid(targetAdminId);
				if( adminLimit != null ) {
					model.addAttribute("menus", adminLimit.getLimits());
				}else{
					System.out.println("     查询条件为空！");
				}
				return "back_page/admin/limit";
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.err.println("假装是日志 { admin-操作动作 : 权限编辑; 位置 : admin/selectLimitById; 类型 : 非正常操作 ; 处理 : 强制下线!");
		return "redirect:/admin/list";
	}
	/**
	 * 权限信息的更改，或添加
	 **/
	@RequestMapping("/saveLimit")
	public String saveLimit(String menu,Integer targetAdminId,HttpServletRequest request,Model model){
		System.out.println("菜单传入值：menu-"+menu+"uid-"+targetAdminId);
		if( targetAdminId == null ){//非法操作直接退出
			request.setAttribute("exit", "<script type=\"text/javascript\">window.parent.location.href=\"login/exitLogin\";</script>");
			return "back_page/admin/limit";
		}
		Admin targetAdmin = adminService.getById(targetAdminId);
		if(targetAdmin == null){//如果不存在，当前操作为非法操作，直接退出
			request.setAttribute("exit", "<script type=\"text/javascript\">window.parent.location.href=\"login/exitLogin\";</script>");
			return "back_page/admin/limit";
		}
		AdminLimit adminLimit = adminLimitService.selectByUid(targetAdmin.getId());	//查询上传账号的权限
		/*
		 * 解析上送权限字符串,组装成对象，然后转成JSON
		 **/
		if( (menu == null || menu.length()<1)&&(adminLimit != null ) ){//这里做权限删除处理,为空的话不需要删除
			deleteLimitByUid(targetAdmin);
			System.out.println("   LOG:执行权限删除操作-"+targetAdmin.toString());
		}else{								//这里做权限更新，添加处理
			String[] limitStr= menu.trim().split(",");
			ArrayList<Integer> limitArray = new ArrayList<Integer>();
			for (String str : limitStr) {
				try{
					limitArray.add(Integer.parseInt(str));
				}catch(Exception e){
					System.err.println("	权限解析异常：");
					System.err.println("       当前曲线目录ID："+str);
					e.printStackTrace();
				}
			}
			if(limitArray.size()<1 && adminLimit != null){	//再次过滤，此处添加权限为空，做权限删除处理
				deleteLimitByUid(targetAdmin);
				System.out.println("   LOG:执行权限删除操作-"+targetAdmin.toString());
			}
			/*
			 * 进行排序
			 **/
			limitArray.sort(new Comparator<Integer>(){
				public int compare(Integer o1, Integer o2) {
					return o1-o2;
				}
			});
			/*	HashMap<String,Object>
			 * 	父节点：parent
			 * 	孩子节点：children
			 **/
			ArrayList<HashMap<String, Object>> menuList = new ArrayList<HashMap<String,Object>>();
			for(Integer limit:limitArray){
				try{
					Menu menuParent = menuService.getById(limit);
					if(menuParent == null){
						throw new Exception("	根据菜单Id查询结果为空！");
					}
					List<Menu> menuChildrens = menuService.listByParentMenuId(menuParent.getMenuId());
					if( menuChildrens ==null || menuChildrens.size()==0 ){
						continue;
					}
					HashMap<String,Object> lashBlock = new HashMap<String,Object>();
					lashBlock.put("parent", menuParent);
					lashBlock.put("children", menuChildrens);
					menuList.add(lashBlock);
				}catch(Exception e){
					System.err.println("	权限解析异常：");
					System.err.println("       当前曲线目录ID："+limit);
					e.printStackTrace();
				}
			}
			if(menuList.size()<1 && adminLimit != null){//进行更新删除操作！
				deleteLimitByUid(targetAdmin);
				System.out.println("   LOG:执行权限删除操作-"+targetAdmin.toString());
			}else{				//进行更新添加操作
				if( adminLimit != null) {	//更新操作
					adminLimit.setLimits(JSON.toJSONString(menuList));
					adminLimit.setUid(targetAdmin.getId());
					adminLimitService.updateLLimitById(adminLimit);
					System.out.println("   LOG:执行权限更新操作-"+JSON.toJSONString(menuList));
				}else{						//新建操作
					adminLimit = new AdminLimit();
					adminLimit.setLimits(JSON.toJSONString(menuList));
					adminLimit.setUid(targetAdmin.getId());
					adminLimitService.addLimitById(adminLimit);
					System.out.println("   LOG:执行权限添加操作-"+JSON.toJSONString(menuList));
				}
			}
		}//处理结束
		return "redirect:/admin/list";
	}
	/*
	 * 用于创建菜单并响应
	 **/
	@RequestMapping("/menu")
	public String loadMenu(HttpServletRequest request){
		Object obj = request.getSession().getAttribute("admin");
		if(obj == null || !(obj instanceof Admin)){
			return "/back_page/a_leftTree";
		}
		Admin admin = (Admin)obj;
		//通过当前用户ID 查询 用户权限
		AdminLimit adminLimit = adminLimitService.selectByUid(admin.getId());
//		/*
//		 * 解析权限
//		 **/
//		if(adminLimit == null){//暂时没配任何权限,不加载任何功能
//			return "/back_page/a_leftTree";
//		}
//		String[] limitStr= adminLimit.getLimits().trim().split(",");
//		ArrayList<Integer> limitArray = new ArrayList<Integer>();
//		for (String str : limitStr) {
//			try{
//				limitArray.add(Integer.parseInt(str));
//			}catch(Exception e){
//				System.err.println("	权限解析异常：");
//				System.err.println("       当前曲线目录ID："+str);
//				e.printStackTrace();
//			}
//		}
//		if(limitArray.size()<1){
//			return "/back_page/a_leftTree";
//		}
//		/*
//		 * 进行排序
//		 **/
//		limitArray.sort(new Comparator<Integer>(){
//			@Override
//			public int compare(Integer o1, Integer o2) {
//				return o1-o2;
//			}
//		});
//		/*	HashMap<String,Object>
//		 * 	父节点：parent
//		 * 	孩子节点：children
//		 **/
//		ArrayList<HashMap<String, Object>> menuList = new ArrayList<HashMap<String,Object>>();
//		for(Integer limit:limitArray){
//			try{
//				Menu menuParent = menuService.getById(limit);
//				if(menuParent == null){
//					throw new Exception("	根据菜单Id查询结果为空！");
//				}
//				List<Menu> menuChildrens = menuService.listByParentMenuId(menuParent.getMenuId());
//				if( menuChildrens ==null || menuChildrens.size()==0 ){
//					continue;
//				}
//				HashMap<String,Object> lashBlock = new HashMap<String,Object>();
//				lashBlock.put("parent", menuParent);
//				lashBlock.put("children", menuChildrens);
//				menuList.add(lashBlock);
//			}catch(Exception e){
//				System.err.println("	权限解析异常：");
//				System.err.println("       当前曲线目录ID："+limit);
//				e.printStackTrace();
//			}
//		}
//		if(menuList.size()>0){//当处理后的菜单不为空的时候，对转JSON后的字符进行处理 
//			request.setAttribute("menu", JSON.toJSONString(menuList));
//		}
//		System.out.println("菜单加载："+JSON.toJSONString( menuList ));
		if(adminLimit !=null ){
			request.setAttribute("menu", adminLimit.getLimits());
		}
		return "/back_page/a_leftTree";
	}
	//清除该用户的所有权限
	private void deleteLimitByUid(Admin admin) {
		try{
			adminLimitService.deleteLimitByUid(admin);
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("     删除用户权限失败！出现异常："+admin.getUsername());
		}
	}
}
