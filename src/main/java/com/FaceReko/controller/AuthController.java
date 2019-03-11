package com.FaceReko.controller;

import com.FaceReko.model.AttendanceRecord;
import com.FaceReko.model.Student;
import com.FaceReko.repository.StudentRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
public class AuthController {

    @Autowired
    StudentRepository studentRepository;

    @PostMapping(value = "/studentregistration",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView registerUser(@ModelAttribute Student student) {
        ModelAndView mav=null;
        System.out.println(student.getEmail());
//        JSONObject object = new JSONObject(studentDetails);

//
//        String name=object.optString("name");
//        String enrollId=object.optString("enroll");
//        String sex=object.optString("sex");
//        String branch=object.optString("branch");
//        String email=object.optString("email");
//        String password=object.optString("password");
//        Long mobile=object.optLong("mobile");
//        Student student=new Student(enrollId,password,name,mobile,sex,branch,email);
//        studentRepository.save(student);
        mav=new ModelAndView("success", HttpStatus.CREATED);
        return mav;
    }
}
