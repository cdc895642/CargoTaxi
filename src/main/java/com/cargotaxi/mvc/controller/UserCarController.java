package com.cargotaxi.mvc.controller;

import com.cargotaxi.mvc.model.Car;
import com.cargotaxi.mvc.model.User;
import com.cargotaxi.mvc.model.UserCar;
import com.cargotaxi.mvc.service.UserCarService;
import com.cargotaxi.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
public class UserCarController {

    @Autowired
    UserService userService;
    @Autowired
    UserCarService userCarService;

    @RequestMapping(value = "/user/cars", params = {"removeCar"})
    public String removeCar(HttpServletRequest req){
        Integer carId=Integer.valueOf(req.getParameter("removeCar"));
//        UserCar userCar=userCarService.findById(carId);
        //Car car=userCar.getCar();
//        User user=userCar.getUser();
//        user.getCars().removeIf(p->p.getId()==carId);
//        userService.save(user);
        //userCar.getOffers();
        userCarService.deleteById(carId);
        return "user/cars";
    }


    @RequestMapping("/user/cars")
    public String listMyCars(Model model, Principal principal){
        List<UserCar> cars= userCarService.findUserCarsOfPrincipal(principal);
        model.addAttribute("cars",cars);
        return "user/cars";
    }
}
