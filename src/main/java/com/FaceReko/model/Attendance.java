package com.FaceReko.model;

import javax.persistence.*;

@Entity
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String studentid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fk_refer")
    private AttendanceRecord attendanceRecord;

    public Attendance(String studentid, AttendanceRecord attendanceRecord) {
        this.studentid = studentid;
        this.attendanceRecord = attendanceRecord;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public AttendanceRecord getAttendanceRecord() {
        return attendanceRecord;
    }

    public void setAttendanceRecord(AttendanceRecord attendanceRecord) {
        this.attendanceRecord = attendanceRecord;
    }
}
