package com.ssafy.coco.vo;

public class WordDictionary {
	long idwordDictionary;
	String word;
	String description;
	String link;
	String thumbnailSrc;
	String title;
	public WordDictionary() {
		super();
	}
	public long getIdwordDictionary() {
		return idwordDictionary;
	}
	public void setIdwordDictionary(long idwordDictionary) {
		this.idwordDictionary = idwordDictionary;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getThumbnailSrc() {
		return thumbnailSrc;
	}
	public void setThumbnailSrc(String thumbnailSrc) {
		this.thumbnailSrc = thumbnailSrc;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public WordDictionary(long idwordDictionary, String word, String description, String link, String thumbnailSrc,
			String title) {
		super();
		this.idwordDictionary = idwordDictionary;
		this.word = word;
		this.description = description;
		this.link = link;
		this.thumbnailSrc = thumbnailSrc;
		this.title = title;
	}
	@Override
	public String toString() {
		return "WordDictionary [idwordDictionary=" + idwordDictionary + ", word=" + word + ", description="
				+ description + ", link=" + link + ", thumbnailSrc=" + thumbnailSrc + ", title=" + title + "]";
	}
}
