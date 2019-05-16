package com.shop.constant;
/**
 * 
 * admin 响应信息体
 * 
 * */
public class AdminMessage {
	private Integer id;
	private String msg;
	private String username;
	
	public AdminMessage() {
		super();
	}
	
	public AdminMessage(Integer id, String msg) {
		super();
		this.id = id;
		this.msg = msg;
	}

	public AdminMessage(Integer id, String msg, String username) {
		super();
		this.id = id;
		this.msg = msg;
		this.username = username;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}
