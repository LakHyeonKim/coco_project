package com.ssafy.coco.service;

import java.util.List;

import com.ssafy.coco.relationvo.FollowInfo;

public interface FollowInfoService {
	public List<FollowInfo> findByFollowingListMember(long myIdMember, long youIdMember);
	public List<FollowInfo> findByFollowerListMember(long myIdMember, long youIdMember);
}
