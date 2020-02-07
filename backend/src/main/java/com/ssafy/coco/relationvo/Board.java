package com.ssafy.coco.relationvo;

import java.util.Arrays;
import java.util.List;

import com.ssafy.coco.vo.Post;

public class Board {
	private Post post;
	private List<String> tags;
	private int commentCount;
	public Board(Post post, List<String> tags, int commentCount) {
		super();
		this.post = post;
		this.tags = tags;
		this.commentCount = commentCount;
	}
	public Board() {
		super();
	}
	public Post getPost() {
		return post;
	}
	public void setPost(Post post) {
		this.post = post;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	@Override
	public String toString() {
		return "Board [post=" + post + ", tags=" + tags + ", commentCount=" + commentCount + "]";
	}
	
	
}
