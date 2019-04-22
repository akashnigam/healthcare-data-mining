package com.swm.searchInterface.repositories;

import com.swm.searchInterface.entity.Disease;
import com.swm.searchInterface.entity.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface DiseaseRepository extends CrudRepository<Disease, Integer> {

    @Override
    @Transactional
    List<Disease> findAll();

    @Query("select d.diseaseName from Disease d")
    List<String> findDiseaseNames();

    @Query("select d from Disease d where d.diseaseId in (:d_ids)")
    List<Disease> findDisease(int[] d_ids);

    @Query("select d from Disease d where d.diseaseName like (:name)")
    Disease findDiseaseByName(String name);


}
