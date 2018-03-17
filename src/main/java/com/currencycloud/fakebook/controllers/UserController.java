package com.currencycloud.fakebook.controllers;

import com.currencycloud.fakebook.entity.User;
import com.currencycloud.fakebook.model.UserForm;
import com.currencycloud.fakebook.service.UserService;
import com.currencycloud.fakebook.utils.ModelEntityHelper;
import com.currencycloud.fakebook.validator.UserFormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by lekanomotayo on 14/03/2018.
 */

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentStatusController.class);

    @Autowired
    private ModelEntityHelper modelEntityHelper;
    @Autowired
    private UserService userService;
    @Autowired
    private UserFormValidator userValidator;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") UserForm userForm, BindingResult bindingResult, Model model) {

        //Validate registration form
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        //Creaet registration
        User user = modelEntityHelper.convertToEntity(userForm);
        user = userService.save(user);

        // Log in user after success save
        userService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());
        return "redirect:/home";
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        return "home";
    }

    @RequestMapping(value = {"/logout"}, method = RequestMethod.GET)
    public String logout(Model model) {
        return "home";
    }



}
