package com.FaceReko.repository;

import com.FaceReko.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceRepo extends JpaRepository<Attendance ,Long> {

    public List<Attendance> findByStudentId(String studentid);
}
