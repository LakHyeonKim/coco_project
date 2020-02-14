package com.ssafy.coco.service;

import java.util.List;

import com.ssafy.coco.vo.Member;


public interface MemberService {
	public List<Member> findAllMember();
	public List<Member> findMember(Member member);
	public List<Member> findWhoPressedTheLikeButton(long idPost);
	public int addMember(Member member);
	public int updateMember(Member member);
	public int deleteMember(Member member);
	public List<Member> findFollowingMemberList(long idMemberFollower);
	public List<Member> findFollowerMemberList(long idMemeberFollowing);
	public boolean check(String id);
	public int findIdByRefreshToken(String refToken);
	public int updateRefreshToken(Member member);
	
}
