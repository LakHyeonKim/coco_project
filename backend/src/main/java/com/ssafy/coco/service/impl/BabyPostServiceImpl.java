package com.ssafy.coco.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.coco.dao.AlarmDao;
import com.ssafy.coco.dao.BabyPostDao;
import com.ssafy.coco.service.AlarmService;
import com.ssafy.coco.service.BabyPostService;
import com.ssafy.coco.vo.Alarm;
import com.ssafy.coco.vo.BabyPost;

@Service
public class BabyPostServiceImpl implements BabyPostService{
	@Autowired
	private BabyPostDao babyPostDao;

	@Override
	public List<BabyPost> findAllBabyPost() {
		return babyPostDao.findAllBabyPost();
	}

	@Override
	public List<BabyPost> findBabyPost(BabyPost babyPost) {
		return babyPostDao.findBabyPost(babyPost);
	}

	@Override
	public int addBabyPost(BabyPost babyPost) {
		return babyPostDao.addBabyPost(babyPost);
	}

	@Override
	public int updateBabyPost(BabyPost babyPost) {
		return babyPostDao.updateBabyPost(babyPost);
	}

	@Override
	public int deleteBabyPost(BabyPost babyPost) {
		return babyPostDao.deleteBabyPost(babyPost);
	}

}
