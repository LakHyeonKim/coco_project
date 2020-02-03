-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema coco
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema coco
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `coco` DEFAULT CHARACTER SET utf8 ;
USE `coco` ;

-- -----------------------------------------------------
-- Table `coco`.`rank`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `coco`.`rank` (
  `idrank` BIGINT NOT NULL,
  `grade` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`idrank`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `coco`.`member`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `coco`.`member` (
  `idmember` BIGINT NOT NULL AUTO_INCREMENT,
  `rank_id` BIGINT NOT NULL,
  `is_manager` TINYINT NOT NULL,
  `is_delete` TINYINT NOT NULL,
  `rank_point` BIGINT NOT NULL,
  `nickname` VARCHAR(50) NOT NULL,
  `id` VARCHAR(50) NOT NULL,
  `password` VARCHAR(50) NOT NULL,
  `email` VARCHAR(150) NULL,
  `git_url` VARCHAR(150) NULL,
  `kakao_url` VARCHAR(150) NULL,
  `instagram_url` VARCHAR(150) NULL,
  `date_created` DATETIME NULL,
  `update_created` DATETIME NULL,
  PRIMARY KEY (`idmember`),
  INDEX `fk_meber_rank1_idx` (`rank_id` ASC) VISIBLE,
  CONSTRAINT `fk_meber_rank1`
    FOREIGN KEY (`rank_id`)
    REFERENCES `coco`.`rank` (`idrank`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `coco`.`post`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `coco`.`post` (
  `idpost` BIGINT NOT NULL AUTO_INCREMENT,
  `member_id` BIGINT NOT NULL,
  `post_title` VARCHAR(200) NULL,
  `post_writer` VARCHAR(50) NULL,
  `date_created` DATETIME NULL,
  `update_created` DATETIME NULL,
  `code` LONGTEXT NULL,
  `like_count` INT NULL,
  `views` INT NULL,
  `image_path` VARCHAR(200) NULL,
  `is_delete` TINYINT NOT NULL,
  PRIMARY KEY (`idpost`),
  INDEX `fk_post_member1_idx` (`member_id` ASC) VISIBLE,
  CONSTRAINT `fk_post_member1`
    FOREIGN KEY (`member_id`)
    REFERENCES `coco`.`member` (`idmember`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `coco`.`follow`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `coco`.`follow` (
  `idfollow` BIGINT NOT NULL AUTO_INCREMENT,
  `member_follower` BIGINT NOT NULL,
  `member_following` BIGINT NOT NULL,
  `is_delete` TINYINT NOT NULL,
  PRIMARY KEY (`idfollow`),
  INDEX `fk_follow_member1_idx` (`member_follower` ASC) VISIBLE,
  INDEX `fk_follow_member2_idx` (`member_following` ASC) VISIBLE,
  CONSTRAINT `fk_follow_member1`
    FOREIGN KEY (`member_follower`)
    REFERENCES `coco`.`member` (`idmember`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_follow_member2`
    FOREIGN KEY (`member_following`)
    REFERENCES `coco`.`member` (`idmember`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `coco`.`tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `coco`.`tag` (
  `idtag` BIGINT NOT NULL AUTO_INCREMENT,
  `tag_name` VARCHAR(50) NOT NULL,
  `searched_number` BIGINT NULL,
  `included_number` BIGINT NULL,
  `first_time_created` DATETIME NULL,
  `most_recently_mentioned_time` DATETIME NULL,
  `image_path` VARCHAR(200) NULL,
  PRIMARY KEY (`idtag`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `coco`.`post_tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `coco`.`post_tag` (
  `idpost_tag` BIGINT NOT NULL AUTO_INCREMENT,
  `post_id` BIGINT NOT NULL,
  `tag_id` BIGINT NOT NULL,
  PRIMARY KEY (`idpost_tag`),
  INDEX `fk_post_tag_post_idx` (`post_id` ASC) VISIBLE,
  INDEX `fk_post_tag_tag1_idx` (`tag_id` ASC) VISIBLE,
  CONSTRAINT `fk_post_tag_post`
    FOREIGN KEY (`post_id`)
    REFERENCES `coco`.`post` (`idpost`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_post_tag_tag1`
    FOREIGN KEY (`tag_id`)
    REFERENCES `coco`.`tag` (`idtag`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `coco`.`comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `coco`.`comment` (
  `idcomment` BIGINT NOT NULL AUTO_INCREMENT,
  `member_id` BIGINT NOT NULL,
  `post_id` BIGINT NOT NULL,
  `comment_writer` VARCHAR(50) NULL,
  `contents` VARCHAR(100) NULL,
  `date_created` DATETIME NULL,
  `update_created` DATETIME NULL,
  `is_delete` TINYINT NOT NULL,
  PRIMARY KEY (`idcomment`),
  INDEX `fk_comment_post1_idx` (`post_id` ASC) VISIBLE,
  INDEX `fk_comment_member1_idx` (`member_id` ASC) VISIBLE,
  CONSTRAINT `fk_comment_post1`
    FOREIGN KEY (`post_id`)
    REFERENCES `coco`.`post` (`idpost`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_comment_member1`
    FOREIGN KEY (`member_id`)
    REFERENCES `coco`.`member` (`idmember`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `coco`.`mypage`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `coco`.`mypage` (
  `idmypage` BIGINT NOT NULL AUTO_INCREMENT,
  `member_id` BIGINT NOT NULL,
  `banner_image_path` VARCHAR(200) NULL,
  `banner_text` VARCHAR(45) NULL,
  `is_delete` TINYINT NOT NULL,
  PRIMARY KEY (`idmypage`),
  INDEX `fk_mypage_meber1_idx` (`member_id` ASC) VISIBLE,
  CONSTRAINT `fk_mypage_meber1`
    FOREIGN KEY (`member_id`)
    REFERENCES `coco`.`member` (`idmember`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `coco`.`like`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `coco`.`like` (
  `idlike` BIGINT NOT NULL AUTO_INCREMENT,
  `post_id` BIGINT NOT NULL,
  `member_id` BIGINT NOT NULL,
  `is_delete` TINYINT NOT NULL,
  PRIMARY KEY (`idlike`),
  INDEX `fk_like_post1_idx` (`post_id` ASC) VISIBLE,
  INDEX `fk_like_member1_idx` (`member_id` ASC) VISIBLE,
  CONSTRAINT `fk_like_post1`
    FOREIGN KEY (`post_id`)
    REFERENCES `coco`.`post` (`idpost`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_like_member1`
    FOREIGN KEY (`member_id`)
    REFERENCES `coco`.`member` (`idmember`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `coco`.`alarm`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `coco`.`alarm` (
  `idalarm` BIGINT NOT NULL AUTO_INCREMENT,
  `member_caller` BIGINT NOT NULL,
  `member_receiver` BIGINT NOT NULL,
  `post_id` BIGINT NOT NULL,
  `like_id` BIGINT NOT NULL,
  `follow_id` BIGINT NOT NULL,
  `is_read` TINYINT NOT NULL,
  `is_delete` TINYINT NOT NULL,
  PRIMARY KEY (`idalarm`),
  INDEX `fk_alarm_post1_idx` (`post_id` ASC) VISIBLE,
  INDEX `fk_alarm_like1_idx` (`like_id` ASC) VISIBLE,
  INDEX `fk_alarm_follow1_idx` (`follow_id` ASC) VISIBLE,
  INDEX `fk_alarm_member1_idx` (`member_caller` ASC) VISIBLE,
  INDEX `fk_alarm_member2_idx` (`member_receiver` ASC) VISIBLE,
  CONSTRAINT `fk_alarm_post1`
    FOREIGN KEY (`post_id`)
    REFERENCES `coco`.`post` (`idpost`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_alarm_like1`
    FOREIGN KEY (`like_id`)
    REFERENCES `coco`.`like` (`idlike`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_alarm_follow1`
    FOREIGN KEY (`follow_id`)
    REFERENCES `coco`.`follow` (`idfollow`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_alarm_member1`
    FOREIGN KEY (`member_caller`)
    REFERENCES `coco`.`member` (`idmember`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_alarm_member2`
    FOREIGN KEY (`member_receiver`)
    REFERENCES `coco`.`member` (`idmember`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `coco`.`word_dictionary`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `coco`.`word_dictionary` (
  `idword_dictionary` BIGINT NOT NULL AUTO_INCREMENT,
  `word` VARCHAR(150) NULL,
  `description` VARCHAR(1000) NULL,
  `link` VARCHAR(500) NULL,
  PRIMARY KEY (`idword_dictionary`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `coco`.`baby_post`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `coco`.`baby_post` (
  `idbaby_post` BIGINT NOT NULL AUTO_INCREMENT,
  `post_parents` BIGINT NOT NULL,
  `post_child` BIGINT NOT NULL,
  PRIMARY KEY (`idbaby_post`),
  INDEX `fk_baby_post_post1_idx` (`post_parents` ASC) VISIBLE,
  INDEX `fk_baby_post_post2_idx` (`post_child` ASC) VISIBLE,
  CONSTRAINT `fk_baby_post_post1`
    FOREIGN KEY (`post_parents`)
    REFERENCES `coco`.`post` (`idpost`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_baby_post_post2`
    FOREIGN KEY (`post_child`)
    REFERENCES `coco`.`post` (`idpost`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `coco`.`mypage_tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `coco`.`mypage_tag` (
  `idmypage_tag` BIGINT NOT NULL AUTO_INCREMENT,
  `mypage_id` BIGINT NOT NULL,
  `tag_id` BIGINT NOT NULL,
  PRIMARY KEY (`idmypage_tag`),
  INDEX `fk_mypage_tag_mypage1_idx` (`mypage_id` ASC) VISIBLE,
  INDEX `fk_mypage_tag_tag1_idx` (`tag_id` ASC) VISIBLE,
  CONSTRAINT `fk_mypage_tag_mypage1`
    FOREIGN KEY (`mypage_id`)
    REFERENCES `coco`.`mypage` (`idmypage`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_mypage_tag_tag1`
    FOREIGN KEY (`tag_id`)
    REFERENCES `coco`.`tag` (`idtag`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `coco`.`member_tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `coco`.`member_tag` (
  `idmember_tag` BIGINT NOT NULL AUTO_INCREMENT,
  `member_id` BIGINT NOT NULL,
  `tag_id` BIGINT NOT NULL,
  `is_delete` TINYINT NOT NULL,
  PRIMARY KEY (`idmember_tag`),
  INDEX `fk_member_tag_member1_idx` (`member_id` ASC) VISIBLE,
  INDEX `fk_member_tag_tag1_idx` (`tag_id` ASC) VISIBLE,
  CONSTRAINT `fk_member_tag_member1`
    FOREIGN KEY (`member_id`)
    REFERENCES `coco`.`member` (`idmember`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_member_tag_tag1`
    FOREIGN KEY (`tag_id`)
    REFERENCES `coco`.`tag` (`idtag`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
