package com.ssafy.coco.vo;

public class BabyPost {
	long idbabyPost;
	long postParents;
	long postChild;
	
	public BabyPost() {
		super();
	}
	public BabyPost(long idbabyPost, long postParents, long postChild) {
		super();
		this.idbabyPost = idbabyPost;
		this.postParents = postParents;
		this.postChild = postChild;
	}
	public long getIdbabyPost() {
		return idbabyPost;
	}
	public void setIdbabyPost(long idbabyPost) {
		this.idbabyPost = idbabyPost;
	}
	public long getPostParents() {
		return postParents;
	}
	public void setPostParents(long postParents) {
		this.postParents = postParents;
	}
	public long getPostChild() {
		return postChild;
	}
	public void setPostChild(long postChild) {
		this.postChild = postChild;
	}
	@Override
	public String toString() {
		return "BabyPost [idbabyPost=" + idbabyPost + ", postParents=" + postParents + ", postChild=" + postChild + "]";
	}
	
}
