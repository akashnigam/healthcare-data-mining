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
-- Table structure for table `symptoms`
--

DROP TABLE IF EXISTS `symptoms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `symptoms` (
  `symptom_id` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`symptom_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `symptoms`
--

LOCK TABLES `symptoms` WRITE;
/*!40000 ALTER TABLE `symptoms` DISABLE KEYS */;
INSERT INTO `symptoms` VALUES ('C0000729','Abdominal Cramps'),('C0000737','Abdominal Pain'),('C0003862','Arthralgia'),('C0004604','Back Pain'),('C0005904','Body Temperature Changes'),('C0006625','Cachexia'),('C0007859','Neck Pain'),('C0008031','Chest Pain'),('C0009806','Constipation'),('C0010200','Coughing'),('C0010201','Chronic cough'),('C0011124','Decreased Libido'),('C0011991','Diarrhea'),('C0012833','Dizziness'),('C0013395','Dyspepsia'),('C0013404','Dyspnea'),('C0013456','Earache'),('C0013911','Emaciation'),('C0014724','Eructation'),('C0015230','Exanthema'),('C0015672','Fatigue'),('C0016199','Flank Pain'),('C0016204','Flatulence'),('C0016382','Flushing'),('C0016512','Foot pain'),('C0018520','Halitosis'),('C0018681','Headache'),('C0018834','Heartburn'),('C0019825','Hoarseness'),('C0020175','Hunger'),('C0023218','Leg cramps'),('C0023222','Pain in lower limb'),('C0024031','Low Back Pain'),('C0026821','Muscle Cramp'),('C0027424','Nasal congestion (finding)'),('C0027497','Nausea'),('C0027498','Nausea and vomiting'),('C0027769','Nervousness'),('C0028081','Night sweats'),('C0028084','Nightmares'),('C0028643','Numbness'),('C0030193','Pain'),('C0033774','Pruritus'),('C0033775','Pruritus Ani'),('C0036396','Sciatica'),('C0036572','Seizures'),('C0037088','Signs and Symptoms'),('C0037090','Signs and Symptoms| Respiratory'),('C0037384','Snoring'),('C0037763','Spasm'),('C0039070','Syncope'),('C0040822','Tremor'),('C0042571','Vertigo'),('C0042963','Vomiting'),('C0043144','Wheezing'),('C0085593','Chills'),('C0085602','Polydipsia'),('C0085624','Burning sensation'),('C0085631','Agitation'),('C0149651','Clubbing'),('C0151315','Neck stiffness'),('C0151830','Pain in urethra'),('C0151908','Dry skin'),('C0152165','Persistent vomiting'),('C0178310','signs and symptoms of ill-defined conditions'),('C0184567','Acute onset pain'),('C0220870','Lightheadedness'),('C0221100','Hangover from any Alcohol or Other Drugs substance'),('C0221170','Muscular stiffness'),('C0221423','Illness (finding)'),('C0221512','Stomach ache'),('C0221758','Stiff back'),('C0231218','Malaise'),('C0231528','Myalgia'),('C0231617','Catch - Finding of sensory dimension of pain'),('C0231655','Scapulalgia'),('C0231749','Knee pain'),('C0231772','Burning feet'),('C0231811','Suffocated'),('C0232292','Chest tightness'),('C0232431','Cold sweat'),('C0232471','Thirsty'),('C0232854','Slowing of urinary stream'),('C0233401','Psychiatric symptom'),('C0233763','Hallucinations| Visual'),('C0234230','Pain| Burning'),('C0234233','Sore to touch'),('C0234238','Ache'),('C0234369','Trembling'),('C0234657','Feeling of heat in eye'),('C0235108','Feeling tense'),('C0235129','Feeling strange'),('C0235299','Right upper quadrant pain'),('C0235309','Upset stomach'),('C0235710','Chest discomfort'),('C0236000','Jaw pain'),('C0236083','Burning feeling vagina'),('C0238656','Ankle pain'),('C0239589','Pain in finger'),('C0239649','Numbness of foot'),('C0239783','Inguinal pain'),('C0239832','Numbness of hand'),('C0240941','Pruritus of scalp'),('C0241374','Thigh pain'),('C0241727','walking pain'),('C0242429','Sore Throat'),('C0264345','Smokers\' cough'),('C0278141','Excruciating pain'),('C0278144','Dull pain'),('C0278145','Stabbing pain'),('C0278146','Shooting pain'),('C0281856','Generalized aches and pains'),('C0302539','spells (neurological symptom)'),('C0312414','Menstrual spotting'),('C0312422','Blackout - symptom'),('C0314719','Dryness of eye'),('C0344232','Blurred vision'),('C0344375','Stomach cramps (finding)'),('C0392171','Influenza-like symptoms'),('C0392674','Exhaustion'),('C0406671','Burning scrotum'),('C0423636','Cardiac pain'),('C0423640','Right Flank Pain'),('C0423736','Scalding pain on urination'),('C0425449','Gasping for breath'),('C0426623','Excessive upper gastrointestinal gas'),('C0427008','Stiffness'),('C0438716','Chest pressure'),('C0455270','Sharp pain'),('C0474585','Feces color: tarry'),('C0497406','Overweight'),('C0497481','Pain in penis'),('C0518214','Perceived quality of life'),('C0522067','Liver pain'),('C0542476','Forgetful'),('C0549299','uti symptoms'),('C0549386','Sensation of warmth'),('C0557875','Tired'),('C0558489','Renal pain'),('C0564822','Pain in left leg'),('C0574002','Edema of foot (finding)'),('C0574068','Rib pain'),('C0577573','Mass of body region'),('C0578050','Pain of lymph node'),('C0578454','Neck swelling'),('C0578687','Sore eye'),('C0581394','Swollen legs'),('C0596601','gastrointestinal gas'),('C0600142','Hot flushes'),('C0683369','Clouded consciousness'),('C0700590','Increased sweating'),('C0741585','BODY ACHE'),('C0742339','chest heaviness'),('C0744318','Acid reflux SYMPTOMS'),('C0748706','side pain'),('C0751295','Memory Loss'),('C0751372','Nerve Pain'),('C0795691','HEART PROBLEM'),('C0812426','Kidney problem'),('C0847550','red nose'),('C0848168','out (of) breath'),('C0848283','rundown'),('C0848290','sick to stomach'),('C0848720','prostate problem'),('C0848724','circulation problem'),('C0849640','skin damage'),('C0849791','sinus drainage'),('C0849796','ear congestion'),('C0849850','blotch'),('C0849959','feeling dizzy'),('C0849963','Feeling nervous'),('C0850149','Dry cough'),('C0850630','tingling in fingers'),('C0854148','cells in urine'),('C0857027','Feeling Sick'),('C0857057','blotchy'),('C0857172','persistent dry cough'),('C0857213','cramp in lower abdomen'),('C0857245','Redness of face'),('C0858600','taste sweet'),('C0858708','itch burning'),('C0863105','sore back'),('C0917801','Sleeplessness'),('C1096446','slight fever'),('C1096596','foot discomfort'),('C1135120','Breakthrough Pain'),('C1260880','Rhinorrhea'),('C1291077','Abdominal bloating'),('C1321587','Breathing abnormally deep'),('C1442877','Sore skin'),('C1446787','Cramping sensation quality'),('C1517205','Flare'),('C1579838','Sore mouth'),('C1579931','Depressed - symptom'),('C1659989','Respiratory problems'),('C1704276','Spasmodic movement'),('C2029681','Symptom of head'),('C2129214','Loose stool'),('C2220244','Symptom of neck'),('C2242996','Has tingling sensation'),('C2364135','Actual Discomfort'),('C2896456','Pain in left thigh'),('C2957106','headache severe'),('C3257803','Watery eyes'),('C3640014','Unbearable Pain'),('C3714552','Weakness');
/*!40000 ALTER TABLE `symptoms` ENABLE KEYS */;
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
