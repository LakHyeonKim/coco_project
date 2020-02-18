package com.ssafy.coco.service;

import java.util.List;

import com.ssafy.coco.relationvo.LikeInfo;
import com.ssafy.coco.relationvo.PostAndMember;

public interface LikeInfoService {
	public List<LikeInfo> findWhoPressedTheLikeButton(PostAndMember postAndMember);
}
