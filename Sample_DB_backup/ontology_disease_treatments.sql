-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: ontology
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
-- Table structure for table `disease_treatments`
--

DROP TABLE IF EXISTS `disease_treatments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `disease_treatments` (
  `disease_id` varchar(255) DEFAULT NULL,
  `treatment_id` varchar(255) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  KEY `disease_id` (`disease_id`),
  KEY `treatment_id` (`treatment_id`),
  CONSTRAINT `disease_treatments_ibfk_1` FOREIGN KEY (`disease_id`) REFERENCES `diseases` (`disease_id`),
  CONSTRAINT `disease_treatments_ibfk_2` FOREIGN KEY (`treatment_id`) REFERENCES `treatment` (`treatment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `disease_treatments`
--

LOCK TABLES `disease_treatments` WRITE;
/*!40000 ALTER TABLE `disease_treatments` DISABLE KEYS */;
INSERT INTO `disease_treatments` VALUES ('C0085119','C0152060',9),('C0011847','C0920418',9),('C0003864','C0392806',9),('C0149725','C0087111',9),('C0036916','C0152054',9),('C0036916','C0184958',9),('C0004096','C2064693',9),('C0004096','C0949884',9),('C0004096','C0087111',99),('C0029456','C1522704',9),('C0029456','C3275067',9),('C0024138','C2912166',9),('C0497169','C0152054',139),('C0024138','C0339897',9),('C0004096','C0374711',9),('C0004096','C0199451',9),('C0004096','C1522704',39),('C0011847','C1519433',269),('C0004096','C0021485',19),('C0442874','C0949766',9),('C0004096','C0992488',9),('C0004096','C1145938',9),('C0011847','C0035139',19),('C0011847','C0021485',69),('C0011847','C0022671',19),('C0011847','C1522704',219),('C0011847','C0332293',9),('C0011847','C0185003',9),('C0004096','C0279232',9),('C0011847','C0339897',69),('C0011847','C0452415',29),('C0004096','C0017296',9),('C0497169','C3687832',99),('C0004096','C1306232',9),('C0004096','C0021459',9),('C0497169','C0087111',209),('C0011847','C0001701',9),('C0017086','C0087111',9),('C0497169','C0184933',9),('C0011847','C0392148',19),('C3280240','C0024875',9),('C3280240','C3275067',9),('C0497169','C0024875',39),('C0497169','C0005841',39),('C0497169','C0185026',9),('C0004096','C0301571',9),('C0004096','C0152277',19),('C0011847','C0175795',19),('C0011847','C3221986',9),('C0011847','C3541460',9),('C0011847','C0087111',139),('C3280240','C1522704',29),('C0004096','C3826866',9),('C0004096','C1274136',19),('C0004096','C3272275',9),('C0004096','C0455941',9),('C0004096','C0393040',9),('C0011847','C0242390',19),('C0011849','C0087111',9),('C0011847','C0411659',9),('C0004096','C0011209',9),('C0004096','C0563322',9),('C0004096','C0002903',9),('C0041912','C0006155',9),('C0004096','C0683525',9),('C0011847','C0013103',9),('C0011847','C0557033',19),('C0011847','C0580209',9),('C0004096','C0152054',9),('C0004096','C0684199',9),('C0012634','C0187769',9),('C0012634','C0920347',9),('C0012634','C1519433',9),('C0012634','C1959633',9),('C0011847','C1322279',9),('C0011849','C0339897',29),('C0011847','C0741847',9),('C0011847','C0079613',29),('C0011847','C1963578',39),('C0004096','C3854686',9),('C0004096','C0184661',9),('C0004096','C0748725',9),('C0004096','C0150141',9),('C0004096','C0339897',19),('C0011860','C0021485',9),('C0011847','C2973270',9),('C0004096','C0441610',9),('C0004096','C0242297',9),('C0011847','C0152054',29),('C3280240','C0108844',9),('C0004096','C0079613',9),('C0011847','C0018821',9),('C0011847','C0162577',9),('C0011847','C0185023',9),('C0011847','C0522776',9),('C0004096','C3216041',9),('C0497169','C0301571',39),('C0011847','C0949766',9),('C0011847','C0152277',39),('C0497169','C0040461',29),('C0497169','C0184958',9),('C0497169','C1511790',39),('C0497169','C1519433',9),('C0019693','C3687832',9),('C0011847','C0021107',9),('C0011847','C3275122',9),('C0011860','C0152054',19),('C0011860','C0087111',9),('C0497169','C1293134',19),('C0011847','C2825182',9),('C0011847','C0162563',9),('C0011847','C1512802',9),('C0011847','C0185364',9),('C0011847','C0185317',9),('C0011847','C0024875',19),('C0400966','C0259836',9),('C0400966','C0301585',9),('C0400966','C1519433',9),('C0400966','C0521974',9),('C0400966','C0242970',9),('C0497169','C1527374',9),('C0268621','C0152054',9),('C0497169','C0020587',9),('C0011860','C1519433',29),('C0019693','C0028678',9),('C0019693','C0199782',9),('C0442874','C0040808',9),('C0442874','C3227243',9),('C0020456','C0021485',9),('C0020456','C1519433',9),('C0497169','C0886384',39),('C0497169','C0185051',19),('C0497169','C2004459',9),('C0011847','C1262684',9),('C0011847','C0441610',9),('C0011847','C0596199',9),('C0011847','C0040431',9),('C0011860','C1522704',29),('C0019693','C0184967',9),('C0019693','C0152054',19),('C0021400','C0008819',9),('C0497169','C0152060',9),('C0011847','C3267174',29),('C0011847','C0948092',9),('C0011847','C0184967',9),('C0011847','C0184661',9),('C0011847','C1511790',9),('C0011847','C0199782',39),('C0011847','C0152060',9),('C0011847','C1959633',29),('C0011847','C0586328',9),('C0497169','C0016143',19),('C0497169','C0586328',9),('C0497169','C0278286',9),('C0497169','C0079613',29),('C0011849','C0152054',9),('C0497169','C0040423',9),('C0497169','C0042387',9),('C0497169','C0392879',9),('C0362046','C1522704',9),('C0011847','C2937251',9),('C0011847','C0600080',9),('C0011847','C0393040',9),('C0011847','C0011878',9),('C0011847','C1697762',9),('C0011847','C3275067',29),('C0011849','C0886384',9),('C0011847','C3687832',19),('C0011847','C0040808',19),('C0011847','C1293124',9),('C0011847','C0282046',19),('C0011847','C1456587',9),('C0011847','C1135440',9),('C0011849','C1522704',19),('C0011847','C2945673',9),('C0001175','C0152054',19),('C0497169','C0150347',9),('C0011849','C0441636',9),('C0270724','C1519433',9),('C0263537','C0850352',9),('C0263537','C0204774',9),('C0263537','C0441645',9),('C0497169','C0021485',39),('C0497169','C0279848',9),('C0011847','C0278286',9),('C0497169','C0193788',9),('C0011847','C0199176',9),('C0011847','C2960841',9),('C0011847','C0745343',9),('C0011847','C0441516',9),('C0011847','C0279266',9),('C0011847','C0392920',9),('C0011847','C0021083',9),('C0011847','C0085162',9),('C0011847','C0557061',29),('C0497169','C0204774',9),('C0011860','C0188605',9),('C0497169','C0872145',19),('C0011847','C0005961',9),('C0011847','C0986239',9),('C0004096','C3687832',9),('C0011847','C0301571',19),('C0497169','C1959633',19),('C0011847','C0474166',9),('C0011847','C1285306',9),('C0027051','C0079613',9),('C0014518','C0152054',9),('C0497169','C3814046',9),('C0497169','C0184661',9),('C0011860','C0339897',19),('C0011847','C1638311',9),('C0497169','C0524861',9),('C0497169','C0002766',9),('C0011847','C0040461',9),('C0011849','C1522449',9),('C0497169','C0152277',29),('C0018939','C0074125',9),('C0011847','C1527077',9),('C0004096','C0199176',9),('C0004096','C0394663',9),('C0004096','C0394664',9),('C0497169','C0582779',9),('C0497169','C0700589',9),('C0497169','C0392920',9),('C0011847','C0013806',9),('C0011847','C0872279',9),('C0011847','C1456647',9),('C0011847','C1533685',9),('C0362046','C1519433',9),('C0362046','C0020699',9),('C0497169','C3850098',29),('C0497169','C1443861',19),('C3280240','C0021485',9),('C3280240','C1519433',9),('C0497169','C0339897',9),('C0497169','C0042196',9),('C0497169','C0679721',9),('C0497169','C1963724',19),('C0497169','C0683525',9),('C0022658','C1948041',9),('C0022650','C0152060',9),('C0235640','C0008819',9),('C0235640','C0087111',9),('C0022650','C1522704',19),('C0022658','C3687832',29),('C0022658','C1697762',9),('C0011847','C2945704',9),('C0009450','C0011946',9),('C0009450','C1961139',9),('C0011847','C0040145',9),('C0022658','C0040732',19),('C0022658','C0452415',19),('C0022658','C1522704',39),('C0022658','C0011946',29),('C0022658','C0022671',29),('C0022658','C1519433',29),('C0022650','C0452415',9),('C0011849','C1638311',9),('C0011849','C3687832',9),('C0011849','C1519433',9),('C0497169','C0392877',19),('C0497169','C0040808',9),('C0022658','C0301571',9),('C0022658','C0332293',9),('C0018799','C0425403',9),('C0022658','C0700589',9),('C0022658','C0374711',9),('C0022658','C0029151',9),('C0011854','C1533734',9),('C0011847','C0150544',9),('C0011847','C1282573',9),('C0022658','C0204774',9),('C0022658','C0013103',9),('C0022658','C0184661',9),('C0022658','C0565198',9),('C0022658','C0017296',9),('C0022658','C0177049',9),('C0022658','C0741419',9),('C0022658','C0002921',29),('C0022658','C0023878',19),('C0022658','C3275067',9),('C0022650','C0683525',9),('C0022658','C0152277',29),('C0242301','C0078044',9),('C0011175','C1306232',9),('C0011175','C0150141',9),('C0011175','C0152277',9),('C0022658','C0441610',19),('C0022658','C0242297',19),('C0022658','C0087111',39),('C0022658','C0886384',9),('C0019693','C0184958',9),('C0022650','C0087111',9),('C0022658','C0184958',19),('C0022658','C0150141',9),('C0497169','C0007471',19),('C0019693','C1511790',19),('C0019693','C0040732',9),('C0019693','C0920425',9),('C0497169','C0185115',9),('C0497169','C0013103',9),('C0497169','C0332293',9),('C0022658','C0441516',9),('C0011847','C0374711',9),('C0011847','C0034991',9),('C0497169','C1318469',9),('C0022658','C0948350',19),('C0022658','C0279268',9),('C0497169','C1533685',9),('C3714506','C0439631',9),('C3714506','C1522704',9),('C0497169','C1948041',9),('C0019693','C0185051',9),('C0497169','C0919867',9),('C0497169','C3275067',9),('C0022658','C0152054',9),('C0497169','C0011209',9),('C0019693','C0024875',9),('C0019693','C3272275',9),('C0858513','C1511790',9),('C0497169','C0522772',9);
/*!40000 ALTER TABLE `disease_treatments` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-04-21 21:14:58
