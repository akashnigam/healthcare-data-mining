package com.swm.searchInterface.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Treatment {

    @Id
    @GeneratedValue
    private int treatmentId;


    private String treatmentName;

    private String description;


    @OneToMany(mappedBy = "treatment", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<DiseaseTreatment> diseases_treatments = new HashSet<DiseaseTreatment>();


    public Treatment() {
    }

    public Set<DiseaseTreatment> getDiseases_treatments() {
        return diseases_treatments;
    }

    public void setDiseases_treatments(Set<DiseaseTreatment> diseases_treatments) {
        this.diseases_treatments = diseases_treatments;
    }

    public Treatment(int treatmentId, String treatmentName, String description) {
        this.treatmentId = treatmentId;
        this.treatmentName = treatmentName;
        this.description = description;
    }

    public int getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(int treatmentId) {
        this.treatmentId = treatmentId;
    }

    public String getTreatmentName() {
        return treatmentName;
    }

    public void setTreatmentName(String treatmentName) {
        this.treatmentName = treatmentName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Treatment{" +
                "treatmentId=" + treatmentId +
                ", treatmentName='" + treatmentName + '\'' +
                ", description='" + description + '\'' +
                ", diseases_treatments=" + diseases_treatments +
                '}';
    }
}
