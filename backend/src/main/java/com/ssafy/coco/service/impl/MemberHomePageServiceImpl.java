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
	public MemberHomePage findByMemberHomePageUserID(long idMember) {
		List<Mypage> myPageList = mypageDao.findMypage(new Mypage(0, idMember, null, null, 0, 0, 0));
		Mypage mypage = myPageList.get(0);
		List<Tag> myPageTagList = TagDao.findAllTagIncludedMypage(mypage.getIdmypage());
		List<String> tags = new ArrayList<>();
		for(Tag tag : myPageTagList) {
			tags.add(tag.getTagName());
		}
		long followingCount = followDao.findFollowingCount(idMember);
		long followerCount = followDao.findFollowerCount(idMember);
		int totalPostCount = postDao.findPost(new Post(0, idMember, null, null, null, null, null, 0, 0, null, 0, 0, 0)).size();
		return new MemberHomePage(mypage, tags, followingCount, followerCount, totalPostCount);
	}
}
