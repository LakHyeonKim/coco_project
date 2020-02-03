package com.ssafy.coco.vo;

public class Mypage {
	long idmypage;
	long memberId;
	String bannerImagePath;
	String bannerText;
	int access;
	
	public Mypage() {
		super();
	}
	public Mypage(long idmypage, long memberId, String bannerImagePath, String bannerText, int access) {
		super();
		this.idmypage = idmypage;
		this.memberId = memberId;
		this.bannerImagePath = bannerImagePath;
		this.bannerText = bannerText;
		this.access = access;
	}
	public long getIdmypage() {
		return idmypage;
	}
	public void setIdmypage(long idmypage) {
		this.idmypage = idmypage;
	}
	public long getMemberId() {
		return memberId;
	}
	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}
	public String getBannerImagePath() {
		return bannerImagePath;
	}
	public void setBannerImagePath(String bannerImagePath) {
		this.bannerImagePath = bannerImagePath;
	}
	public String getBannerText() {
		return bannerText;
	}
	public void setBannerText(String bannerText) {
		this.bannerText = bannerText;
	}
	public int getAccess() {
		return access;
	}
	public void setAccess(int access) {
		this.access = access;
	}
	@Override
	public String toString() {
		return "Mypage [idmypage=" + idmypage + ", memberId=" + memberId + ", bannerImagePath=" + bannerImagePath
				+ ", bannerText=" + bannerText + ", access=" + access + "]";
	}
	
	
	
	
}
