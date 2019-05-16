package com.shop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.mapper.UserMapper;
import com.shop.pojo.User;
import com.shop.pojo.UserExample;
import com.shop.pojo.UserExample.Criteria;

@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;
	/**
	 * 	如果该用户名存在时,查询到的结果(list的长度大于0) 返回为真
	 * */
	public boolean isExistUser(String username) {
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<User> list = userMapper.selectByExample(example);
		return list.size()>0?true:false;
	}
	/**
	 *	添加一个新用户
	 * 
	 * */
	public boolean addRegist(User user) {
		try {
			if( userMapper.insert(user)>0) {
				return true;
			}else {
				return false;
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.err.println("假装是日志 { user-操作动作 : 添加用户; 位置 : userLR/userRegist ; 类型 : 数据库添加操作捕捉到异常 ; 处理 : 提示!");
			return false;
		}
	}
	//查找用户,返回一个用户对象
	public User selectByUserName(String username) {
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<User> list = userMapper.selectByExample(example);
		if( list.size()>0 ) {
			return list.get(0);
		}
		return null;
	}
	
	
}
