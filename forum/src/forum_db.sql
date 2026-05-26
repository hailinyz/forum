-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: forum_db
-- ------------------------------------------------------
-- Server version	8.0.42

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `t_article`
--

CREATE Database forum_db;
USE forum_db;

DROP TABLE IF EXISTS `t_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_article` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号，主键自增',
  `boardId` bigint NOT NULL COMMENT '帖子编号',
  `userId` bigint NOT NULL COMMENT '发帖人，关联用户编号',
  `title` varchar(100) COLLATE utf8mb4_general_ci NOT NULL COMMENT '帖子标题',
  `content` text COLLATE utf8mb4_general_ci NOT NULL COMMENT '帖子正文',
  `visitCount` int NOT NULL DEFAULT '0' COMMENT '访问量',
  `replyCount` int NOT NULL DEFAULT '0' COMMENT '回复数',
  `likeCount` int NOT NULL DEFAULT '0' COMMENT '点赞数',
  `state` tinyint NOT NULL DEFAULT '0' COMMENT '状态 0正常，1禁用',
  `deleteState` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除，0否，1是',
  `createTime` datetime NOT NULL COMMENT '创建时间，精确到秒',
  `updateTime` datetime NOT NULL COMMENT '更新时间，精确到秒',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_article`
--

LOCK TABLES `t_article` WRITE;
/*!40000 ALTER TABLE `t_article` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_article_reply`
--

DROP TABLE IF EXISTS `t_article_reply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_article_reply` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号，主键自增',
  `articleId` bigint NOT NULL COMMENT '关联帖子编号',
  `postUserId` bigint NOT NULL COMMENT '楼主用户，关联用户编号',
  `replyId` bigint NOT NULL COMMENT '关联回复编号，支持楼中楼',
  `replyUserId` bigint NOT NULL COMMENT '楼主下的回复用户编号，支持楼',
  `content` varchar(500) COLLATE utf8mb4_general_ci NOT NULL COMMENT '回贴内容',
  `likeCount` int NOT NULL COMMENT '回贴内容',
  `state` tinyint NOT NULL DEFAULT '0' COMMENT '状态 0正常，1禁用',
  `deleteState` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除，0否，1是',
  `createTime` datetime NOT NULL COMMENT '创建时间，精确到秒',
  `updateTime` datetime NOT NULL COMMENT '更新时间，精确到秒',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_article_reply`
--

LOCK TABLES `t_article_reply` WRITE;
/*!40000 ALTER TABLE `t_article_reply` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_article_reply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_board`
--

DROP TABLE IF EXISTS `t_board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_board` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号，主键自增',
  `name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '版块名',
  `articleCount` int NOT NULL DEFAULT '0' COMMENT '帖子数量',
  `sort` int NOT NULL DEFAULT '0' COMMENT '排序优先级，升序',
  `state` tinyint NOT NULL DEFAULT '0' COMMENT '状态 0正常，1禁用',
  `deleteState` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除，0否，1是',
  `createTime` datetime NOT NULL COMMENT '创建时间，精确到秒',
  `updateTime` datetime NOT NULL COMMENT '更新时间，精确到秒',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_board`
--

LOCK TABLES `t_board` WRITE;
/*!40000 ALTER TABLE `t_board` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_board` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_message`
--

DROP TABLE IF EXISTS `t_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_message` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号，主键自增',
  `postUserId` bigint NOT NULL COMMENT '发送者，关联用户编号',
  `receiveUserId` bigint NOT NULL COMMENT '接收者，关联用户编号',
  `content` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容',
  `state` tinyint NOT NULL DEFAULT '0' COMMENT '状态 0正常，1禁用',
  `deleteState` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除，0否，1是',
  `createTime` datetime NOT NULL COMMENT '创建时间，精确到秒',
  `updateTime` datetime NOT NULL COMMENT '更新时间，精确到秒',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_message`
--

LOCK TABLES `t_message` WRITE;
/*!40000 ALTER TABLE `t_message` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user`
--

DROP TABLE IF EXISTS `t_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号，主键自增',
  `username` varchar(20) COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名，唯一',
  `password` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '加密后的密码',
  `nickname` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '昵称',
  `phoneNum` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '手机号',
  `email` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '电子邮箱',
  `gender` tinyint NOT NULL DEFAULT '2' COMMENT '性别 0女，1男，2保密',
  `salt` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '为密码加盐',
  `avatarUrl` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户头像路径',
  `articleCount` int NOT NULL DEFAULT '0' COMMENT '发帖数量',
  `isAdmin` tinyint NOT NULL DEFAULT '0' COMMENT '是否管理员 0否，1是',
  `remark` varchar(1000) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注，自我介绍',
  `state` tinyint NOT NULL DEFAULT '0' COMMENT '状态 0正常，1禁言',
  `deleteState` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除，0否，1是',
  `createTime` datetime NOT NULL COMMENT '创建时间，精确到秒',
  `updateTime` datetime NOT NULL COMMENT '更新时间，精确到秒',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user`
--

LOCK TABLES `t_user` WRITE;
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;
INSERT INTO `t_user` VALUES (1,'bittooy','123456','比特',NULL,NULL,2,'123','avatar.png',0,1,NULL,0,0,'2022-12-13 22:30:10','2022-12-13 22:30:13');
/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-05-26 12:12:14
