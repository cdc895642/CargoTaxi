package com.cargotaxi.mvc.controller;

import com.cargotaxi.mvc.dao.UserRepository;
import com.cargotaxi.mvc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by cdc89 on 10.06.2017.
 */
@Controller
public class MainController {
    @Autowired
    UserRepository userRepository;

    @RequestMapping("/")
    public ModelAndView startPage() {
        System.out.println("Main is called");
        User user=new User();
        user.setLogin("aaa45");
        user.setPassword("1231rty");
        user.setEmail("123@frt.com");
        user=userRepository.save(user);
        System.out.println(user.getId()+" - "+user.getLogin());
        List<User> users=userRepository.findAll();
        users.forEach(x->System.out.println(x.getLogin()));
        return new ModelAndView("index.html", "",null);
    }
}
