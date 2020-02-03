package com.ssafy.coco.service;

import java.util.List;

import com.ssafy.coco.vo.Mypage;

public interface MyPageService {
	public List<Mypage> findAllMypage();
	public List<Mypage> findMypage(Mypage mypage);
	public int addMypage(Mypage mypage);
	public int updateMypage(Mypage mypage);
	public int updateMypageVisitedCount(long idMember);
	public int deleteMypage(Mypage mypage);
}
