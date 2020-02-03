-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: coco
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `alarm`
--

DROP TABLE IF EXISTS `alarm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `alarm` (
  `idalarm` bigint(20) NOT NULL AUTO_INCREMENT,
  `member_caller` bigint(20) NOT NULL,
  `member_receiver` bigint(20) NOT NULL,
  `post_id` bigint(20) DEFAULT NULL,
  `like_id` bigint(20) DEFAULT NULL,
  `follow_id` bigint(20) DEFAULT NULL,
  `is_read` tinyint(4) NOT NULL,
  `access` tinyint(4) NOT NULL,
  PRIMARY KEY (`idalarm`),
  KEY `fk_alarm_post1_idx` (`post_id`),
  KEY `fk_alarm_like1_idx` (`like_id`),
  KEY `fk_alarm_follow1_idx` (`follow_id`),
  KEY `fk_alarm_member1_idx` (`member_caller`),
  KEY `fk_alarm_member2_idx` (`member_receiver`),
  CONSTRAINT `fk_alarm_follow1` FOREIGN KEY (`follow_id`) REFERENCES `follow` (`idfollow`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_alarm_like1` FOREIGN KEY (`like_id`) REFERENCES `like` (`idlike`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_alarm_member1` FOREIGN KEY (`member_caller`) REFERENCES `member` (`idmember`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_alarm_member2` FOREIGN KEY (`member_receiver`) REFERENCES `member` (`idmember`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_alarm_post1` FOREIGN KEY (`post_id`) REFERENCES `post` (`idpost`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alarm`
--

LOCK TABLES `alarm` WRITE;
/*!40000 ALTER TABLE `alarm` DISABLE KEYS */;
INSERT INTO `alarm` VALUES (1,8,6,5,NULL,NULL,1,1),(2,7,10,NULL,NULL,19,1,1),(4,6,6,5,NULL,NULL,1,1),(5,8,6,5,NULL,NULL,1,1),(6,10,6,5,NULL,NULL,1,1),(7,7,6,5,1,NULL,1,1);
/*!40000 ALTER TABLE `alarm` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-01-31 11:09:31
