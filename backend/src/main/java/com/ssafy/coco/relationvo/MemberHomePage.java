package com.ssafy.coco.relationvo;

import java.util.List;

import com.ssafy.coco.vo.Member;
import com.ssafy.coco.vo.Mypage;

public class MemberHomePage {
	private Member member;
	private Mypage mypage;
	private List<String> tags;
	private long followingCount;
	private long followerCount;
	private int totalPostCount;
	private int isFollow;
	
	public int getIsFollew() {
		return isFollow;
	}

	public void setIsFollew(int isFollew) {
		this.isFollow = isFollew;
	}

	public MemberHomePage(Member member, Mypage mypage, List<String> tags, long followingCount, long followerCount,
			int totalPostCount, int isFollew) {
		super();
		this.member = member;
		this.mypage = mypage;
		this.tags = tags;
		this.followingCount = followingCount;
		this.followerCount = followerCount;
		this.totalPostCount = totalPostCount;
		this.isFollow = isFollew;
	}

	public MemberHomePage() {
		super();
	}

	public MemberHomePage(Member member, Mypage mypage, List<String> tags, long followingCount, long followerCount,
			int totalPostCount) {
		super();
		this.member = member;
		this.mypage = mypage;
		this.tags = tags;
		this.followingCount = followingCount;
		this.followerCount = followerCount;
		this.totalPostCount = totalPostCount;
	}
	

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Mypage getMypage() {
		return mypage;
	}

	public void setMypage(Mypage mypage) {
		this.mypage = mypage;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public long getFollowingCount() {
		return followingCount;
	}

	public void setFollowingCount(long followingCount) {
		this.followingCount = followingCount;
	}

	public long getFollowerCount() {
		return followerCount;
	}

	public void setFollowerCount(long followerCount) {
		this.followerCount = followerCount;
	}

	public int getTotalPostCount() {
		return totalPostCount;
	}

	public void setTotalPostCount(int totalPostCount) {
		this.totalPostCount = totalPostCount;
	}

	@Override
	public String toString() {
		return "MemberHomePage [member=" + member + ", mypage=" + mypage + ", tags=" + tags + ", followingCount="
				+ followingCount + ", followerCount=" + followerCount + ", totalPostCount=" + totalPostCount
				+ ", isFollew=" + isFollow + "]";
	}
	
	
	
}
