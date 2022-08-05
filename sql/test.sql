-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: localhost    Database: apartment_manager
-- ------------------------------------------------------
-- Server version	8.0.29
drop database apartment_manager; 
create database apartment_manager;
use apartment_manager;

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
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `Id_admin` varchar(20) NOT NULL,
  `So_dien_thoai` varchar(15) DEFAULT NULL,
  `Email` varchar(40) DEFAULT NULL,
  `Password` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`Id_admin`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES ('ad_anhpq0','0386293232','phamquanganh750@gmail.com','123'),('ad_nhungth0','0385874847','tranhongnhung250@gmail.com','123'),('ad_quangnd0','0384564492','quangnguyenduc1212@gmail.com','123');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chu_so_huu`
--

DROP TABLE IF EXISTS `chu_so_huu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chu_so_huu` (
  `Id_chu_so_huu` varchar(20) NOT NULL,
  `CCCD` varchar(20) DEFAULT NULL,
  `Ten` varchar(40) DEFAULT NULL,
  `So_dien_thoai` varchar(15) DEFAULT NULL,
  `Email` varchar(40) DEFAULT NULL,
  `Gioi_tinh` varchar(10) DEFAULT NULL,
  `Password` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`Id_chu_so_huu`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chu_so_huu`
--

LOCK TABLES `chu_so_huu` WRITE;
/*!40000 ALTER TABLE `chu_so_huu` DISABLE KEYS */;
INSERT INTO `chu_so_huu` VALUES ('anhnq0','230301326548','Nguyen Quynh Anh','155658456','anhquynh@gmail.com','Nu','sd0'),('anhnq1','436265565598','Nguyen Quang Anh','165484548','anhnq@gmail.com','Nam','0fc'),('anhpq0',NULL,NULL,'0386293232','phamquanganh750@gmail.com',NULL,'2Ac'),('binhlv0','154898154587','Le Van Binh','211545578',NULL,'Nam','45d'),('giangnt0','251147770151','Nguyen Thi Giang','215154154',NULL,'Nu','15f'),('hieunv0','254612114457','Nguyen Van Hieu',NULL,'nguyenvanhieu@gmail.com','Nam','cdf'),('hoangnm0','235601711201','Nguyen Minh Hoang','135456480','minhhoang123@gmail.com','Nam','ds0'),('hoant0','216548646551','Nguyen Thi Hoa',NULL,'hoanguyen20@gmail.com','Nu','dfs'),('hunght0','370545114521','Tran Huu Hung','156433215','hungnguyen@gmail.com','Nam','dfg'),('linhnt0','370065401547','Nguyen Thi Linh','868509981','linhthinguyen@gmail.com','Nu','0%r'),('longnv0','215757110001','Nguyen Van Long','21533145','nguyenlong@gmail.com','Nam','d30'),('minhtn0','102359400457','Tran Ngoc Minh','299584756','minhtranngoc@gmail.com','Nam','25s'),('nhungth0',NULL,NULL,'0385874847','tranhongnhung250@gmail.com',NULL,'1@a'),('quangnd0',NULL,NULL,'0384564492','quangnguyenduc1212@gmail.com',NULL,'39#'),('quangpa0','121545959887','Pham Anh Quang','386541251','anhquang706@gmail.com','Nam','w@e'),('quocthv0','556232323264','Tran Huu Van Quoc','254867898',NULL,'Nam','nbs'),('tungnt0','772222222114','Nguyen Thanh Tung','123589332','thanhtung@gmail.com','Nam','cvf'),('vanlt0','878221220217','Le Thi Van',NULL,'levan@gmail.com','Nu','xvx');
/*!40000 ALTER TABLE `chu_so_huu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dich_vu`
--

DROP TABLE IF EXISTS `dich_vu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dich_vu` (
  `Ma_phong` int NOT NULL,
  `Ma_dich_vu` int NOT NULL,
  `So_cu` int DEFAULT NULL,
  `So_moi` int DEFAULT NULL,
  `Thang` date NOT NULL,
  `Da_dong` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`Ma_phong`,`Ma_dich_vu`,`Thang`),
  KEY `Ma_dich_vu` (`Ma_dich_vu`),
  CONSTRAINT `dich_vu_ibfk_1` FOREIGN KEY (`Ma_phong`) REFERENCES `phong` (`Ma_phong`),
  CONSTRAINT `dich_vu_ibfk_2` FOREIGN KEY (`Ma_dich_vu`) REFERENCES `don_gia_dich_vu` (`Ma_dich_vu`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dich_vu`
--

LOCK TABLES `dich_vu` WRITE;
/*!40000 ALTER TABLE `dich_vu` DISABLE KEYS */;
INSERT INTO `dich_vu` VALUES (101,1,2000,2200,'2022-05-01',1),(101,1,2200,2500,'2022-06-01',1),(101,1,2500,2790,'2022-07-01',1),(101,2,200,225,'2022-05-01',1),(101,2,225,255,'2022-06-01',1),(101,2,255,283,'2022-07-01',1),(101,3,0,1,'2022-05-01',1),(101,3,0,1,'2022-06-01',1),(101,3,0,1,'2022-07-01',1),(102,1,1851,1998,'2022-05-01',1),(102,1,1998,2300,'2022-06-01',1),(102,1,2300,2623,'2022-07-01',1),(102,2,110,135,'2022-05-01',1),(102,2,135,163,'2022-06-01',1),(102,2,163,190,'2022-07-01',1),(102,3,0,1,'2022-05-01',1),(102,3,0,1,'2022-06-01',1),(102,3,0,1,'2022-07-01',1),(103,1,111,456,'2022-05-01',1),(103,1,456,700,'2022-06-01',1),(103,1,700,1000,'2022-07-01',0),(103,2,45,59,'2022-05-01',1),(103,2,59,80,'2022-06-01',1),(103,2,80,105,'2022-07-01',0),(103,3,0,1,'2022-05-01',1),(103,3,0,1,'2022-06-01',1),(103,3,0,1,'2022-07-01',0),(201,1,6589,6723,'2022-05-01',1),(201,1,6723,7100,'2022-06-01',1),(201,1,7100,7425,'2022-07-01',1),(201,2,200,230,'2022-05-01',1),(201,2,230,265,'2022-06-01',1),(201,2,265,293,'2022-07-01',1),(201,3,0,1,'2022-05-01',1),(201,3,0,1,'2022-06-01',1),(201,3,0,1,'2022-07-01',1),(202,1,7895,7999,'2022-05-01',1),(202,1,7999,8230,'2022-06-01',1),(202,1,8230,8500,'2022-07-01',1),(202,2,500,515,'2022-05-01',1),(202,2,515,537,'2022-06-01',1),(202,2,537,560,'2022-07-01',1),(202,3,0,1,'2022-05-01',1),(202,3,0,1,'2022-06-01',1),(202,3,0,1,'2022-07-01',1),(203,1,18945,19120,'2022-05-01',1),(203,1,19120,19500,'2022-06-01',0),(203,1,19500,19835,'2022-07-01',1),(203,2,515,540,'2022-05-01',1),(203,2,540,572,'2022-06-01',0),(203,2,572,600,'2022-07-01',1),(203,3,0,1,'2022-05-01',1),(203,3,0,1,'2022-06-01',0),(203,3,0,1,'2022-07-01',1),(301,1,200,450,'2022-05-01',1),(301,1,450,678,'2022-06-01',1),(301,1,678,999,'2022-07-01',1),(301,2,30,50,'2022-05-01',1),(301,2,50,72,'2022-06-01',1),(301,2,72,99,'2022-07-01',1),(301,3,0,1,'2022-05-01',1),(301,3,0,1,'2022-06-01',1),(301,3,0,1,'2022-07-01',1),(302,1,6875,7025,'2022-05-01',1),(302,1,7025,7289,'2022-06-01',1),(302,1,7289,7500,'2022-07-01',1),(302,2,402,430,'2022-05-01',1),(302,2,430,452,'2022-06-01',1),(302,2,452,475,'2022-07-01',1),(302,3,0,1,'2022-05-01',1),(302,3,0,1,'2022-06-01',1),(302,3,0,1,'2022-07-01',1),(304,1,5000,5400,'2022-05-01',1),(304,1,5400,5760,'2022-06-01',1),(304,1,5760,6000,'2022-07-01',1),(304,2,330,350,'2022-05-01',1),(304,2,350,376,'2022-06-01',1),(304,2,376,400,'2022-07-01',1),(304,3,0,1,'2022-05-01',1),(304,3,0,1,'2022-06-01',1),(304,3,0,1,'2022-07-01',1),(401,1,68795,69030,'2022-05-01',0),(401,1,69030,69400,'2022-06-01',1),(401,1,69400,69700,'2022-07-01',1),(401,2,2000,2032,'2022-05-01',0),(401,2,2032,2070,'2022-06-01',1),(401,2,2070,2098,'2022-07-01',1),(401,3,0,1,'2022-05-01',0),(401,3,0,1,'2022-06-01',1),(401,3,0,1,'2022-07-01',1),(402,1,5800,6054,'2022-05-01',1),(402,1,6054,6400,'2022-06-01',1),(402,1,6400,6735,'2022-07-01',1),(402,2,895,930,'2022-05-01',1),(402,2,930,955,'2022-06-01',1),(402,2,955,1015,'2022-07-01',1),(402,3,0,1,'2022-05-01',1),(402,3,0,1,'2022-06-01',1),(402,3,0,1,'2022-07-01',1),(403,1,51563,51800,'2022-05-01',1),(403,1,51800,52030,'2022-06-01',1),(403,1,52030,52400,'2022-07-01',1),(403,2,1522,1550,'2022-05-01',1),(403,2,1550,1580,'2022-06-01',1),(403,2,1580,1602,'2022-07-01',1),(403,3,0,1,'2022-05-01',1),(403,3,0,1,'2022-06-01',1),(403,3,0,1,'2022-07-01',1),(404,1,52278,52600,'2022-05-01',1),(404,1,52600,52950,'2022-06-01',1),(404,1,52950,53200,'2022-07-01',1),(404,2,4022,4050,'2022-05-01',1),(404,2,4050,4076,'2022-06-01',1),(404,2,4076,4100,'2022-07-01',1),(404,3,0,1,'2022-05-01',1),(404,3,0,1,'2022-06-01',1),(404,3,0,1,'2022-07-01',1),(502,1,50006,50500,'2022-05-01',1),(502,1,50500,50935,'2022-06-01',1),(502,1,50935,51300,'2022-07-01',1),(502,2,600,645,'2022-05-01',1),(502,2,645,670,'2022-06-01',1),(502,2,670,700,'2022-07-01',1),(502,3,0,1,'2022-05-01',1),(502,3,0,1,'2022-06-01',1),(502,3,0,1,'2022-07-01',1),(504,1,85678,86000,'2022-05-01',1),(504,1,86000,86375,'2022-06-01',1),(504,1,86375,86700,'2022-07-01',1),(504,2,4567,4603,'2022-05-01',1),(504,2,4603,4630,'2022-06-01',1),(504,2,4630,4668,'2022-07-01',1),(504,3,0,1,'2022-05-01',1),(504,3,0,1,'2022-06-01',1),(504,3,0,1,'2022-07-01',1);
/*!40000 ALTER TABLE `dich_vu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `don_gia_dich_vu`
--

DROP TABLE IF EXISTS `don_gia_dich_vu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `don_gia_dich_vu` (
  `Ma_dich_vu` int NOT NULL,
  `Ten_dich_vu` varchar(20) DEFAULT NULL,
  `Don_gia_bac_1` float DEFAULT NULL,
  `Don_gia_bac_2` float DEFAULT NULL,
  `Don_gia_bac_3` float DEFAULT NULL,
  `Don_gia_bac_4` float DEFAULT NULL,
  `Don_gia_bac_5` float DEFAULT NULL,
  `Don_gia_bac_6` float DEFAULT NULL,
  PRIMARY KEY (`Ma_dich_vu`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `don_gia_dich_vu`
--

LOCK TABLES `don_gia_dich_vu` WRITE;
/*!40000 ALTER TABLE `don_gia_dich_vu` DISABLE KEYS */;
INSERT INTO `don_gia_dich_vu` VALUES (1,'Dien',1.678,1.734,2.014,2.536,2.834,2.927),(2,'Nuoc',5.973,7.052,8.669,15.929,NULL,NULL),(3,'Ve_sinh',60000,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `don_gia_dich_vu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `don_gia_gui_xe`
--

DROP TABLE IF EXISTS `don_gia_gui_xe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `don_gia_gui_xe` (
  `Loai_xe` varchar(20) NOT NULL,
  `Don_gia` int DEFAULT NULL,
  PRIMARY KEY (`Loai_xe`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `don_gia_gui_xe`
--

LOCK TABLES `don_gia_gui_xe` WRITE;
/*!40000 ALTER TABLE `don_gia_gui_xe` DISABLE KEYS */;
INSERT INTO `don_gia_gui_xe` VALUES ('o_to',500000),('xe_dap',100000),('xe_may',200000);
/*!40000 ALTER TABLE `don_gia_gui_xe` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phong`
--

DROP TABLE IF EXISTS `phong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phong` (
  `Ma_phong` int NOT NULL,
  `Id_chu_so_huu` varchar(20) DEFAULT NULL,
  `So_nguoi` int DEFAULT NULL,
  `Loai_phong` varchar(20) DEFAULT NULL,
  `Trang_thai_phong` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`Ma_phong`),
  KEY `Id_chu_so_huu` (`Id_chu_so_huu`),
  CONSTRAINT `phong_ibfk_1` FOREIGN KEY (`Id_chu_so_huu`) REFERENCES `chu_so_huu` (`Id_chu_so_huu`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phong`
--

LOCK TABLES `phong` WRITE;
/*!40000 ALTER TABLE `phong` DISABLE KEYS */;
INSERT INTO `phong` VALUES (101,'anhnq1',4,'3 phong ngu',0),(102,'giangnt0',3,'2 phong ngu',0),(103,'vanlt0',5,'3 phong ngu',0),(104,NULL,NULL,'2 phong ngu',1),(201,'quangpa0',3,'3 phong ngu',0),(202,'tungnt0',3,'2 phong ngu',0),(203,'anhnq0',5,'3 phong ngu',0),(204,NULL,NULL,'2 phong ngu',1),(301,'binhlv0',3,'3 phong ngu',0),(302,'hieunv0',4,'2 phong ngu',0),(303,NULL,NULL,'3 phong ngu',1),(304,'linhnt0',4,'2 phong ngu',0),(401,'longnv0',3,'3 phong ngu',0),(402,'hunght0',3,'2 phong ngu',0),(403,'hoangnm0',4,'3 phong ngu',0),(404,'quocthv0',5,'2 phong ngu',0),(501,NULL,NULL,'3 phong ngu',1),(502,'hoant0',4,'2 phong ngu',0),(503,NULL,NULL,'3 phong ngu',1),(504,'minhtn0',4,'2 phong ngu',0);
/*!40000 ALTER TABLE `phong` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `xe`
--

DROP TABLE IF EXISTS `xe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `xe` (
  `Ten_chu_xe` varchar(40) NOT NULL,
  `Ma_phong` int NOT NULL,
  `Loai_xe` varchar(20) NOT NULL,
  `Bien_so_xe` varchar(20) DEFAULT NULL,
  `Mau_sac` varchar(20) DEFAULT NULL,
  `Thang` date NOT NULL,
  `Da_dong` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`Ten_chu_xe`,`Ma_phong`,`Loai_xe`,`Thang`),
  KEY `Ma_phong` (`Ma_phong`),
  KEY `Loai_xe` (`Loai_xe`),
  CONSTRAINT `xe_ibfk_1` FOREIGN KEY (`Ma_phong`) REFERENCES `phong` (`Ma_phong`),
  CONSTRAINT `xe_ibfk_2` FOREIGN KEY (`Loai_xe`) REFERENCES `don_gia_gui_xe` (`Loai_xe`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `xe`
--

LOCK TABLES `xe` WRITE;
/*!40000 ALTER TABLE `xe` DISABLE KEYS */;
INSERT INTO `xe` VALUES ('Cao Minh Thuy',403,'xe_may','29K1-50926','Den','2022-05-01',1),('Cao Minh Thuy',403,'xe_may','29K1-50926','Den','2022-06-01',1),('Cao Minh Thuy',403,'xe_may','29K1-50926','Den','2022-07-01',1),('Dinh Thi Thu',301,'xe_may','28K2-02468','Trang','2022-05-01',1),('Dinh Thi Thu',301,'xe_may','28K2-02468','Trang','2022-06-01',1),('Dinh Thi Thu',301,'xe_may','28K2-02468','Trang','2022-07-01',1),('Le Thi Van',103,'o_to','35A-99999','Den','2022-07-01',0),('Le Thi Van',103,'xe_may','35K1-48391','Den','2022-05-01',1),('Le Thi Van',103,'xe_may','35K1-48391','Den','2022-06-01',1),('Le Thi Van',103,'xe_may','35K1-48391','Den','2022-07-01',1),('Le Van Binh',301,'o_to','28A-56201','Xanh','2022-05-01',1),('Le Van Binh',301,'o_to','28A-56201','Xanh','2022-06-01',1),('Le Van Binh',301,'o_to','28A-56201','Xanh','2022-07-01',1),('Le Van Tien',102,'xe_may','30B1-89748','Do','2022-07-01',0),('Nguyen Lan Phuong',403,'xe_dap',NULL,'Xanh','2022-07-01',0),('Nguyen Minh Hoang',403,'o_to','29A-45678','Den','2022-05-01',1),('Nguyen Minh Hoang',403,'o_to','29A-45678','Den','2022-06-01',1),('Nguyen Minh Hoang',403,'o_to','29A-45678','Den','2022-07-01',1),('Nguyen Quang Anh',101,'o_to','29A-12345','Den','2022-05-01',1),('Nguyen Quang Anh',101,'o_to','29A-12345','Den','2022-06-01',1),('Nguyen Quang Anh',101,'o_to','29A-12345','Den','2022-07-01',1),('Nguyen Quynh Anh',203,'o_to','29A-05896','Den','2022-05-01',1),('Nguyen Quynh Anh',203,'o_to','29A-05896','Den','2022-06-01',1),('Nguyen Quynh Anh',203,'o_to','29A-05896','Den','2022-07-01',1),('Nguyen Thanh Tung',202,'o_to','37A-24560','Den','2022-07-01',0),('Nguyen Thanh Tung',202,'xe_may','37K1-08568','Den','2022-05-01',1),('Nguyen Thanh Tung',202,'xe_may','37K1-08568','Den','2022-06-01',1),('Nguyen Thanh Tung',202,'xe_may','37K1-08568','Den','2022-07-01',1),('Nguyen Thi Giang',102,'xe_may','30B1-10289','Do','2022-05-01',1),('Nguyen Thi Giang',102,'xe_may','30B1-10289','Do','2022-06-01',1),('Nguyen Thi Giang',102,'xe_may','30B1-10289','Do','2022-07-01',1),('Nguyen Thi Hoa',502,'xe_may','18X2-07854','Den','2022-05-01',1),('Nguyen Thi Hoa',502,'xe_may','18X2-07854','Den','2022-06-01',1),('Nguyen Thi Hoa',502,'xe_may','18X2-07854','Den','2022-07-01',1),('Nguyen Thi Linh',304,'o_to','89A-55555','Den','2022-05-01',1),('Nguyen Thi Linh',304,'o_to','89A-55555','Den','2022-06-01',1),('Nguyen Thi Linh',304,'o_to','89A-55555','Den','2022-07-01',1),('Nguyen Thuy Quynh',401,'xe_may','29B1-22466','Den','2022-05-01',1),('Nguyen Thuy Quynh',401,'xe_may','29B1-22466','Den','2022-06-01',1),('Nguyen Thuy Quynh',401,'xe_may','29B1-22466','Den','2022-07-01',1),('Nguyen Van Hieu',302,'xe_may','29X1-60245','Den','2022-05-01',1),('Nguyen Van Hieu',302,'xe_may','29X1-60245','Den','2022-06-01',1),('Nguyen Van Hieu',302,'xe_may','29X1-60245','Den','2022-07-01',1),('Nguyen Van Long',401,'o_to','29A-66666','Do','2022-05-01',1),('Nguyen Van Long',401,'o_to','29A-66666','Do','2022-06-01',1),('Nguyen Van Long',401,'o_to','29A-66666','Do','2022-07-01',1),('Pham Anh Quang',201,'o_to','18A-20356','Do','2022-05-01',1),('Pham Anh Quang',201,'o_to','18A-20356','Do','2022-06-01',1),('Pham Anh Quang',201,'o_to','18A-20356','Do','2022-07-01',1),('Pham Hai Dang',502,'xe_may','38B1-23015','Den','2022-05-01',1),('Pham Hai Dang',502,'xe_may','38B1-23015','Den','2022-06-01',1),('Pham Hai Dang',502,'xe_may','38B1-23015','Den','2022-07-01',1),('Tran Huu Hung',402,'xe_may','29B2-45623','Trang','2022-05-01',1),('Tran Huu Hung',402,'xe_may','29B2-45623','Trang','2022-06-01',1),('Tran Huu Hung',402,'xe_may','29B2-45623','Trang','2022-07-01',1),('Tran Huu Van Quoc',404,'o_to','29A-03256','Den','2022-05-01',1),('Tran Huu Van Quoc',404,'o_to','29A-03256','Den','2022-06-01',1),('Tran Huu Van Quoc',404,'o_to','29A-03256','Den','2022-07-01',1),('Tran Ngoc Minh',504,'xe_may','29B2-10236','Trang','2022-05-01',1),('Tran Ngoc Minh',504,'xe_may','29B2-10236','Trang','2022-06-01',1),('Tran Ngoc Minh',504,'xe_may','29B2-10236','Trang','2022-07-01',1),('Tran Thi Ha',101,'xe_may','29A-56789','Trang','2022-05-01',1),('Tran Thi Ha',101,'xe_may','29A-56789','Trang','2022-06-01',1),('Tran Thi Ha',101,'xe_may','29A-56789','Trang','2022-07-01',1),('Tran Thi Huyen',201,'xe_dap',NULL,'Xanh','2022-05-01',1),('Tran Thi Huyen',201,'xe_dap',NULL,'Xanh','2022-06-01',1),('Tran Thi Huyen',201,'xe_dap',NULL,'Xanh','2022-07-01',1),('Tran Thi Thuy Van',404,'xe_may','29B1-45621','Do','2022-05-01',1),('Tran Thi Thuy Van',404,'xe_may','29B1-45621','Do','2022-06-01',1),('Tran Thi Thuy Van',404,'xe_may','29B1-45621','Do','2022-07-01',1),('Tran Tien Dung',203,'xe_may','29B1-56321','Do','2022-05-01',1),('Tran Tien Dung',203,'xe_may','29B1-56321','Do','2022-06-01',1),('Tran Tien Dung',203,'xe_may','29B1-56321','Do','2022-07-01',1),('Tran Van Hao',103,'o_to','35A-88888','Xanh','2022-05-01',1),('Tran Van Hao',103,'o_to','35A-88888','Xanh','2022-06-01',1),('Tran Van Hao',103,'o_to','35A-88888','Xanh','2022-07-01',1),('Tran Van Hieu',304,'xe_may','89K1-25610','Den','2022-05-01',1),('Tran Van Hieu',304,'xe_may','89K1-25610','Den','2022-06-01',1),('Tran Van Hieu',304,'xe_may','89K1-25610','Den','2022-07-01',1),('Tran Van Nam',203,'xe_dap',NULL,'Den','2022-07-01',0);
/*!40000 ALTER TABLE `xe` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-08-04 11:31:48
