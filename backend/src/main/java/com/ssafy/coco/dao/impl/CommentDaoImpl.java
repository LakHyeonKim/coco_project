package com.ssafy.coco.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.coco.dao.CommentDao;
import com.ssafy.coco.vo.Comment;

@Repository
public class CommentDaoImpl implements CommentDao{
	private String ns = "Comment_Mapper.";
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<Comment> findAllComment() {
		return sqlSession.selectList(ns+"findAllComment");
	}

	@Override
	public List<Comment> findComment(Comment comment) {
		return sqlSession.selectList(ns+"findComment",comment);
	}

	@Override
	public int addComment(Comment comment) {
		return sqlSession.insert(ns+"addComment",comment);
	}

	@Override
	public int updateComment(Comment comment) {
		return sqlSession.update(ns+"updateComment",comment);
	}

	@Override
	public int deleteComment(Comment comment) {
		return sqlSession.delete(ns+"deleteComment",comment);
	}
	
 
}
