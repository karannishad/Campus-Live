package com.FaceReko.model;

import javax.persistence.*;

@Entity
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String subject;

    private String batch;

    private String dos; //dos=Date of Submmissiion

    @Lob
    private byte[] fileField;

    public Assignment()
    {

    }

    public Assignment(Long id, String subject, String batch, String dos, byte[] fileField) {
        this.id = id;
        this.subject = subject;
        this.batch = batch;
        this.dos = dos;
        this.fileField = fileField;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getDos() {
        return dos;
    }

    public void setDos(String dos) {
        this.dos = dos;
    }

    public byte[] getFileField() {
        return fileField;
    }

    public void setFileField(byte[] fileField) {
        this.fileField = fileField;
    }
}
