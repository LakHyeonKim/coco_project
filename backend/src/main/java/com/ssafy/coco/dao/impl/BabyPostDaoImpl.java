package com.ssafy.coco.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.coco.dao.AlarmDao;
import com.ssafy.coco.dao.BabyPostDao;
import com.ssafy.coco.vo.Alarm;
import com.ssafy.coco.vo.BabyPost;

@Repository
public class BabyPostDaoImpl implements BabyPostDao{
	private String ns = "BabyPost_Mapper.";
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<BabyPost> findAllBabyPost() {
		return sqlSession.selectList(ns+"findAllBabyPost");
	}

	@Override
	public List<BabyPost> findBabyPost(BabyPost babyPost) {
		return sqlSession.selectList(ns+"findBabyPost",babyPost);
	}

	@Override
	public int addBabyPost(BabyPost babyPost) {
		return sqlSession.insert(ns+"addBabyPost",babyPost);
	}

	@Override
	public int updateBabyPost(BabyPost babyPost) {
		return sqlSession.update(ns+"updateBabyPost",babyPost);
	}

	@Override
	public int deleteBabyPost(BabyPost babyPost) {
		return sqlSession.delete(ns+"deleteBabyPost",babyPost);
	}
 
}
