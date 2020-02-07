package com.ssafy.coco.dao;

import java.util.List;

import com.ssafy.coco.vo.MypageTag;

public interface MyPageTagDao {
	public List<MypageTag> findAllMypageTag();
	public List<MypageTag> findMypageTag(MypageTag mypagetag);
	public int addMypageTag(MypageTag mypagetag);
	public int deleteMypageTag(MypageTag mypagetag);
}
