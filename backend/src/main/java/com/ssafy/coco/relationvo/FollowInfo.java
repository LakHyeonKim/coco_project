package com.ssafy.coco.relationvo;

import com.ssafy.coco.vo.Member;

public class FollowInfo {
	private Member member;
	private int isFollow;
	
	public FollowInfo() {
		super();
	}

	public FollowInfo(Member member, int isFollow) {
		super();
		this.member = member;
		this.isFollow = isFollow;
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

	@Override
	public String toString() {
		return "FollowInfo [member=" + member + ", isFollow=" + isFollow + "]";
	}

}
