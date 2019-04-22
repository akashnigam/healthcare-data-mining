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
public class DiseaseTreatment implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private Disease disease;

    @Id
    @ManyToOne
    @JoinColumn
    @JsonManagedReference
    private Treatment treatment;


    private int count;

    public DiseaseTreatment() {
    }

    public DiseaseTreatment(Disease disease, Treatment treatment, int count) {
        this.disease = disease;
        this.treatment = treatment;
        this.count = count;
    }

    public Disease getDisease() {
        return disease;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }





}
