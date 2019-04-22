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
-- Table structure for table `anatomy`
--

DROP TABLE IF EXISTS `anatomy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `anatomy` (
  `anatomy_id` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`anatomy_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `anatomy`
--

LOCK TABLES `anatomy` WRITE;
/*!40000 ALTER TABLE `anatomy` DISABLE KEYS */;
INSERT INTO `anatomy` VALUES ('C0003461','Anus\r'),('C0003842','Arteries\r'),('C0004895','Beak\r'),('C0005682','Urinary Bladder\r'),('C0005847','Blood Vessel\r'),('C0006104','Brain\r'),('C0006141','Breast\r'),('C0006255','Bronchi\r'),('C0015385','Limb structure\r'),('C0015392','Eye\r'),('C0015420','Eyebrow structure\r'),('C0015422','Eyelash\r'),('C0015426','Eyelid structure\r'),('C0015665','Fat Body\r'),('C0016504','Foot\r'),('C0016505','Bone structure of foot\r'),('C0016536','Forearm\r'),('C0016976','Gallbladder\r'),('C0017420','Genitalia\r'),('C0017562','Gingiva\r'),('C0018494','Hair\r'),('C0018534','Hallux structure\r'),('C0018563','Hand\r'),('C0018787','Heart\r'),('C0019552','Hip structure\r'),('C0019564','Hippocampus (Brain)\r'),('C0019939','Horns\r'),('C0021853','Intestines\r'),('C0022122','Bone structure of ischium\r'),('C0022131','Islets of Langerhans\r'),('C0022359','Jaw\r'),('C0022646','Kidney\r'),('C0022655','Structure of cortex of kidney\r'),('C0022742','Knee\r'),('C0022889','Labyrinth\r'),('C0023759','Lip structure\r'),('C0023884','Liver\r'),('C0024109','Lung\r'),('C0024204','lymph nodes\r'),('C0025148','Medulla Oblongata\r'),('C0026369','Structure of wisdom tooth\r'),('C0026639','Oral mucous membrane structure\r'),('C0027342','Nail plate\r'),('C0027532','Skeletal muscle structure of neck\r'),('C0028109','Nipples\r'),('C0028429','Nose\r'),('C0030274','Pancreas\r'),('C0030647','Patella\r'),('C0030786','Hip Bone\r'),('C0030797','Pelvis\r'),('C0030851','penis\r'),('C0031119','Peripheral Nerves\r'),('C0031354','Pharyngeal structure\r'),('C0033572','Prostate\r'),('C0034896','Rectum\r'),('C0035561','Bone structure of rib\r'),('C0036270','Scalp structure\r'),('C0036277','Bone structure of scapula\r'),('C0036394','Structure of sciatic nerve\r'),('C0037941','Spinal nerve structure\r'),('C0037949','Vertebral column\r'),('C0037993','Spleen\r'),('C0038293','Sternum\r'),('C0038351','Stomach\r'),('C0038553','Sublingual Gland\r'),('C0038925','Surgical Flaps\r'),('C0039508','Tendon structure\r'),('C0039597','Testis\r'),('C0040067','Thumb structure\r'),('C0040132','Thyroid Gland\r'),('C0040357','Toes\r'),('C0040408','Tongue\r'),('C0040421','Palatine Tonsil\r'),('C0040426','Tooth structure\r'),('C0040452','Tooth root structure\r'),('C0040578','Trachea\r'),('C0041638','Umbilicus (Anatomy)\r'),('C0041951','Ureter\r'),('C0041967','Urethra\r'),('C0042027','Urinary tract\r'),('C0042232','Vagina\r'),('C0042449','Veins\r'),('C0043189','Wing\r'),('C0178784','Organ\r'),('C0221997','Nail bed structure\r'),('C0222001','Structure of nail of finger\r'),('C0222007','Structure of nail of toe\r'),('C0222017','Abdominal skin pouch\r'),('C0222032','Spur (body structure)\r'),('C0222045','Integumentary scale\r'),('C0222074','Skin structure of forehead\r'),('C0222149','Skin of chest\r'),('C0222193','Skin of penis\r'),('C0222204','Skin of upper arm\r'),('C0222213','Skin structure of elbow\r'),('C0222224','Skin structure of hand\r'),('C0222762','Thoracic cage structure\r'),('C0224086','Belly of skeletal muscle\r'),('C0224334','Skeletal muscle structure of back\r'),('C0224337','Skeletal muscle structure of thorax\r'),('C0227047','Maxillary left first premolar\r'),('C0227073','Mandibular right first molar\r'),('C0227121','Gum of maxilla\r'),('C0227123','Gum of mandible\r'),('C0227192','Inferior esophageal sphincter structure\r'),('C0227613','Right kidney\r'),('C0227614','Left kidney\r'),('C0227662','Macula densa\r'),('C0227665','Both kidneys\r'),('C0227759','Labium\r'),('C0227936','Body of penis\r'),('C0227952','Foreskin of penis\r'),('C0227997','Structure of right testis\r'),('C0228505','Nodulus cerebelli\r'),('C0229200','Structure of retina of left eye\r'),('C0229315','Ear lobe\r'),('C0229962','Body part\r'),('C0229985','Surgical margins\r'),('C0230102','Lower back structure\r'),('C0230347','Left upper arm structure\r'),('C0230348','Both upper arms\r'),('C0230388','Index finger\r'),('C0230398','Fourth finger\r'),('C0230403','Fifth finger\r'),('C0230425','Structure of right thigh\r'),('C0230443','Structure of left lower leg\r'),('C0230446','Both lower legs\r'),('C0262950','Skeletal bone\r'),('C0376161','Comb animal structure\r'),('C0423866','Lanugo\r'),('C0446987','Cardiac wall structure\r'),('C0447584','Fundus of urinary bladder\r'),('C0448194','Entire distal femur\r'),('C0448953','Mustache (body structure)\r'),('C0458582','Upper lip structure\r'),('C0458827','Airway structure\r'),('C0507115','Superior segmental bronchus\r'),('C0521421','Entire ear\r'),('C0559499','Biceps brachii muscle structure\r'),('C0588054','Cervical lymph node group\r'),('C0694662','Left vastus lateralis\r'),('C0694666','Right deltoid\r'),('C0699819','Gut\r'),('C0746922','Anatomic Node\r'),('C0796494','lobe\r'),('C0816233','Entire skin of chest\r'),('C0817320','Left femur\r'),('C0930960','Left index finger\r'),('C0934502','anatomical layer\r'),('C0935602','Right thumb\r'),('C0935603','Left thumb\r'),('C0935616','Chamber (anatomical)\r'),('C0947182','ribs on right side\r'),('C1140618','Upper Extremity\r'),('C1184743','bony process\r'),('C1185738','Column (anatomic)\r'),('C1186763','Stem of Organ\r'),('C1253959','Branch of\r'),('C1261317','Transplanted kidney\r'),('C1266878','Cutaneous gland\r'),('C1268087','Upper body structure\r'),('C1268126','Kidney part\r'),('C1268131','Vaginal part\r'),('C1268255','Entire right upper arm\r'),('C1268986','Entire trochlear nerve\r'),('C1268990','Entire breast\r'),('C1269001','Entire gallbladder\r'),('C1269078','Entire upper limb\r'),('C1269537','Entire brain\r'),('C1269612','Entire upper arm\r'),('C1278836','Entire spinal cord\r'),('C1278896','Entire nose\r'),('C1278908','Entire lung\r'),('C1278911','Entire lip\r'),('C1278914','Entire palate\r'),('C1278916','Entire parotid gland\r'),('C1278919','Entire esophagus\r'),('C1278920','Entire stomach\r'),('C1278926','Entire rectum\r'),('C1278927','Entire anus\r'),('C1278929','Entire liver\r'),('C1278978','Entire kidney\r'),('C1278985','Entire vagina\r'),('C1278997','Entire scalp\r'),('C1279050','Entire inguinal lymph node\r'),('C1279153','Entire cheek\r'),('C1279605','Entire right lower leg\r'),('C1279606','Entire left lower leg\r'),('C1279998','Entire right foot\r'),('C1279999','Entire left foot\r'),('C1280202','Entire eye\r'),('C1280388','Entire upper lip\r'),('C1280389','Entire lower lip\r'),('C1280538','Entire tendon\r'),('C1280541','Entire nerve\r'),('C1280698','Entire throat (surface region of neck)\r'),('C1280739','Entire penis\r'),('C1280740','Entire scrotum\r'),('C1280744','Entire umbilicus\r'),('C1281573','Entire bladder\r'),('C1281583','Entire hand\r'),('C1281584','Entire finger\r'),('C1281586','Entire thigh\r'),('C1281587','Entire foot\r'),('C1281589','Entire toe\r'),('C1281590','Entire head\r'),('C1281591','Entire face\r'),('C1281592','Entire neck\r'),('C1281594','Entire abdomen\r'),('C1283788','Entire spinal nerve\r'),('C1285092','Gland\r'),('C1288948','Entire right hand\r'),('C1288949','Entire left hand\r'),('C1305417','Entire elbow region\r'),('C1305418','Entire calf of leg (body structure)\r'),('C1550235','Cord - Body Parts\r'),('C1550261','Glans\r'),('C1552158','Supernumerary mandibular right central primary incisor\r'),('C1760774','Structure of muscle of forearm\r'),('C1995013','Set of muscles\r'),('C2339084','Fatty layer\r'),('C2987482','Animal Coat\r'),('C3887642','Hippocampus Proper\r'),('C4048756','Right arm\r');
/*!40000 ALTER TABLE `anatomy` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-04-21 21:14:57
