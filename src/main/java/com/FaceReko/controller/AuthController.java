package com.FaceReko.controller;

import com.FaceReko.model.LoginData;
import com.FaceReko.model.User;
import com.FaceReko.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    UserRepository userRepository;



    @RequestMapping("/checkLogin")
    public String loginRequest(@ModelAttribute LoginData loginData, HttpSession httpSession, HttpServletResponse response) {
        User user = userRepository.findByEnrollIdAndPassword(loginData.getEnrollid(), loginData.getPassword());
        response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
        if (user != null) {
            httpSession.setAttribute("id", user.getEnrollId());
            httpSession.setAttribute("name",user.getName());
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



    @RequestMapping("/studentregistration")
    public ModelAndView registerUser(@ModelAttribute User user, HttpSession httpSession) throws Exception {
        ModelAndView mav = null;
        httpSession.setAttribute("newuser", user.getEnrollId());
        userRepository.save(user);
        if(user.getRole().equals("Faculty")){

            return new ModelAndView("response").addObject("response","User Added successfully");

        }
       else {
            mav = new ModelAndView("setImage");

            return mav;
       }
    }

    @RequestMapping("/logout")
    public String logoutRequest(HttpSession httpSession) {

        httpSession.invalidate();

        return "redirect:/";
    }
}
