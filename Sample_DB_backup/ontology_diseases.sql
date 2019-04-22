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
-- Table structure for table `diseases`
--

DROP TABLE IF EXISTS `diseases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `diseases` (
  `disease_id` varchar(255) NOT NULL,
  `disease_name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `records_count` int(11) DEFAULT NULL,
  `has_symptom_count` int(11) DEFAULT NULL,
  `has_treatment_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`disease_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `diseases`
--

LOCK TABLES `diseases` WRITE;
/*!40000 ALTER TABLE `diseases` DISABLE KEYS */;
INSERT INTO `diseases` VALUES ('C0000833','Abscess','new',20,10,0),('C0001175','Acquired Immunodeficiency Syndrome','new',110,30,20),('C0003864','Arthritis','new',50,0,10),('C0004096','Asthma','new',3390,1260,550),('C0004099','Asthma| Exercise-Induced','new',10,0,0),('C0004135','Ataxia Telangiectasia','new',40,10,0),('C0005683','Urinary Bladder Calculi (disorder)','new',10,0,0),('C0009421','Comatose','new',20,0,0),('C0009443','Common Cold','new',70,20,0),('C0009450','Communicable Diseases','new',40,0,20),('C0010709','Cyst','new',30,10,0),('C0011175','Dehydration','new',100,50,30),('C0011847','Diabetes','new',9290,2180,2010),('C0011849','Diabetes Mellitus','new',700,250,130),('C0011854','Diabetes Mellitus| Insulin-Dependent','new',30,0,10),('C0011860','Diabetes Mellitus| Non-Insulin-Dependent','new',430,40,130),('C0012634','Disease','new',170,40,40),('C0014518','Toxic Epidermal Necrolysis','new',80,30,10),('C0017086','Gangrene','new',30,0,10),('C0017665','Membranous glomerulonephritis','new',20,10,0),('C0018621','Hay fever','new',20,10,0),('C0018799','Heart Diseases','new',20,0,10),('C0018939','Hematological Disease','new',50,20,10),('C0019340','herpes','new',40,10,0),('C0019360','Herpes zoster disease','new',60,10,0),('C0019693','HIV Infections','new',1060,160,140),('C0020456','Hyperglycemia','new',70,10,20),('C0020538','Hypertensive disease','new',10,0,0),('C0021400','Influenza','new',50,0,10),('C0022650','Kidney Calculi','new',230,60,60),('C0022658','Kidney Diseases','new',4220,1520,600),('C0024138','Lupus Erythematosus| Discoid','new',170,50,20),('C0027051','Myocardial Infarction','new',60,30,10),('C0029456','Osteoporosis','new',50,0,20),('C0030360','Papillon-Lefevre Disease','new',20,10,0),('C0031350','Pharyngitis','new',140,60,0),('C0032827','Potassium Deficiency','new',10,0,0),('C0033771','Prurigo','new',50,10,0),('C0036916','Sexually Transmitted Diseases','new',350,150,20),('C0037036','Sialorrhea','new',10,0,0),('C0040253','Tinea of perianal region','new',40,10,0),('C0041912','Upper Respiratory Infections','new',100,20,10),('C0042029','Urinary tract infection','new',40,20,0),('C0042109','Urticaria','new',70,20,0),('C0043352','Xerostomia','new',40,30,0),('C0085119','Foot Ulcer','new',30,0,10),('C0149725','Lower respiratory tract infection','new',70,40,10),('C0149756','Fasciitis| Plantar','new',20,10,0),('C0235640','Posthitis','new',50,10,20),('C0237974','AIDS i','new',110,50,0),('C0241266','Subcutaneous Abscess','new',30,10,0),('C0242301','furuncle','new',20,0,10),('C0263068','Furuncle of groin','new',80,30,0),('C0263537','Onychogryposis','new',80,0,30),('C0263940','Curb','new',10,0,0),('C0268621','Hepatic methionine adenosyltransferase deficiency','new',110,0,10),('C0270724','Infantile Neuroaxonal Dystrophy','new',80,20,10),('C0272285','Heparin-induced thrombocytopenia','new',20,0,0),('C0272302','Gray Platelet Syndrome','new',10,0,0),('C0362046','Prediabetes syndrome','new',80,0,30),('C0400966','Non-alcoholic Fatty Liver Disease','new',70,0,50),('C0442874','Neuropathy','new',140,60,30),('C0497169','hiv-infection/aids','new',12740,4570,1380),('C0702135','Primary atypical pneumonia','new',80,70,0),('C0796085','Nance-Horan syndrome','new',60,40,0),('C0858513','hiv-3','new',30,0,10),('C1263960','Diabetes with coma (disorder)','new',20,10,0),('C1384594','Nephroptosis','new',60,30,0),('C1535939','Pneumocystis jiroveci pneumonia','new',10,0,0),('C1704330','Dental Diseases','new',30,10,0),('C3280240','MICROCEPHALY| EPILEPSY| AND DIABETES SYNDROME','new',370,80,80),('C3714506','Meckel syndrome type 1','new',70,10,20),('C3887499','Renal cyst','new',20,10,0);
/*!40000 ALTER TABLE `diseases` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-04-21 21:14:55
