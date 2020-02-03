package com.ssafy.coco.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.coco.dao.BabyPostDao;
import com.ssafy.coco.dao.CommentDao;
import com.ssafy.coco.dao.FollowDao;
import com.ssafy.coco.service.CommentService;
import com.ssafy.coco.service.FollowService;
import com.ssafy.coco.vo.BabyPost;
import com.ssafy.coco.vo.Follow;
@Service
public class FollowServiceImpl implements FollowService{

	@Autowired
	private FollowDao followDao;

	@Override
	public List<Follow> findAllFollow() {
		return followDao.findAllFollow();
	}

	@Override
	public List<Follow> findFollow(Follow follow) {
		return followDao.findFollow(follow);
	}

	@Override
	public int addFollow(Follow follow) {
		return followDao.addFollow(follow);
	}

	@Override
	public int updateFollow(Follow follow) {
		return followDao.updateFollow(follow);
	}

	@Override
	public int deleteFollow(Follow follow) {
		return followDao.deleteFollow(follow);
	}

	@Override
	public long findFollowingCount(long idMemberFollower) {
		return followDao.findFollowingCount(idMemberFollower);
	}

	@Override
	public long findFollowerCount(long idMemberFollowing) {
		return followDao.findFollowerCount(idMemberFollowing);
	}
}
