package com.ssafy.coco.relationvo;

public class KeywordSearch {
	long idMember;
	String keyWord;
	
	public KeywordSearch() {
		super();
	}

	public KeywordSearch(long idMember, String keyWord) {
		super();
		this.idMember = idMember;
		this.keyWord = keyWord;
	}

	public long getIdMember() {
		return idMember;
	}

	public void setIdMember(long idMember) {
		this.idMember = idMember;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	@Override
	public String toString() {
		return "KeywordSearch [idMember=" + idMember + ", keyWord=" + keyWord + "]";
	}
	
}
