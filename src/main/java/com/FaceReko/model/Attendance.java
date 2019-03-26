package com.FaceReko.model;

import javax.persistence.*;

@Entity
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String studentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fk_refer")
    private AttendanceRecord attendanceRecord;

    public Attendance(){

    }

    public Attendance(String studentid, AttendanceRecord attendanceRecord) {
        this.studentId = studentid;
        this.attendanceRecord = attendanceRecord;
    }

    public String getStudentid() {
        return studentId;
    }

    public void setStudentid(String studentid) {
        this.studentId = studentid;
    }

    public AttendanceRecord getAttendanceRecord() {
        return attendanceRecord;
    }

    public void setAttendanceRecord(AttendanceRecord attendanceRecord) {
        this.attendanceRecord = attendanceRecord;
    }
}
