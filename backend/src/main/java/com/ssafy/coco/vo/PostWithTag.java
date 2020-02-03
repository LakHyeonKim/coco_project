package com.ssafy.coco.vo;

public class PostWithTag {
	Post post;
	String tags;
	public PostWithTag(Post post, String tags) {
		super();
		this.post = post;
		this.tags = tags;
	}
	@Override
	public String toString() {
		return "PostWithTag [post=" + post + ", tags=" + tags + "]";
	}
	public Post getPost() {
		return post;
	}
	public void setPost(Post post) {
		this.post = post;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	
}
