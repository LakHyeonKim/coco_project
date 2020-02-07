package com.ssafy.coco.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.coco.dao.BabyPostDao;
import com.ssafy.coco.dao.CommentDao;
import com.ssafy.coco.service.CommentService;
import com.ssafy.coco.vo.BabyPost;
import com.ssafy.coco.vo.Comment;
@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	private CommentDao commentDao;

	@Override
	public List<Comment> findAllComment() {
		return commentDao.findAllComment();
	}

	@Override
	public List<Comment> findComment(Comment comment) {
		return commentDao.findComment(comment);
	}

	@Override
	public int addComment(Comment comment) {
		return commentDao.addComment(comment);
	}

	@Override
	public int updateComment(Comment comment) {
		return commentDao.updateComment(comment);
	}

	@Override
	public int deleteComment(Comment comment) {
		return commentDao.deleteComment(comment);
	}

}
