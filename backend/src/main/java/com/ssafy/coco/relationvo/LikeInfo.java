package com.ssafy.coco.relationvo;

import com.ssafy.coco.vo.Member;

public class LikeInfo {
	private Member member;
	private int isFollow;
	
	public LikeInfo(Member member, int isFollow) {
		super();
		this.member = member;
		this.isFollow = isFollow;
	}

	public LikeInfo() {
		super();
	}

	@Override
	public String toString() {
		return "LikeInfo [member=" + member + ", isFollow=" + isFollow + "]";
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public int getIsFollow() {
		return isFollow;
	}

	public void setIsFollow(int isFollow) {
		this.isFollow = isFollow;
	}
	
	
}
