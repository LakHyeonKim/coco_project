package com.ssafy.coco.service;

import java.util.List;

import com.ssafy.coco.vo.Follow;


public interface FollowService {
	public List<Follow> findAllFollow();
	public List<Follow> findFollow(Follow follow);
	public int addFollow(Follow follow);
	public int updateFollow(Follow follow);
	public int deleteFollow(Follow follow);
	public long findFollowingCount(long idMemberFollower);
	public long findFollowerCount(long idMemberFollowing);
}
