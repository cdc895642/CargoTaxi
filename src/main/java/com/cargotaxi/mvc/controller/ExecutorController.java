package com.cargotaxi.mvc.controller;

import com.cargotaxi.mvc.controller.form.NewCarModelAttribute;
import com.cargotaxi.mvc.model.CarType;
import com.cargotaxi.mvc.model.User;
import com.cargotaxi.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/executor")
public class ExecutorController {

    @Autowired
    UserService userService;

    @RequestMapping("/find-all")
    public String newCar(Model model) {
        List<User> users=userService.findAll();
        List<User> executors=users.stream().filter(user -> user.getCars().size
                ()>0).collect(Collectors.toList());
        model.addAttribute("executors",executors);
        return "executor/find";
    }
}
