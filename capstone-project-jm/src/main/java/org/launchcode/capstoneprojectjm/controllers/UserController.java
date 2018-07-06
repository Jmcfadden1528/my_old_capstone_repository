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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

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
    public String processSignUpForm(@ModelAttribute @Valid User newUser, Errors errors, String verify,
                                    Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("user", newUser);
            model.addAttribute("title", "Sign Up");

            if (!newUser.getPassword().equals(verify)) {

                model.addAttribute("message", "Your passwords don't match.");
            }

            return "user/sign-up";
        }
        List<User> sameName = userDao.findByUsername(newUser.getUsername());
        if (!errors.hasErrors() && newUser.getPassword().equals(verify) && sameName.isEmpty()) {
            model.addAttribute("user", newUser);
            userDao.save(newUser);
            return "user/index";
        } else {
            model.addAttribute("user", newUser);
            model.addAttribute("title", "Sign Up");

            if (!newUser.getPassword().equals(verify)) {

                model.addAttribute("message", "Your passwords don't match.");

                if (!sameName.isEmpty()) {
                    model.addAttribute("usernameError", "That username is already taken.");
                }
            }
            if (!sameName.isEmpty()) {
                model.addAttribute("usernameError", "That username is already taken.");
            }
        }
        return "user/sign-up";
    }

    @RequestMapping(value = "login")
    public String displayLoginForm(Model model) {
        model.addAttribute("title", "User Login");
        model.addAttribute(new User());

        return "user/login";
    }


    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String processLoginForm(Model model, @ModelAttribute User user, HttpServletResponse response) {
        List<User> u = userDao.findByUsername(user.getUsername());
        if (u.isEmpty()) {
            model.addAttribute("message", "Invalid username");
            model.addAttribute("title", "User Login");
            return "user/login";
        }
        User loggedIn = u.get(0); //its the first and only username in the list
        if (loggedIn.getPassword().equals(user.getPassword())) {
            Cookie c = new Cookie("user", user.getUsername());
            c.setPath("/");
            response.addCookie(c);
            return "redirect:/event";
        }
        else {
            model.addAttribute("message", "Invalid Password");
            model.addAttribute("title", "User Login");
            return "user/login";
        }
    }


    @RequestMapping(value = "logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie c : cookies) {
                c.setMaxAge(0);
                c.setPath("/");
                response.addCookie(c);
            }
        }
        return "user/login";
    }

}