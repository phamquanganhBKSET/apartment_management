-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: localhost    Database: apartment_manager
-- ------------------------------------------------------
-- Server version	8.0.29

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
INSERT INTO `admin` VALUES ('anhpq0','0386293232','phamquanganh750@gmail.com','2Ac'),('nhungth0','0385874847','tranhongnhung250@gmail.com','1@a'),('quangnd0','0384564492','quangnguyenduc1212@gmail.com','39#');
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
  `So_gio_ve_sinh` float DEFAULT NULL,
  `No_cu` float DEFAULT NULL,
  PRIMARY KEY (`Ma_phong`,`Ma_dich_vu`),
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
INSERT INTO `dich_vu` VALUES (101,1,2000,2200,NULL,0),(101,2,200,225,NULL,0),(101,3,NULL,NULL,1,0),(102,1,1851,1998,NULL,0),(102,2,110,135,NULL,0),(102,3,NULL,NULL,0,0),(103,1,111,456,NULL,0),(103,2,45,59,NULL,50000),(103,3,NULL,NULL,2.5,0),(201,1,6589,6723,NULL,200000),(201,2,200,250,NULL,0),(201,3,NULL,NULL,0,0),(202,1,7895,7999,NULL,0),(202,2,500,530,NULL,0),(202,3,NULL,NULL,0,0),(203,1,18945,19120,NULL,0),(203,2,515,540,NULL,0),(203,3,NULL,NULL,1.5,0),(301,1,200,450,NULL,0),(301,2,30,50,NULL,0),(301,3,NULL,NULL,1.2,0),(302,1,6875,7025,NULL,0),(302,2,402,430,NULL,0),(302,3,NULL,NULL,0,0),(304,1,5000,5400,NULL,0),(304,2,330,350,NULL,0),(304,3,NULL,NULL,0.8,0),(401,1,68795,69030,NULL,0),(401,2,2000,2050,NULL,0),(401,3,NULL,NULL,0,0),(402,1,5800,6054,NULL,75500),(402,2,895,930,NULL,0),(402,3,NULL,NULL,0,0),(403,1,51563,51800,NULL,0),(403,2,1522,1550,NULL,0),(403,3,NULL,NULL,0,0),(404,1,52278,52600,NULL,0),(404,2,4022,4050,NULL,50000),(404,3,NULL,NULL,3,0),(502,1,50006,50500,NULL,0),(502,2,600,645,NULL,0),(502,3,NULL,NULL,3.2,0),(504,1,85678,86000,NULL,0),(504,2,4567,4603,NULL,0),(504,3,NULL,NULL,1.2,0);
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
INSERT INTO `don_gia_dich_vu` VALUES (1,'Dien',1.678,1.734,2.014,2.536,2.834,2.927),(2,'Nuoc',5.973,7.052,8.669,15.929,NULL,NULL),(3,'Ve_sinh',60000,40000,30000,NULL,NULL,NULL);
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
  PRIMARY KEY (`Ten_chu_xe`,`Ma_phong`,`Loai_xe`),
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
INSERT INTO `xe` VALUES ('Cao Minh Thuy',403,'xe_may','29K1-50926','Den'),('Dinh Thi Thu',301,'xe_may','28K2-02468','Trang'),('Le Thi Van',103,'o_to','35A-99999','Den'),('Le Thi Van',103,'xe_may','35K1-48391','Xanh'),('Le Van Binh',301,'o_to','28A-56201','Xanh'),('Nguyen Lan Phuong',403,'xe_dap',NULL,'Xanh'),('Nguyen Minh Hoang',403,'o_to','29A-45678','Den'),('Nguyen Quang Anh',101,'o_to','29A-12345','Den'),('Nguyen Quynh Anh',203,'o_to','29A-05896','Den'),('Nguyen Thanh Tung',202,'xe_may','37A-08568','Den'),('Nguyen Thi Giang',102,'xe_may','30A-10289','Do'),('Nguyen Thi Hoa',502,'xe_may','18X2-07854','Den'),('Nguyen Thi Linh',304,'o_to','89A-55555','Den'),('Nguyen Thuy Quynh',401,'xe_may','29B1-22466','Den'),('Nguyen Van Hieu',302,'xe_may','29X1-60245','Den'),('Nguyen Van Long',401,'o_to','29A-66666','Do'),('Pham Anh Quang',201,'o_to','18A-20356','Do'),('Pham Hai Dang',502,'xe_may','38B1-23015','Den'),('Tran Ha Trang',404,'xe_dap',NULL,'Trang'),('Tran Huu Hung',402,'xe_may','29B2-45623','Trang'),('Tran Huu Van Quoc',404,'o_to','29A-03256','Den'),('Tran Ngoc Minh',504,'xe_may','29A-10236','Trang'),('Tran Thi Ha',101,'xe_may','29A-56789','Trang'),('Tran Thi Huyen',201,'xe_dap',NULL,'Xanh'),('Tran Thi Thuy Van',404,'xe_may','29B1-45621','Do'),('Tran Tien Dung',203,'xe_may','29A-56321','Do'),('Tran Van Hao',103,'o_to','35A-88888','Trang'),('Tran Van Hieu',304,'xe_may','89K1-25610','Den'),('Tran Van Nam',203,'xe_dap',NULL,'Den');
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

-- Dump completed on 2022-08-03 21:37:02
