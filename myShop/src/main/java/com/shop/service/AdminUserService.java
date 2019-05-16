package com.shop.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.mapper.UserMapper;
import com.shop.pojo.User;
import com.shop.pojo.UserExample;
import com.shop.pojo.UserExample.Criteria;

@Service
@Transactional
public class AdminUserService {
	@Autowired
	UserMapper userMapper;
	
	public List<User> list(String mohuName){
		UserExample example=new UserExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andUsernameLike("%"+mohuName+"%");
		return userMapper.selectByExample(example);
	}

	public int del(Integer id) {
		return userMapper.deleteByPrimaryKey(id);
	}

	public List<User> searchOrder(HashMap<String, Object> parameter) {
		try {
			return userMapper.getList(parameter);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("   LOG：客户详细查询失败！");
		}
		return null;
	}
	public Integer searchOrderCount(HashMap<String, Object> parameter) {
		try {
			return userMapper.getListCount(parameter);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("   LOG：客户详细查询总数失败！");
		}
		return 0;
	}
}
