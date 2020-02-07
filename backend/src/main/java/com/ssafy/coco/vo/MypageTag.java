package com.ssafy.coco.vo;

public class MypageTag {
	long idmypageTag;
	long mypageId;
	long tagId;
	
	public MypageTag() {
		super();
	}
	public MypageTag(long idmypageTag, long mypageId, long tagId) {
		super();
		this.idmypageTag = idmypageTag;
		this.mypageId = mypageId;
		this.tagId = tagId;
	}
	public long getIdmypageTag() {
		return idmypageTag;
	}
	public void setIdmypageTag(long idmypageTag) {
		this.idmypageTag = idmypageTag;
	}
	public long getMypageId() {
		return mypageId;
	}
	public void setMypageId(long mypageId) {
		this.mypageId = mypageId;
	}
	public long getTagId() {
		return tagId;
	}
	public void setTagId(long tagId) {
		this.tagId = tagId;
	}
	@Override
	public String toString() {
		return "MypageTag [idmypageTag=" + idmypageTag + ", mypageId=" + mypageId + ", tagId=" + tagId + "]";
	}
	
}
