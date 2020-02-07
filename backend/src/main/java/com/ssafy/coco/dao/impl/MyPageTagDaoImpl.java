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
import com.ssafy.coco.dao.MyPageTagDao;
import com.ssafy.coco.vo.Alarm;
import com.ssafy.coco.vo.BabyPost;
import com.ssafy.coco.vo.Follow;
import com.ssafy.coco.vo.Like;
import com.ssafy.coco.vo.Member;
import com.ssafy.coco.vo.MemberTag;
import com.ssafy.coco.vo.Mypage;
import com.ssafy.coco.vo.MypageTag;

@Repository
public class MyPageTagDaoImpl implements MyPageTagDao{
	private String ns = "MypageTag_Mapper.";
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<MypageTag> findAllMypageTag() {
		return sqlSession.selectList(ns+"findAllMypageTag");
	}

	@Override
	public List<MypageTag> findMypageTag(MypageTag mypagetag) {
		return sqlSession.selectList(ns+"findMypageTag",mypagetag);
	}

	@Override
	public int addMypageTag(MypageTag mypagetag) {
		return sqlSession.insert(ns+"addMypageTag",mypagetag);
	}

	@Override
	public int deleteMypageTag(MypageTag mypagetag) {
		return sqlSession.delete(ns+"deleteMypageTag",mypagetag);
	}

 
}
