package com.shop.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.constant.AdminConstant;
import com.shop.pojo.Menu;
import com.shop.service.MenuService;
import com.shop.utils.Message;
import com.shop.utils.Page;
import com.shop.utils.ValidateValue;
/**
 * 权限管理
 **/
@Controller
@RequestMapping("/menu")
public class MenuController {
	
	@Autowired
	private MenuService menuService;

	
	 /*
	  * 账户列表查询
	  * */
	@RequestMapping("/list")
	public String list(Model model,String mohuName,String curPage){
		
		Integer currPage=ValidateValue.isInteger(curPage)?Integer.parseInt(curPage.trim()):1;
		mohuName=ValidateValue.isEmpty(mohuName)?"":mohuName.trim();
		
		int pageSize=AdminConstant.PAGE_SIZE;//每页显示的信息的数量
		PageHelper.startPage(currPage, pageSize);
		
		List<Menu> list=menuService.listBymenuName(mohuName);
		
		PageInfo<Menu> pageInfo = new PageInfo<Menu>(list);
		Page page = new Page(currPage,(int)pageInfo.getTotal(),pageSize);
		
		model.addAttribute("list",list);
		model.addAttribute("mohu",mohuName);
		model.addAttribute("page",page);
		
		return "back_page/menu/menu_List";
	}
	/**
	 *添加前查询 
	 **/
	@RequestMapping("addBeforeSel")
	public String addBeforeSel(Model model){
		getBeforeSel(model);
		return "back_page/menu/menu_Add";
	}
	/*
	 * 根据上送id删除目录项
	 * */
	@RequestMapping("deleteById")
	public String deleteById(Integer id,String mohuName,String curPage,Model model){
		try {
			if(id != null) {
				if(id>15){
					if( menuService.deleteById(id)>0 ) {
						System.out.println("开始执行删除");
						return "redirect:/menu/list?mohuName="+mohuName+"&curPage="+curPage;
					}else{
						System.out.println("执行删除失败");
						model.addAttribute("exit", "<script type=\"text/javascript\">alert(\"删除失败！\");location.href=\"menu/list?curPage="+curPage+"&mohuName="+mohuName+"\";</script>");
						return "back_page/menu/menu_List";
					}
				}else{
					System.out.println("不允许删除目录id为"+id);
					model.addAttribute("exit", "<script type=\"text/javascript\">alert(\"系统目录不允许删除！\");location.href=\"menu/list?curPage="+curPage+"&mohuName="+mohuName+"\";</script>");
					return "back_page/menu/menu_List";
				}
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.err.println("假装是日志 { 目录删除-操作动作 : 删除 ; 位置 : menu/del; 类型 : 非正常操作 ; 处理 : 强制下线!");
		addExit(model);
		return "back_page/menu/menu_List";
	}
	/*
	 *	 通过ID查询目录信息
	 * */
	@RequestMapping("/selectById")
	public String selectById(Integer id,Model model){
		try{
			if(id != null) {
				Menu menu=menuService.getById(id);
				if(menu != null) {
					setMessage(menu, "resMenu", null, null, model);
					getBeforeSel(model);
					return "back_page/menu/menu_Update";
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		addExit(model);
		return "back_page/menu/menu_Update";
	}
	/*
	 * 退出登录代码块
	 * */
	private void addExit(Model model) {
		System.err.println("假装是日志 { admin-操作动作 : 修改信息前查询; 位置 : admin/selectById; 类型 : 非正常操作 ; 处理 : 强制下线!");
		model.addAttribute("exit", "<script type=\"text/javascript\">window.parent.location.href=\"login/exitLogin\";</script>");
	}
	/*
	 * 更改账号或密码
	 * 
	 * */
	@RequestMapping("/update")
	public String update(Integer id, String menuId,String menuName,String menuURL,String parentMenuId,HttpServletRequest request,Model model){
		Menu menu = new Menu(id,menuId,menuName,menuURL,parentMenuId);
		System.out.println(menu.toString());
		Menu byId = null;
		// 非法操作！正常操作不会出现的——校验当前传入目录对象是否存在
		if(id == null || (byId=menuService.getById(id))==null) {
			addExit(model);
			return "back_page/menu/menu_Update";
		}
		// 拦截id在 1-15 的目录不能执行删除更新操作！
		if( menu.getId()<16 ){									
			System.out.println("   有人试图修改终极目录！");
			setMessage(menu,"resMenu","<script type=\"text/javascript\">alert(\"当前目录项不允许修改操作！\");</script>","errUpdateMenu",model);
			getBeforeSel(model);
			return "back_page/menu/menu_Update";
		}
		// 校验上传值
		if(ValidateValue.isEmpty(menuId)) {
			setMessage(menu,"resMenu","必填选项","menuId",model);
			getBeforeSel(model);
			return "back_page/menu/menu_Update";
		}
		if(ValidateValue.isEmpty(menuName)) {
			setMessage(menu,"resMenu","必填选项","menuName",model);
			getBeforeSel(model);
			return "back_page/menu/menu_Update";
		}
		if(ValidateValue.isEmpty(parentMenuId)) {
			setMessage(menu,"resMenu","必填选项","parentMenuId",model);
			getBeforeSel(model);
			return "back_page/menu/menu_Update";
		}
		// 校验当前是否没有修改
		if( menu.getMenuId().equals(byId.getMenuId()) && menu.getMenuName().equals(byId.getMenuName()) &&
				menu.getMenuURL().equals(byId.getMenuURL()) && menu.getParentMenuId().equals(byId.getParentMenuId()) ){
			System.out.println("   当前目录未做任何修改！");
			setMessage(menu,"resMenu","<script type=\"text/javascript\">alert(\"当前目录项未做任何修改！\");</script>","errUpdateMenu",model);
			getBeforeSel(model);
			return "back_page/menu/menu_Update";
		}
		//校验上送父目录是否存在——正常操作的都存在
		List<Menu> listByMenuId = menuService.listByMenuId(parentMenuId);
		System.out.println(JSON.toJSON(listByMenuId));
		if( listByMenuId==null || listByMenuId.size()<1){//父目录不存在，这情况正常操作不可能出现
			setMessage(menu,"errMemu","父目录不存在！","parentMenuId",model);
			getBeforeSel(model);
			return "back_page/menu/menu_Update";
		}
		//必须以父目录开头，不是的话，就主动构造
		if(!menuURL.endsWith(parentMenuId)){
			menuId = parentMenuId+menuId;
			menu.setMenuId(menuId);
		}
		/* 校验当前上送menuID是否被占用
		 * 情况1：只有一个且不是该目录本身（直接忽略）
		 * 情况2：该查询到的目录数大于1 
		 * */
		listByMenuId = menuService.listByMenuId(menu.getMenuId());
		if( listByMenuId != null && ( (listByMenuId.size()==1 && listByMenuId.get(0).getId() != menu.getId()) || listByMenuId.size()>1 ) ){
			setMessage(menu,"resMenu","该目录ID已经别占用！","menuId",model);
			getBeforeSel(model);
			return "back_page/menu/menu_Update";
		}
		try {
			menuService.updateMenu(menu);
			System.out.println("    LOG：更新旧目录："+byId.toString()+"   到："+menu.toString());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("    更新菜单项失败："+menu.toString());
			setMessage(menu,"resMenu","<script type=\"text/javascript\">alert(\"更新菜单项失败!\");</script>","errUpdateMenu",model);
			getBeforeSel(model);
			return "back_page/menu/menu_Update";
		}
		return "redirect:/menu/list";
	}
	/**
	 * 添加新的后台账号
	 **/
	@RequestMapping("/add")
	public String add(String menuId,String menuName,String menuURL,String parentMenuId,HttpServletRequest request,Model model) {

		Menu menu = new Menu(menuId,menuName,menuURL,parentMenuId);
		System.out.println(menu.toString());
		if(ValidateValue.isEmpty(menuId)) {
			setMessage(menu,"errMemu","必填选项","menuId",model);
			getBeforeSel(model);
			return "back_page/menu/menu_Add";
		}
		if(ValidateValue.isEmpty(menuName)) {
			setMessage(menu,"errMemu","必填选项","menuName",model);
			getBeforeSel(model);
			return "back_page/menu/menu_Add";
		}
		if(ValidateValue.isEmpty(menuURL)) {
			setMessage(menu,"errMemu","必填选项","menuURL",model);
			getBeforeSel(model);
			return "back_page/menu/menu_Add";
		}
		if(ValidateValue.isEmpty(parentMenuId)) {
			setMessage(menu,"errMemu","必填选项","parentMenuId",model);
			getBeforeSel(model);
			return "back_page/menu/menu_Add";
		}
		List<Menu> listByMenuId = menuService.listByMenuId(parentMenuId);
		System.out.println(JSON.toJSON(listByMenuId));
		if( listByMenuId==null || listByMenuId.size()<1){//父目录不存在，这情况正常操作不可能出现
			setMessage(menu,"errMemu","父目录不存在！","parentMenuId",model);
			getBeforeSel(model);
			return "back_page/menu/menu_Add";
		}
		if(!menuURL.endsWith(parentMenuId)){//必须以父目录开头，不是的话，就构造
			menuId = parentMenuId+menuId;
			menu.setMenuId(menuId);
		}
		listByMenuId = menuService.listByMenuId(menu.getMenuId());
		System.out.println();
		if( listByMenuId != null && listByMenuId.size()>0){//该目录ID已经存在
			setMessage(menu,"errMemu","该目录ID已经别占用！","menuId",model);
			getBeforeSel(model);
			return "back_page/menu/menu_Add";
		}
		try {
			menuService.addMenu(menu);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("    添加菜单项失败："+menu.toString());
			setMessage(menu,"errMemu","<script type=\"text/javascript\">alert(\"添加菜单项失败!\");</script>","errAddMenu",model);
			getBeforeSel(model);
			return "back_page/menu/menu_Add";
		}
		return "redirect:/menu/list";
	}
	/**
	 * 响应消息体
	 * menu 		Message.entity属性，要返回的实体数据对象	
	 * resObjName	Message对象的映射名
	 * resMessage   Message对象的msg属性，错误信息存放处
	 * resCode		Message对象的code属性	，用来标记通知是哪个地方出问题了
	 **/
	private void setMessage(Menu menu, String resObjName , String resMessage,String resCode, Model model) {
		Message<Object> msgObj= new Message<Object>();
		msgObj.setEntity(menu);
		msgObj.setMsg(resMessage);
		msgObj.setCode(resCode);
		model.addAttribute(resObjName, msgObj);
	}
	private void getBeforeSel(Model model) {
		List<Menu> menus = menuService.listAsParentMenu();
		if(menus != null && menus.size()>0){
			model.addAttribute("parentMenu", menus);
		}
	}
}
