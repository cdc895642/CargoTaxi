package com.cargotaxi.mvc.controller;

import com.cargotaxi.mvc.controller.form.NewUserDTO;
import com.cargotaxi.mvc.controller.form.PhoneDTO;
import com.cargotaxi.mvc.model.Phone;
import com.cargotaxi.mvc.model.User;
import com.cargotaxi.mvc.service.PhoneService;
import com.cargotaxi.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserService<User> userService;
    @Autowired
    MessageSource messageSource;
    @Autowired
    PhoneService phoneService;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String registerUserAccount(@ModelAttribute("newUserDTO") @Valid
                                              NewUserDTO newUserDTO,
                                      BindingResult result, Model model) {
        String registerResult;
        if (result.hasErrors()) {
            registerResult = "valid.error.register.user";
            model.addAttribute("result", registerResult);
            return "signup";
        }
        User user = saveUser(newUserDTO);
        if (user != null) {
            registerResult = "valid.success.register.user";
            model.addAttribute("result", registerResult);
            return "signup";
        }
        checkLogin(newUserDTO, result, model);
        checkEmail(newUserDTO, result, model);
        checkPhones(newUserDTO, result, model);
        return "signup";
    }

    private void checkPhones(NewUserDTO newUserDTO, BindingResult result,
                             Model model) {
        boolean errors=false;
        for (PhoneDTO phoneDTO : newUserDTO.getPhones()) {
            if (phoneService.isPhoneExist(phoneDTO.getNumber())) {
                result.rejectValue("phones", "valid.error.phone.exist",
                        new Object[]{phoneDTO.getNumber()}, "phone is"
                                + " used");
                errors=true;
            }
        }
        if (errors){
            String registerResult = "valid.error.register.user";
            model.addAttribute("result", registerResult);
        }
    }

    private void checkEmail(NewUserDTO newUserDTO, BindingResult result,
                            Model model) {
        Specification<User> condition = (root, query, builder) -> {
            return builder.equal(root.get("email"), newUserDTO.getEmail());
        };
        List<User> users = userService.findAll(condition);
        if (users.size() != 0) {
            result.rejectValue("email", "valid.error.email.exist");
            String registerResult = "valid.error.register.user";
            model.addAttribute("result", registerResult);
        }
    }

    private void checkLogin(NewUserDTO newUserDTO, BindingResult result,
                            Model model) {
        if (userService.isLoginExist(newUserDTO.getLogin())) {
            result.rejectValue("login", "valid.error.register.login"
                    + ".exist");
            String registerResult = "valid.error.register.user";
            model.addAttribute("result", registerResult);
        }
    }

    private User saveUser(NewUserDTO newUserDTO) {
        User user = null;
        try {
            user = userService.registerNewUser(newUserDTO);
        } catch (DataIntegrityViolationException e) {
            //create message for error page
        }
        return user;
    }

    @RequestMapping("/signup")
    public String signupPage(Model model) {
        NewUserDTO newUserDTO = new NewUserDTO();
        model.addAttribute("newUserDTO", newUserDTO);
        return "signup";
    }

    @RequestMapping(value = "/signup", params = {"addPhone"})
    public String addPhone(@ModelAttribute("newUserDTO") NewUserDTO
                                   newUserDTO) {
        newUserDTO.getPhones().add(new PhoneDTO());
        return "signup";
    }

    @RequestMapping(value = "/signup", params = {"removePhone"})
    public String removePhone(
            NewUserDTO newUserDTO, HttpServletRequest req) {
        Integer rowId = Integer.valueOf(req.getParameter("removePhone"));
        newUserDTO.getPhones().remove(rowId.intValue());
        return "signup";
    }
}
