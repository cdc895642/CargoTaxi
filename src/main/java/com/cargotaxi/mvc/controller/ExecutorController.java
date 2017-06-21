package com.cargotaxi.mvc.controller;

import com.cargotaxi.mvc.model.User;
import com.cargotaxi.mvc.service.UserService;
import com.cargotaxi.mvc.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/executor")
public class ExecutorController {

    @Autowired
    UserService userService;

    @RequestMapping("/find-all")
    public String findExecutorsAll(Model model) {
        List<User> executors= userService.findExecutorsAll();
        model.addAttribute("executors",executors);
        return "executor/find";
    }
}
