package com.FaceReko.controller;

import com.FaceReko.awscollection.CompareFaces;
import com.FaceReko.model.AttendanceRecord;
import com.FaceReko.repository.AttendanceRecordRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.jws.WebParam;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class ActionController {

    @Autowired
    AttendanceRecordRepo attendanceRecordRepo;

    @RequestMapping
    public String upload(){
        return "upload";
    }
    @RequestMapping("/uploadAttendance")
    public ModelAndView uploadAttendance(@ModelAttribute AttendanceRecord attendanceRecord, HttpSession httpSession){
        ModelAndView modelAndView=null;
        attendanceRecord.setTeacherid((String) httpSession.getAttribute("id"));
        attendanceRecordRepo.save(attendanceRecord);
        httpSession.setAttribute("attendId",attendanceRecord.getId());
            modelAndView =new ModelAndView("uploadClassImage");
            return modelAndView;

    }

    @RequestMapping("/uploadAttendanceImage")
    public ModelAndView uploadAttendanceImage(@RequestParam("image") MultipartFile file, HttpSession httpSession){
        ModelAndView mav =null;
        try {


            AttendanceRecord attendanceRecord = attendanceRecordRepo.findById((Long)httpSession.getAttribute("attendId")).get();
            attendanceRecord.setImage(file.getBytes());
            attendanceRecordRepo.save(attendanceRecord);
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
}