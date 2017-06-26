package com.cargotaxi.mvc.controller;

import com.cargotaxi.mvc.controller.form.NewCarDTO;
import com.cargotaxi.mvc.controller.form.OfferDTO;
import com.cargotaxi.mvc.model.CarType;
import com.cargotaxi.mvc.model.UserCar;
import com.cargotaxi.mvc.service.CarTypeService;
import com.cargotaxi.mvc.service.CarTypeServiceImpl;
import com.cargotaxi.mvc.service.UserCarService;
import com.cargotaxi.mvc.service.UserCarServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequestMapping("/car")
public class CarController {

    @Autowired
    CarTypeService carTypeService;
    @Autowired
    UserCarService userCarService;

    @RequestMapping("/new")
    public String newCar(Model model) {
        model.addAttribute("carTypes", carTypeService.findAll());
        return "car/new";
    }

    @ModelAttribute
    private NewCarDTO newCarDTO() {
        return new NewCarDTO();
    }

    @RequestMapping(value = "/saveNewCar", method = RequestMethod.POST)
    public String saveCar(@ModelAttribute("newCarModelAttribute")
                                  NewCarDTO newCarDTO, Model model, Principal
                                  principal) {
        // logic to process input data
        model.addAttribute("carTypes", carTypeService.findAll());
        UserCar userCar = userCarService.createNewCar(newCarDTO, principal);
        String result = userCar == null ? "error" : "success";
        model.addAttribute("result", result);
        return "car/new";
    }

    @RequestMapping(value = "/saveNewCar", params = {"addOffer"})
    public String addOffer(@ModelAttribute("newCarDTO") NewCarDTO
                                   newCarDTO, Model model) {

        newCarDTO.getOffers().add(new OfferDTO());
        model.addAttribute("carTypes", carTypeService.findAll());
        return "car/new";
    }

    @RequestMapping(value = "/saveNewCar", params = {"removeOffer"})
    public String removeOffer(
            NewCarDTO newCarDTO, HttpServletRequest req, Model model) {
        Integer rowId = Integer.valueOf(req.getParameter("removeOffer"));
        newCarDTO.getOffers().remove(rowId.intValue());
        model.addAttribute("carTypes", carTypeService.findAll());
        return "car/new";
    }
}



