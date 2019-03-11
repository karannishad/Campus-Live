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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;

@Controller
public class UploadController {
    @Autowired
    AttendanceRecordService attendanceRecordService;


     @RequestMapping("/")
    public String index() {
        return "registration";
    }

    @PostMapping("/uploadFile")
    public ModelAndView uploadFile(@RequestParam("file") MultipartFile file) {
         ModelAndView mav =null;
        try {
            AttendanceRecord attendanceRecord = attendanceRecordService.storeFile(file);
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/downloadFile/")
                    .path(attendanceRecord.getId().toString())
                .toUriString();

            List<String> result = CompareFaces.getAttendance(attendanceRecord);
            mav=new ModelAndView("success");
            mav.addObject("studentList",result);
            return mav;

        } catch (IOException e) {
            e.printStackTrace();
        }
        mav=new ModelAndView("fail");
        return mav;
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
