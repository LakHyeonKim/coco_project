package com.ssafy.coco.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.coco.dao.BabyPostDao;
import com.ssafy.coco.dao.CommentDao;
import com.ssafy.coco.dao.FollowDao;
import com.ssafy.coco.dao.LikeDao;
import com.ssafy.coco.service.CommentService;
import com.ssafy.coco.service.FollowService;
import com.ssafy.coco.service.LikeService;
import com.ssafy.coco.vo.BabyPost;
import com.ssafy.coco.vo.Follow;
import com.ssafy.coco.vo.Like;
@Service
public class LikeServiceImpl implements LikeService{

	@Autowired
	private LikeDao likeDao;

	@Override
	public List<Like> findAllLike() {
		return likeDao.findAllLike();
	}

	@Override
	public List<Like> findLike(Like like) {
		return likeDao.findLike(like);
	}

	@Override
	public int addLike(Like like) {
		return likeDao.addLike(like);	
	}

	@Override
	public int updateLike(Like like) {
		return likeDao.updateLike(like);
	}

	@Override
	public int deleteLike(Like like) {
		return likeDao.deleteLike(like);
	}

	

}
