package com.ssafy.coco.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.coco.dao.AlarmDao;
import com.ssafy.coco.dao.BabyPostDao;
import com.ssafy.coco.dao.CommentDao;
import com.ssafy.coco.dao.FollowDao;
import com.ssafy.coco.dao.LikeDao;
import com.ssafy.coco.vo.Alarm;
import com.ssafy.coco.vo.BabyPost;
import com.ssafy.coco.vo.Follow;
import com.ssafy.coco.vo.Like;

@Repository
public class LikeDaoImpl implements LikeDao{
	private String ns = "Like_Mapper.";
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<Like> findAllLike() {
		return sqlSession.selectList(ns+"findAllLike");
	}

	@Override
	public List<Like> findLike(Like like) {
		return sqlSession.selectList(ns+"findLike",like);
	}

	@Override
	public int addLike(Like like) {
		return sqlSession.insert(ns+"addLike",like);
	}

	@Override
	public int updateLike(Like like) {
		return sqlSession.update(ns+"updateLike",like);
	}

	@Override
	public int deleteLike(Like like) {
		return sqlSession.delete(ns+"deleteLike",like);
	}

	
 
}
