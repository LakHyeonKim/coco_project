package com.ssafy.coco.relationvo;

public class OrderSearch {
	long idMember;
	int order;
	
	
	public OrderSearch() {
		super();
	}


	public OrderSearch(long idMember, int order) {
		super();
		this.idMember = idMember;
		this.order = order;
	}


	public long getIdMember() {
		return idMember;
	}


	public void setIdMember(long idMember) {
		this.idMember = idMember;
	}


	public int getOrder() {
		return order;
	}


	public void setOrder(int order) {
		this.order = order;
	}


	@Override
	public String toString() {
		return "OrderSearch [idMember=" + idMember + ", order=" + order + "]";
	}
	
}
