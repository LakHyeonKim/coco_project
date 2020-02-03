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
import com.ssafy.coco.vo.Alarm;
import com.ssafy.coco.vo.BabyPost;
import com.ssafy.coco.vo.Follow;
import com.ssafy.coco.vo.Like;
import com.ssafy.coco.vo.Member;

@Repository
public class MemberDaoImpl implements MemberDao{
	private String ns = "Member_Mapper.";
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<Member> findAllMember() {
		return sqlSession.selectList(ns+"findAllMember");
	}

	@Override
	public List<Member> findMember(Member member) {
		return sqlSession.selectList(ns+"findMember",member);
	}

	@Override
	public int addMember(Member member) {
		return sqlSession.insert(ns+"addMember",member);
	}

	@Override
	public int updateMember(Member member) {
		return sqlSession.update(ns+"updateMember",member);
	}

	@Override
	public int deleteMember(Member member) {
		return sqlSession.delete(ns+"deleteMember",member);
	}

	@Override
	public List<Member> findFollowingMemberList(long idMemberFollower) {
		return sqlSession.selectList(ns+"findFollowingMemberList",idMemberFollower);
	}

	@Override
	public List<Member> findFollowerMemberList(long idMemeberFollowing) {
		return sqlSession.selectList(ns+"findFollowerMemberList",idMemeberFollowing);
	}

	@Override
	public List<Member> findWhoPressedTheLikeButton(long idPost) {
		return sqlSession.selectList(ns+"findWhoPressedTheLikeButton",idPost);
	}
}
