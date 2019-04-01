package com.FaceReko.controller;

import com.FaceReko.awscollection.CreateBucket;
import com.FaceReko.credentials.Credentials;
import com.FaceReko.model.Batch;
import com.FaceReko.model.User;
import com.FaceReko.repository.BatchRepo;
import com.FaceReko.repository.UserRepository;
import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BatchRepo batchRepo;

    AmazonS3 amazonS3= Credentials.getS3Client();
    private static File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    @RequestMapping("/setimage")
    public ModelAndView setImage(@RequestParam("image")MultipartFile image, HttpSession httpSession)throws Exception{
        User user = userRepository.findByEnrollId((String) httpSession.getAttribute("newuser"));
        File imagefile=convertMultiPartToFile(image);
        String bucketName=user.getBatch();
        amazonS3.putObject(bucketName,user.getEnrollId(),imagefile);
        user.setImage(image.getBytes());

        imagefile.delete();
        userRepository.save(user);

        return new ModelAndView("home").addObject("response","User "+user.getEnrollId()
                + " created successfully");
    }


    @RequestMapping("/createClass")
    public ModelAndView addNewClass(@RequestParam("classname") String classname){
        ModelAndView modelAndView=new ModelAndView("response");
        Batch batch=new Batch(classname);
        if(CreateBucket.createBucket(classname))
        {
            batchRepo.save(batch);
            modelAndView.addObject("response","Class Added.");
        }
        else {
            modelAndView.addObject("response", "Class Already Exist !");
        }
        return modelAndView;
    }
}
