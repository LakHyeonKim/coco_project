package com.ssafy.coco.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.coco.dao.FollowDao;
import com.ssafy.coco.dao.MemberDao;
import com.ssafy.coco.relationvo.FollowInfo;
import com.ssafy.coco.service.FollowInfoService;
import com.ssafy.coco.vo.Follow;
import com.ssafy.coco.vo.Member;

@Service
public class FollowInfoServiceImpl implements FollowInfoService {
	
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private FollowDao followDao;

	@Override
	public List<FollowInfo> findByFollowingListMember(long myIdMember, long youIdMember) {
		List<Member> list = memberDao.findFollowingMemberList(youIdMember);
		return followCheck(list,myIdMember);
	}

	@Override
	public List<FollowInfo> findByFollowerListMember(long myIdMember, long youIdMember) {
		List<Member> list = memberDao.findFollowerMemberList(youIdMember);
		return followCheck(list,myIdMember);
	}
	
	private List<FollowInfo> followCheck(List<Member> list, long myIdMember) {
		List<FollowInfo> results = new ArrayList<>();
		for(Member member : list) {
			long youIdMember = member.getIdmember();
			int isFollow = 0;
			if(followDao.findFollow(new Follow(0, myIdMember, youIdMember, 0)).size() != 0) {
				isFollow = 1;
			}
			results.add(new FollowInfo(member, isFollow));
		}
		return results;
	}


}
