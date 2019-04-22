package com.swm.searchInterface.repositories;

import com.swm.searchInterface.entity.Disease;
import com.swm.searchInterface.entity.DiseaseCount;
import com.swm.searchInterface.entity.Symptom;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface SymptomRepository extends CrudRepository<Symptom, Integer> {


    @Override
    @Transactional
    List<Symptom> findAll();

    @Query("select s.symptomName from Symptom s")
    List<String> findSymptomNames();


//    @Query("select (d) from Disease d inner join DiseaseSymptom ds where ds.symptom_id = 1")
//    List<Object[]> findDiseases(String symptom);


}
