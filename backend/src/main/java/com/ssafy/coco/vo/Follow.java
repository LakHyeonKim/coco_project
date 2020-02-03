package com.ssafy.coco.vo;

public class Follow {
	long idfollow;
	long memberFollower;
	long memberFollowing;
	int access;
	
	public Follow() {
		super();
	}
	public Follow(long idfollow, long memberFollower, long memberFollowing, int access) {
		super();
		this.idfollow = idfollow;
		this.memberFollower = memberFollower;
		this.memberFollowing = memberFollowing;
		this.access = access;
	}
	@Override
	public String toString() {
		return "Follow [idfollow=" + idfollow + ", memberFollower=" + memberFollower + ", memberFollowing="
				+ memberFollowing + ", access=" + access + "]";
	}
	public long getIdfollow() {
		return idfollow;
	}
	public void setIdfollow(long idfollow) {
		this.idfollow = idfollow;
	}
	public long getMemberFollower() {
		return memberFollower;
	}
	public void setMemberFollower(long memberFollower) {
		this.memberFollower = memberFollower;
	}
	public long getMemberFollowing() {
		return memberFollowing;
	}
	public void setMemberFollowing(long memberFollowing) {
		this.memberFollowing = memberFollowing;
	}
	public int getAccess() {
		return access;
	}
	public void setAccess(int access) {
		this.access = access;
	}
	
}
