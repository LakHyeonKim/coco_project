package com.ssafy.coco.relationvo;

import org.springframework.web.multipart.MultipartFile;


public class MemberInfoModify {
	private MultipartFile bannerImage;
	private MultipartFile profileImage;
	private long idmember;
	private String bannerText;
	private String password;
	private String tags;
	private String nickName;
	private String gitUrl;
	private String kakaoUrl;
	private String instagramUrl;
	
	
	
	public MemberInfoModify() {
		super();
	}



	public MemberInfoModify(MultipartFile bannerImage, MultipartFile profileImage, long idmember, String bannerText,
			String password, String tags, String nickName, String gitUrl, String kakaoUrl, String instagramUrl) {
		super();
		this.bannerImage = bannerImage;
		this.profileImage = profileImage;
		this.idmember = idmember;
		this.bannerText = bannerText;
		this.password = password;
		this.tags = tags;
		this.nickName = nickName;
		this.gitUrl = gitUrl;
		this.kakaoUrl = kakaoUrl;
		this.instagramUrl = instagramUrl;
	}



	public MultipartFile getBannerImage() {
		return bannerImage;
	}



	public void setBannerImage(MultipartFile bannerImage) {
		this.bannerImage = bannerImage;
	}



	public MultipartFile getProfileImage() {
		return profileImage;
	}



	public void setProfileImage(MultipartFile profileImage) {
		this.profileImage = profileImage;
	}



	public long getIdmember() {
		return idmember;
	}



	public void setIdmember(long idmember) {
		this.idmember = idmember;
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



	public String getTags() {
		return tags;
	}



	public void setTags(String tags) {
		this.tags = tags;
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



	@Override
	public String toString() {
		return "MemberInfoModify [bannerImage=" + bannerImage + ", profileImage=" + profileImage + ", idmember="
				+ idmember + ", bannerText=" + bannerText + ", password=" + password + ", tags=" + tags + ", nickName="
				+ nickName + ", gitUrl=" + gitUrl + ", kakaoUrl=" + kakaoUrl + ", instagramUrl=" + instagramUrl + "]";
	}
	
	
	
}
	
