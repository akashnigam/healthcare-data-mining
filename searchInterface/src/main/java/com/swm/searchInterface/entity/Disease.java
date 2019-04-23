package com.swm.searchInterface.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Disease {

    @Id
    private String diseaseId;


    @OneToMany(mappedBy = "disease", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<DiseaseSymptom> diseaseSymptoms = new HashSet<>();

    @OneToMany(mappedBy = "treatment", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<DiseaseTreatment> diseases_treatments = new HashSet<DiseaseTreatment>();


    public Set<DiseaseTreatment> getDiseases_treatments() {
        return diseases_treatments;
    }

    public void setDiseases_treatments(Set<DiseaseTreatment> diseases_treatments) {
        this.diseases_treatments = diseases_treatments;
    }

    public Set<DiseaseSymptom> getDiseaseSymptoms() {
        return diseaseSymptoms;
    }

    public void setDiseaseSymptoms(Set<DiseaseSymptom> diseaseSymptoms) {
        this.diseaseSymptoms = diseaseSymptoms;
    }

    public Disease() {


    }


    private String diseaseName;

    private String diseaseDescription;

    private int recordCount;

    private int symptomCount;

    private int treatmentCount;

    public Disease(String diseaseId, String diseaseName, String diseaseDescription, int recordCount, int symptomCount, int treatmentCount) {
        this.diseaseId = diseaseId;
        this.diseaseName = diseaseName;
        this.diseaseDescription = diseaseDescription;
        this.recordCount = recordCount;
        this.symptomCount = symptomCount;
        this.treatmentCount = treatmentCount;
    }

    public String getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(String diseaseId) {
        this.diseaseId = diseaseId;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getDiseaseDescription() {
        return diseaseDescription;
    }

    public void setDiseaseDescription(String diseaseDescription) {
        this.diseaseDescription = diseaseDescription;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public int getSymptomCount() {
        return symptomCount;
    }

    public void setSymptomCount(int symptomCount) {
        this.symptomCount = symptomCount;
    }

    public int getTreatmentCount() {
        return treatmentCount;
    }

    public void setTreatmentCount(int treatmentCount) {
        this.treatmentCount = treatmentCount;
    }

    @Override
    public String toString() {
        return "Disease{" +
                "diseaseId=" + diseaseId +
                ", diseaseName='" + diseaseName + '\'' +
                ", diseaseDescription='" + diseaseDescription + '\'' +
                ", recordCount=" + recordCount +
                ", symptomCount=" + symptomCount +
                ", treatmentCount=" + treatmentCount +
                '}';
    }

}