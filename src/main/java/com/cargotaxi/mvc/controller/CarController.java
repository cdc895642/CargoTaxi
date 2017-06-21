package com.cargotaxi.mvc.controller;

import com.cargotaxi.mvc.controller.form.NewCarDTO;
import com.cargotaxi.mvc.model.*;
import com.cargotaxi.mvc.service.*;
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
    UserService<User> userService;
    @Autowired
    CarTypeService carTypeService;
    @Autowired
    CarService carService;
    @Autowired
    UserCarService userCarService;

    @RequestMapping("/new")
    public String newCar(Model model) {
        List<User> users = userService.findAll();
        List<CarType> carTypes = carTypeService.findAll();
        model.addAttribute("users", users);
        model.addAttribute("carTypes", carTypes);
        model.addAttribute("newCarModelAttribute", new NewCarDTO());
        return "car/new";
    }

    @RequestMapping(value = "/saveNewCar", method = RequestMethod.POST)
    public String saveCar(@ModelAttribute("newCarModelAttribute")
                                  NewCarDTO
                                  newCarDTO, Model model) {
        // logic to process input data
        List<User> users = userService.findAll();
        List<CarType> carTypes = carTypeService.findAll();
        model.addAttribute("users", users);
        model.addAttribute("carTypes", carTypes);
        UserCar userCar=createNewCar(newCarDTO);
        String result = userCar==null ? "error" : "success";
        model.addAttribute("result", result);
        return "car/new";
    }

    private UserCar createNewCar(NewCarDTO newCarDTO){
        User user= userService.findById(newCarDTO.getUserId());
        CarType carType=carTypeService.findById(newCarDTO
                .getCarTypeId());
        Car car=new Car();
        car.setLoad(newCarDTO.getLoad());
        car.setCapacity(newCarDTO.getCapacity());
        car.setCarType(carType);
        car=carService.create(car);
        if (car==null){
            return null;
        }
        UserCar userCar=new UserCar();
        userCar.setCar(car);
        userCar.setDescription(newCarDTO.getDescription());
        userCar.setLocation(newCarDTO.getLocation());
        userCar.setUser(user);
        userCar=userCarService.create(userCar);
        return userCar;
    }
}



