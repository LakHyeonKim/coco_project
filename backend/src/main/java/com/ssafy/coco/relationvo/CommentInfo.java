package com.ssafy.coco.relationvo;

import com.ssafy.coco.vo.Comment;

public class CommentInfo {
	private Comment comment;
	private int isFollow;
	private String postWriterProfileImage;
	
	
	public CommentInfo() {
		super();
	}


	public CommentInfo(Comment comment, int isFollow, String postWriterProfileImage) {
		super();
		this.comment = comment;
		this.isFollow = isFollow;
		this.postWriterProfileImage = postWriterProfileImage;
	}

	
	
	public Comment getComment() {
		return comment;
	}


	public void setComment(Comment comment) {
		this.comment = comment;
	}


	public int getIsFollow() {
		return isFollow;
	}


	public void setIsFollow(int isFollow) {
		this.isFollow = isFollow;
	}


	public String getPostWriterProfileImage() {
		return postWriterProfileImage;
	}


	public void setPostWriterProfileImage(String postWriterProfileImage) {
		this.postWriterProfileImage = postWriterProfileImage;
	}


	@Override
	public String toString() {
		return "CommentInfo [comment=" + comment + ", isFollow=" + isFollow + ", postWriterProfileImage="
				+ postWriterProfileImage + "]";
	}
	
	
}
