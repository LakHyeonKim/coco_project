package com.ssafy.coco.relationvo;

public class OrderSearchKeyword {
	private long myIdMember;
	private long youIdMember;
	private int order;
	private String keyword;
	
	
	public OrderSearchKeyword() {
		super();
	}


	public OrderSearchKeyword(long youIdMember, int order, String keyword) {
		super();
		this.youIdMember = youIdMember;
		this.order = order;
		this.keyword = keyword;
	}
	


	public OrderSearchKeyword(long myIdMember, long youIdMember, int order, String keyword) {
		super();
		this.myIdMember = myIdMember;
		this.youIdMember = youIdMember;
		this.order = order;
		this.keyword = keyword;
	}


	public long getMyIdMember() {
		return myIdMember;
	}


	public void setMyIdMember(long myIdMember) {
		this.myIdMember = myIdMember;
	}


	public long getYouIdMember() {
		return youIdMember;
	}


	public void setYouIdMember(long youIdMember) {
		this.youIdMember = youIdMember;
	}


	public int getOrder() {
		return order;
	}


	public void setOrder(int order) {
		this.order = order;
	}


	public String getKeyword() {
		return keyword;
	}


	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}


	@Override
	public String toString() {
		return "OrderSearchKeyword [myIdMember=" + myIdMember + ", youIdMember=" + youIdMember + ", order=" + order
				+ ", keyword=" + keyword + "]";
	}
	
	
}
