package com.ssafy.coco.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.coco.dao.BabyPostDao;
import com.ssafy.coco.dao.CommentDao;
import com.ssafy.coco.dao.FollowDao;
import com.ssafy.coco.dao.LikeDao;
import com.ssafy.coco.dao.MemberDao;
import com.ssafy.coco.dao.MemberTagDao;
import com.ssafy.coco.dao.MyPageDao;
import com.ssafy.coco.service.CommentService;
import com.ssafy.coco.service.FollowService;
import com.ssafy.coco.service.LikeService;
import com.ssafy.coco.service.MemberService;
import com.ssafy.coco.service.MemberTagService;
import com.ssafy.coco.service.MyPageService;
import com.ssafy.coco.vo.BabyPost;
import com.ssafy.coco.vo.Follow;
import com.ssafy.coco.vo.Like;
import com.ssafy.coco.vo.Member;
import com.ssafy.coco.vo.MemberTag;
import com.ssafy.coco.vo.Mypage;
@Service
public class MypageServiceImpl implements MyPageService{

	@Autowired
	private MyPageDao myPageDao;

	@Override
	public List<Mypage> findAllMypage() {
		return myPageDao.findAllMypage();
	}

	@Override
	public List<Mypage> findMypage(Mypage mypage) {
		return myPageDao.findMypage(mypage);
	}

	@Override
	public int addMypage(Mypage mypage) {
		return myPageDao.addMypage(mypage);
	}

	@Override
	public int updateMypage(Mypage mypage) {
		return myPageDao.updateMypage(mypage);
	}

	@Override
	public int deleteMypage(Mypage mypage) {
		return myPageDao.deleteMypage(mypage);
	}

	@Override
	public int updateMypageVisitedCount(long idMember) {
		return myPageDao.updateMypageVisitedCount(idMember);
	}

	@Override
	public int updateMypageTodayVisitedReset() {
		return myPageDao.updateMypageTodayVisitedReset();
	}


	

}
