package com.ssafy.coco.vo;

public class MemberTag {
	long idmemberTag;
	long memberId;
	long tagId;
	int access;
	long frequency;
	
	public MemberTag() {
		super();
	}
	public MemberTag(long idmemberTag, long memberId, long tagId, int access, long frequency) {
		super();
		this.idmemberTag = idmemberTag;
		this.memberId = memberId;
		this.tagId = tagId;
		this.access = access;
		this.frequency = frequency;
	}
	@Override
	public String toString() {
		return "MemberTag [idmemberTag=" + idmemberTag + ", memberId=" + memberId + ", tagId=" + tagId + ", access="
				+ access + ", frequency=" + frequency + "]";
	}
	public long getIdmemberTag() {
		return idmemberTag;
	}
	public void setIdmemberTag(long idmemberTag) {
		this.idmemberTag = idmemberTag;
	}
	public long getMemberId() {
		return memberId;
	}
	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}
	public long getTagId() {
		return tagId;
	}
	public void setTagId(long tagId) {
		this.tagId = tagId;
	}
	public int getAccess() {
		return access;
	}
	public void setAccess(int access) {
		this.access = access;
	}
	public long getFrequency() {
		return frequency;
	}
	public void setFrequency(long frequency) {
		this.frequency = frequency;
	}
	
	 
}
