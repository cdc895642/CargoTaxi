package com.cargotaxi.mvc.controller;

import com.cargotaxi.mvc.dao.UserRepository;
import com.cargotaxi.mvc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    UserRepository userRepository;

    @RequestMapping("/")
    public ModelAndView startPage() {
        return new ModelAndView("index", "",null);
    }
}
