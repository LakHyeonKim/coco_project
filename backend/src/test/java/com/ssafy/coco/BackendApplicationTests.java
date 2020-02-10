package com.ssafy.coco;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ssafy.coco.relationvo.Board;
import com.ssafy.coco.service.BoardService;
import com.ssafy.coco.service.CommentService;
import com.ssafy.coco.service.MailService;
import com.ssafy.coco.vo.Alarm;
import com.ssafy.coco.vo.BabyPost;
import com.ssafy.coco.vo.Comment;
import com.ssafy.coco.vo.Follow;
import com.ssafy.coco.vo.Like;
import com.ssafy.coco.vo.Member;
import com.ssafy.coco.vo.MemberTag;
import com.ssafy.coco.vo.Mypage;
import com.ssafy.coco.vo.MypageTag;
import com.ssafy.coco.vo.Post;
import com.ssafy.coco.vo.PostTag;
import com.ssafy.coco.vo.Tag;
import com.ssafy.coco.vo.WordDictionary;

@SpringBootTest
class BackendApplicationTests {
	@Autowired
	CommentService ts;

	@Autowired
	SqlSession sqlsession;

	@Autowired
	BoardService boardService;

	@Autowired
	MailService mailService;
	
	//@Test void transactionContextLoads() throws Exception{
	//	mailService.test();
	//}
		  
		 
	/*
	 * @Test void relationVoContextLoads() throws Exception{
	 * 
	 * 
	 * List<Board> list = boardService.findByAllNewsfeed(5L); for(Board b : list) {
	 * System.out.println(b); }
	 * 
	 * //mailService.test(); }
	 * 
	 * 
	 * 
	 * 
	 * @Test void transactionContextLoads() throws Exception{
	 * //findPostByNewsfeed(5L); //findPostByAll("java"); //findPostByTag("java");
	 * //findPostByTitle("하"); //findPostByCode("어쩌구");
	 * //findPostByPostWriter("진호"); //findFollowingCount(5L);
	 * //findFollowerCount(5L); //findFollowingMemberList(5L);
	 * //findFollowerMemberList(5L); //findPostByFrequency(7L);
	 * //findPostByPostComment(5L); }
	 * 
	 * @Test void contextLoads() throws IOException { WordDictionary input = new
	 * WordDictionary(1, null, null, null); //updateWordDictionary(input);
	 * //deleteWordDictionary(input);
	 * 
	 * 
	 * Tag inputTag = new Tag(10, "cccc", 2, 2, null, null, null);
	 * //addTag(inputTag); //findAllTag(); //find(inputTag); //updateTag(inputTag);
	 * //deleteTag(inputTag); //findAllTagIncludedPost(7L);
	 * 
	 * 
	 * 
	 * //테스트 완 Post inputPost = new Post(5, 2, "45123", "nick", null , null,
	 * "어쩌다저쩌따", 0, 0, "wow",1); Post inputPost1 = new Post(0, 0, null, null, null ,
	 * null, "어쩌구", 0, 0, null,0);
	 * 
	 * //findAllPost(); //findPost(inputPost1); //addPost(inputPost);
	 * //updatePost(inputPost); //deletePost(inputPost); //updatePostViewCount(5L);
	 * 
	 * PostTag inputPT = new PostTag(1, 1, 3);
	 * 
	 * //findAllPostTag(); //addPostTag(inputPT); //findPostTag(inputPT);
	 * //deletePostTag(inputPT);
	 * 
	 * Member inputMember = new Member(9, 1, 0, 0, "고구마", "a13975", "1234", null,
	 * null, null, null, null, null, null); //addMember(inputMember);
	 * //findAllMember(); //findMember(inputMember); //updateMember(inputMember);
	 * //deleteMember(inputMember); //findWhoPressedTheLikeButton(5L);
	 * 
	 * Mypage inputMypage = new Mypage(1, 2, "오잉", null, 0);
	 * //addMypage(inputMypage); //findAllMypage(); //findMypage(inputMypage);
	 * //updateMypage(inputMypage); //deleteMypage(inputMypage);
	 * //updateMypageVisitedCount(11L);
	 * 
	 * MemberTag inputMT = new MemberTag(2, 2, 1, 0, 81); //addMemberTag(inputMT);
	 * //findAllMemberTag(); //findMemberTag(inputMT); //updateMemberTag(inputMT);
	 * //deleteMemberTag(inputMT); //updateMemberTagFrequencyCount(5L, 1L);
	 * 
	 * MypageTag inputMPT = new MypageTag(2, 2, 1); //addMypageTag(inputMPT);
	 * //findAllMypageTag(); //findMypageTag(inputMPT); //deleteMypageTag(inputMPT);
	 * 
	 * Like inputLike = new Like(2, 4, 9, 2); //addLike(inputLike); //findAllLike();
	 * //findLike(inputLike); //updateLike(inputLike); //deleteLike(inputLike);
	 * 
	 * Follow inputfollow = new Follow(11, 5, 6, 0); //findAllFollow();
	 * //findFollow(inputfollow); //addFollow(inputfollow);
	 * //updateFollow(inputfollow); //deleteFollow(inputfollow);
	 * 
	 * Comment inputComment = new Comment(11,7,2, "D", null, null, null, 1);
	 * //findAllComment(); //findComment(inputComment); //addComment(inputComment);
	 * //updateComment(inputComment); //deleteComment(inputComment);
	 * 
	 * BabyPost inputBP = new BabyPost(1, 1, 4); //addBabyPost(inputBP);
	 * //findAllBabyPost(); //findBabyPost(inputBP); //updateBabyPost(inputBP);
	 * //deleteBabyPost(inputBP);
	 * 
	 * Alarm inputA = new Alarm(3, 2, 3, 1, 1, 4, 0, 0); //addAlarm(inputA);
	 * //findAllAlarm(); //findAlarm(inputA); //updateAlarm(inputA);
	 * //deleteAlarm(inputA); //List<Comment> list = ts.findAllComment();
	 * //for(Comment c : list) //{ // System.out.println(c); //} }
	 * 
	 * 
	 * private void findWhoPressedTheLikeButton(long idPost) { List<Member> list =
	 * sqlsession.selectList("Member_Mapper.findWhoPressedTheLikeButton",idPost);
	 * for(Member m : list) { System.out.println(m); } }
	 * 
	 * 
	 * private void findAllTagIncludedPost(long idPost) { List<Tag> list =
	 * sqlsession.selectList("Tag_Mapper.findAllTagIncludedPost",idPost); for(Tag
	 * t:list) { System.out.println(t); } }
	 * 
	 * private void updateMypageVisitedCount(long idMember) {
	 * sqlsession.update("Mypage_Mapper.updateMypageVisitedCount",idMember); }
	 * 
	 * private void deleteAlarm(Alarm inputA) {
	 * sqlsession.delete("Alarm_Mapper.deleteAlarm",inputA); }
	 * 
	 * private void updateAlarm(Alarm inputA) {
	 * sqlsession.update("Alarm_Mapper.updateAlarm",inputA); }
	 * 
	 * private void findAlarm(Alarm inputA) { List<Alarm> list =
	 * sqlsession.selectList("Alarm_Mapper.addAlarm",inputA); for(Alarm p: list) {
	 * System.out.println(p); } }
	 * 
	 * private void addAlarm(Alarm inputA) {
	 * sqlsession.insert("Alarm_Mapper.addAlarm",inputA); }
	 * 
	 * private void findAllAlarm() { List<Alarm> list =
	 * sqlsession.selectList("Alarm_Mapper.findAllAlarm"); for(Alarm p: list) {
	 * System.out.println(p); } }
	 * 
	 * private void deleteBabyPost(BabyPost inputBP) {
	 * sqlsession.delete("BabyPost_Mapper.deleteBabyPost",inputBP); }
	 * 
	 * private void updateBabyPost(BabyPost inputBP) {
	 * sqlsession.update("BabyPost_Mapper.updateBabyPost",inputBP); }
	 * 
	 * private void findBabyPost(BabyPost inputBP) { List<BabyPost> list =
	 * sqlsession.selectList("BabyPost_Mapper.findBabyPost",inputBP); for(BabyPost
	 * p: list) { System.out.println(p); } }
	 * 
	 * private void findAllBabyPost() { List<BabyPost> list =
	 * sqlsession.selectList("BabyPost_Mapper.findAllBabyPost"); for(BabyPost p:
	 * list) { System.out.println(p); } }
	 * 
	 * private void addBabyPost(BabyPost inputBP) {
	 * sqlsession.insert("BabyPost_Mapper.addBabyPost",inputBP); }
	 * 
	 * private void deleteComment(Comment inputComment) {
	 * sqlsession.delete("Comment_Mapper.deleteComment",inputComment); }
	 * 
	 * private void updateComment(Comment inputComment) {
	 * sqlsession.update("Comment_Mapper.updateComment",inputComment); }
	 * 
	 * private void addComment(Comment inputComment) {
	 * sqlsession.insert("Comment_Mapper.addComment",inputComment); }
	 * 
	 * private void findComment(Comment inputComment) { List<Comment> list =
	 * sqlsession.selectList("Comment_Mapper.findComment",inputComment); for(Comment
	 * p: list) { System.out.println(p); } }
	 * 
	 * private void findAllComment() { List<Comment> list =
	 * sqlsession.selectList("Comment_Mapper.findAllComment"); for(Comment p: list)
	 * { System.out.println(p); } }
	 * 
	 * private void deleteFollow(Follow inputfollow) {
	 * sqlsession.delete("Follow_Mapper.deleteFollow",inputfollow); }
	 * 
	 * private void updateFollow(Follow inputfollow) {
	 * sqlsession.update("Follow_Mapper.updateFollow",inputfollow); }
	 * 
	 * private void addFollow(Follow inputfollow) {
	 * sqlsession.insert("Follow_Mapper.addFollow",inputfollow); }
	 * 
	 * private void findFollow(Follow inputfollow) { List<Follow> list =
	 * sqlsession.selectList("Follow_Mapper.findFollow",inputfollow); for(Follow p:
	 * list) { System.out.println(p); } }
	 * 
	 * private void findAllFollow() { List<Follow> list =
	 * sqlsession.selectList("Follow_Mapper.findAllFollow"); for(Follow p: list) {
	 * System.out.println(p); } }
	 * 
	 * private void deleteLike(Like inputLike) {
	 * sqlsession.delete("Like_Mapper.deleteLike",inputLike); }
	 * 
	 * private void updateLike(Like inputLike) {
	 * sqlsession.update("Like_Mapper.updateLike",inputLike); }
	 * 
	 * private void findLike(Like inputLike) { List<Like> list =
	 * sqlsession.selectList("Like_Mapper.findLike",inputLike); for(Like p: list) {
	 * System.out.println(p); } }
	 * 
	 * private void findAllLike() { List<Like> list =
	 * sqlsession.selectList("Like_Mapper.findAllLike"); for(Like p: list) {
	 * System.out.println(p); } }
	 * 
	 * private void addLike(Like inputLike) {
	 * sqlsession.insert("Like_Mapper.addLike",inputLike); }
	 * 
	 * private void deleteMypageTag(MypageTag inputMPT) {
	 * sqlsession.delete("MypageTag_Mapper.deleteMypageTag",inputMPT); }
	 * 
	 * private void findMypageTag(MypageTag inputMPT) { List<MypageTag> list =
	 * sqlsession.selectList("MypageTag_Mapper.findMypageTag",inputMPT);
	 * for(MypageTag p: list) { System.out.println(p); } }
	 * 
	 * private void findAllMypageTag() { List<MypageTag> list =
	 * sqlsession.selectList("MypageTag_Mapper.findAllMypageTag"); for(MypageTag p:
	 * list) { System.out.println(p); } }
	 * 
	 * private void addMypageTag(MypageTag inputMPT) {
	 * sqlsession.insert("MypageTag_Mapper.addMypageTag",inputMPT); }
	 * 
	 * private void deleteMemberTag(MemberTag inputMT) {
	 * sqlsession.delete("MemberTag_Mapper.deleteMemberTag",inputMT); }
	 * 
	 * private void updateMemberTag(MemberTag inputMT) {
	 * sqlsession.update("MemberTag_Mapper.updateMemberTag",inputMT); }
	 * 
	 * private void updateMemberTagFrequencyCount(long idMember, long idTag) {
	 * Map<String, Long> map = new HashMap<String, Long>(); map.put("parameter1",
	 * idMember); map.put("parameter2", idTag);
	 * sqlsession.update("MemberTag_Mapper.updateMemberTagFrequencyCount", map); }
	 * 
	 * private void findMemberTag(MemberTag inputMT) { List<MemberTag> list =
	 * sqlsession.selectList("MemberTag_Mapper.findMemberTag",inputMT);
	 * for(MemberTag p: list) { System.out.println(p); } }
	 * 
	 * private void findAllMemberTag() { List<MemberTag> list =
	 * sqlsession.selectList("MemberTag_Mapper.findAllMemberTag"); for(MemberTag p:
	 * list) { System.out.println(p); } }
	 * 
	 * private void addMemberTag(MemberTag inputMT) {
	 * sqlsession.insert("MemberTag_Mapper.addMemberTag",inputMT); }
	 * 
	 * private void deleteMypage(Mypage inputMypage) {
	 * sqlsession.delete("Mypage_Mapper.deleteMypage",inputMypage); }
	 * 
	 * private void updateMypage(Mypage inputMypage) {
	 * sqlsession.update("Mypage_Mapper.updateMypage",inputMypage); }
	 * 
	 * private void findMypage(Mypage inputMypage) { List<Mypage> list =
	 * sqlsession.selectList("Mypage_Mapper.findMypage",inputMypage); for(Mypage p:
	 * list) { System.out.println(p); } }
	 * 
	 * private void findAllMypage() { List<Mypage> list =
	 * sqlsession.selectList("Mypage_Mapper.findAllMypage"); for(Mypage p: list) {
	 * System.out.println(p); } }
	 * 
	 * private void addMypage(Mypage inputMypage) {
	 * sqlsession.insert("Mypage_Mapper.addMypage",inputMypage); }
	 * 
	 * private void deleteMember(Member inputMember) {
	 * sqlsession.delete("Member_Mapper.deleteMember"); }
	 * 
	 * private void updateMember(Member inputMember) {
	 * sqlsession.update("Member_Mapper.updateMember",inputMember); }
	 * 
	 * private void findMember(Member inputMember) { List<Member> list =
	 * sqlsession.selectList("Member_Mapper.findMember",inputMember); for(Member p:
	 * list) { System.out.println(p); } }
	 * 
	 * private void findAllMember() { List<Member> list =
	 * sqlsession.selectList("Member_Mapper.findAllMember"); for(Member p: list) {
	 * System.out.println(p); } }
	 * 
	 * private void addMember(Member inputMember) {
	 * sqlsession.insert("Member_Mapper.addMember",inputMember); }
	 * 
	 * private void deletePostTag(PostTag inputPT) {
	 * sqlsession.delete("PostTag_Mapper.deletePostTag",inputPT); }
	 * 
	 * private void findPostTag(PostTag inputPT) { List<PostTag> list =
	 * sqlsession.selectList("PostTag_Mapper.findPostTag",inputPT); for(PostTag p:
	 * list) { System.out.println(p); } }
	 * 
	 * private void addPostTag(PostTag inputPT) {
	 * sqlsession.insert("PostTag_Mapper.addPostTag",inputPT); }
	 * 
	 * private void findAllPostTag() { List<PostTag> list =
	 * sqlsession.selectList("PostTag_Mapper.findAllPostTag"); for(PostTag p: list)
	 * { System.out.println(p); } }
	 * 
	 * private void deletePost(Post inputPost) {
	 * sqlsession.delete("Post_Mapper.deletePost",inputPost); }
	 * 
	 * private void updatePost(Post inputPost) {
	 * sqlsession.update("Post_Mapper.updatePost",inputPost); }
	 * 
	 * private void updatePostViewCount(long idPost) {
	 * sqlsession.update("Post_Mapper.updatePostViewCount",idPost); }
	 * 
	 * private void addPost(Post inputPost) {
	 * sqlsession.insert("Post_Mapper.addPost",inputPost); }
	 * 
	 * private void findPost(Post inputPost) { List<Post> list =
	 * sqlsession.selectList("Post_Mapper.findPost",inputPost); for(Post p: list) {
	 * System.out.println(p); } }
	 * 
	 * private void findAllPost() { List<Post> list =
	 * sqlsession.selectList("Post_Mapper.findAllPost"); for(Post p: list) {
	 * System.out.println(p); } }
	 * 
	 * private void deleteTag(Tag inputTag) {
	 * sqlsession.delete("Tag_Mapper.deleteTag",inputTag); }
	 * 
	 * private void updateTag(Tag inputTag) {
	 * sqlsession.update("Tag_Mapper.updateTag",inputTag); }
	 * 
	 * private void find(Tag inputTag) { List<Tag> list =
	 * sqlsession.selectList("Tag_Mapper.findTag",inputTag); for(Tag testData :
	 * list) { System.out.println(testData); } }
	 * 
	 * private void findAllTag() { List<Tag> list =
	 * sqlsession.selectList("Tag_Mapper.findAllTag"); for(Tag testData : list) {
	 * System.out.println(testData); } }
	 * 
	 * private void addTag(Tag inputTag) {
	 * sqlsession.insert("Tag_Mapper.addTag",inputTag); }
	 * 
	 * private void deleteWordDictionary(WordDictionary input) {
	 * sqlsession.delete("WordDictionary_Mapper.deleteWordDictionary",input); }
	 * 
	 * public void updateWordDictionary(WordDictionary input) {
	 * sqlsession.update("WordDictionary_Mapper.updateWordDictionary",input); }
	 * 
	 * public void findWordDictionary() { WordDictionary input = new
	 * WordDictionary(0, null,null, null); List<WordDictionary> list =
	 * sqlsession.selectList("WordDictionary_Mapper.findWordDictionary",input);
	 * for(WordDictionary w : list) { System.out.println(w); } }
	 * 
	 * public void findallWordDictionary() throws IOException { WordDictionary wd =
	 * new WordDictionary(1, "happy", "so happy", "1");
	 * //sqlsession.insert("WordDictionary_Mapper.addWordDictionary", wd);
	 * List<WordDictionary> list =
	 * sqlsession.selectList("WordDictionary_Mapper.findAllWordDictionary");
	 * for(WordDictionary w : list) { System.out.println(w); } //session.commit(); }
	 * 
	 * 
	 * // Transaction -- select-- public void findPostByNewsfeed(long idMember) {
	 * List<Post> list =
	 * sqlsession.selectList("Post_Mapper.findPostByNewsfeed",idMember); for(Post p
	 * : list) { System.out.println(p); } }
	 * 
	 * public void findPostByAll(String keyWord) { List<Post> list =
	 * sqlsession.selectList("Post_Mapper.findPostByAll",keyWord); for(Post p :
	 * list) { System.out.println(p); } }
	 * 
	 * public void findPostByTag(String keyWord) { List<Post> list =
	 * sqlsession.selectList("Post_Mapper.findPostByTag",keyWord); for(Post p :
	 * list) { System.out.println(p); } }
	 * 
	 * public void findPostByTitle(String keyWord) { List<Post> list =
	 * sqlsession.selectList("Post_Mapper.findPostByTitle",keyWord); for(Post p :
	 * list) { System.out.println(p); } }
	 * 
	 * public void findPostByCode(String keyWord) { List<Post> list =
	 * sqlsession.selectList("Post_Mapper.findPostByCode",keyWord); for(Post p :
	 * list) { System.out.println(p); } }
	 * 
	 * public void findPostByPostWriter(String keyWord) { List<Post> list =
	 * sqlsession.selectList("Post_Mapper.findPostByPostWriter",keyWord); for(Post p
	 * : list) { System.out.println(p); } }
	 * 
	 * public void findFollowingCount(long idMember) { long count =
	 * sqlsession.selectOne("Follow_Mapper.findFollowingCount",idMember);
	 * System.out.println(count); }
	 * 
	 * public void findFollowerCount(long idMember) { long count =
	 * sqlsession.selectOne("Follow_Mapper.findFollowerCount",idMember);
	 * System.out.println(count); }
	 * 
	 * public void findFollowingMemberList(long idMember) { List<Member> list =
	 * sqlsession.selectList("Member_Mapper.findFollowingMemberList",idMember);
	 * for(Member m : list) { System.out.println(m); } }
	 * 
	 * public void findFollowerMemberList(long idMember) { List<Member> list =
	 * sqlsession.selectList("Member_Mapper.findFollowerMemberList",idMember);
	 * for(Member m : list) { System.out.println(m); } }
	 * 
	 * public void findPostByFrequency(long idMember) { List<Post> list =
	 * sqlsession.selectList("Post_Mapper.findPostByFrequency",idMember); for(Post p
	 * : list) { System.out.println(p); } }
	 * 
	 * public void findPostByPostComment(long idPost) { List<Post> list =
	 * sqlsession.selectList("Post_Mapper.findPostByPostComment",idPost); for(Post p
	 * : list) { System.out.println(p); } }
	 */
}
