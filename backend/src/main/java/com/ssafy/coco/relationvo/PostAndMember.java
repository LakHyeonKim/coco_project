package com.ssafy.coco.relationvo;

import com.ssafy.coco.vo.Member;
import com.ssafy.coco.vo.Post;

public class PostAndMember {
	Post post;
	Member member;
	@Override
	public String toString() {
		return "postAndMember [post=" + post + ", member=" + member + "]";
	}
	public PostAndMember(Post post, Member member) {
		super();
		this.post = post;
		this.member = member;
	}
	public Post getPost() {
		return post;
	}
	public void setPost(Post post) {
		this.post = post;
	}
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	
}
