package org.launchcode.capstoneprojectjm.controllers;


import org.launchcode.capstoneprojectjm.models.Data.UserDao;
import org.launchcode.capstoneprojectjm.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "user")
public class UserController {


    @Autowired
    private UserDao userDao; // Allows us to interact with database

    @RequestMapping(value = "sign-up")
    public String signUpForm(Model model) {
        model.addAttribute("title", "Sign Up");
        model.addAttribute("user", new User());

        return "user/sign-up";
    }

    @RequestMapping(value = "sign-up", method = RequestMethod.POST)
    public String signUpForm(@ModelAttribute @Valid User newUser,
                             Errors errors, Model model) {

        userDao.save(newUser);

        return "redirect:";

    }
}