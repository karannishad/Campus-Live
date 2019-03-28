package com.FaceReko.controller;

import com.FaceReko.repository.BatchRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Redirect {

    @Autowired
    BatchRepo batchRepo;

    @RequestMapping("/")
    public String index() {
        return "index";
    }
    @RequestMapping("/upload")
    public ModelAndView upload() {
        ModelAndView modelAndView=new ModelAndView("upload");
        modelAndView.addObject("classlist",batchRepo.findAll());
        return modelAndView;

    }

    @RequestMapping("/log")
    public String login() {

        return "login";

    }

    @RequestMapping("/createClassForm")
    public String createClassForm(){

        return "createClassForm";
    }

    @RequestMapping("/registration")
    public ModelAndView registration() {
        ModelAndView modelAndView=new ModelAndView("registration");
        modelAndView.addObject("classlist",batchRepo.findAll());
        return modelAndView;

    }

}
