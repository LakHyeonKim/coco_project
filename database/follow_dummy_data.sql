
INSERT INTO `coco`.`follow` (`member_follower`, `member_following`, `access`) VALUES ('5', '6', '1');
INSERT INTO `coco`.`follow` (`member_follower`, `member_following`, `access`) VALUES ('5', '7', '1');
INSERT INTO `coco`.`follow` (`member_follower`, `member_following`, `access`) VALUES ('5', '8', '1');
INSERT INTO `coco`.`follow` (`member_follower`, `member_following`, `access`) VALUES ('5', '9', '1');
INSERT INTO `coco`.`follow` (`member_follower`, `member_following`, `access`) VALUES ('5', '10', '1');
INSERT INTO `coco`.`follow` (`member_follower`, `member_following`, `access`) VALUES ('6', '5', '1');
INSERT INTO `coco`.`follow` (`member_follower`, `member_following`, `access`) VALUES ('6', '9', '1');
INSERT INTO `coco`.`follow` (`member_follower`, `member_following`, `access`) VALUES ('6', '10', '1');
INSERT INTO `coco`.`follow` (`member_follower`, `member_following`, `access`) VALUES ('8', '5', '1');
INSERT INTO `coco`.`follow` (`member_follower`, `member_following`, `access`) VALUES ('8', '6', '1');
INSERT INTO `coco`.`follow` (`member_follower`, `member_following`, `access`) VALUES ('8', '7', '1');
INSERT INTO `coco`.`follow` (`member_follower`, `member_following`, `access`) VALUES ('8', '9', '1');
INSERT INTO `coco`.`follow` (`member_follower`, `member_following`, `access`) VALUES ('9', '10', '1');
INSERT INTO `coco`.`follow` (`member_follower`, `member_following`, `access`) VALUES ('10', '5', '1');
INSERT INTO `coco`.`follow` (`member_follower`, `member_following`, `access`) VALUES ('10', '6', '1');
INSERT INTO `coco`.`follow` (`member_follower`, `member_following`, `access`) VALUES ('10', '7', '1');
INSERT INTO `coco`.`follow` (`member_follower`, `member_following`, `access`) VALUES ('10', '8', '1');

SELECT * FROM coco.member;
SELECT * FROM coco.follow;