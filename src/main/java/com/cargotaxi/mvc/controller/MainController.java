package com.cargotaxi.mvc.controller;

import com.cargotaxi.mvc.dao.UserRepository;
import com.cargotaxi.mvc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping("/")
    public ModelAndView startPage(Model model) {
        List<User> users=userRepository.findAll();
        HashMap<String, String> encodePasswords=new HashMap<>();
        users.forEach(x->encodePasswords.put(x.getLogin(),
                passwordEncoder.encode(x.getPassword())));

        return new ModelAndView("index", "passwords",encodePasswords);
    }
}
