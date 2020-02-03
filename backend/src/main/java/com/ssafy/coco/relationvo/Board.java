package com.ssafy.coco.relationvo;

import java.util.List;

import com.ssafy.coco.vo.Comment;
import com.ssafy.coco.vo.Member;
import com.ssafy.coco.vo.Post;
import com.ssafy.coco.vo.Tag;

public class Board {
	private Post post;
	private List<Tag> tags;
	private List<Comment> comments;
	private List<Member> likes;
	private List<Post> babyPosts;
	private long commentCount;
	private long likeCount;
	
	
	public Board() {
		super();
	}

	public Board(Post post, List<Tag> tags, List<Comment> comments, List<Member> likes, List<Post> babyPosts,
			long commentCount, long likeCount) {
		super();
		this.post = post;
		this.tags = tags;
		this.comments = comments;
		this.likes = likes;
		this.babyPosts = babyPosts;
		this.commentCount = commentCount;
		this.likeCount = likeCount;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Member> getLikes() {
		return likes;
	}

	public void setLikes(List<Member> likes) {
		this.likes = likes;
	}

	public List<Post> getBabyPosts() {
		return babyPosts;
	}

	public void setBabyPosts(List<Post> babyPosts) {
		this.babyPosts = babyPosts;
	}

	public long getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(long commentCount) {
		this.commentCount = commentCount;
	}

	public long getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(long likeCount) {
		this.likeCount = likeCount;
	}

	@Override
	public String toString() {
		return "Board [post=" + post + ", tags=" + tags + ", comments=" + comments + ", likes=" + likes + ", babyPosts="
				+ babyPosts + ", commentCount=" + commentCount + ", likeCount=" + likeCount + "]";
	}
	
}
