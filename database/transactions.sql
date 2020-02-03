SELECT * FROM coco.post;
SELECT * FROM coco.baby_post;
SELECT * FROM coco.comment;
SELECT * FROM coco.alarm;
SELECT * FROM coco.follow;
SELECT * FROM coco.member;
SELECT * FROM coco.member_tag;
SELECT * FROM coco.`like`;
SELECT * FROM coco.post_tag;
SELECT * FROM coco.tag;
SELECT * FROM coco.mypage;
SELECT * FROM coco.rank;
SELECT * FROM coco.word_dictionary;

##기타 환경설정##
set SQL_SAFE_UPDATES = 0;
##############
# (사용자 id는 중복이 되면 안된다. 중복검사 필수)


##################################### 삽입 부분 #################################

####---------회원가입--------####

# 1. 사용자가 회원 가입 했을 때 마이페이지 까지 자동으로 만들어지는 트랜잭션 (사용자 id는 중복이 되면 안된다. 중복검사 필수)

INSERT INTO `coco`.`member` (`rank_id`, `is_manager`, `is_delete`, `nickname`, `id`, `password`, `email`, `git_url`, `kakao_url`, `instagram_url`, `date_created`, `update_created`) VALUES ('22', '0', '0', '찐빵맨', '123442', '1234', 'kimd@gmail.com', '123@github.com', null, null, null, null);
INSERT INTO `coco`.`mypage` (`member_id`, `banner_image_path`, `banner_text`, `access`) VALUES ((select idmember from member where id='123442'), '어쩌구 저쩌구', '어쩌저구', '1');

##############################

####---------post---------####

# 해당 포스트를 예로 들어서 ...
INSERT INTO `coco`.`post` (`member_id`, `post_title`, `post_writer`, `date_created`, `code`, `like_count`, `views`, `image_path`, `access`) VALUES ('6', '하 난 왤케 많이 먹을까', '락현짱짱맨', now(), '어쩌구 저쩌구', '0', '0', '어쩌구', '1');

# 1. 사용자가 포스트에 댓글을 달았을 때 트랜잭션

INSERT INTO `coco`.`comment` (`member_id`, `post_id`, `comment_writer`, `contents`, `date_created`, `access`) VALUES ('8', '5', '서현짱짱맨', '락현아 퍼갈께', now(), '1');
INSERT INTO `coco`.`alarm` (`member_caller`, `member_receiver`, `post_id`, `like_id`, `follow_id`, `is_read`, `access`) VALUES ('8', (select member_id from post where idpost = 5), '5', null, null, '1', '1');

# 2. 사용자가 포스트에 포스트 댓글을 달았을 때 트랜잭션
## ex) 5번 포스트에 포스트 댓글 
INSERT INTO `coco`.`post` (`member_id`, `post_title`, `post_writer`, `date_created`, `code`, `like_count`, `views`, `image_path`, `access`) VALUES ('10', '넌 그냥 많이 먹잖아', '진호짱짱맨', now(), '어쩌구 저쩌구', '0', '0', '어쩌구', '1');
INSERT INTO `coco`.`baby_post` (`post_parents`, `post_child`) VALUES ('5', (select idpost from post where member_id=10));
INSERT INTO `coco`.`alarm` (`member_caller`, `member_receiver`, `post_id`, `like_id`, `follow_id`, `is_read`, `access`) VALUES ('10', (select member_id from post where idpost=5), '5', null, null,  '1', '1');

# 3. 사용자가 포스트를 클릭시 
## 사용자가 상대방 post를 방문시 view 방문자 증가
UPDATE `coco`.`post` SET `views` = `views` + 1 WHERE idpost = '7'; 
## 사용자가 해당 포스트를 클릭 하면 자신이 자주 보는 태그에 대해서 빈도수를 측정 
select tag_id from `coco`.`post_tag` where post_id = '7'; # (7번 포스트가 가지고 있는 태그 id를 모두 가지고 온다.)
## for문 이용해야함 
select * from `coco`.`member_tag` where member_id='5' and tag_id='tag_id'; # (select 해서 값 유무 판단)
### select가 값이 null이면 member_tag에 삽입
INSERT INTO `coco`.`member_tag` (`member_id`, `tag_id`, `access`, `frequency`) VALUES ('5', 'tag_id', '1', '0');
### select가 값이 null 아니면 member_tag 에 frequency값 업데이트 +1을 해준다.
UPDATE `coco`.`member_tag` SET `frequency` = `frequency` + '1' WHERE member_id = '5' and tag_id = '1';

#############################


####---------팔로우--------####

# 1. 사용자가 팔로우 했을 때 트랜잭션

INSERT INTO `coco`.`follow` (`member_follower`, `member_following`, `access`) VALUES ('7', '10', '1'); # 7번이 10번을 팔로우
INSERT INTO `coco`.`alarm` (`member_caller`, `member_receiver`, `post_id`, `like_id`, `follow_id`, `is_read`, `access`) VALUES ('7', '10', null, null, (select idfollow from follow where member_follower = 7 and member_following = 10), '1', '1');

#############################

####---------좋아요--------####

# 1. 사용자가 포스트에 좋아요 눌렀을 때 트랜잭션

INSERT INTO `coco`.`like` (`post_id`, `member_id`, `access`) VALUES ('5', '6', '1'); # 5번 포스트에 6번 맴버가 좋아요 누름
INSERT INTO `coco`.`alarm` (`member_caller`, `member_receiver`, `post_id`, `like_id`, `follow_id`, `is_read`, `access`) VALUES ('6', (select member_id from post where idpost = 5), 5, null, null, '1', '1');

# 2. 사용자가 post에 좋아요 눌렀 을 때 (이게 맞음)

INSERT INTO `coco`.`like` (`post_id`, `member_id`, `access`) VALUES ('5', '7', '1'); # 7번 사용자가 5번 post에 좋아요 누름
INSERT INTO `coco`.`alarm` (`member_caller`, `member_receiver`, `post_id`, `like_id`, `follow_id`, `is_read`, `access`) VALUES ('7', (select member_id from post where idpost = '5'), '5', (select idlike from coco.like where post_id='5' and member_id='7'), null, '1', '1');

#############################

####---------태그---------####

# 1. 포스트에서 사용자가 태그를 생성했을 때 트랜잭션

select * from `coco`.`tag` where tag_name = 'tag_name'; #(select문이 결과가 null이면 해당 태그 없음)

## 정의한 태그가 있으면

# INSERT INTO `coco`.`post` (`member_id`, `post_title`, `post_writer`, `date_created`, `code`, `like_count`, `views`, `image_path`, `access`) VALUES ('7', '아아아 졸령 ㅜㅜ', '영은짱짱맨', now(), '어쩌구 저쩌구', '0', '0', '어쩌구', '1'); 
## 아래 작업이 여러번
update `coco`.`tag` set `included_number` = `included_number` + 1 , `most_recently_mentioned_time` = now() where tag_name = 'java'; 
INSERT INTO `coco`.`post_tag` (`post_id`, `tag_id`) VALUES ('7', (select idtag from tag where tag_name = 'java'));
# INSERT INTO `coco`.`post_tag` (`post_id`, `tag_id`) VALUES ('7', (select idtag from tag where tag_name = 'javascript'));

## 정의한 태그가 없으면
INSERT INTO `coco`.`tag` (`tag_name`, `searched_number`, `included_number`, `first_time_created`, `most_recently_mentioned_time`, `image_path`) VALUES ('C언어', '0', '1', now(), now(), ' ');
INSERT INTO `coco`.`post_tag` (`post_id`, `tag_id`) VALUES ('7', (select idtag from tag where tag_name = 'C언어'));


############################


####-------마이페이지------####

# 1. 사용자가 상대방 마이페이지 방문시 방문자 증가 
UPDATE `coco`.`mypage` SET `today_visited_count` = `today_visited_count` + 1, `total_visited_count` = `total_visited_count` + 1 WHERE member_id = '11'; 

############################





################################ 선택 부분 ############################################

# 1. 사용자가 뉴스피드 Post를 가지고 올때 (5번 사용자가 팔로우 하고 있는 사람들의 포스트를 모두 가져온다.)
select * from `coco`.`post` where member_id in (select member_following from `coco`.`follow` where member_follower = 5) order by date_created desc;

# 2. 사용자 rank를 가지고 올 때
select * from `coco`.`member` as m join `coco`.`rank` as r where m.rank_id = r.idrank;

# 3. (tag, 또는 타이틀, 내용) post 검색 할 때 ex) java (전체검색)

select * from `coco`.`post` where idpost in (select post_id from `coco`.`post_tag` where tag_id in (select idtag from `coco`.`tag` where tag_name like '%java%')) or post_title like '%java%' or code like '%java%' or post_writer like '%진호%' order by date_created desc;

# 4. tag로만 검색
select * from `coco`.`post` where idpost in (select post_id from `coco`.`post_tag` where tag_id in (select idtag from `coco`.`tag` where tag_name like '%java%'));

# 5. title로만 검색
select * from `coco`.`post` where post_title like '%java%';

# 6. 내용(code)로만 검색
select * from `coco`.`post` where code like '%java%';

# 7. 아이디로 검색
select * from `coco`.`post` where post_writer like '%락%';

# 8. 팔로잉 수 가져오기
select count(member_following) as member_following from `coco`.`follow` where member_follower = '5'; 

# 9. 팔로워 수 가져오기
select count(member_follower) as member_follower from `coco`.`follow` where member_following = '5'; 

# 10. 팔로잉 한 사람 리스트 가져오기
select * from `coco`.`member` where idmember in (select member_following from `coco`.`follow` where member_follower = '5');

# 11. 팔로워 한 사람 리스트 가져오기
select * from `coco`.`member` where idmember in (select member_follower from `coco`.`follow` where member_following = '5');

# 12. 사용자가 자주 찾아본 태그 빈도수 기반으로 검색 화면 디폴트로 post 가져오기 (p.idpost, p.member_id, p.post_title, p.post_writer, p.date_created, p.update_created, p.code, p.like_count, p.views, p.image_path, p.access)
select p.*
from member_tag as mt inner join tag as t inner join post_tag as pt inner join post as p on mt.tag_id = t.idtag and t.idtag = pt.tag_id and pt.post_id = p.idpost and mt.member_id = '7' order by frequency desc;

# 13. 포스트 상세페이지 가면 댓글 가져와야함..
select * from `coco`.`comment` where post_id = '2' order by date_created desc;

# 14. 포스트 상세페이지 가면 포스트 댓글 가져와야함
select * from `coco`.`post` where idpost in (select post_child from baby_post where post_parents = '5') order by date_created desc;

# 15. 포스트가 가진 태그 정보를 모두 가지고 온다.
select * from `coco`.`tag` where idtag in (select tag_id from post_tag where post_id = '7');

# 16. 포스트에 좋아요 누른 사람 모두 찾기
select idmember, rank_id, is_manager,is_delete, nickname, id, password,
		email, git_url, kakao_url,instagram_url ,date_created,update_created, grade 
from `coco`.`member` as m join `coco`.`rank` as r where  m.rank_id = r.idrank and idmember in (select member_id from `coco`.`like` where post_id = '5');