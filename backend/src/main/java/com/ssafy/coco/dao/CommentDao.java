package com.ssafy.coco.dao;

import java.util.List;

import com.ssafy.coco.vo.BabyPost;
import com.ssafy.coco.vo.Comment;

public interface CommentDao {
	public List<Comment> findAllComment();
	public List<Comment> findComment(Comment comment);
	public int addComment(Comment comment);
	public int updateComment(Comment comment);
	public int deleteComment(Comment comment);
}
