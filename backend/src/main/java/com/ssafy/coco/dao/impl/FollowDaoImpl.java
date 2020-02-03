package com.ssafy.coco.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.coco.dao.AlarmDao;
import com.ssafy.coco.dao.BabyPostDao;
import com.ssafy.coco.dao.CommentDao;
import com.ssafy.coco.dao.FollowDao;
import com.ssafy.coco.vo.Alarm;
import com.ssafy.coco.vo.BabyPost;
import com.ssafy.coco.vo.Follow;

@Repository
public class FollowDaoImpl implements FollowDao{
	private String ns = "Follow_Mapper.";
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<Follow> findAllFollow() {
		return sqlSession.selectList(ns+"findAllFollow");
	}

	@Override
	public List<Follow> findFollow(Follow follow) {
		return sqlSession.selectList(ns+"findFollow",follow);
	}

	@Override
	public int addFollow(Follow follow) {
		return sqlSession.insert(ns+"addFollow",follow);
	}

	@Override
	public int updateFollow(Follow follow) {
		return sqlSession.update(ns+"updateFollow",follow);
	}

	@Override
	public int deleteFollow(Follow follow) {
		return sqlSession.delete(ns+"deleteFollow",follow);
	}

	@Override
	public long findFollowingCount(long idMemberFollower) {
		return sqlSession.selectOne(ns+"findFollowingCount",idMemberFollower);
	}

	@Override
	public long findFollowerCount(long idMemberFollowing) {
		return sqlSession.selectOne(ns+"findFollowerCount",idMemberFollowing);
	}

	
 
}
