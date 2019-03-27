package com.FaceReko.repository;

import com.FaceReko.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.sql.Date;
public interface AttendanceRepo extends JpaRepository<Attendance ,Long> {

    public List<Attendance> findByStudentId(String studentId);


    
}
