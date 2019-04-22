package com.swm.searchInterface.entity;


public class DiseaseCount {


    private Disease disease;

    private int count;


    public DiseaseCount() {
    }

    public DiseaseCount(Disease disease, int count) {
        this.disease = disease;
        this.count = count;
    }

    public Disease getDisease() {
        return disease;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
