package com.ssafy.coco.dao.impl;

import java.util.List;
import java.util.Map;

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
import com.ssafy.coco.vo.Alarm;
import com.ssafy.coco.vo.BabyPost;
import com.ssafy.coco.vo.Follow;
import com.ssafy.coco.vo.Like;
import com.ssafy.coco.vo.Member;
import com.ssafy.coco.vo.MemberTag;

@Repository
public class MemberTagImpl implements MemberTagDao{
	private String ns = "MemberTag_Mapper.";
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<MemberTag> findAllMemberTag() {
		return sqlSession.selectList(ns+"findAllMemberTag");
	}

	@Override
	public List<MemberTag> findMemberTag(MemberTag membertag) {
		return sqlSession.selectList(ns+"findMemberTag",membertag);
	}

	@Override
	public int addMemberTag(MemberTag membertag) {
		return sqlSession.insert(ns+"addMemberTag",membertag);
	}

	@Override
	public int updateMemberTag(MemberTag membertag) {
		return sqlSession.update(ns+"updateMemberTag",membertag);
	}

	@Override
	public int deleteMemberTag(MemberTag membertag) {
		return sqlSession.delete(ns+"deleteMemberTag",membertag);
	}

	@Override
	public int updateMemberTagFrequencyCount(Map<String, Long> hashMap) {
		return sqlSession.update(ns+"updateMemberTagFrequencyCount", hashMap);
	}
}
