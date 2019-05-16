package com.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.constant.CategoryMessage;
import com.shop.pojo.Category;
import com.shop.service.AdminCategoryService;
import com.shop.utils.Page;
import com.shop.utils.ValidateValue;

@Controller
@RequestMapping("/category")
public class AdminCategoryController {
	@Autowired
	private AdminCategoryService service;
	
	@RequestMapping("/list")
	public String list(String mohu,String curPage,Model model){
		String mohuName=ValidateValue.isEmpty(mohu)?"":mohu.trim();//如果为空,就赋值空串
		Integer currPage=ValidateValue.isNumber(curPage)?Integer.parseInt(curPage.trim()):1;//如果字符是数值,就转为Interger.否则就赋值1
		
		int sizePage=8;//每页显示的数量
		
		PageHelper.startPage(currPage,sizePage);
		List<Category>list=service.list(mohuName);
		PageInfo<Category> pageInfo = new PageInfo<Category>(list);
		Page page = new Page(currPage, (int)pageInfo.getTotal(), sizePage);
		
		model.addAttribute("list",list);
		model.addAttribute("page",page);
		model.addAttribute("mohu",mohuName);
		
		return "back_page/classOne/classOne_List";
	}
	
	@RequestMapping("/deleteById")
	public String deleteById(Integer id,String curPage,String mohuName){
		try {
			if(id != null) {
				if( service.deleteById(id) > 0)
				return "redirect:/category/list?mohuName="+mohuName+"&curPage="+curPage;
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.err.println("假装是日志 { 一级分类-操作动作 : 删除 ; 位置 : category/del; 类型 : 非正常操作 ; 处理 : 强制下线!");
		return "redirect:/login/exitLogin";
	}
	
	@RequestMapping("/add")
	public String add(Category category,Model model){
		CategoryMessage massage = new CategoryMessage();
		try {
			
			if(!ValidateValue.isEmpty(category.getName())) {
				Boolean val=service.selectByName(category.getName());
				if(!val){//如果不存在,一级分类名称可用
					if ( service.add(category)>0)
					return "redirect:/category/list";
				}else{
					massage.setMsg("该一级分类名称已经存在!");
					massage.setName(category.getName());
					model.addAttribute("message",massage);
					return "back_page/classOne/classOne_Add";
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		massage.setMsg("*您输入信息有误,请重新输入!");
		massage.setName(category.getName());
		model.addAttribute("message",massage);
		return "back_page/classOne/classOne_Add";
	}

	@RequestMapping("/selectById")
	public String selectById(Integer id,Model model){
		try{
			if(id != null) {
				Category category=service.selectById(id);
				if(category != null) {
					model.addAttribute("category",category);
					return "back_page/classOne/classOne_Update";
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.err.println("假装是日志 { 一级分类-操作动作 : 修改前查询; 位置 : category/selectById; 类型 : 非正常操作 ; 处理 : 强制下线!");
		return "redirect:/login/exitLogin";
	}
	/*
	 * 
	 * 修改一级分类信息
	 * 
	 * */
	@RequestMapping("/update")
	public String update(Category category,String oldName,Model model){
		//oldName 不会出现为空的情况 ID 也不会出现为空的情况
		if(  ValidateValue.isEmpty(oldName) || category.getId() == null) {
			System.err.println("假装是日志 { 一级分类-操作动作 : 修改; 位置 : category/update; 类型 : 非正常操作 ; 处理 : 强制下线!");
			return "redirect:/login/exitLogin";
		}
		if( ValidateValue.isEmpty(category.getName()) ) {
			getMessage(category,model,oldName,"*输入信息不合法!");
			return "back_page/classOne/classOne_Update";
		}else {//不为空值的值的情况下
			if(category.getName().equals(oldName)){//未进行修改
				getMessage(category,model,oldName,"*您未修改任何信息!");
				return "back_page/classOne/classOne_Update";
			}else{//一级分类名称已经存在
				if(service.selectByName(category.getName())){//一级分类名称查重
					getMessage(category,model,oldName,"*该一级分类已经存在!");
					return "back_page/classOne/classOne_Update";
				}else{
					try {
						service.update(category);
						return "redirect:/category/list";
					}catch (Exception e) {
						e.printStackTrace();
						getMessage(category,model,oldName,"*修改失败!");
						return "back_page/classOne/classOne_Update";
					}
				}
			}
		}
	}

	private void getMessage(Category category, Model model, String old,String msg) {
		CategoryMessage message = new CategoryMessage();
		message.setMsg(msg);
		category.setName(old);
		model.addAttribute("category",category);
		model.addAttribute("message",message);
	}
	
	
}
