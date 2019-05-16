package com.shop.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.mapper.CategoryMapper;
import com.shop.pojo.Category;
import com.shop.pojo.CategoryExample;
import com.shop.pojo.CategoryExample.Criteria;

@Service
@Transactional
public class AdminCategoryService {
	@Autowired
	private CategoryMapper categoryMapper;

	public List<Category> list(String mohuName) {
		CategoryExample example=new CategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andNameLike("%"+mohuName+"%");
		return categoryMapper.selectByExample(example);
	}

	public int deleteById(Integer id) {
		return categoryMapper.deleteByPrimaryKey(id);
	}
/**
 * 	查询一级分类名称是否存在
 *	如果存在 返回为 true
 *	否则返回 false
 * */
	public Boolean selectByName(String name) {
		CategoryExample example=new CategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andNameEqualTo(name);
		List<Category> list = categoryMapper.selectByExample(example);
		return list.size()>0;//查询到对象,值大于0,为真
	}

	public int add(Category category) {
		return categoryMapper.insert(category);
	}

	public Category selectById(Integer id) {
		return categoryMapper.selectByPrimaryKey(id);
	}

	public void update(Category category) {
		categoryMapper.updateByPrimaryKey(category);
	}

	public List<Category> addPreList() {
		return categoryMapper.selectByExample(null);
	}
}
