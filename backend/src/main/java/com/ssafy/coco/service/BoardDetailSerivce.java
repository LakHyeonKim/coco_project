package com.ssafy.coco.service;

import com.ssafy.coco.relationvo.BoardDetail;

public interface BoardDetailSerivce {
	public BoardDetail findByBoardDetailPostId(long idPost, long idMember);
	public int deleteFile(long idPost);
}
