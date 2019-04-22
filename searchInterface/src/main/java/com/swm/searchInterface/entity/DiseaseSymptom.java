package com.swm.searchInterface.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
public class DiseaseSymptom implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private Disease disease;


    @Id
    @ManyToOne
    @JoinColumn
    @JsonManagedReference
    private Symptom symptom;


    private int count;


    public DiseaseSymptom(Disease disease, Symptom symptom, int count) {
        this.disease = disease;
        this.symptom = symptom;
        this.count = count;
    }

    public DiseaseSymptom() {
    }

    public Disease getDisease() {
        return disease;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    public Symptom getSymptom() {
        return symptom;
    }

    public void setSymptom(Symptom symptom) {
        this.symptom = symptom;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
