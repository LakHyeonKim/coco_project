package com.ssafy.coco.vo;

public class Rank {
	long idrank;
	String grade;
	
	public Rank() {
		super();
	}
	public Rank(long idrank, String grade) {
		super();
		this.idrank = idrank;
		this.grade = grade;
	}
	public long getIdrank() {
		return idrank;
	}
	public void setIdrank(long idrank) {
		this.idrank = idrank;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	
}
