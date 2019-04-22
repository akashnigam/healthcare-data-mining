package com.swm.searchInterface.repositories;
import com.swm.searchInterface.entity.Symptom;
import com.swm.searchInterface.entity.Treatment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface TreatmentRepository  extends CrudRepository<Treatment, Integer> {


    @Override
    @Transactional
    List<Treatment> findAll();

    @Query("select t.treatmentName from Treatment t")
    List<String> findTreatmentNames();


}
