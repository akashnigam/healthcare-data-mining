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
-- Table structure for table `treatment`
--

DROP TABLE IF EXISTS `treatment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `treatment` (
  `treatment_id` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`treatment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `treatment`
--

LOCK TABLES `treatment` WRITE;
/*!40000 ALTER TABLE `treatment` DISABLE KEYS */;
INSERT INTO `treatment` VALUES ('C0001701','Exercise| Aerobic','Treatment or Exercise'),('C0002766','Pain management','Treatment or Exercise'),('C0002903','Anesthesia procedures','Treatment or Exercise'),('C0002921','Local anesthesia','Treatment or Exercise'),('C0005841','Blood Transfusion','Treatment or Exercise'),('C0005961','Bone Marrow Transplantation','Treatment or Exercise'),('C0006155','Breathing Exercises','Treatment or Exercise'),('C0007471','Cauterization - action','Treatment or Exercise'),('C0008819','Male Circumcision','Treatment or Exercise'),('C0011209','Obstetric Delivery','Treatment or Exercise'),('C0011878','Diabetic Diet','Treatment or Exercise'),('C0011946','Dialysis procedure','Treatment or Exercise'),('C0013103','Drainage procedure','Treatment or Exercise'),('C0013806','Electroconvulsive Therapy','Treatment or Exercise'),('C0016143','First Aid','Treatment or Exercise'),('C0017296','gene therapy','Treatment or Exercise'),('C0018821','Cardiac Surgery procedures','Treatment or Exercise'),('C0020587','Hypnotherapy','Treatment or Exercise'),('C0020699','Hysterectomy','Treatment or Exercise'),('C0021083','Immunotherapy','Treatment or Exercise'),('C0021107','Implantation procedure','Treatment or Exercise'),('C0021459','Inhalation Therapy','Treatment or Exercise'),('C0021485','Injection of therapeutic agent','Treatment or Exercise'),('C0022671','Kidney Transplantation','Treatment or Exercise'),('C0023878','Lithotripsy','Treatment or Exercise'),('C0024875','Massage','Treatment or Exercise'),('C0028678','nursing therapy','Treatment or Exercise'),('C0029151','Oral contraception','Treatment or Exercise'),('C0034991','Rehabilitation therapy','Treatment or Exercise'),('C0035139','Surgical Replantation','Treatment or Exercise'),('C0040145','Thyroidectomy','Treatment or Exercise'),('C0040423','Tonsillectomy','Treatment or Exercise'),('C0040431','Tooth Bleaching','Treatment or Exercise'),('C0040461','Toothbrushing','Treatment or Exercise'),('C0040732','Transplantation','Treatment or Exercise'),('C0040808','Treatment Protocols','Treatment or Exercise'),('C0042196','Vaccination','Treatment or Exercise'),('C0042387','Vasectomy','Treatment or Exercise'),('C0074125','bleomycin/doxorubicin/lomustine/streptozocin protocol','Treatment or Exercise'),('C0078044','VAPAC protocol','Treatment or Exercise'),('C0079613','Adoptive Immunotherapy','Treatment or Exercise'),('C0085162','Pancreaticoduodenectomy','Treatment or Exercise'),('C0087111','Therapeutic procedure','Treatment or Exercise'),('C0108844','CDE protocol-cyclophosphamide/doxorubicin/etoposide','Treatment or Exercise'),('C0150141','Bathing','Treatment or Exercise'),('C0150347','Simple massage','Treatment or Exercise'),('C0150544','Diabetic care','Treatment or Exercise'),('C0152054','Therapeutic tactile stimulation','Treatment or Exercise'),('C0152060','Transection (procedure)','Treatment or Exercise'),('C0152277','Incision and drainage','Treatment or Exercise'),('C0162563','Cardiac ablation','Treatment or Exercise'),('C0162577','Angioplasty','Treatment or Exercise'),('C0175795','oral medication','Treatment or Exercise'),('C0177049','Insertion of indwelling urinary catheter','Treatment or Exercise'),('C0184661','Interventional procedure','Treatment or Exercise'),('C0184933','Scraping','Treatment or Exercise'),('C0184958','Toilet procedure','Treatment or Exercise'),('C0184967','Insertion of pack (procedure)','Treatment or Exercise'),('C0185003','Reparative closure','Treatment or Exercise'),('C0185023','Fixation - action','Treatment or Exercise'),('C0185026','Plication','Treatment or Exercise'),('C0185051','Removal by grinding','Treatment or Exercise'),('C0185115','Extraction','Treatment or Exercise'),('C0185317','Arthroplasty| Replacement','Treatment or Exercise'),('C0185364','Osteoplasty','Treatment or Exercise'),('C0187769','Operative procedure on knee','Treatment or Exercise'),('C0188605','Amputation of the foot (procedure)','Treatment or Exercise'),('C0193788','Total thyroidectomy (procedure)','Treatment or Exercise'),('C0199176','Prophylactic treatment','Treatment or Exercise'),('C0199451','Continuous Positive Airway Pressure','Treatment or Exercise'),('C0199782','Administration of insulin','Treatment or Exercise'),('C0204774','Soaking Procedure','Treatment or Exercise'),('C0242297','Dietary Supplementation','Treatment or Exercise'),('C0242390','Home Remedies','Treatment or Exercise'),('C0242970','Fat-Restricted Diet','Treatment or Exercise'),('C0259836','Diet| Carbohydrate-Restricted','Treatment or Exercise'),('C0278286','Dressing of skin or wound','Treatment or Exercise'),('C0279232','fluorouracil/melphalan/tamoxifen','Treatment or Exercise'),('C0279266','carmustine/methotrexate/procarbazine protocol','Treatment or Exercise'),('C0279268','doxorubicin/semustine/streptozocin protocol','Treatment or Exercise'),('C0279848','cisplatin/etoposide/mitoxantrone/tamoxifen protocol','Treatment or Exercise'),('C0282046','Surgery| Day','Treatment or Exercise'),('C0301571','Liquid diet','Treatment or Exercise'),('C0301585','Fat diet','Treatment or Exercise'),('C0332293','Treated with','Treatment or Exercise'),('C0339897','Transjugular intrahepatic portosystemic shunt procedure','Treatment or Exercise'),('C0374711','Surgical repair','Treatment or Exercise'),('C0392148','Providing presence (regime/therapy)','Treatment or Exercise'),('C0392806','Hip Replacement Arthroplasty (procedure)','Treatment or Exercise'),('C0392877','infusion of drug','Treatment or Exercise'),('C0392879','infusion of cortisone','Treatment or Exercise'),('C0392920','Chemotherapy Regimen','Treatment or Exercise'),('C0393040','cisplatin/ifosfamide/mesna/mitomycin protocol','Treatment or Exercise'),('C0394663','Cupping','Treatment or Exercise'),('C0394664','Acupuncture procedure','Treatment or Exercise'),('C0411659','Opening of skin','Treatment or Exercise'),('C0425403','Increased protein diet','Treatment or Exercise'),('C0439631','Primary operation','Treatment or Exercise'),('C0441516','Demand (clinical)','Treatment or Exercise'),('C0441610','Reduction - action','Treatment or Exercise'),('C0441636','Surgical shortening - action','Treatment or Exercise'),('C0441645','Trimming - action','Treatment or Exercise'),('C0452415','Diet good','Treatment or Exercise'),('C0455941','Vibration - treatment','Treatment or Exercise'),('C0474166','Medical counseling','Treatment or Exercise'),('C0521974','Diet| High-Fat','Treatment or Exercise'),('C0522772','Shaving - surgical procedure','Treatment or Exercise'),('C0522776','Placement of stent','Treatment or Exercise'),('C0524861','Oral Surgical Procedures','Treatment or Exercise'),('C0557033','Reminding','Treatment or Exercise'),('C0557061','Discussion (procedure)','Treatment or Exercise'),('C0563322','Intravenous steroid injection','Treatment or Exercise'),('C0565198','Drainage of bladder','Treatment or Exercise'),('C0580209','Surgical abrasion','Treatment or Exercise'),('C0582779','Exposure technique','Treatment or Exercise'),('C0586328','Subcutaneous injection of insulin','Treatment or Exercise'),('C0596199','blood vessel restoration','Treatment or Exercise'),('C0600080','Stretching exercises','Treatment or Exercise'),('C0679721','prevention service','Treatment or Exercise'),('C0683525','treatment options','Treatment or Exercise'),('C0684199','Homeopathy','Treatment or Exercise'),('C0700589','Contraceptive methods','Treatment or Exercise'),('C0741419','back surgery','Treatment or Exercise'),('C0741847','Bypass','Treatment or Exercise'),('C0745343','insulin treatment','Treatment or Exercise'),('C0748725','sinus surgery','Treatment or Exercise'),('C0850352','pedicure','Treatment or Exercise'),('C0872145','Directly Observed Therapy','Treatment or Exercise'),('C0872279','Resistance Training','Treatment or Exercise'),('C0886384','5 minutes Office visit','Treatment or Exercise'),('C0919867','dental cleaning','Treatment or Exercise'),('C0920347','Procedure on spinal cord (procedure)','Treatment or Exercise'),('C0920418','cancer radiation therapy','Treatment or Exercise'),('C0920425','cancer therapy','Treatment or Exercise'),('C0948092','diabetes mellitus management','Treatment or Exercise'),('C0948350','renal and pancreas transplant','Treatment or Exercise'),('C0949766','Physical therapy','Treatment or Exercise'),('C0949884','Sweat Lodge','Treatment or Exercise'),('C0986239','glimepiride 1 MG','Drug'),('C0992488','lamotrigine 150 MG','Drug'),('C1135440','Procedures involving the use of pharmaceuticals','Treatment or Exercise'),('C1145938','quetiapine 200 MG','Drug'),('C1262684','Allergy treatment','Treatment or Exercise'),('C1274136','Symptom control (regime/therapy)','Treatment or Exercise'),('C1282573','Evacuation procedure','Treatment or Exercise'),('C1285306','Immune system destructive procedure','Treatment or Exercise'),('C1293124','Staple implantation procedure','Treatment or Exercise'),('C1293134','Enlargement procedure','Treatment or Exercise'),('C1306232','Severing','Treatment or Exercise'),('C1318469','Lubrication procedure','Treatment or Exercise'),('C1322279','Dilate procedure','Treatment or Exercise'),('C1443861','Post-Exposure Prophylaxis','Treatment or Exercise'),('C1456587','Bariatric Surgery','Treatment or Exercise'),('C1456647','Childhood Immunization','Treatment or Exercise'),('C1511790','Detection','Treatment or Exercise'),('C1512802','Insulin| 70/30','Drug'),('C1519433','Special Diet Therapy','Treatment or Exercise'),('C1522449','Therapeutic radiology procedure','Treatment or Exercise'),('C1522704','Exercise Pain Management','Treatment or Exercise'),('C1527077','Peripheral Electronic Nerve Stimulation','Treatment or Exercise'),('C1527374','Group Therapy','Treatment or Exercise'),('C1533685','Injection procedure','Treatment or Exercise'),('C1533734','Administration procedure','Treatment or Exercise'),('C1638311','Blood glucose management','Treatment or Exercise'),('C1697762','Booster Immunization - ActSubstanceAdministrationCode','Treatment or Exercise'),('C1948041','Surgical and medical procedures','Treatment or Exercise'),('C1959633','Spatial guidance for medical procedure','Treatment or Exercise'),('C1961139','Grafting procedure','Treatment or Exercise'),('C1963578','Release (procedure)','Treatment or Exercise'),('C1963724','Antiretroviral therapy','Treatment or Exercise'),('C2004459','Abdominoperineal resection','Treatment or Exercise'),('C2064693','Humid air','Treatment or Exercise'),('C2825182','Atrial Ablation','Treatment or Exercise'),('C2912166','Hydroxychloroquine Sulfate 200 MG','Drug'),('C2937251','sliding scale','Treatment or Exercise'),('C2945673','Clinical adjustment','Treatment or Exercise'),('C2945704','Conventional Treatment','Treatment or Exercise'),('C2960841','Lifestyle change therapy','Treatment or Exercise'),('C2973270','Health Checkup','Treatment or Exercise'),('C3216041','Prednisone Pill','Drug'),('C3221986','Janumet Pill','Drug'),('C3227243','Edurant Pill','Drug'),('C3267174','Blood Glucose control','Treatment or Exercise'),('C3272275','Urgent Procedure','Treatment or Exercise'),('C3275067','Cardiac Lead Procedure','Treatment or Exercise'),('C3275122','Pacemaker Procedure','Treatment or Exercise'),('C3541460','Potassium supplement therapy','Treatment or Exercise'),('C3687832','DRUGS','Treatment or Exercise'),('C3814046','PAD Regimen','Treatment or Exercise'),('C3826866','Bortezomib/Cyclophosphamide/Dexamethasone Regimen','Treatment or Exercise'),('C3850098','Pre-Exposure Prophylaxis','Treatment or Exercise'),('C3854686','Thermoplasty of bronchus','Treatment or Exercise');
/*!40000 ALTER TABLE `treatment` ENABLE KEYS */;
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