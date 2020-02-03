package com.ssafy.coco.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.coco.dao.BabyPostDao;
import com.ssafy.coco.dao.CommentDao;
import com.ssafy.coco.dao.FollowDao;
import com.ssafy.coco.dao.LikeDao;
import com.ssafy.coco.dao.MemberDao;
import com.ssafy.coco.service.CommentService;
import com.ssafy.coco.service.FollowService;
import com.ssafy.coco.service.LikeService;
import com.ssafy.coco.service.MemberService;
import com.ssafy.coco.vo.BabyPost;
import com.ssafy.coco.vo.Follow;
import com.ssafy.coco.vo.Like;
import com.ssafy.coco.vo.Member;
@Service
public class MemberServiceImpl implements MemberService{
	@Autowired
	private MemberDao memberDao;

	@Override
	public List<Member> findAllMember() {
		return memberDao.findAllMember();
	}

	@Override
	public List<Member> findMember(Member member) {
		return memberDao.findMember(member);
	}

	@Override
	public int addMember(Member member) {
		return memberDao.addMember(member);
	}

	@Override
	public int updateMember(Member member) {
		return memberDao.updateMember(member);
	}

	@Override
	public int deleteMember(Member member) {
		return memberDao.deleteMember(member);
	}

	@Override
	public List<Member> findFollowingMemberList(long idMemberFollower) {
		return memberDao.findFollowingMemberList(idMemberFollower);
	}
	@Override
	public boolean check(String id) {
		Member m = new Member();
		m.setId(id);
		int size = memberDao.findMember(m).size();
		System.out.println("in check id"+id);
		System.out.println("in check size"+size);
		return size == 0 ? true : false;
	}
	
	

	@Override
	public List<Member> findFollowerMemberList(long idMemeberFollowing) {
		return memberDao.findFollowerMemberList(idMemeberFollowing);
	}

	@Override
	public List<Member> findWhoPressedTheLikeButton(long idPost) {
		return memberDao.findWhoPressedTheLikeButton(idPost);
	}
}
