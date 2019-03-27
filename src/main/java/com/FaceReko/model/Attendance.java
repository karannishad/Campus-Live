package com.FaceReko.model;

import javax.persistence.*;

@Entity
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String studentId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="fk_refer")
    private AttendanceRecord attendanceRecord;

    public Attendance(){

    }

    public Attendance(String studentid, AttendanceRecord attendanceRecord) {
        this.studentId = studentid;
        this.attendanceRecord = attendanceRecord;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public AttendanceRecord getAttendanceRecord() {
        return attendanceRecord;
    }

    public void setAttendanceRecord(AttendanceRecord attendanceRecord) {
        this.attendanceRecord = attendanceRecord;
    }
}
