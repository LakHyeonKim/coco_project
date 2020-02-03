package com.ssafy.coco.vo;

public final class Like {
	long idlike;
	long postId;
	long memberId;
	int access;
	
	public Like() {
		super();
	}
	public Like(long idlike, long postId, long memberId, int access) {
		super();
		this.idlike = idlike;
		this.postId = postId;
		this.memberId = memberId;
		this.access = access;
	}
	@Override
	public String toString() {
		return "Like [idlike=" + idlike + ", postId=" + postId + ", memberId=" + memberId + ", access=" + access + "]";
	}
	public long getIdlike() {
		return idlike;
	}
	public void setIdlike(long idlike) {
		this.idlike = idlike;
	}
	public long getPostId() {
		return postId;
	}
	public void setPostId(long postId) {
		this.postId = postId;
	}
	public long getMemberId() {
		return memberId;
	}
	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}
	public int getAccess() {
		return access;
	}
	public void setAccess(int access) {
		this.access = access;
	}
}
