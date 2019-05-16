package com.shop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.mapper.AdminMapper;
import com.shop.pojo.Admin;
import com.shop.pojo.AdminExample;
import com.shop.pojo.AdminExample.Criteria;

@Service
@Transactional
public class AdminService {
	@Autowired 
	private AdminMapper adminMapper;
	
	public Admin getById(Integer id) {
		return adminMapper.selectByPrimaryKey(id);
	}
	
	public Admin getByUsername(String username) {
		AdminExample example=new AdminExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<Admin> list = adminMapper.selectByExample(example);
		System.out.println(username);
		return list.size()>0?list.get(0):null;
	}
	
	public List<Admin> list(String mohuName) {
		AdminExample example=new AdminExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameLike("%"+mohuName+"%");
		List<Admin> list = adminMapper.selectByExample(example);
		return list;
	}
	
	public int del(Integer id) {
		return adminMapper.deleteByPrimaryKey(id);
	}
	
	public int update(Admin admin) {
		return adminMapper.updateByPrimaryKey(admin);
	}
	
	public int add(Admin admin) {
		return adminMapper.insert(admin);
	}
	
}
