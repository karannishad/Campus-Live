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

    private String branch;

    private String year;

    private String lectureno;
    @Lob
    private byte[] image;
    @Column(name="timestamp", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Date timestamp;

    @OneToMany(mappedBy = "attendanceRecord")
    private Set<Attendance> attendanceSet;
    public AttendanceRecord() {

    }

    public AttendanceRecord(Long id, String teacherid, String subject, String branch, String year, String lectureno, byte[] image) {
        this.id = id;
        this.teacherid = teacherid;
        this.subject = subject;
        this.branch = branch;
        this.year = year;
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

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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
