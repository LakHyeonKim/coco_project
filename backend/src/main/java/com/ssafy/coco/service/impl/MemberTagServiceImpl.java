package com.ssafy.coco.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.coco.dao.BabyPostDao;
import com.ssafy.coco.dao.CommentDao;
import com.ssafy.coco.dao.FollowDao;
import com.ssafy.coco.dao.LikeDao;
import com.ssafy.coco.dao.MemberDao;
import com.ssafy.coco.dao.MemberTagDao;
import com.ssafy.coco.service.CommentService;
import com.ssafy.coco.service.FollowService;
import com.ssafy.coco.service.LikeService;
import com.ssafy.coco.service.MemberService;
import com.ssafy.coco.service.MemberTagService;
import com.ssafy.coco.vo.BabyPost;
import com.ssafy.coco.vo.Follow;
import com.ssafy.coco.vo.Like;
import com.ssafy.coco.vo.Member;
import com.ssafy.coco.vo.MemberTag;
@Service
public class MemberTagServiceImpl implements MemberTagService{

	@Autowired
	private MemberTagDao memberTagDao;

	@Override
	public List<MemberTag> findAllMemberTag() {
		return memberTagDao.findAllMemberTag();
	}

	@Override
	public List<MemberTag> findMemberTag(MemberTag membertag) {
		return memberTagDao.findMemberTag(membertag);
	}

	@Override
	public int addMemberTag(MemberTag membertag) {
		return memberTagDao.addMemberTag(membertag);
	}

	@Override
	public int updateMemberTag(MemberTag membertag) {
		return memberTagDao.updateMemberTag(membertag);
	}

	@Override
	public int deleteMemberTag(MemberTag membertag) {
		return memberTagDao.deleteMemberTag(membertag);
	}

	@Override
	public int updateMemberTagFrequencyCount(Map<String, Long> hashMap) {
		return memberTagDao.updateMemberTagFrequencyCount(hashMap);
	}

}
