package com.shop.pojo;

public class Menu {
	private Integer id;
	private String menuId;
	private String menuName;
	private String menuURL;
	private String parentMenuId;
	private String parentMenuName;
	
	public Menu() {
		super();
	}
	
	public Menu(Integer id, String menuId, String menuName, String menuURL,
			String parentMenuId) {
		super();
		this.id = id;
		this.menuId = menuId;
		this.menuName = menuName;
		this.menuURL = menuURL;
		this.parentMenuId = parentMenuId;
	}

	public Menu(String menuId, String menuName, String menuURL,String parentMenuId) {
		super();
		this.menuId = menuId;
		this.menuName = menuName;
		this.menuURL = menuURL;
		this.parentMenuId = parentMenuId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuURL() {
		return menuURL;
	}

	public void setMenuURL(String menuURL) {
		this.menuURL = menuURL;
	}

	public String getParentMenuId() {
		return parentMenuId;
	}

	public void setParentMenuId(String parentMenuId) {
		this.parentMenuId = parentMenuId;
	}

	public String getParentMenuName() {
		return parentMenuName;
	}

	public void setParentMenuName(String parentMenuName) {
		this.parentMenuName = parentMenuName;
	}

	@Override
	public String toString() {
		return "Menu [id=" + id + ", menuId=" + menuId + ", menuName="
				+ menuName + ", menuURL=" + menuURL + ", parentMenuId="
				+ parentMenuId + ", parentMenuName=" + parentMenuName + "]";
	}

}
