package com.shop.pojo;

public class Secondcategory {
    private Integer id;

    private String name;

    private Integer cid;
    
    private Category category;
    
    

    public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

	@Override
	public String toString() {
		return "Secondcategory [id=" + id + ", name=" + name + ", cid=" + cid + ", category=" + category + "]";
	}
    
}