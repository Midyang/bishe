package com.shop.utils;
/**
 * 
 * 分页类
 * */
public class Page { 
	private int currPage;//当前页
	private int prePage;//上一页
	private int nextPage;//下一页
	private int count;//总记录数
	private int sizePage;//每页容量
	private int countPage;//总页数
	private int startIndex;//查询的启示索引
	
	//					第几页		总记录数   	每页的容量
	public Page(Integer currPage, int count, int sizePage) {
		this.currPage = currPage;
		this.count = count;
		this.sizePage = sizePage;
		this.countPage = this.count%this.sizePage==0?this.count/this.sizePage:this.count/this.sizePage+1;
		this.countPage=this.count==0?1:this.countPage;
		this.prePage = this.currPage==1?this.currPage:this.currPage-1;
		this.nextPage = this.currPage==this.countPage?this.currPage:this.currPage+1;
		this.startIndex = (this.currPage-1)*sizePage;
		
	}


	public int getCurrPage() {
		return currPage;
	}


	public int getPrePage() {
		return prePage;
	}


	public int getNextPage() {
		return nextPage;
	}


	public int getCount() {
		return count;
	}


	public int getSizePage() {
		return sizePage;
	}


	public int getCountPage() {
		return countPage;
	}


	public int getStartIndex() {
		return startIndex;
	}


	@Override
	public String toString() {
		return "Page [currPage=" + currPage + ", prePage=" + prePage + ", nextPage=" + nextPage + ", count=" + count
				+ ", sizePage=" + sizePage + ", countPage=" + countPage + ", startIndex=" + startIndex + "]";
	}
}
