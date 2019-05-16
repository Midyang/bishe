package com.shop.utils;
/**
 * 用于区分，不同状态的响应 
 **/
public class ResState {
	private String code;
	private String msg;
	
	
	public ResState() {
		super();
	}
	
	public ResState(String code) {
		super();
		this.code = code;
	}

	public ResState(String code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
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
	
	@Override
	public String toString() {
		return "ResState [code=" + code + ", msg=" + msg + "]";
	}
	
	
}
