-- MySQL dump 10.13  Distrib 8.0.14, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: project-manager-600
-- ------------------------------------------------------
-- Server version	8.0.14

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
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `comments` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) COLLATE utf8_polish_ci NOT NULL,
  `creation_date` datetime NOT NULL,
  `author_id` bigint(20) DEFAULT NULL,
  `task_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKn2na60ukhs76ibtpt9burkm27` (`author_id`),
  KEY `FKi7pp0331nbiwd2844kg78kfwb` (`task_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `logs`
--

DROP TABLE IF EXISTS `logs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `logs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) COLLATE utf8_polish_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logs`
--

LOCK TABLES `logs` WRITE;
/*!40000 ALTER TABLE `logs` DISABLE KEYS */;
INSERT INTO `logs` VALUES (1,'Użytkownik Sebastian stworzył nowe zadanie: Pierwsze zadanie.\n2019-05-14 13:48:30'),(2,'Użytkownik Mariusz stworzył nowe zadanie: Drugie zadanie.\n2019-05-14 13:49:35'),(3,'Użytkownik Mariusz zmienił status zadania: Drugie zadanie, na: W trakcie.\n2019-05-14 13:49:41'),(4,'Użytkownik Mariusz stworzył nowe zadanie: Trzecie zadanie.\n2019-05-14 13:50:34'),(5,'Użytkownik Kazimiera stworzył nowe zadanie: Ukończone zadanie.\n2019-05-14 13:51:56'),(6,'Użytkownik Kazimiera zmienił status zadania: Ukończone zadanie, na: Zakończone.\n2019-05-14 13:52:13');
/*!40000 ALTER TABLE `logs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tasks`
--

DROP TABLE IF EXISTS `tasks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tasks` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `deadline_date` date NOT NULL,
  `description` varchar(255) COLLATE utf8_polish_ci NOT NULL,
  `end_date` date DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_polish_ci NOT NULL,
  `start_date` date NOT NULL,
  `status` varchar(255) COLLATE utf8_polish_ci NOT NULL,
  `assignee_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKekr1dgiqktpyoip3qmp6lxsit` (`assignee_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tasks`
--

LOCK TABLES `tasks` WRITE;
/*!40000 ALTER TABLE `tasks` DISABLE KEYS */;
INSERT INTO `tasks` VALUES (1,'2019-05-23','Zadanie Sebastiana.',NULL,'Pierwsze zadanie','2019-05-19','TO_DO',1),(2,'2019-05-30','Zadanie Mariusza.',NULL,'Drugie zadanie','2019-05-19','IN_PROGRESS',2),(3,'2019-05-30','Zadanie Sebastiana utworzone przez Mariusza.',NULL,'Trzecie zadanie','2019-05-12','TO_DO',1),(4,'2019-05-16','Zadanie Mariusza utworzone przez Kazimiere.','2019-05-09','Ukończone zadanie','2019-04-30','DONE',2);
/*!40000 ALTER TABLE `tasks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `password` varchar(60) COLLATE utf8_polish_ci NOT NULL,
  `role` varchar(255) COLLATE utf8_polish_ci NOT NULL,
  `username` varchar(60) COLLATE utf8_polish_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_r43af9ap4edm43mmtq01oddj6` (`username`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'$2a$10$UobSnw8qHrkdK5i55nmFnuX384ZGNtDHC.hAezdbSk4mc8HwbzKZK','PROGRAMMER','Sebastian'),(2,'$2a$10$aFLmHy1J4Fel2BWA46cjW.4cVzLpztPri0WOPYJXvFnL3JV3b/c0S','PROGRAMMER','Mariusz'),(3,'$2a$10$XLP3RJPUys/QFC3Ep6MDL.ocI0/G5hKnSDg/D5sIuCPx.WGWLSBWi','PROJECT_MANAGER','Kazimiera');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-05-14 14:10:01
