package com.ssafy.coco.vo;

public class PostTag {
	long idpostTag;
	long postId;
	long tagId;
	
	public PostTag() {
		super();
	}
	public PostTag(long idpostTag, long postId, long tagId) {
		super();
		this.idpostTag = idpostTag;
		this.postId = postId;
		this.tagId = tagId;
	}
	@Override
	public String toString() {
		return "PostTag [idpostTag=" + idpostTag + ", postId=" + postId + ", tagId=" + tagId + "]";
	}
	public long getIdpostTag() {
		return idpostTag;
	}
	public void setIdpostTag(long idpostTag) {
		this.idpostTag = idpostTag;
	}
	public long getPostId() {
		return postId;
	}
	public void setPostId(long postId) {
		this.postId = postId;
	}
	public long getTagId() {
		return tagId;
	}
	public void setTagId(long tagId) {
		this.tagId = tagId;
	}
	
	
}
