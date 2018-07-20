package org.launchcode.capstoneprojectjm.controllers;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;




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
    @RequestMapping(value = "edit-events")
    public String adminConsole(Model model, @CookieValue(value = "user", defaultValue = "none") String username) {

        if (username.equals("none")) {
            return "redirect:/user/login";
        }
        User user = userDao.findByUsername(username).get(0);
        if (user.getAdmin() != true) {
            return "redirect:/user/login"; //TODO: change to access denied page
        }

        Iterable<Event> events = eventDao.findAll();

        model.addAttribute("events", events);
        model.addAttribute("title", "All Events");
        model.addAttribute("currentUser", user);
        return "admin/edit-events";
    }

    //TODO: No idea what the fuck is going on and why I can't get this to work. Need to fix.
    @RequestMapping(value = "remove-event", method = RequestMethod.POST)
    public String processRemoveEventAdmin(Model model, @CookieValue(value = "user", defaultValue = "none") String username, @RequestParam int[] ids) {
        User user = userDao.findByUsername(username).get(0);

        if (username.equals("none")) {
            return "redirect:user/login";
        }

        for (int id : ids) {
            Event event = eventDao.findOne(id);
            System.out.println(event.getUsers());
            event.clearUsers();
            System.out.println(event.getUsers());

        }
        for (int id : ids) {
            eventDao.delete(id);
        }

        model.addAttribute("currentUser", user);
        model.addAttribute("title", "Not sure");
        Iterable<Event> events = eventDao.findAll();
        model.addAttribute("events", events);
        return "admin/edit-events";
    }

}
