package com.cargotaxi.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SigninController {

    @RequestMapping("/signin")
    public String signinPage(Model model) {
        return "signin";
    }

    @RequestMapping("/signin-error.html")
    public String signinError(Model model) {
        model.addAttribute("loginError", true);
        return "signin";
    }
}
