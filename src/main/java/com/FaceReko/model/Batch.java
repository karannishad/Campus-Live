package com.FaceReko.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Batch {
    @Id
    private String batchName;

    public Batch(){

    }
    public Batch(String batchName) {
        this.batchName = batchName;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }
}
