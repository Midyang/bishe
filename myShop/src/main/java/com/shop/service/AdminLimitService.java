package com.shop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.mapper.AdminLimitMapper;
import com.shop.pojo.Admin;
import com.shop.pojo.AdminLimit;

@Transactional
@Service
public class AdminLimitService {
	@Autowired
	private AdminLimitMapper  adminLinitMapper;
	
	/**
	 * 根据用户Id查询权限
	 **/
	public AdminLimit selectByUid(Integer uid){
		List<AdminLimit> adminLimit = adminLinitMapper.select(new AdminLimit(uid));
		if(adminLimit != null && adminLimit.size() > 0){
			return adminLimit.get(0);
		}else{
			return null;
		}
		
	}
	/**
	 *通过用户删除该用户的权限 
	 **/
	public void deleteLimitByUid(Admin admin) {
		adminLinitMapper.deleteByUid(admin.getId());
	}
	/**
	 * 更新当前用户的权限
	 **/
	public void updateLLimitById(AdminLimit adminLimit) {
		adminLinitMapper.update(adminLimit);
	}
	/**
	 * 给当前用户添加权限 
	 **/
	public void addLimitById(AdminLimit adminLimit) {
		adminLinitMapper.insert(adminLimit);
	}
}
