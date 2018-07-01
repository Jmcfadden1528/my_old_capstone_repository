package org.launchcode.capstoneprojectjm.controllers;

import org.launchcode.capstoneprojectjm.models.Data.CategoryDao;
import org.launchcode.capstoneprojectjm.models.Data.EventDao;
import org.launchcode.capstoneprojectjm.models.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("event")
public class EventController {



    @Autowired
    private EventDao eventDao; // Allows us to interact with database

    @Autowired
    private CategoryDao categoryDao;

    @RequestMapping(value = "")
    public String index(Model model) {
        model.addAttribute("events", eventDao.findAll());
        model.addAttribute("title", "All Events");

        return "event/index";
    }


    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddEventForm(Model model) {
        model.addAttribute("title", "Add Event");
        model.addAttribute(new Event());

        return "event/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddEventForm(@ModelAttribute @Valid Event newEvent,
                                       Errors errors, Model model) {
        System.out.println("works to here!");
        /*if (errors.hasErrors()) {
            model.addAttribute("title", "Add Event");
            return "event/add";
        }*/
        eventDao.save(newEvent);

        return "redirect:";
    }

    @RequestMapping(value = "event-view/{id}")
    public String displayEvent(Model model, @PathVariable int id) {
        Event theEvent = eventDao.findOne(id);
        model.addAttribute("theEvent", theEvent);

        return "event/event-view";
    }

}