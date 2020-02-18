package com.ssafy.coco.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.coco.dao.FollowDao;
import com.ssafy.coco.dao.MemberDao;
import com.ssafy.coco.relationvo.LikeInfo;
import com.ssafy.coco.relationvo.PostAndMember;
import com.ssafy.coco.service.LikeInfoService;
import com.ssafy.coco.vo.Follow;
import com.ssafy.coco.vo.Member;

@Service
public class LikeInfoServiceImpl implements LikeInfoService {
	
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private FollowDao followDao;

	@Override
	public List<LikeInfo> findWhoPressedTheLikeButton(PostAndMember postAndMember) {
		List<Member> members = memberDao.findWhoPressedTheLikeButton(postAndMember.getPost().getIdpost());
		List<LikeInfo> result = new ArrayList<LikeInfo>();
		for(Member member : members) {
			int isFollow = 1;
			if(followDao.findFollow(new Follow(0, postAndMember.getMember().getIdmember(), member.getIdmember(), 0)).size() == 0) {
				isFollow = 0;
			}
			result.add(new LikeInfo(member, isFollow));
		}
		return result;
	}

}
