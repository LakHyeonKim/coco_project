package com.ssafy.coco.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.coco.dao.FollowDao;
import com.ssafy.coco.dao.MemberDao;
import com.ssafy.coco.dao.MyPageDao;
import com.ssafy.coco.dao.PostDao;
import com.ssafy.coco.dao.TagDao;
import com.ssafy.coco.relationvo.MemberHomePage;
import com.ssafy.coco.service.MemberHomePageService;
import com.ssafy.coco.vo.Member;
import com.ssafy.coco.vo.Mypage;
import com.ssafy.coco.vo.Post;
import com.ssafy.coco.vo.Tag;
@Service
public class MemberHomePageServiceImpl implements MemberHomePageService{
	
	@Autowired
	private MyPageDao mypageDao;
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private TagDao TagDao;
	@Autowired
	private PostDao postDao;
	@Autowired
	private FollowDao followDao;
	
	@Override
	public MemberHomePage findByMemberHomePageUserID(long myIdMember, long youIdMember) {
		if(myIdMember != youIdMember) {
			mypageDao.updateMypageVisitedCount(youIdMember);
		}
		List<Mypage> myPageList = mypageDao.findMypage(new Mypage(0, youIdMember, null, null, 0, 0, 0));
		Mypage mypage = myPageList.get(0);
		List<Tag> myPageTagList = TagDao.findAllTagIncludedMypage(mypage.getIdmypage());
		List<String> tags = new ArrayList<>();
		for(Tag tag : myPageTagList) {
			tags.add(tag.getTagName());
		}
		long followingCount = followDao.findFollowingCount(youIdMember);
		long followerCount = followDao.findFollowerCount(youIdMember);
		int totalPostCount = postDao.findPost(new Post(0, youIdMember, null, null, null, null, null, 0, 0, null, 0, 0, 0)).size();
		Member findMember = new Member();
		findMember.setIdmember(youIdMember);
		List<Member> member = memberDao.findMember(findMember);
		List<Member> myFollowingMember = memberDao.findFollowingMemberList(myIdMember);
		int isFollew = 0;
		for(Member followMember : myFollowingMember) {
			if(followMember.getIdmember() == youIdMember) {
				isFollew = 1;
				break;
			}
		}
		return new MemberHomePage(member.get(0), mypage, tags, followingCount, followerCount, totalPostCount, isFollew);
	}
}
