-- MySQL dump 10.13  Distrib 5.7.24, for Linux (x86_64)
--
-- Host: localhost    Database: chat
-- ------------------------------------------------------
-- Server version	5.7.24-0ubuntu0.16.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `CONVERSATION_MESSAGE`
--

DROP TABLE IF EXISTS `CONVERSATION_MESSAGE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CONVERSATION_MESSAGE` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fromUser` varchar(20) COLLATE utf8_bin NOT NULL,
  `toUser` varchar(20) COLLATE utf8_bin NOT NULL,
  `time` datetime NOT NULL,
  `friendId` int(11) NOT NULL,
  `content` text COLLATE utf8_bin,
  PRIMARY KEY (`id`),
  KEY `friendId_index` (`friendId`),
  KEY `fk_CONVERSATION_MESSAGE_fromUser_idx` (`fromUser`),
  KEY `fk_CONVERSATION_MESSAGE_toUser_idx` (`toUser`),
  CONSTRAINT `fk_CONVERSATION_MESSAGE_friend` FOREIGN KEY (`friendId`) REFERENCES `FRIEND` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_CONVERSATION_MESSAGE_fromUser` FOREIGN KEY (`fromUser`) REFERENCES `USER` (`userName`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_CONVERSATION_MESSAGE_toUser` FOREIGN KEY (`toUser`) REFERENCES `USER` (`userName`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CONVERSATION_MESSAGE`
--

LOCK TABLES `CONVERSATION_MESSAGE` WRITE;
/*!40000 ALTER TABLE `CONVERSATION_MESSAGE` DISABLE KEYS */;
/*!40000 ALTER TABLE `CONVERSATION_MESSAGE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FRIEND`
--

DROP TABLE IF EXISTS `FRIEND`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FRIEND` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userA` varchar(20) COLLATE utf8_bin NOT NULL,
  `userB` varchar(20) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique` (`userA`,`userB`),
  KEY `fk_FRIEND_1_idx` (`userA`),
  KEY `fk_FRIEND_userB_idx` (`userB`),
  CONSTRAINT `fk_FRIEND_userA` FOREIGN KEY (`userA`) REFERENCES `USER` (`userName`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_FRIEND_userB` FOREIGN KEY (`userB`) REFERENCES `USER` (`userName`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FRIEND`
--

LOCK TABLES `FRIEND` WRITE;
/*!40000 ALTER TABLE `FRIEND` DISABLE KEYS */;
/*!40000 ALTER TABLE `FRIEND` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GROUP`
--

DROP TABLE IF EXISTS `GROUP`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `GROUP` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `groupName` varchar(20) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GROUP`
--

LOCK TABLES `GROUP` WRITE;
/*!40000 ALTER TABLE `GROUP` DISABLE KEYS */;
/*!40000 ALTER TABLE `GROUP` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GROUP_MEMBER`
--

DROP TABLE IF EXISTS `GROUP_MEMBER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `GROUP_MEMBER` (
  `groupId` int(11) NOT NULL,
  `user` varchar(20) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`groupId`,`user`),
  KEY `fk_GROUP_MEMBER_user_idx` (`user`),
  CONSTRAINT `fk_GROUP_MEMBER_group` FOREIGN KEY (`groupId`) REFERENCES `GROUP` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_GROUP_MEMBER_user` FOREIGN KEY (`user`) REFERENCES `USER` (`userName`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GROUP_MEMBER`
--

LOCK TABLES `GROUP_MEMBER` WRITE;
/*!40000 ALTER TABLE `GROUP_MEMBER` DISABLE KEYS */;
/*!40000 ALTER TABLE `GROUP_MEMBER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GROUP_MESSAGE`
--

DROP TABLE IF EXISTS `GROUP_MESSAGE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `GROUP_MESSAGE` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `groupId` int(11) NOT NULL,
  `time` datetime NOT NULL,
  `fromUser` varchar(20) COLLATE utf8_bin NOT NULL,
  `content` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `groupId_index` (`groupId`),
  KEY `fk_GROUP_MESSAGE_fromUser_idx` (`fromUser`),
  CONSTRAINT `fk_GROUP_MESSAGE_fromUser` FOREIGN KEY (`fromUser`) REFERENCES `USER` (`userName`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_GROUP_MESSAGE_group` FOREIGN KEY (`groupId`) REFERENCES `GROUP` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GROUP_MESSAGE`
--

LOCK TABLES `GROUP_MESSAGE` WRITE;
/*!40000 ALTER TABLE `GROUP_MESSAGE` DISABLE KEYS */;
/*!40000 ALTER TABLE `GROUP_MESSAGE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER`
--

DROP TABLE IF EXISTS `USER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER` (
  `userName` varchar(20) COLLATE utf8_bin NOT NULL,
  `password` varchar(64) COLLATE utf8_bin NOT NULL,
  `email` varchar(50) COLLATE utf8_bin NOT NULL,
  `fullName` varchar(50) COLLATE utf8_bin NOT NULL,
  `isOnline` tinyint(1) NOT NULL,
  PRIMARY KEY (`userName`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER`
--

LOCK TABLES `USER` WRITE;
/*!40000 ALTER TABLE `USER` DISABLE KEYS */;
/*!40000 ALTER TABLE `USER` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-12-10 11:01:28
