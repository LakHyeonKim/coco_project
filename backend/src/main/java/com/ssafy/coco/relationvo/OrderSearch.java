package com.ssafy.coco.relationvo;

public class OrderSearch {
	long myIdMember;
	long youIdMember;
	int order;
	
	
	
	@Override
	public String toString() {
		return "OrderSearch [myIdMember=" + myIdMember + ", youIdMember=" + youIdMember + ", order=" + order + "]";
	}



	public OrderSearch() {
		super();
	}



	public OrderSearch(long myIdMember, long youIdMember, int order) {
		super();
		this.myIdMember = myIdMember;
		this.youIdMember = youIdMember;
		this.order = order;
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
	
	

	
}
