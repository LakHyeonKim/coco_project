package com.ssafy.coco.service;

import java.util.List;

import com.ssafy.coco.vo.Like;

public interface LikeService {
	public List<Like> findAllLike();
	public List<Like> findLike(Like like);
	public int addLike(Like like);
	public int updateLike(Like like);
	public int deleteLike(Like like);
}
