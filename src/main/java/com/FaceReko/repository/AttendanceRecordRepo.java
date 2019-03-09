package com.FaceReko.repository;

import com.FaceReko.model.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRecordRepo extends JpaRepository<AttendanceRecord, Long> {
}
