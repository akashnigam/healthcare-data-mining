package com.swm.searchInterface.helloworld;

import com.swm.searchInterface.entity.Disease;
import com.swm.searchInterface.entity.DiseaseCount;
import com.swm.searchInterface.entity.Symptom;
import com.swm.searchInterface.repositories.DiseaseRepository;
import com.swm.searchInterface.repositories.SymptomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class SymptomController {


    @Autowired
    private MessageSource messageSource;
    //private MessageSource resourceBundleMessageSource;
    //GTE
    //URI - /hello world
    //method
    // @RequestMapping(method = RequestMethod.GET, path = "/hello_world")

    @Autowired
    public DiseaseRepository diseaseRepository;

    @Autowired
    public EntityManager entityManager;

    @Autowired
    public SymptomRepository symptomRepository;


    @GetMapping(path = "/symptomNames")
    public List<String> symptomNames(){
        return symptomRepository.findSymptomNames();
    }


    @GetMapping(path = "/symptoms")
    public List<Symptom> symptoms(){
        return symptomRepository.findAll();
    }


    @GetMapping(path = "/getDiseasesSymptom/{symptomName}")
    public List<DiseaseCount> getDiseases(@PathVariable("symptomName") String symptomName){
        Query query = entityManager.createNativeQuery("select d.* from disease d inner join disease_symptom ds on d.disease_id=ds.disease_disease_id where ds.symptom_symptom_id in (select symptom_id from symptom where symptom_name like :symptomName)", Disease.class);
        List<Disease> diseases = query.setParameter("symptomName", symptomName).getResultList();
        query = entityManager.createNativeQuery("select (ds.count) from disease d inner join disease_symptom ds on d.disease_id=ds.disease_disease_id where ds.symptom_symptom_id in (select symptom_id from symptom where symptom_name like :symptomName)");
        List<Integer> countValues = query.setParameter("symptomName", symptomName).getResultList();
        List<DiseaseCount> diseaseCounts = new ArrayList<>();
        for(int i = 0;i < diseases.size();i++){
            DiseaseCount diseaseCount = new DiseaseCount(diseases.get(i), countValues.get(i));
            diseaseCounts.add(diseaseCount);
        }
        return diseaseCounts;
    }



    @RequestMapping(path="/getDisease/{diseaseName}")
    public Disease getDisease(@PathVariable("diseaseName") String diseaseName) {
        return diseaseRepository.findDiseaseByName(diseaseName);
    }




}
