package com.ssafy.coco.dao;

import java.util.List;

import com.ssafy.coco.vo.Like;

public interface LikeDao {
	public List<Like> findAllLike();
	public List<Like> findLike(Like like);
	public int addLike(Like like);
	public int updateLike(Like like);
	public int deleteLike(Like like);
}
