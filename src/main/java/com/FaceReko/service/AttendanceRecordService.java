package com.FaceReko.service;

import com.FaceReko.model.AttendanceRecord;
import com.FaceReko.repository.AttendanceRecordRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class AttendanceRecordService {
    @Autowired
    AttendanceRecordRepo attendanceRecordRepo;

    public AttendanceRecord storeFile(MultipartFile file) throws IOException {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        AttendanceRecord attendanceRecord = new AttendanceRecord("Trial", fileName, file.getContentType(), file.getBytes());

        return attendanceRecordRepo.save(attendanceRecord);
    }

    public AttendanceRecord getFile(long fileId) {
        return attendanceRecordRepo.findById(fileId).get();
    }

}
