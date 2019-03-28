package com.FaceReko.model;



import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
public class AttendanceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String teacherid;

    private String subject;

    private String batch;

    private String lectureno;
    @Lob
    private byte[] image;
    @Column(name="timestamp", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Date timestamp;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @OneToMany(mappedBy = "attendanceRecord")
    private Set<Attendance> attendanceSet;
    public AttendanceRecord() {

    }



    public AttendanceRecord(Long id, String teacherid, String subject, String batch, String lectureno, byte[] image) {
        this.id = id;
        this.teacherid = teacherid;
        this.subject = subject;
        this.batch = batch;

        this.lectureno = lectureno;
        this.image = image;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeacherid() {
        return teacherid;
    }

    public void setTeacherid(String teacherid) {
        this.teacherid = teacherid;
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



    public String getLectureno() {
        return lectureno;
    }

    public void setLectureno(String lectureno) {
        this.lectureno = lectureno;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
