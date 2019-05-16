package com.shop.mapper;

import java.util.List;

import com.shop.pojo.Menu;

public interface MenuMapper {
	public List<Menu> select(Menu al);
	public int insert(Menu al);
	public int delete(Integer id);
	public int update(Menu al);
	public List<Menu> selectAll(Menu al);
}
