package com.cargotaxi.mvc.controller;

import com.cargotaxi.mvc.controller.form.FindCarDTO;
import com.cargotaxi.mvc.model.User;
import com.cargotaxi.mvc.service.CarTypeService;
import com.cargotaxi.mvc.service.UserService;
import com.cargotaxi.mvc.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/executor")
public class ExecutorController {

    @Autowired
    UserService userService;
    @Autowired
    CarTypeService carTypeService;

    @RequestMapping("/find-all")
    public String findExecutorsAll(@ModelAttribute("findCarDTO") FindCarDTO
                                               findCarDTO,
                                   Model model) {
        List<User> executors= userService.findExecutorsAll();
        model.addAttribute("executors",executors);
        model.addAttribute("carTypes", carTypeService.findAll());

        return "executor/find";
    }

    @RequestMapping(value="/find-all", params = {"Find"})
    public String findExecutorsBySpecification(@ModelAttribute("findCarDTO")
                                                     FindCarDTO
                                           findCarDTO,
                                   Model model) {
        List<User> executors= userService.findExecutorsBySpecification(findCarDTO);
        model.addAttribute("executors",executors);
        model.addAttribute("carTypes", carTypeService.findAll());

        return "executor/find";
    }

    @ModelAttribute
    private FindCarDTO findCarDTO() {
        return new FindCarDTO();
    }

}
