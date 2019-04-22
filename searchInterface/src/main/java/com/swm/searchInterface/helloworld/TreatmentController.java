package com.swm.searchInterface.helloworld;
import com.swm.searchInterface.entity.Disease;
import com.swm.searchInterface.entity.DiseaseCount;
import com.swm.searchInterface.entity.Treatment;
import com.swm.searchInterface.repositories.DiseaseRepository;
import com.swm.searchInterface.repositories.TreatmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TreatmentController {


    @Autowired
    private MessageSource messageSource;

    @Autowired
    public DiseaseRepository diseaseRepository;

    @Autowired
    public EntityManager entityManager;


    @Autowired
    public TreatmentRepository treatmentRepository;

    @GetMapping(path = "/treatmentNames")
    public List<String> treatmentNames(){
        return treatmentRepository.findTreatmentNames();
    }


    @GetMapping(path = "/treatments")
    public List<Treatment> treatments(){
        return treatmentRepository.findAll();
    }


    @GetMapping(path = "/getDiseasesFromTreatMent")
    public List<DiseaseCount> getDiseases(){
        String treatMentName = "fever";
        Query query = entityManager.createNativeQuery("select d.* from disease d inner join disease_symptom ds on d.disease_id=ds.disease_disease_id where ds.symptom_symptom_id in (select symptom_id from symptom where symptom_name like :symptomName)", Disease.class);
        List<Disease> diseases = query.setParameter("symptomName", treatMentName).getResultList();
        query = entityManager.createNativeQuery("select (ds.count) from disease d inner join disease_symptom ds on d.disease_id=ds.disease_disease_id where ds.symptom_symptom_id in (select symptom_id from symptom where symptom_name like :symptomName)");
        List<Integer> countValues = query.setParameter("symptomName", treatMentName).getResultList();
        List<DiseaseCount> diseaseCounts = new ArrayList<>();
        for(int i = 0;i < diseases.size();i++){
            DiseaseCount diseaseCount = new DiseaseCount(diseases.get(i), countValues.get(i));
            diseaseCounts.add(diseaseCount);
        }
        return diseaseCounts;
    }


}
