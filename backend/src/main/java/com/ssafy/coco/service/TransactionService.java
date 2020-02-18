package com.ssafy.coco.service;

import java.io.IOException;

import com.ssafy.coco.relationvo.BabyBoardWrite;
import com.ssafy.coco.relationvo.BoardDetail;
import com.ssafy.coco.relationvo.BoardWrite;
import com.ssafy.coco.relationvo.MemberInfoModify;
import com.ssafy.coco.relationvo.SignUpMember;
import com.ssafy.coco.vo.Comment;
import com.ssafy.coco.vo.Post;

public interface TransactionService {
	public long signUp(SignUpMember signUpMember) throws IllegalStateException, IOException, Exception;
	public void makeComment(Comment commentData, long receiver);
	public void makeBabyPost(BabyBoardWrite babyBoardWrite) throws IllegalStateException, IOException;
	public void postClick(Post post, long idMember);
	public void makeFollow(long idMemberFollower, long idMemberFollowing);
	public void makeUnFollow(long idMemberFollower, long idMemberFollowing);
	public void pushLike(long idPost, long idMember);
	public void unLike(long idPost, long idMember);
	public void makePost(BoardWrite board) throws IllegalStateException, IOException;
	public void updateMemeberInfo(MemberInfoModify memberInfoModify) throws Exception;
	public void deleteMemberProfile(long idMember);
	public void deleteMemberBannerImage(long idMember);
	public void deleteComment(long postId,long receiver,long caller,long commentId );
}
