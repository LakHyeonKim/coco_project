package com.ssafy.coco.relationvo;

public class BoardDetailSearch {
	long idPost;
	long idMember;
	
	public BoardDetailSearch() {
		super();
	}

	public BoardDetailSearch(long idPost, long idMember) {
		super();
		this.idPost = idPost;
		this.idMember = idMember;
	}

	public long getIdPost() {
		return idPost;
	}

	public void setIdPost(long idPost) {
		this.idPost = idPost;
	}

	public long getIdMember() {
		return idMember;
	}

	public void setIdMember(long idMember) {
		this.idMember = idMember;
	}

	@Override
	public String toString() {
		return "BoardDetailSearch [idPost=" + idPost + ", idMember=" + idMember + "]";
	}
	
}
