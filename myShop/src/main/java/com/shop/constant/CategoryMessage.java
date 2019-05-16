package com.shop.constant;
/**
 * 
 * 一级分类 响应信息体
 * 
 * */
public class CategoryMessage { 
	private Integer id;
	private String msg;
	private Integer categoryId;
	private String name;
	public CategoryMessage(Integer id, String msg, Integer categoryId, String name) {
		super();
		this.id = id;
		this.msg = msg;
		this.categoryId = categoryId;
		this.name = name;
	}
	public CategoryMessage() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "CategoryMassage [id=" + id + ", msg=" + msg + ", categoryId=" + categoryId + ", name=" + name + "]";
	}
	
	
}
