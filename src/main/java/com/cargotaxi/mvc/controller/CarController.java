package com.cargotaxi.mvc.controller;

import com.cargotaxi.mvc.controller.form.NewCarModelAttribute;
import com.cargotaxi.mvc.model.*;
import com.cargotaxi.mvc.service.CarService;
import com.cargotaxi.mvc.service.CarTypeService;
import com.cargotaxi.mvc.service.UserCarService;
import com.cargotaxi.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/car")
public class CarController {

    @Autowired
    UserService userService;
    @Autowired
    CarTypeService carTypeService;
    @Autowired
    CarService carService;
    @Autowired
    UserCarService userCarService;

    @RequestMapping("/new")
    public String newCar(Model model) {
        System.out.println("new car is called");
        List<User> users = userService.findAll();
        List<CarType> carTypes = carTypeService.findAll();
        System.out.println("users - " + users.size());
        model.addAttribute("users", users);
        model.addAttribute("carTypes", carTypes);
        model.addAttribute("newCarModelAttribute", new NewCarModelAttribute());
        return "car/new";
    }

    @RequestMapping(value = "/saveNewCar", method = RequestMethod.POST)
    public String saveStudent(@ModelAttribute("newCarModelAttribute")
                                      NewCarModelAttribute
                                          newCarModelAttribute, Model model) {
        // logic to process input data
        List<User> users = userService.findAll();
        List<CarType> carTypes = carTypeService.findAll();
        model.addAttribute("users", users);
        model.addAttribute("carTypes", carTypes);
        UserCar userCar=createNewCar(newCarModelAttribute);
        String result = userCar==null ? "error" : "success";
        model.addAttribute("result", result);
        return "car/new";
    }

    private UserCar createNewCar(NewCarModelAttribute newCarModelAttribute){
        User user=userService.findById(newCarModelAttribute.getUserId());
        CarType carType=carTypeService.findById(newCarModelAttribute
                .getCarTypeId());
        Car car=new Car();
        car.setLoad(newCarModelAttribute.getLoad());
        car.setCapacity(newCarModelAttribute.getCapacity());
        car.setCarType(carType);
        car=carService.create(car);
        if (car==null){
            return null;
        }
        UserCar userCar=new UserCar();
        userCar.setCar(car);
        userCar.setDescription(newCarModelAttribute.getDescription());
        userCar.setLocation(newCarModelAttribute.getLocation());
        userCar.setUser(user);
        userCar=userCarService.create(userCar);
        return userCar;
    }
}



