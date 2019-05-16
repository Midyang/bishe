package com.shop.pojo;

public class ProductCookie {
	private int id;
	private int nuber;
	
	public ProductCookie() {
		super();
	}
	
	public ProductCookie(int id, int nuber) {
		super();
		this.id = id;
		this.nuber = nuber;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNuber() {
		return nuber;
	}
	public void setNuber(int nuber) {
		this.nuber = nuber;
	}

	@Override
	public String toString() {
		return "ProductCookie [id=" + id + ", nuber=" + nuber + "]";
	}
	
}
