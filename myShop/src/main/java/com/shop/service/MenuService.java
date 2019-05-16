package com.shop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.shop.mapper.MenuMapper;
import com.shop.pojo.Menu;

@Service
public class MenuService {
	@Autowired
	private MenuMapper menuMapper;
	/**
	 *  根据权限名称 模糊查询有哪些权限
	 **/
	public List<Menu> listBymenuName(String mohuName) {
		Menu menu = new Menu();
		menu.setMenuName(mohuName);
		System.out.println(mohuName);
		return menuMapper.selectAll(menu);
	}
	
	/**
	 *  根据父菜单ID查询
	 **/
	public List<Menu> listByParentMenuId(String parentMenuId) {
		Menu menu = new Menu();
		menu.setParentMenuId(parentMenuId);
		return menuMapper.select(menu);
	}
	/**
	 *  根据菜单ID查询
	 **/
	public List<Menu> listByMenuId(String menuId) {
		Menu menu = new Menu();
		menu.setMenuId(menuId);
		return menuMapper.select(menu);
	}
	/**
	 *  根据ID查询
	 **/
	public Menu getById(Integer id) {
		List<Menu> menus = null;
		try{
			Menu menu = new Menu();
			menu.setId(id);
			menus = menuMapper.select(menu);
			if(menus != null && menus.size()>0 ){
				return menus.get(0);
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("   通过目录ID查询目录失败(查询结果为):"+JSON.toJSONString(menus));
		}
		return null;//出现异常就返回为空,查询不到也返回为空
	}
	/**
	 * 查询到可以作为父目录的菜单
	 **/
	public List<Menu> listAsParentMenu() {
		Menu menu = new Menu();
		menu.setMenuURL("");
		List<Menu> selectResuilt = menuMapper.select(menu);
		if(selectResuilt == null){
			return null;
		}
		return selectResuilt.size()>0?selectResuilt:null;
	}
	/*
	 * 添加一个新的目录
	 * */
	public void addMenu(Menu menu) {
		try {
			menuMapper.insert(menu);
		} catch (Exception e) {
			System.out.println("   添加一个新的目录：失败"+menu.toString());
		}
	}
	/*
	 * 更新操作
	 * */
	public void updateMenu(Menu menu) {
		try {
			menuMapper.update(menu);
		} catch (Exception e) {
			throw e;
		}
	}
	/*
	 * 根据id删除
	 * */
	public int deleteById(Integer id) throws Exception {
		try{
			if(id>15){//不允许删除系统目录
				return menuMapper.delete(id);
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("     LOG:根据id删除失败！");
		}
		return 0;
	}
	
}
