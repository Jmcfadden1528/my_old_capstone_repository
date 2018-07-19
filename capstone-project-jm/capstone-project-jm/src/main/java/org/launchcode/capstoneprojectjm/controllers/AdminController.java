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
            return "redirect:/user/login";
        }

        Iterable<Event> events = eventDao.findAll();

        model.addAttribute("events", events);
        model.addAttribute("title", "All Events");
        model.addAttribute("currentUser", user);
        return "admin/edit-events";
    }

    @RequestMapping(value = "remove-event", method = RequestMethod.POST)
    public String processRemoveCheeseForm(Model model, @CookieValue(value = "user", defaultValue = "none") String username, @RequestParam int[] ids) {
        User user = userDao.findByUsername(username).get(0);
        model.addAttribute("currentUser", user);
        if (username.equals("none")) {
            return "redirect:user/login";
        }
//        User u = userDao.findByUsername(username).get(0);
//        List<Event> adminEventsEdit = u.getEvents();
        for (int id : ids) {
            Event thisEvent = eventDao.findOne(id);
            thisEvent.getUsers().clear();
            eventDao.delete(eventDao.findOne(id));
        }


        return "redirect:edit-events";
    }

}
