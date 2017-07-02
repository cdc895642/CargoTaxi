package com.cargotaxi.mvc.controller;

import com.cargotaxi.mvc.controller.form.NewCarDTO;
import com.cargotaxi.mvc.controller.form.OfferDTO;
import com.cargotaxi.mvc.model.Car;
import com.cargotaxi.mvc.model.User;
import com.cargotaxi.mvc.model.UserCar;
import com.cargotaxi.mvc.service.CarTypeService;
import com.cargotaxi.mvc.service.UserCarService;
import com.cargotaxi.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
public class UserCarController {

    //    @Autowired
//    UserService userService;
    @Autowired
    CarTypeService carTypeService;
    @Autowired
    UserCarService userCarService;

    @RequestMapping(value = "/user/car/saveChange", method = RequestMethod.POST)
    public String saveEditCarChange(@ModelAttribute("car") NewCarDTO car, Model
            model) {
        UserCar userCar = userCarService.saveCarChange(car);
        NewCarDTO carDTO = new NewCarDTO(userCar);
        model.addAttribute("car", carDTO);
        model.addAttribute("carTypes", carTypeService.findAll());
        return "user/car/edit";
    }

    @RequestMapping(value = "/user/cars", params = {"editCar"})
    public String getEditCarPage(HttpServletRequest req, Model model) {
        Integer carId = Integer.valueOf(req.getParameter("editCar"));
        UserCar car = userCarService.findById(carId);
        NewCarDTO carDTO = new NewCarDTO(car);
        model.addAttribute("car", carDTO);
        model.addAttribute("carTypes", carTypeService.findAll());
        return "user/car/edit";
    }

    @RequestMapping(value = "/user/cars", params = {"removeCar"})
    public String removeCar(HttpServletRequest req, Model model, Principal
            principal) {
        Integer carId = Integer.valueOf(req.getParameter("removeCar"));
        userCarService.deleteById(carId);
        listMyCars(model, principal);
        return "user/cars";
    }

    @RequestMapping("/user/cars")
    public String listMyCars(Model model, Principal principal) {
        List<UserCar> cars = userCarService.findUserCarsOfPrincipal(principal);
        model.addAttribute("cars", cars);
        return "user/cars";
    }

    @RequestMapping(value = "/user/car/saveChange", params = {"addOffer"})
    public String addOffer(@ModelAttribute("car") NewCarDTO
                                   car, Model model) {
        car.getOffers().add(new OfferDTO());
        model.addAttribute("carTypes", carTypeService.findAll());
        return "user/car/edit";
    }

    @RequestMapping(value = "/user/car/saveChange", params = {"removeOffer"})
    public String removeOffer(@ModelAttribute("car")
                                      NewCarDTO car, HttpServletRequest req,
                              Model model) {
        Integer rowId = Integer.valueOf(req.getParameter("removeOffer"));
        car.getOffers().remove(rowId.intValue());
        model.addAttribute("carTypes", carTypeService.findAll());
        return "user/car/edit";
    }
}
