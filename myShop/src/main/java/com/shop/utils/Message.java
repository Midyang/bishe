package com.shop.utils;

import java.util.List;
import java.util.Map;

/**
 *	主要用于 AJAX 响应对象
 *	页面渲染数据的暂时存放(计划，暂时未涉及)
 *	@Author midy
 *	@Field  code 响应状态码
 *	@Field  msg 响应的消息
 *	@Field  entity 响应的实体
 *	@Field  data 响应的其他数据
 * */
public class Message <T> {
	/**
	 * 
	 * 	响应码
	 * */
	private String code;
	 /**
	  * 
	  *	响应消息
	  * */
	private String msg;
	 /**
	  * 
	  * 	响应实体
	  * */
	private Object entity;
	 /**
	  * 
	  * 	响应的其它数据 map类型
	  * */
	private Map<String,Object> map;
	/**
	  * 
	  * 	响应的其它数据 list类型
	  * */
	private List<T> list;
	
	/**
	 * 
	 * 获取实体
	 * @Param type 要获取实体的类对象  Class 对象
	 * 属性entity 为null，或参数type为null 返回null
	 * 类型转化异常后 返回null
	 * */
	public <E> E getEntity(Class<E> type) throws NullPointerException{
		if(type == null || this.entity == null){
			//throw new NullPointerException("Message 的 data或 传入参数为空!");
			return null;
		}
		try{
			return type.cast(this.entity);
		}catch(Exception e){
			System.err.println("	获取实体时,类型转换异常!");
			e.printStackTrace();
			return null;
		}
	}
	
	public Message(String i) {
		super();
		this.code = i;
	}

	public Message(String code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public Message(String code, String msg, Object entity, Map<String, Object> map) {
		super();
		this.code = code;
		this.msg = msg;
		this.entity = entity;
		this.map = map;
	}
	
	public Message() {
		super();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public Object getEntity() {
		return entity;
	}

	public void setEntity(Object entity) {
		this.entity = entity;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
	
	
}
