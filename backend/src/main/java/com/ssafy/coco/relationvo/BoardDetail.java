package com.ssafy.coco.relationvo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.coco.vo.Comment;
import com.ssafy.coco.vo.Like;
import com.ssafy.coco.vo.Member;
import com.ssafy.coco.vo.Post;
import com.ssafy.coco.vo.Tag;

public class BoardDetail {
	private Post post;
	private List<Tag> tags;
	private List<CommentInfo> commentInfos;
	private List<Comment> comments;
	private List<Member> likes;
	private List<Post> babyPosts;
	private long commentCount;
	private String postWriterProfileImage;
	private int isFollow;
	
	
	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	public BoardDetail() {
		super();
	}

	public BoardDetail(Post post, List<Tag> tags, List<CommentInfo> commentInfos, List<Member> likes,
			List<Post> babyPosts, long commentCount, String postWriterProfileImage, int isFollow) {
		super();
		this.post = post;
		this.tags = tags;
		this.commentInfos = commentInfos;
		this.likes = likes;
		this.babyPosts = babyPosts;
		this.commentCount = commentCount;
		this.postWriterProfileImage = postWriterProfileImage;
		this.isFollow = isFollow;
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

	public List<CommentInfo> getCommentInfos() {
		return commentInfos;
	}

	public void setCommentInfos(List<CommentInfo> commentInfos) {
		this.commentInfos = commentInfos;
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

	public String getPostWriterProfileImage() {
		return postWriterProfileImage;
	}

	public void setPostWriterProfileImage(String postWriterProfileImage) {
		this.postWriterProfileImage = postWriterProfileImage;
	}

	public int getIsFollow() {
		return isFollow;
	}

	public void setIsFollow(int isFollow) {
		this.isFollow = isFollow;
	}

	@Override
	public String toString() {
		return "BoardDetail [post=" + post + ", tags=" + tags + ", commentInfos=" + commentInfos + ", likes=" + likes
				+ ", babyPosts=" + babyPosts + ", commentCount=" + commentCount + ", postWriterProfileImage="
				+ postWriterProfileImage + ", isFollow=" + isFollow + "]";
	}
	
}
