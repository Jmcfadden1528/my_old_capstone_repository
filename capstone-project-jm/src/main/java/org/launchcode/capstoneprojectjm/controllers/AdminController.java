package org.launchcode.capstoneprojectjm.controllers;

import org.launchcode.capstoneprojectjm.models.Data.CategoryDao;
import org.launchcode.capstoneprojectjm.models.Data.EventDao;
import org.launchcode.capstoneprojectjm.models.Data.UserDao;
import org.launchcode.capstoneprojectjm.models.Event;
import org.launchcode.capstoneprojectjm.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;




@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private EventDao eventDao; // Allows us to interact with database

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private UserDao userDao;

    //    TODO: display in chronological order
    @RequestMapping(value = "")
    public String index(Model model, @CookieValue(value = "user", defaultValue = "none") String username) {

        if (username.equals("none")) {
            return "redirect:/user/login";
        }
        User user = userDao.findByUsername(username).get(0);
        if (user.getAdmin() != true) {
            return "redirect:/user/login";
        }

        Iterable<Event> events = eventDao.findAll();

        model.addAttribute("events", events);
        model.addAttribute("title", "All Events");

        return "event/index";
    }
}
