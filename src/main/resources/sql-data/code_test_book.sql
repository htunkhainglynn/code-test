-- MySQL dump 10.13  Distrib 8.0.31, for macos12 (x86_64)
--
-- Host: localhost    Database: code_test
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cover_photourl` varchar(255) NOT NULL,
  `isbn` varchar(255) DEFAULT NULL,
  `language` varchar(255) DEFAULT NULL,
  `pages` int NOT NULL,
  `pdfurl` varchar(255) DEFAULT NULL,
  `published_date` date DEFAULT NULL,
  `publisher` varchar(255) DEFAULT NULL,
  `summary` varchar(255) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ehpdfjpu1jm3hijhj4mm0hx9h` (`isbn`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES (1,'http://res.cloudinary.com/dx97yn40v/image/upload/v1698510964/books/ldgdxaydzb2jcineawd5.jpg','978-0307387899','English',287,'https://www.test.com','2006-09-26','Knopf','A post-apocalyptic novel about a father and son\'s journey.','The Road'),(2,'http://res.cloudinary.com/dx97yn40v/image/upload/v1698511315/books/hqda4ksqsxsgnaw431oq.png','978-0743273565','English',180,NULL,'1925-04-10','Scribner','A story of decadence and excess in the Jazz Age.','The Great Gatsby'),(3,'http://res.cloudinary.com/dx97yn40v/image/upload/v1698511502/books/aklsnt2p7j2itdyomdrg.webp','978-0061120084','English',336,NULL,'1960-07-11','J. B. Lippincott & Co.','A novel about racial injustice and moral growth in the American South.','To Kill a Mockingbird'),(4,'http://res.cloudinary.com/dx97yn40v/image/upload/v1698512297/books/qeoc4d5uypjo4r3qjt2a.jpg','978-0451524935','English',328,NULL,'1949-05-28','Secker and Warburg','A vision of a totalitarian society where the government exercises total control.','1984'),(5,'http://res.cloudinary.com/dx97yn40v/image/upload/v1698512520/books/ewtge2wngr9ri8if4pj3.jpg','978-0486280486','English',279,NULL,'1813-01-28','T. Egerton, Whitehall','A classic novel exploring themes of love, class, and morality.','Pride and Prejudice'),(6,'http://res.cloudinary.com/dx97yn40v/image/upload/v1698512605/books/qgchnrlzmuyy8vf7bmrs.jpg','978-0316769488','English',277,NULL,'1951-07-16','Little, Brown and Company','A story of a teenager\'s search for authenticity in a phony world.','The Catcher in the Rye'),(8,'http://res.cloudinary.com/dx97yn40v/image/upload/v1698513128/books/aupy6cmzavdoljn5ivqx.jpg','978-0618002214','English',310,'www.pdftest.com','1937-09-21','George Allen & Unwin','An adventure novel set in a fantasy world.','The Hobbit'),(9,'http://res.cloudinary.com/dx97yn40v/image/upload/v1698513153/books/klgwkadegdgeakperknm.jpg','978-0385121675','English',447,'www.pdftest.com','1977-01-28','Doubleday','A horror novel about a haunted hotel.','The Shining'),(10,'http://res.cloudinary.com/dx97yn40v/image/upload/v1698517208/books/ti1tpsamubiin2hky97f.jpg','978-0060850524','English',311,'www.pdftest.com','1932-01-07','Chatto & Windus','A dystopian novel exploring a futuristic society.','Brave New World');
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-10-29  0:59:33
