package com.ssafy.coco.service;

import com.ssafy.coco.relationvo.MemberHomePage;
import com.ssafy.coco.relationvo.MemberInfoModifyReceive;

public interface MemberHomePageService {
	public MemberHomePage findByMemberHomePageUserID(long myIdMember, long youIdMember);
	public MemberInfoModifyReceive findByMemberHomePageModify(long myIdMember);
}