package com.ssafy.coco.relationvo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.coco.vo.Comment;
import com.ssafy.coco.vo.Member;
import com.ssafy.coco.vo.Post;
import com.ssafy.coco.vo.Tag;

public class BoardDetail {
	private Post post;
	private List<Tag> tags;
	private List<Comment> comments;
	private List<Member> likes;
	private List<Post> babyPosts;
	private long commentCount;
	private MultipartFile attachments;
	
	
	public MultipartFile getAttachments() {
		return attachments;
	}

	public void setAttachments(MultipartFile attachments) {
		this.attachments = attachments;
	}

	public BoardDetail(Post post, List<Tag> tags, List<Comment> comments, List<Member> likes, List<Post> babyPosts,
			long commentCount, MultipartFile attachments) {
		super();
		this.post = post;
		this.tags = tags;
		this.comments = comments;
		this.likes = likes;
		this.babyPosts = babyPosts;
		this.commentCount = commentCount;
		this.attachments = attachments;
	}

	public BoardDetail() {
		super();
	}

	public BoardDetail(Post post, List<Tag> tags, List<Comment> comments, List<Member> likes, List<Post> babyPosts,
			long commentCount) {
		super();
		this.post = post;
		this.tags = tags;
		this.comments = comments;
		this.likes = likes;
		this.babyPosts = babyPosts;
		this.commentCount = commentCount;
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

	@Override
	public String toString() {
		return "BoardDetail [post=" + post + ", tags=" + tags + ", comments=" + comments + ", likes=" + likes
				+ ", babyPosts=" + babyPosts + ", commentCount=" + commentCount + ", attachments=" + attachments + "]";
	}
}
