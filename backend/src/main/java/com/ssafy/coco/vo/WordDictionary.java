package com.ssafy.coco.vo;

public class WordDictionary {
	long idwordDictionary;
	String word;
	String description;
	String link;
	
	public WordDictionary() {
		super();
	}
	public WordDictionary(long idwordDictionary, String word, String descrption, String link) {
		super();
		this.idwordDictionary = idwordDictionary;
		this.word = word;
		this.description = descrption;
		this.link = link;
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
	@Override
	public String toString() {
		return "WordDictionary [idwordDictionary=" + idwordDictionary + ", word=" + word + ", description="
				+ description + ", link=" + link + "]";
	}
	
}
