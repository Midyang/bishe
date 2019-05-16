package com.shop.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.mapper.SecondcategoryMapper;
import com.shop.pojo.Secondcategory;
import com.shop.pojo.SecondcategoryExample;
import com.shop.pojo.SecondcategoryExample.Criteria;

@Service
@Transactional
public class AdminSecondcategoryService {
	@Autowired
	private SecondcategoryMapper mapper;

	public List<Secondcategory> list(String mohu, int i, int j) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("mohu", mohu);
		map.put("startIndex", i);
		map.put("sizePage", j);
		List<Secondcategory> list=mapper. myGetBynameList(map);
		return list;
	}

	public int getCount(String mohu) {
		SecondcategoryExample example=new SecondcategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andNameLike("%"+mohu+"%");
		return mapper.countByExample(example);
	}

	public Integer deleteById(Integer id) {
		return mapper.deleteByPrimaryKey(id);
	}

	public List<Secondcategory> getByName(String name) {
		SecondcategoryExample example=new SecondcategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andNameEqualTo(name);
		List<Secondcategory> list = mapper.selectByExample(example);
		return list;
	}

	public int add(Secondcategory secondcategory) {
		return mapper.insert(secondcategory);
	}

	public Secondcategory selectById(Integer id) {
		return mapper.selectByPrimaryKey(id);
	}

	public int update(Secondcategory secondcategory) {
		return mapper.updateByPrimaryKey(secondcategory);
	}

	public List<Secondcategory> addPreList() {
		return mapper.selectByExample(null);
	}


}
