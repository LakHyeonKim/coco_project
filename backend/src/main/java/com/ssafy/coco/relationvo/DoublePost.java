package com.ssafy.coco.relationvo;

import com.ssafy.coco.vo.Post;

public class DoublePost {
	Post parent;
	Post son;
	@Override
	public String toString() {
		return "doublePost [parent=" + parent + ", son=" + son + "]";
	}
	public DoublePost(Post parent, Post son) {
		super();
		this.parent = parent;
		this.son = son;
	}
	public Post getParent() {
		return parent;
	}
	public void setParent(Post parent) {
		this.parent = parent;
	}
	public Post getSon() {
		return son;
	}
	public void setSon(Post son) {
		this.son = son;
	}
	
}
