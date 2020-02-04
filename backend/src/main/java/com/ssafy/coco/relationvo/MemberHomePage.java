package com.ssafy.coco.relationvo;

import java.util.List;

import com.ssafy.coco.vo.Mypage;
import com.ssafy.coco.vo.Post;
import com.ssafy.coco.vo.Tag;

public class MemberHomePage {
	private MemberInfo memberInfo;
	private Mypage mypage;
	private List<Tag> tags;
	private List<Post> posts;
	private long followingCount;
	private long followerCount;
	
	public MemberHomePage() {
		super();
	}

	public MemberHomePage(MemberInfo memberInfo, Mypage mypage, List<Tag> tags, long followingCount,
			long followerCount) {
		super();
		this.memberInfo = memberInfo;
		this.mypage = mypage;
		this.tags = tags;
		this.followingCount = followingCount;
		this.followerCount = followerCount;
	}

	public MemberInfo getMemberInfo() {
		return memberInfo;
	}

	public void setMemberInfo(MemberInfo memberInfo) {
		this.memberInfo = memberInfo;
	}

	public Mypage getMypage() {
		return mypage;
	}

	public void setMypage(Mypage mypage) {
		this.mypage = mypage;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
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

	@Override
	public String toString() {
		return "MemberHomePage [memberInfo=" + memberInfo + ", mypage=" + mypage + ", tags=" + tags
				+ ", followingCount=" + followingCount + ", followerCount=" + followerCount + "]";
	}
	
}
