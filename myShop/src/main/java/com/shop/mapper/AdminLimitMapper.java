package com.shop.mapper;

import java.util.List;

import com.shop.pojo.AdminLimit;

public interface AdminLimitMapper {
	public List<AdminLimit> select(AdminLimit al);
	public int insert(AdminLimit al);
	public int delete(Integer id);
	public int update(AdminLimit al);
	public int deleteByUid(Integer uid);
}
