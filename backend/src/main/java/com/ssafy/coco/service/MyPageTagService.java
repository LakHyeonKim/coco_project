package com.ssafy.coco.service;

import java.util.List;

import com.ssafy.coco.vo.MypageTag;

public interface MyPageTagService {
	public List<MypageTag> findAllMypageTag();
	public List<MypageTag> findMypageTag(MypageTag mypagetag);
	public int addMypageTag(MypageTag mypagetag);
	public int deleteMypageTag(MypageTag mypagetag);
}
