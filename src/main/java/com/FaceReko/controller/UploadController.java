package com.FaceReko.controller;

import com.FaceReko.awscollection.CompareFaces;
import com.FaceReko.model.AttendanceRecord;
import com.FaceReko.service.AttendanceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;

@Controller
public class UploadController {
    @Autowired
    AttendanceRecordService attendanceRecordService;


     @RequestMapping("/")
    public String index() {
        return "upload";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            AttendanceRecord attendanceRecord = attendanceRecordService.storeFile(file);
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/downloadFile/")
                    .path(attendanceRecord.getId().toString())
                .toUriString();

            List<String> result = CompareFaces.getAttendance(attendanceRecord);
            System.out.println(result);
            return "";
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) {
        // Load file from database
        AttendanceRecord attendanceRecord = attendanceRecordService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attendanceRecord.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attendanceRecord.getFileName() + "\"")
                .body(new ByteArrayResource(attendanceRecord.getImage()));
    }

}
