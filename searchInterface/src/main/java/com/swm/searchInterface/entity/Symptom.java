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
    @GeneratedValue
    private int symptomId;


    private String symptomName;

    private String description;

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


    public Symptom(int symptomId, String symptomName, String description) {
        this.symptomId = symptomId;
        this.symptomName = symptomName;
        this.description = description;
    }

    public int getSymptomId() {
        return symptomId;
    }

    public void setSymptomId(int symptomId) {
        this.symptomId = symptomId;
    }

    public String getSymptomName() {
        return symptomName;
    }

    public void setSymptomName(String symptomName) {
        this.symptomName = symptomName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return "Symptom{" +
                "symptomId=" + symptomId +
                ", symptomName='" + symptomName + '\'' +
                ", description='" + description + '\'' +
                ", diseases="  +
                '}';
    }
}
