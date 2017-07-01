package com.cargotaxi.mvc.controller;

import com.cargotaxi.mvc.model.User;
import com.cargotaxi.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class UserCarController {

    @Autowired
    UserService userService;

    @RequestMapping("/user/cars")
    public String listMyCars(Model model){
        List<User> executors= userService.findExecutorsAll();
        model.addAttribute("executors",executors);
        return "user/cars";
    }
}
