package com.FaceReko.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Redirect {

@RequestMapping("/upload")
    public String upload(){
    return "upload";
}
}
