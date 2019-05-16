package com.shop.pojo;

public class AdminLimit {
	
	private Integer id;
	private Integer uid;
	private String limits;
	
	public AdminLimit() {
		super();
	}

	public AdminLimit(Integer id, Integer uid, String limits) {
		super();
		this.id = id;
		this.uid = uid;
		this.limits = limits;
	}
	
	public AdminLimit(Integer uid) {
		super();
		this.uid = uid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getLimits() {
		return limits;
	}

	public void setLimits(String limits) {
		this.limits = limits;
	}

	@Override
	public String toString() {
		return "AdminLimit [id=" + id + ", uid=" + uid + ", limits=" + limits
				+ "]";
	}
	
}
