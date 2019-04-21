USE `ontology`;
DROP procedure IF EXISTS `ontology_insertion_prc`;

DELIMITER $$
USE `ontology`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `ontology_insertion_prc`()
BEGIN
  DECLARE done INT DEFAULT FALSE ;
  DECLARE d_id,d_name,s_id,s_name TEXT;
  DECLARE cur1 CURSOR FOR SELECT Diseaseid,DiseaseName,SymptomId,SymptomName
  FROM ontology.patientsinfo p1;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

  OPEN cur1;

  read_loop: LOOP
    FETCH cur1 INTO d_id,d_name,s_id,s_name;
    IF done THEN
      LEAVE read_loop;
    END IF;
    SELECT count(1) INTO @dis_cnt FROM ontology.diseases WHERE disease_id=d_id;
    IF @dis_cnt =0 THEN
      INSERT INTO ontology.diseases VALUES (d_id,d_name,"new",1,0,0);
    ELSE
      UPDATE ontology.diseases SET records_count=records_count+1 WHERE disease_id=d_id;
    END IF;
    select length(s_id);
    IF s_id<>"" THEN
		UPDATE ontology.diseases SET has_symptom_count=has_symptom_count+1 WHERE disease_id=d_id;
		SELECT count(1) INTO @sym_cnt FROM ontology.symptoms WHERE symptom_id=s_id;
        IF @sym_cnt=0 THEN
			INSERT INTO ontology.symptoms VALUES (s_id,s_name);
		END IF;
        SELECT count(1) INTO @dis_sym_cnt FROM ontology.disease_symptoms WHERE symptom_id=s_id and disease_id=d_id;
        IF @dis_sym_cnt=0 THEN
			INSERT INTO ontology.disease_symptoms VALUES (d_id,s_id,1);
		ELSE
			UPDATE ontology.disease_symptoms SET count=count+1
            WHERE symptom_id=s_id AND disease_id=d_id;
		END IF;
    END IF;
  END LOOP;

  CLOSE cur1;
END$$

DELIMITER ;

