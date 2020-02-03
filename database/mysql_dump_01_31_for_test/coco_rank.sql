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
-- Table structure for table `rank`
--

DROP TABLE IF EXISTS `rank`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `rank` (
  `idrank` bigint(20) NOT NULL AUTO_INCREMENT,
  `grade` varchar(50) NOT NULL,
  PRIMARY KEY (`idrank`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rank`
--

LOCK TABLES `rank` WRITE;
/*!40000 ALTER TABLE `rank` DISABLE KEYS */;
INSERT INTO `rank` VALUES (1,'아이언'),(2,'아이언'),(3,'아이언'),(4,'아이언'),(5,'아이언'),(6,'아이언'),(7,'아이언'),(8,'아이언'),(9,'아이언'),(10,'아이언'),(11,'브론즈'),(12,'브론즈'),(13,'브론즈'),(14,'브론즈'),(15,'브론즈'),(16,'브론즈'),(17,'브론즈'),(18,'브론즈'),(19,'브론즈'),(20,'브론즈'),(21,'실버'),(22,'실버'),(23,'실버'),(24,'실버'),(25,'실버'),(26,'실버'),(27,'실버'),(28,'실버'),(29,'실버'),(30,'실버'),(31,'골드'),(32,'골드'),(33,'골드'),(34,'골드'),(35,'골드'),(36,'골드'),(37,'골드'),(38,'골드'),(39,'골드'),(40,'골드'),(41,'플래티넘'),(42,'플래티넘'),(43,'플래티넘'),(44,'플래티넘'),(45,'플래티넘'),(46,'플래티넘'),(47,'플래티넘'),(48,'플래티넘'),(49,'플래티넘'),(50,'플래티넘'),(51,'다이아'),(52,'다이아'),(53,'다이아'),(54,'다이아'),(55,'다이아'),(56,'다이아'),(57,'다이아'),(58,'다이아'),(59,'다이아'),(60,'다이아'),(61,'마스터'),(62,'마스터'),(63,'마스터'),(64,'마스터'),(65,'마스터'),(66,'마스터'),(67,'마스터'),(68,'마스터'),(69,'마스터'),(70,'마스터'),(71,'챌린저'),(72,'챌린저'),(73,'챌린저'),(74,'챌린저'),(75,'챌린저'),(76,'챌린저'),(77,'챌린저'),(78,'챌린저'),(79,'챌린저'),(80,'챌린저');
/*!40000 ALTER TABLE `rank` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-01-31 11:09:32
