package com.ssafy.coco.dao;

import java.util.List;

import com.ssafy.coco.vo.Member;


public interface MemberDao {
	public List<Member> findAllMember();
	public List<Member> findMember(Member member);
	public List<Member> findWhoPressedTheLikeButton(long idPost);
	public int addMember(Member member);
	public int updateMember(Member member);
	public int deleteMember(Member member);
	public List<Member> findFollowingMemberList(long idMemberFollower);
	public List<Member> findFollowerMemberList(long idMemeberFollowing);
}
