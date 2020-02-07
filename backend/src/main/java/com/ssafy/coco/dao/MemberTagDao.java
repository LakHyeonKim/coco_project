package com.ssafy.coco.dao;

import java.util.List;
import java.util.Map;

import com.ssafy.coco.vo.MemberTag;

public interface MemberTagDao {
	public List<MemberTag> findAllMemberTag();
	public List<MemberTag> findMemberTag(MemberTag membertag);
	public int addMemberTag(MemberTag membertag);
	public int updateMemberTag(MemberTag membertag);
	public int updateMemberTagFrequencyCount(Map<String,Long> hashMap);
	public int deleteMemberTag(MemberTag membertag);
}
