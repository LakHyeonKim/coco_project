package com.ssafy.coco.relationvo;

public class MemberHomePageSearch {
	long myIdMemeber;
	long youIdMember;
	
	public MemberHomePageSearch() {
		super();
	}

	@Override
	public String toString() {
		return "MemberHomePageSearch [myIdMemeber=" + myIdMemeber + ", youIdMember=" + youIdMember + "]";
	}

	public long getMyIdMemeber() {
		return myIdMemeber;
	}

	public void setMyIdMemeber(long myIdMemeber) {
		this.myIdMemeber = myIdMemeber;
	}

	public long getYouIdMember() {
		return youIdMember;
	}

	public void setYouIdMember(long youIdMember) {
		this.youIdMember = youIdMember;
	}

	public MemberHomePageSearch(long myIdMemeber, long youIdMember) {
		super();
		this.myIdMemeber = myIdMemeber;
		this.youIdMember = youIdMember;
	}
	
}
