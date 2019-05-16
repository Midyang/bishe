package com.shop.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.shop.mapper.ProductMapper;
import com.shop.pojo.Product;
import com.shop.pojo.ProductExample;
import com.shop.pojo.ProductExample.Criteria;

@Service
@Transactional
public class AdminProductService {
	@Autowired
	private ProductMapper mapper;

	public List<Product> list(String mohu) {
		ProductExample example=new ProductExample();
		Criteria criteria = example.createCriteria();
		criteria.andNameLike("%"+mohu+"%");
		return mapper.selectByExample(example);
	}

	public void add(Product product) {
		mapper.insert(product);
	}

	public int deleteById(Integer id) {
		return mapper.deleteByPrimaryKey(id);
	}

	public Product selectById(Integer id) {
		return mapper.selectByPrimaryKey(id);
	}

	public void update(Product product) {
		mapper.updateByPrimaryKey(product);
	}
	/*
	 * 详细查询商品的信息
	 * */
	public List<Product> searchProduct(Map<String, Object> map) {
		List<Product> list = null;
		try{
			list = mapper.getList(map);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("   LOG：详细查询商品失败！");
			System.out.println("   LOG：当前参数为："+JSON.toJSONString(map));
		}
		return list;
	}
	/*
	 * 详细查询商品的时候，查询商品的总数量 
	 **/
	public Integer searchProductCount(Map<String, Object> map) {
		Integer listCount = 0;
		try{
			listCount = mapper.listCount(map);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("   LOG：商品的详细查询时，报错！");
			System.out.println("   LOG：当前参数为："+JSON.toJSONString(map));
		}
		return listCount;
	}
}
