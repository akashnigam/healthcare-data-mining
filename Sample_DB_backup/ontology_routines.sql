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
-- Dumping routines for database 'ontology'
--
/*!50003 DROP PROCEDURE IF EXISTS `ontology_insertion_prc` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `ontology_insertion_prc`()
BEGIN
  DECLARE done INT DEFAULT FALSE ;
  DECLARE my_rec_cnt INT default 0;
  DECLARE d_id,d_name,s_id,s_name,t_id,t_name,dr_id,dr_name,b_id,b_name TEXT;
  DECLARE cur1 CURSOR FOR SELECT Diseaseid,DiseaseName,SymptomId,SymptomName,TreatmentId,TreatmentName,DrugId,DrugName,BodypartId,BodypartName
  FROM ontology.patientsinfo p1;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

  OPEN cur1;

  read_loop: LOOP
    FETCH cur1 INTO d_id,d_name,s_id,s_name,t_id,t_name,dr_id,dr_name,b_id,b_name;
    IF done THEN
      LEAVE read_loop;
    END IF;
    SET my_rec_cnt = my_rec_cnt+1;
    SELECT count(1) INTO @dis_cnt FROM ontology.diseases WHERE disease_id=d_id;
    IF @dis_cnt =0 THEN
      INSERT INTO ontology.diseases VALUES (d_id,d_name,"new",1,0,0);
    ELSE
      UPDATE ontology.diseases SET records_count=records_count+1 WHERE disease_id=d_id;
    END IF;
   /* select length(s_id); */
    IF s_id<>"" THEN
		UPDATE ontology.diseases SET has_symptom_count=has_symptom_count+1 WHERE disease_id=d_id;
		SELECT count(1) INTO @sym_cnt FROM ontology.symptoms WHERE symptom_id=s_id;
        IF @sym_cnt=0 THEN
			INSERT INTO ontology.symptoms VALUES (s_id,s_name);
		END IF;
        SELECT count(1) INTO @dis_sym_cnt FROM ontology.disease_symptoms WHERE symptom_id=s_id and disease_id=d_id;
        IF @dis_sym_cnt=0 THEN
			INSERT INTO ontology.disease_symptoms VALUES (d_id,s_id,0);
		ELSE
			UPDATE ontology.disease_symptoms SET count=count+1
            WHERE symptom_id=s_id AND disease_id=d_id;
		END IF;
    END IF;
	
    IF t_id<>"" THEN
		UPDATE ontology.diseases SET has_treatment_count=has_treatment_count+1 WHERE disease_id=d_id;
		SELECT count(1) INTO @t_cnt FROM ontology.treatment WHERE treatment_id=t_id;
        IF @t_cnt=0 THEN
			INSERT INTO ontology.treatment VALUES (t_id,t_name,"Treatment or Exercise");
		END IF;
        SELECT count(1) INTO @dis_tr_cnt FROM ontology.disease_treatments WHERE disease_id=d_id and treatment_id=t_id;
        IF @dis_tr_cnt=0 THEN
			INSERT INTO ontology.disease_treatments VALUES (d_id,t_id,0);
		ELSE
			UPDATE ontology.disease_treatments SET count=count+1
            WHERE treatment_id=t_id AND disease_id=d_id;
		END IF;
    END IF;
	
	IF dr_id<>"" THEN
		UPDATE ontology.diseases SET has_treatment_count=has_treatment_count+1 WHERE disease_id=d_id;
		SELECT count(1) INTO @td_cnt FROM ontology.treatment WHERE treatment_id=dr_id;
        IF @td_cnt=0 THEN
			INSERT INTO ontology.treatment VALUES (dr_id,dr_name,"Drug");
		END IF;
        SELECT count(1) INTO @dis_tr1_cnt FROM ontology.disease_treatments WHERE disease_id=d_id and treatment_id=dr_id;
        IF @dis_tr1_cnt=0 THEN
			INSERT INTO ontology.disease_treatments VALUES (d_id,dr_id,0);
		ELSE
			UPDATE ontology.disease_treatments SET count=count+1
            WHERE treatment_id=dr_id AND disease_id=d_id;
		END IF;
    END IF;
	
	IF b_id<>"" THEN
		SELECT count(1) INTO @b_cnt FROM ontology.anatomy WHERE anatomy_id=b_id;
        IF @b_cnt=0 THEN
			INSERT INTO ontology.anatomy VALUES (b_id,b_name);
		END IF;
        SELECT count(1) INTO @dis_a_cnt FROM ontology.disease_anatomy WHERE disease_id=d_id and anatomy_id=b_id;
        IF @dis_a_cnt=0 THEN
			INSERT INTO ontology.disease_anatomy VALUES (d_id,b_id);
		END IF;
    END IF;
	
  END LOOP;
  
SELECT concat('Successfully inserted ', my_rec_cnt, ' records in ontology') as Message;
  CLOSE cur1;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-04-21 21:14:59
