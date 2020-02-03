package com.ssafy.coco.vo;

import java.sql.Date;
import java.sql.Timestamp;

public class Tag {
	long idtag;
	String tagName;
	long searchedNumber;
	long includedNumber;
	Timestamp firstTimeCreated;
	Timestamp mostRecentlyMentionedTime;
	String imagePath;
	
	public Tag() {
		super();
	}
	@Override
	public String toString() {
		return "Tag [idtag=" + idtag + ", tagName=" + tagName + ", searchedNumber=" + searchedNumber
				+ ", includedNumber=" + includedNumber + ", firstTimeCreated=" + firstTimeCreated
				+ ", mostRecentlyMentionedTime=" + mostRecentlyMentionedTime + ", imagePath=" + imagePath + "]";
	}
	public Tag(long idtag, String tagName, long searchedNumber, long includedNumber, Timestamp firstTimeCreated,
			Timestamp mostRecentlyMentionedTime, String imagePath) {
		super();
		this.idtag = idtag;
		this.tagName = tagName;
		this.searchedNumber = searchedNumber;
		this.includedNumber = includedNumber;
		this.firstTimeCreated = firstTimeCreated;
		this.mostRecentlyMentionedTime = mostRecentlyMentionedTime;
		this.imagePath = imagePath;
	}
	public long getIdtag() {
		return idtag;
	}
	public void setIdtag(long idtag) {
		this.idtag = idtag;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public long getSearchedNumber() {
		return searchedNumber;
	}
	public void setSearchedNumber(long searchedNumber) {
		this.searchedNumber = searchedNumber;
	}
	public long getIncludedNumber() {
		return includedNumber;
	}
	public void setIncludedNumber(long includedNumber) {
		this.includedNumber = includedNumber;
	}
	
	public Timestamp getFirstTimeCreated() {
		return firstTimeCreated;
	}
	public void setFirstTimeCreated(Timestamp firstTimeCreated) {
		this.firstTimeCreated = firstTimeCreated;
	}
	public Timestamp getMostRecentlyMentionedTime() {
		return mostRecentlyMentionedTime;
	}
	public void setMostRecentlyMentionedTime(Timestamp mostRecentlyMentionedTime) {
		this.mostRecentlyMentionedTime = mostRecentlyMentionedTime;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
}
