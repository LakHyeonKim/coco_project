package com.ssafy.coco.relationvo;

import java.util.List;

import com.ssafy.coco.vo.Member;
import com.ssafy.coco.vo.Tag;

public class MemberInfo {
	private Member member;
	private List<Tag> tags;
	
	public MemberInfo() {
		super();
	}

	public MemberInfo(Member member, List<Tag> tags) {
		super();
		this.member = member;
		this.tags = tags;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {
		return "MemberInfo [member=" + member + ", tags=" + tags + "]";
	}
	
}
