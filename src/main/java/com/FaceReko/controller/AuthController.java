package com.FaceReko.controller;

import com.FaceReko.model.LoginData;
import com.FaceReko.model.User;
import com.FaceReko.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    UserRepository userRepository;



    @RequestMapping("/checkLogin")
    public String loginRequest(@ModelAttribute LoginData loginData, HttpSession httpSession) {
        User user = userRepository.findByEnrollIdAndPassword(loginData.getEnrollid(), loginData.getPassword());
        if (user != null) {
            httpSession.setAttribute("id", user.getEnrollId());
            if(user.getRole().equals("Faculty")){
                return "facultyhome";
            }
            if(user.getRole().equals("Student")){
                return "studenthome";
            }
            if(user.getRole().equals("Admin")){
                return "adminhome";
            }
        }
        else {
            httpSession.setAttribute("error" ,"login Failed. Try Again");

        }
        return "login";
    }

    @RequestMapping("/log")
    public String login() {

        return "login";

    }

    @RequestMapping("/registration")
    public String registraion() {

        return "registration";

    }

    @RequestMapping("/studentregistration")
    public ModelAndView registerUser(@ModelAttribute User user, HttpSession httpSession) throws Exception {
        ModelAndView mav = null;
        if (httpSession.getAttribute("id") == null)
            httpSession.setAttribute("id", user.getEnrollId());
        userRepository.save(user);
        mav = new ModelAndView("setImage", HttpStatus.CREATED);
        return mav;
    }

    @RequestMapping("/logout")
    public String logoutRequest(HttpSession httpSession) {

        httpSession.invalidate();

        return "redirect:/";
    }
}
