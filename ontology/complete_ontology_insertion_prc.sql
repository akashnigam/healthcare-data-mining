CREATE DEFINER=`root`@`localhost` PROCEDURE `ontology_insertion_prc`()
BEGIN
  DECLARE done INT DEFAULT FALSE ;
  DECLARE d_id,d_name,s_id,s_name TEXT;
  DECLARE cur1 CURSOR FOR SELECT Diseaseid,DiseaseName,SymptomId,SymptomName,TreatmentId,TreatmentName,DrugId,DrugName,BodypartId,BodypartName
  FROM ontology.patientsinfo p1;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

  OPEN cur1;

  read_loop: LOOP
    FETCH cur1 INTO d_id,d_name,s_id,s_name,t_id,t_name,dr_id,dr_name,b_id,b_name;
    IF done THEN
      LEAVE read_loop;
    END IF;
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
		SELECT count(1) INTO @td_cnt FROM ontology.treatment WHERE treatment_id=t_id;
        IF @td_cnt=0 THEN
			INSERT INTO ontology.treatment VALUES (t_id,t_name,"Drug");
		END IF;
        SELECT count(1) INTO @dis_tr1_cnt FROM ontology.disease_treatments WHERE disease_id=d_id and treatment_id=t_id;
        IF @dis_tr1_cnt=0 THEN
			INSERT INTO ontology.disease_treatments VALUES (d_id,t_id,0);
		ELSE
			UPDATE ontology.disease_treatments SET count=count+1
            WHERE treatment_id=t_id AND disease_id=d_id;
		END IF;
    END IF;
	
  END LOOP;

  CLOSE cur1;
END