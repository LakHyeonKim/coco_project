package com.ssafy.coco.vo;

import java.sql.Timestamp;

public class Member {
	long idmember;
	long rankId;
	int isManager;
	int isDelete;
	String nickname;
	String id;
	String password;
	String email;
	String gitUrl;
	String kakaoUrl;
	String instagramUrl;
	Timestamp dateCreated;
	Timestamp updateCreated;
	String grade;
	String imageUrl;
	String refreshToken;
	
	public Member() {
		super();
	}
	
	

	public Member(long idmember, long rankId, int isManager, int isDelete, String nickname, String id, String password,
			String email, String gitUrl, String kakaoUrl, String instagramUrl, Timestamp dateCreated,
			Timestamp updateCreated, String grade, String imageUrl, String refreshToken) {
		super();
		this.idmember = idmember;
		this.rankId = rankId;
		this.isManager = isManager;
		this.isDelete = isDelete;
		this.nickname = nickname;
		this.id = id;
		this.password = password;
		this.email = email;
		this.gitUrl = gitUrl;
		this.kakaoUrl = kakaoUrl;
		this.instagramUrl = instagramUrl;
		this.dateCreated = dateCreated;
		this.updateCreated = updateCreated;
		this.grade = grade;
		this.imageUrl = imageUrl;
		this.refreshToken = refreshToken;
	}



	public Member(long idmember, long rankId, int isManager, int isDelete, String nickname, String id, String password,
			String email, String gitUrl, String kakaoUrl, String instagramUrl, Timestamp dateCreated,
			Timestamp updateCreated, String grade, String refreshToken) {
		super();
		this.idmember = idmember;
		this.rankId = rankId;
		this.isManager = isManager;
		this.isDelete = isDelete;
		this.nickname = nickname;
		this.id = id;
		this.password = password;
		this.email = email;
		this.gitUrl = gitUrl;
		this.kakaoUrl = kakaoUrl;
		this.instagramUrl = instagramUrl;
		this.dateCreated = dateCreated;
		this.updateCreated = updateCreated;
		this.grade = grade;
		this.refreshToken = refreshToken;
	}

	public Member(long idmember, long rankId, int isManager, int isDelete, String nickname, String id, String password,
			String email, String gitUrl, String kakaoUrl, String instagramUrl, Timestamp dateCreated,
			Timestamp updateCreated, String grade) {
		super();
		this.idmember = idmember;
		this.rankId = rankId;
		this.isManager = isManager;
		this.isDelete = isDelete;
		this.nickname = nickname;
		this.id = id;
		this.password = password;
		this.email = email;
		this.gitUrl = gitUrl;
		this.kakaoUrl = kakaoUrl;
		this.instagramUrl = instagramUrl;
		this.dateCreated = dateCreated;
		this.updateCreated = updateCreated;
		this.grade = grade;
	}

	public long getIdmember() {
		return idmember;
	}

	public void setIdmember(long idmember) {
		this.idmember = idmember;
	}

	public long getRankId() {
		return rankId;
	}

	public void setRankId(long rankId) {
		this.rankId = rankId;
	}

	public int getIsManager() {
		return isManager;
	}

	public void setIsManager(int isManager) {
		this.isManager = isManager;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Timestamp getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Timestamp getUpdateCreated() {
		return updateCreated;
	}

	public void setUpdateCreated(Timestamp updateCreated) {
		this.updateCreated = updateCreated;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	
	public String getImageUrl() {
		return imageUrl;
	}


	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


	@Override
	public String toString() {
		return "Member [idmember=" + idmember + ", rankId=" + rankId + ", isManager=" + isManager + ", isDelete="
				+ isDelete + ", nickname=" + nickname + ", id=" + id + ", password=" + password + ", email=" + email
				+ ", gitUrl=" + gitUrl + ", kakaoUrl=" + kakaoUrl + ", instagramUrl=" + instagramUrl + ", dateCreated="
				+ dateCreated + ", updateCreated=" + updateCreated + ", grade=" + grade + ", imageUrl=" + imageUrl
				+ ", refreshToken=" + refreshToken + "]";
	}
	
	
}
