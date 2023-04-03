package com.example.mvccrud.controller;

import com.example.mvccrud.entity.Admin;
import com.example.mvccrud.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SecurityController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/login-error")
    public String loginError(Model model){
        model.addAttribute("loginError",true);
        return "login";
    }

    @GetMapping("/sign-up")
    public String signUp(Model model){
        model.addAttribute("admin",new Admin());
        return "signup";
    }

    @PostMapping("/register")
    public String register(Admin admin, BindingResult result){
        if(result.hasErrors()){
            return "signup";
        }
        adminService.signUp(admin);
        return "redirect:/login";
    }


}
