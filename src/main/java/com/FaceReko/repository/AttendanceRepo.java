package com.FaceReko.repository;

import com.FaceReko.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepo extends JpaRepository<Attendance ,Long> {
}
