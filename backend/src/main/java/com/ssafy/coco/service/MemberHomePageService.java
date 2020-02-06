package com.ssafy.coco.service;

import com.ssafy.coco.relationvo.MemberHomePage;

public interface MemberHomePageService {
	public MemberHomePage findByMemberHomePageUserID(long myIdMember, long youIdMember);
}