package com.cargotaxi.mvc.controller;

import com.cargotaxi.mvc.controller.form.NewUserDTO;
import com.cargotaxi.mvc.controller.form.PhoneDTO;
import com.cargotaxi.mvc.model.User;
import com.cargotaxi.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

/**
 * Created by cdc89 on 17.06.2017.
 */
@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    MessageSource messageSource;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String registerUserAccount(@ModelAttribute("newUserDTO") @Valid
                                              NewUserDTO newUserDTO,
                                      BindingResult result, Model model,
                                      Errors errors) {
        String registerResult;
        if (result.hasErrors()) {
            registerResult = "valid.error.register.user";
            model.addAttribute("result", registerResult);
            return "signup";
        }
        User user = userService.registerNewUser(newUserDTO);
        if (user != null) {
            registerResult = "valid.success.register.user";
            model.addAttribute("result", registerResult);
            return "signup";
        }
        if (userService.isLoginExist(newUserDTO.getLogin())){
            result.rejectValue("login","valid.error.register.login"
                    + ".exist");
            registerResult = "valid.error.register.user";
            model.addAttribute("result", registerResult);
        }
        return "signup";
    }

    @RequestMapping("/signup")
    public String signupPage(Model model) {
        //@ModelAttribute("newUserDTO") NewUserDTO newUserDTO
        NewUserDTO newUserDTO = new NewUserDTO();
        model.addAttribute("newUserDTO", newUserDTO);
        return "signup";
    }

    @RequestMapping(value = "/signup", params = {"addRow"})
    public String addRow(@ModelAttribute("newUserDTO") NewUserDTO newUserDTO
//            , final BindingResult
//            bindingResult, Model model
    ) {
        newUserDTO.getPhones().add(new PhoneDTO());
        //model.addAttribute("newUserDTO", newUserDTO);
        return "signup";
    }

    @RequestMapping(value = "/signup", params = {"removeRow"})
    public String removeRow(
            final NewUserDTO newUserDTO, final BindingResult bindingResult,
            final HttpServletRequest req) {
        final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
        newUserDTO.getPhones().remove(rowId.intValue());
        return "signup";
    }
}
