DROP TABLE IF EXISTS ontology.patientsinfo;
use ontology;
CREATE TABLE `patientsinfo` (
  `PostNumber` int(11) DEFAULT NULL,
  `DiseaseId` text,
  `DiseaseName` text,
  `SymptomId` text,
  `SymptomName` text,
  `TreatmentId` text,
  `TreatmentName` text,
  `DrugId` text,
  `DrugName` text,
  `BodypartId` text,
  `BodypartName` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


/* change path as per location of CSV */
LOAD DATA INFILE 'D:\2ndSem\\SWM Project\\PatientsInfo2019-04-09_03-54-25.csv' 
INTO TABLE ontology.patientsinfo
FIELDS TERMINATED BY ',' 
LINES TERMINATED BY '\n'
IGNORE 1 ROWS;

select * from patientsinfo;

update patientsinfo a, patientsinfo b
set a.Diseaseid=b.diseaseId,a.DiseaseName=b.DiseaseName where a.Postnumber=b.PostNumber
and b.Diseaseid<>"" and a.Diseaseid="";

select * from patientsinfo;
