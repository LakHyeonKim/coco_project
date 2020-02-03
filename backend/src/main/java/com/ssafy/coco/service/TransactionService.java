package com.ssafy.coco.service;

import java.util.List;
import java.util.Map;

import com.ssafy.coco.vo.Comment;
import com.ssafy.coco.vo.Member;
import com.ssafy.coco.vo.Post;
import com.ssafy.coco.vo.Tag;

public interface TransactionService {
	public int signUp(Member member);
	public void makeComment(Comment commentData, long receiver);
	public void makeBabyPost(Post baby,Post parent);
	public void postClick(Post post, long idMember);
	public void makeFollow(long idMemberFollower, long idMemberFollowing);
	public void pushLike(long idPost, long idMember);
	public void makeTagsFromPost(Post post, List<Tag> tags);
}
