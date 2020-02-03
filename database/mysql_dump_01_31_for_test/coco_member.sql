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
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `member` (
  `idmember` bigint(20) NOT NULL AUTO_INCREMENT,
  `rank_id` bigint(20) NOT NULL,
  `is_manager` tinyint(4) NOT NULL,
  `is_delete` tinyint(4) NOT NULL,
  `nickname` varchar(50) NOT NULL,
  `id` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `email` varchar(150) DEFAULT NULL,
  `git_url` varchar(150) DEFAULT NULL,
  `kakao_url` varchar(150) DEFAULT NULL,
  `instagram_url` varchar(150) DEFAULT NULL,
  `date_created` datetime DEFAULT NULL,
  `update_created` datetime DEFAULT NULL,
  PRIMARY KEY (`idmember`),
  KEY `fk_meber_rank1_idx` (`rank_id`),
  CONSTRAINT `fk_meber_rank1` FOREIGN KEY (`rank_id`) REFERENCES `rank` (`idrank`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES (5,40,0,0,'진호짱짱맨','a13975','1234','email@gmail.com','a1234@github.com',NULL,'123@instagram.com','2020-01-23 11:10:21',NULL),(6,38,0,0,'락현짱짱맨','a13975','1234','email@gmail.com','a1234@github.com',NULL,'123@instagram.com','2020-01-23 11:10:21',NULL),(7,60,0,0,'영은짱짱맨','a1113975','1234','email@gmail.com','a1234@github.com',NULL,'123@instagram.com','2020-01-23 11:10:21',NULL),(8,40,0,0,'서현짱짱맨','a3213975','1234','email@gmail.com','a1234@github.com',NULL,'123@instagram.com','2020-01-23 11:10:21',NULL),(9,50,0,0,'지훈짱짱맨','a1213975','1234','email@gmail.com','a1234@github.com',NULL,'123@instagram.com','2020-01-23 11:10:21',NULL),(10,10,0,0,'진호짱짱맨','a1323975','1234','email@gmail.com','a1234@github.com',NULL,'123@instagram.com','2020-01-23 11:10:21',NULL),(11,22,0,0,'찐빵맨','123442','1234','kimd@gmail.com','123@github.com',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
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
