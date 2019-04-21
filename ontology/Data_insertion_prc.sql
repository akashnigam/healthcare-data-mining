USE `ontology`;
DROP procedure IF EXISTS `cur_insertion`;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `cur_insertion``()
BEGIN
  DECLARE done INT DEFAULT FALSE ;
  DECLARE d_id,d_name,s_id,s_name TEXT;
  DECLARE cur1 CURSOR FOR SELECT Diseaseid,DiseaseName,SymptomId,SymptomName
  FROM ontology.patientsinfo1 p1;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

  OPEN cur1;

  read_loop: LOOP
    FETCH cur1 INTO d_id,d_name,s_id,s_name;
    IF done THEN
      LEAVE read_loop;
    END IF;
    select count(1) into @cnt from ontology.diseases1 where disease_id=d_id;
    IF @cnt =0 THEN
      INSERT INTO ontology.diseases1 VALUES (d_id,d_name,"new",1,1,1);
    ELSE
      update ontology.diseases1 set description="Shaubham",records_count=records_count+1 where disease_id=d_id;
    END IF;
  END LOOP;

  CLOSE cur1;
END$$
DELIMITER ;
