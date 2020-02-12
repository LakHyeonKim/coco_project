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
import com.ssafy.coco.dao.MemberDao;
import com.ssafy.coco.dao.MemberTagDao;
import com.ssafy.coco.dao.MyPageDao;
import com.ssafy.coco.vo.Alarm;
import com.ssafy.coco.vo.BabyPost;
import com.ssafy.coco.vo.Follow;
import com.ssafy.coco.vo.Like;
import com.ssafy.coco.vo.Member;
import com.ssafy.coco.vo.MemberTag;
import com.ssafy.coco.vo.Mypage;

@Repository
public class MyPageDaoImpl implements MyPageDao{
	private String ns = "Mypage_Mapper.";
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<Mypage> findAllMypage() {
		return sqlSession.selectList(ns+"findAllMypage");
	}

	@Override
	public List<Mypage> findMypage(Mypage mypage) {
		return sqlSession.selectList(ns+"findMypage",mypage);
	}

	@Override
	public int addMypage(Mypage mypage) {
		return sqlSession.insert(ns+"addMypage",mypage);
	}

	@Override
	public int updateMypage(Mypage mypage) {
		return sqlSession.update(ns+"updateMypage",mypage);
	}

	@Override
	public int deleteMypage(Mypage mypage) {
		return sqlSession.delete(ns+"deleteMypage",mypage);
	}

	@Override
	public int updateMypageVisitedCount(long idMember) {
		return sqlSession.update(ns+"updateMypageVisitedCount",idMember);
	}

	@Override
	public int updateMypageTodayVisitedReset() {
		return sqlSession.update(ns+"updateMypageTodayVisitedReset");
	}
 
}
