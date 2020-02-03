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
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `post` (
  `idpost` bigint(20) NOT NULL AUTO_INCREMENT,
  `member_id` bigint(20) NOT NULL,
  `post_title` varchar(200) DEFAULT NULL,
  `post_writer` varchar(50) DEFAULT NULL,
  `date_created` datetime DEFAULT NULL,
  `update_created` datetime DEFAULT NULL,
  `code` longtext,
  `like_count` int(11) DEFAULT NULL,
  `views` int(11) DEFAULT NULL,
  `image_path` varchar(200) DEFAULT NULL,
  `access` tinyint(4) NOT NULL,
  PRIMARY KEY (`idpost`),
  KEY `fk_post_member1_idx` (`member_id`),
  CONSTRAINT `fk_post_member1` FOREIGN KEY (`member_id`) REFERENCES `member` (`idmember`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (1,5,'동적쿼리 작성하는 법','진호짱짱맨','2020-01-23 11:29:43',NULL,'어쩌구 저쩌구',0,0,'어쩌구',1),(2,5,'인생은 왜 외로운가','진호짱짱맨','2020-01-23 11:31:14',NULL,'어쩌구 저쩌구',0,0,'어쩌구',1),(3,5,'라이언은 왜 귀여운가','진호짱짱맨','2020-01-23 11:31:16',NULL,'어쩌구 저쩌구',0,0,'어쩌구',1),(4,5,'진호따리진호따리','진호짱짱맨','2020-01-23 11:31:18',NULL,'어쩌구 저쩌구',0,0,'어쩌구',1),(5,6,'하 난 왤케 많이 먹을까','락현짱짱맨','2020-01-23 12:13:49',NULL,'어쩌구 저쩌구',0,3,'어쩌구',1),(6,7,'아아아 졸령 ㅜㅜ','영은짱짱맨','2020-01-23 14:16:22',NULL,'어쩌구 저쩌구',0,0,'어쩌구',1),(7,6,'하 난 왤케 많이 먹을까','락현짱짱맨','2020-01-23 14:18:59',NULL,'어쩌구 저쩌구',0,0,'어쩌구',1),(8,10,'넌 그냥 많이 먹잖아','진호짱짱맨','2020-01-23 15:17:04',NULL,'어쩌구 저쩌구',0,0,'어쩌구',1);
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-01-31 11:09:33
