-- MySQL dump 10.13  Distrib 5.7.24, for osx11.1 (x86_64)
--
-- Host: localhost    Database: videostreamingdb
-- ------------------------------------------------------
-- Server version	9.2.0

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
-- Table structure for table `engagement_statistics`
--

DROP TABLE IF EXISTS `engagement_statistics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `engagement_statistics` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `impressions` bigint DEFAULT NULL,
  `views` bigint DEFAULT NULL,
  `video_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKk2wm2egs3mwpglaubtfkxueh5` (`video_id`),
  KEY `idx_video_id` (`video_id`),
  CONSTRAINT `FK5v3pmhsdnjb80wjnbww2hdygm` FOREIGN KEY (`video_id`) REFERENCES `videos_content` (`video_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `engagement_statistics`
--

LOCK TABLES `engagement_statistics` WRITE;
/*!40000 ALTER TABLE `engagement_statistics` DISABLE KEYS */;
INSERT INTO `engagement_statistics` VALUES (1,0,0,1),(2,0,0,2),(3,0,1,3),(4,0,0,4),(5,0,0,5),(6,0,0,6),(7,0,0,7),(8,0,0,8),(9,0,0,9),(10,0,0,10);
/*!40000 ALTER TABLE `engagement_statistics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'$2a$10$IcAv3oH3EdFcCZd1YKq5suZQR0ytOcG3bnJ.d0wjT/N/8HdTSAlX6','USER','ruchir'),(2,'$2a$10$/lB7CYxM.sTnscAkkIYFceDkf8WHVgjiU8XbEsRtQxc/6NlwiGdOO','USER','john');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `videos_content`
--

DROP TABLE IF EXISTS `videos_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `videos_content` (
  `video_id` bigint NOT NULL AUTO_INCREMENT,
  `content` text,
  `is_delisted` bit(1) NOT NULL,
  `title` varchar(255) NOT NULL,
  PRIMARY KEY (`video_id`),
  UNIQUE KEY `UKqpbgrm4ky1ycem6ldaxgy7xcd` (`title`),
  KEY `idx_video_id` (`video_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `videos_content`
--

LOCK TABLES `videos_content` WRITE;
/*!40000 ALTER TABLE `videos_content` DISABLE KEYS */;
INSERT INTO `videos_content` VALUES (1,'Sample Video Content',_binary '\0','Pulp Fiction'),(2,'Sample Video Content',_binary '\0','The Shawshank Redemption'),(3,'Sample Video Content',_binary '\0','Inception'),(4,'Sample Video Content',_binary '\0','The Godfather'),(5,'Sample Video Content',_binary '\0','The Dark Knight'),(6,'Sample Video Content',_binary '\0','Forrest Gump'),(7,'Sample Video Content',_binary '\0','Fight Club'),(8,'Sample Video Content',_binary '\0','The Matrix'),(9,'Sample Video Content',_binary '\0','The Lion King'),(10,'Sample Video Content',_binary '\0','Gladiator');
/*!40000 ALTER TABLE `videos_content` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `videos_meta_data`
--

DROP TABLE IF EXISTS `videos_meta_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `videos_meta_data` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cast` varchar(255) DEFAULT NULL,
  `director` varchar(255) DEFAULT NULL,
  `genre` varchar(255) DEFAULT NULL,
  `running_time` int DEFAULT NULL,
  `synopsis` varchar(255) DEFAULT NULL,
  `year_of_release` int DEFAULT NULL,
  `video_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKdoqvnmnqcgo2fkxva9hyjj0xe` (`video_id`),
  KEY `idx_year_of_release` (`year_of_release`),
  KEY `idx_genre` (`genre`),
  CONSTRAINT `FKitlqo1uix2ew2qsi7sj4fqr7c` FOREIGN KEY (`video_id`) REFERENCES `videos_content` (`video_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `videos_meta_data`
--

LOCK TABLES `videos_meta_data` WRITE;
/*!40000 ALTER TABLE `videos_meta_data` DISABLE KEYS */;
INSERT INTO `videos_meta_data` VALUES (1,'John Travolta, Uma Thurman','Quentin Tarantino','Crime',154,'The lives of two mob hitmen, a boxer, a gangster\'s wife, and a pair of diner bandits intertwine in four tales of violence and redemption.',1994,1),(2,'Tim Robbins, Morgan Freeman','Frank Darabont','Drama',142,'Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.',1994,2),(3,'Leonardo DiCaprio, Joseph Gordon-Levitt','Christopher Nolan','Action, Sci-Fi',148,'A thief who enters the dreams of others to steal secrets from their subconscious is given a chance to have his criminal history erased if he can successfully perform an inception.',2010,3),(4,'Marlon Brando, Al Pacino','Francis Ford Coppola','Crime, Drama',175,'The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.',1972,4),(5,'Christian Bale, Heath Ledger','Christopher Nolan','Action, Crime, Drama',152,'When the menace known as The Joker emerges from his mysterious past, he wreaks havoc and chaos on the people of Gotham.',2008,5),(6,'Tom Hanks, Robin Wright','Robert Zemeckis','Drama, Romance',142,'The presidencies of Kennedy and Johnson, the Vietnam War, the Watergate scandal, and other historical events unfold from the perspective of an Alabama man with an extraordinary life.',1994,6),(7,'Brad Pitt, Edward Norton','David Fincher','Drama',139,'An insomniac office worker and a soap salesman form an underground fight club that evolves into something much, much more.',1999,7),(8,'Keanu Reeves, Laurence Fishburne','Lana Wachowski, Lilly Wachowski','Action, Sci-Fi',136,'A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.',1999,8),(9,'Matthew Broderick, Jeremy Irons','Roger Allers, Rob Minkoff','Animation, Adventure, Drama',88,'Lion prince Simba and his father are targeted by his evil uncle, who wants to rule the land.',1994,9),(10,'Russell Crowe, Joaquin Phoenix','Ridley Scott','Action, Adventure, Drama',155,'A betrayed Roman general seeks revenge against the corrupt emperor who murdered his family and sent him into slavery.',2000,10);
/*!40000 ALTER TABLE `videos_meta_data` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-13 11:03:57
