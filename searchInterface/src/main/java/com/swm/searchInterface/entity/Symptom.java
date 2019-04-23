package com.swm.searchInterface.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Symptom {



    @Id
    private String symptomId;


    private String symptomName;

//    private String description;

    @OneToMany(mappedBy = "symptom", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<DiseaseSymptom> diseaseSymptoms = new HashSet<>();

    public Set<DiseaseSymptom> getDiseaseSymptoms() {
        return diseaseSymptoms;
    }

    public void setDiseaseSymptoms(Set<DiseaseSymptom> diseaseSymptoms) {
        this.diseaseSymptoms = diseaseSymptoms;
    }

    public Symptom() {
    }


    public Symptom(String symptomId, String symptomName) {
        this.symptomId = symptomId;
        this.symptomName = symptomName;
    }

    public String getSymptomId() {
        return symptomId;
    }

    public void setSymptomId(String symptomId) {
        this.symptomId = symptomId;
    }

    public String getSymptomName() {
        return symptomName;
    }

    public void setSymptomName(String symptomName) {
        this.symptomName = symptomName;
    }



    @Override
    public String toString() {
        return "Symptom{" +
                "symptomId=" + symptomId +
                ", symptomName='" + symptomName + '\'' +
                ", diseases="  +
                '}';
    }
}
