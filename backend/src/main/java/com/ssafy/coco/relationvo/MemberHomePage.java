package com.ssafy.coco.relationvo;

import java.util.List;

import com.ssafy.coco.vo.Mypage;
import com.ssafy.coco.vo.Post;
import com.ssafy.coco.vo.Tag;

public class MemberHomePage {
	private Mypage mypage;
	private List<String> tags;
	private long followingCount;
	private long followerCount;
	private int totalPostCount;
	
	public MemberHomePage() {
		super();
	}
	public MemberHomePage(Mypage mypage, List<String> tags, long followingCount, long followerCount, int totalPostCount) {
		super();
		this.mypage = mypage;
		this.tags = tags;
		this.followingCount = followingCount;
		this.followerCount = followerCount;
		this.totalPostCount = totalPostCount;
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
	public void setFollowingCount(int followingCount) {
		this.followingCount = followingCount;
	}
	public long getFollowerCount() {
		return followerCount;
	}
	public void setFollowerCount(int followerCount) {
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
		return "MemberHomePage [mypage=" + mypage + ", tags=" + tags + ", followingCount=" + followingCount
				+ ", followerCount=" + followerCount + ", totalPostCount=" + totalPostCount + "]";
	}

}
