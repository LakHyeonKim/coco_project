package com.ssafy.coco.dao;

import java.util.List;

import com.ssafy.coco.vo.BabyPost;

public interface BabyPostDao {
	public List<BabyPost> findAllBabyPost();
	public List<BabyPost> findBabyPost(BabyPost babyPost);
	public int addBabyPost(BabyPost babyPost);
	public int updateBabyPost(BabyPost babyPost);
	public int deleteBabyPost(BabyPost babyPost);
}
