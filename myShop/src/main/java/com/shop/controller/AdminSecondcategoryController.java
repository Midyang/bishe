package com.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.constant.AdminMessage;
import com.shop.pojo.Category;
import com.shop.pojo.Secondcategory;
import com.shop.service.AdminCategoryService;
import com.shop.service.AdminSecondcategoryService;
import com.shop.utils.Page;
import com.shop.utils.ValidateValue;

@Controller
@RequestMapping("/secondcategory")
public class AdminSecondcategoryController {
	@Autowired
	private AdminSecondcategoryService service;
	@Autowired
	private AdminCategoryService categoryService;
	/**
	 * 
	 * 列表信息
	 * */
	@RequestMapping("/list")
	public String liString(String mohu,String curPage,Model model){
		
		Integer currPage=ValidateValue.isInteger(curPage)?Integer.parseInt(curPage.trim()):1;
		mohu=ValidateValue.isEmpty(mohu)?"":mohu.trim();
		
		Page page = new Page(currPage, service.getCount(mohu), 8);
		List<Secondcategory>list=service.list(mohu,page.getStartIndex(),page.getSizePage());
		
		model.addAttribute("list",list);
		model.addAttribute("page", page);
		model.addAttribute("mohu", mohu);
		
		return "back_page/classTwo/classTwo_List";
	}
	/**
	 * 
	 * 删除二级分类信息
	 * */
	@RequestMapping("/deleteById")
	public String deleteById(Integer id,String curPage,String mohu){
		try {
			if(id != null) {
				if( service.deleteById(id)>0 ) {
					return "redirect:/secondcategory/list?mohuName="+mohu+"&curPage="+curPage;
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.err.println("假装是日志 { 二级分类-操作动作 : 删除 ; 位置 : admin/del; 类型 : 非正常操作 ; 处理 : 强制下线!");
		return "redirect:/login/exitLogin";
	}
	/**
	 * 
	 * 	一级分类信息,下拉框数据
	 * 
	 * */
	@RequestMapping("/addPreList")
	public String addPreList(Model model){
		selectPre(model);
		return "back_page/classTwo/classTwo_Add";
	}
	private void selectPre(Model model) {
		List<Category>list=categoryService.addPreList();
		model.addAttribute("list",list);
	}
	/**
	 * 
	 * 添加二级分类
	 * */
	@RequestMapping("/add")
	public String addPreList(Secondcategory secondcategory, Model model){
		
		 // 验证要添加的一级分类所属的一级分类(不能为空)
		if( secondcategory.getCid() == null) {
			returnMessage(secondcategory, model , "*请选择要添加的一级分类" );
			return "back_page/classTwo/classTwo_Add";
		}
		// 验证要添加的一级分类所属的一级分类(不能为空)
		if( ValidateValue.isEmpty( secondcategory.getName() ) ) {
			returnMessage(secondcategory, model , "*添加信息不能为空" );
			return "back_page/classTwo/classTwo_Add";
		}
		
		List<Secondcategory> second=service.getByName(secondcategory.getName());
		if(second == null && service.add(secondcategory)>0){
			return "redirect:/secondcategory/list";//添加成功!
		}else{
			for(Secondcategory s:second){//允许同名,但cid不相等
					if (s.getCid()==secondcategory.getCid()) {
						returnMessage(secondcategory, model , "*该条记录已经存在!请不要重复添加" );
						return "back_page/classTwo/classTwo_Add";
					}
			}
			if( service.add(secondcategory)>0 ) {
				return "redirect:/secondcategory/list";
			}
			
			returnMessage(secondcategory, model , "*添加失败,请稍后尝试..." );
			return "back_page/classTwo/classTwo_Add";
			
			
		}//else	end
	}
	/**
	 * 
	 * 用于返回的消息封装
	 * */
	private void returnMessage(Secondcategory secondcategory, Model model , String message ) {
		model.addAttribute("second",secondcategory);
		AdminMessage massage = new AdminMessage();
		massage.setMsg(message);
		model.addAttribute("message",massage);
		selectPre(model);
	}
	
	/**
	 * 
	 * 修改前信息查询
	 * */
	@RequestMapping("/selectById")
	public String selectById(Integer id,Model model){
		try{
			if(id != null) {
				Secondcategory secondcategory=service.selectById(id);
				if(secondcategory != null) {
					model.addAttribute("second",secondcategory);
					selectPre(model);
					return "back_page/classTwo/classTwo_Update";
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.err.println("假装是日志 { 二级分类-操作动作 : 修改信息前查询; 位置 : admin/selectById; 类型 : 非正常操作 ; 处理 : 强制下线!");
		return "redirect:/login/exitLogin";
	}
	/**
	 * 
	 * 
	 * 二级分类信息修改
	 * */
	@RequestMapping("/update")
	public String update(Secondcategory secondcategory,String oldName,String oldCid,Model model){

		if ( ValidateValue.isEmpty(oldName) || !ValidateValue.isInteger(oldCid) ) {
			System.err.println("假装是日志 { 二级分类-操作动作 : 修改信息; 位置 : admin/selectById; 类型 : 非正常操作 ; 处理 : 强制下线!");
			return "redirect:/login/exitLogin";
		}
		
		if ( ValidateValue.isEmpty(secondcategory.getName() ) ) {
			selectPre(model);
			AdminMessage massage = new AdminMessage();
			massage.setMsg("*更改信息不能为空 !");
			model.addAttribute("message",massage);
			secondcategory.setCid(Integer.parseInt(oldCid));
			secondcategory.setName(oldName);
			model.addAttribute("second",secondcategory);
			return "back_page/classTwo/classTwo_Add";
		}
		List<Secondcategory> second=service.getByName(secondcategory.getName());
		if(second==null && service.update(secondcategory)>0 ){
			return "redirect:/secondcategory/list";
		}else{
			for(Secondcategory s:second){
					if (s.getCid()==secondcategory.getCid()) {
						selectPre(model);
						AdminMessage massage = new AdminMessage();
						massage.setMsg("*该条记录已经存在!");
						model.addAttribute("message",massage);
						secondcategory.setCid(Integer.parseInt(oldCid));
						secondcategory.setName(oldName);
						model.addAttribute("second",secondcategory);
						return "back_page/classTwo/classTwo_Add";
					}
			}
			if( service.update(secondcategory)>0 ) {
				return "redirect:/secondcategory/list";
			}else {
				selectPre(model);
				AdminMessage massage = new AdminMessage();
				massage.setMsg("*添加失败,请稍后尝试...");
				model.addAttribute("message",massage);
				secondcategory.setCid(Integer.parseInt(oldCid));
				secondcategory.setName(oldName);
				model.addAttribute("second",secondcategory);
				return "back_page/classTwo/classTwo_Add";
			}
		}
		
	}
	
}
