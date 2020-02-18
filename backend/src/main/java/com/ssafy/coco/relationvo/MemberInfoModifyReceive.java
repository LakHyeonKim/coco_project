package com.ssafy.coco.relationvo;

import java.util.Arrays;
import java.util.List;

public class MemberInfoModifyReceive {
	private String profileImageUrl;
	private String bannerImageUrl;
	private String bannerText;
	private String id;
	private String password;
	private List<String> tags;
	private String nickName;
	private String gitUrl;
	private String kakaoUrl;
	private String instagramUrl;
	
	public MemberInfoModifyReceive(String profileImageUrl, String bannerImageUrl, String bannerText, String id,
			String password, List<String> tags, String nickName, String gitUrl, String kakaoUrl, String instagramUrl) {
		super();
		this.profileImageUrl = profileImageUrl;
		this.bannerImageUrl = bannerImageUrl;
		this.bannerText = bannerText;
		this.id = id;
		this.password = password;
		this.tags = tags;
		this.nickName = nickName;
		this.gitUrl = gitUrl;
		this.kakaoUrl = kakaoUrl;
		this.instagramUrl = instagramUrl;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public MemberInfoModifyReceive() {
		super();
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public String getBannerImageUrl() {
		return bannerImageUrl;
	}

	public void setBannerImageUrl(String bannerImageUrl) {
		this.bannerImageUrl = bannerImageUrl;
	}

	public String getBannerText() {
		return bannerText;
	}

	public void setBannerText(String bannerText) {
		this.bannerText = bannerText;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getGitUrl() {
		return gitUrl;
	}

	public void setGitUrl(String gitUrl) {
		this.gitUrl = gitUrl;
	}

	public String getKakaoUrl() {
		return kakaoUrl;
	}

	public void setKakaoUrl(String kakaoUrl) {
		this.kakaoUrl = kakaoUrl;
	}

	public String getInstagramUrl() {
		return instagramUrl;
	}

	public void setInstagramUrl(String instagramUrl) {
		this.instagramUrl = instagramUrl;
	}

	public MemberInfoModifyReceive(String profileImageUrl, String bannerImageUrl, String bannerText, String password,
			List<String> tags, String nickName, String gitUrl, String kakaoUrl, String instagramUrl) {
		super();
		this.profileImageUrl = profileImageUrl;
		this.bannerImageUrl = bannerImageUrl;
		this.bannerText = bannerText;
		this.password = password;
		this.tags = tags;
		this.nickName = nickName;
		this.gitUrl = gitUrl;
		this.kakaoUrl = kakaoUrl;
		this.instagramUrl = instagramUrl;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {
		return "MemberInfoModifyReceive [profileImageUrl=" + profileImageUrl + ", bannerImageUrl=" + bannerImageUrl
				+ ", bannerText=" + bannerText + ", id=" + id + ", password=" + password + ", tags=" + tags
				+ ", nickName=" + nickName + ", gitUrl=" + gitUrl + ", kakaoUrl=" + kakaoUrl + ", instagramUrl="
				+ instagramUrl + "]";
	}


}
