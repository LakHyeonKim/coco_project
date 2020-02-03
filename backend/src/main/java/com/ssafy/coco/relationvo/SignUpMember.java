package com.ssafy.coco.relationvo;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.coco.vo.Member;

public class SignUpMember {
	private MultipartFile file;
	private Member member;
	
	public SignUpMember() {
		super();
	}
	public SignUpMember(MultipartFile file, Member member) {
		super();
		this.file = file;
		this.member = member;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	
	@Override
	public String toString() {
		return "SignUpMember [file=" + file + ", member=" + member + "]";
	}
	
	
	
}
