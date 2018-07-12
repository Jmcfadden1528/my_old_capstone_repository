package org.launchcode.capstoneprojectjm.controllers;

import org.launchcode.capstoneprojectjm.models.Data.CategoryDao;
import org.launchcode.capstoneprojectjm.models.Data.EventDao;
import org.launchcode.capstoneprojectjm.models.Data.UserDao;
import org.launchcode.capstoneprojectjm.models.Event;
import org.launchcode.capstoneprojectjm.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import sun.misc.IOUtils;

import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.launchcode.capstoneprojectjm.controllers.FileUploadController.uploadDirectory;


@Controller
@RequestMapping("event")
public class EventController {


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

        Iterable<Event> events = eventDao.findAll();

        model.addAttribute("events", events);
        model.addAttribute("title", "All Events");

        return "event/index";
    }


    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddEventForm(Model model, @CookieValue(value = "user", defaultValue = "none") String username) {

        if (username.equals("none")) {
            return "redirect:/user/login";
        }

        model.addAttribute("title", "Add Event");
        model.addAttribute(new Event());
        return "event/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddEventForm(@ModelAttribute @Valid Event newEvent, @RequestParam("image") File image,
            Errors errors, Model model, @CookieValue(value = "user", defaultValue = "none") String username) throws IOException {

        if (username.equals("none")) {
            return "redirect:/user/login";
        }
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Event");
            model.addAttribute("event", newEvent);
            return "event/add";
        }
        User u = userDao.findByUsername(username).get(0);
        StringBuilder fileNames = new StringBuilder();
            Path fileNameAndPath = Paths.get(uploadDirectory, image.getPath());


        byte[] bFile = new byte[(int) image.length()];
        //image.toPath didn't work
        //byte[] array = Files.readAllBytes(new File("/path/to/file").toPath()) didn't work -- got absolute path i think
        //byte[] bFile = Files.readAllBytes(Paths.get("sunrise.png")) didn't work -- got just file name (also didn't work with / in front)


            try {
                Files.write(fileNameAndPath, bFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

        model.addAttribute("msg", "Successfully uploaded files "+fileNames.toString());
        return "event/uploadstatusview";
    }



    @RequestMapping(value = "event-view/{id}")
    public String displayEvent(Model model, @PathVariable int id) throws IOException {
        Event theEvent = eventDao.findOne(id);
        List<User> usersAttending = theEvent.getUsers();


        model.addAttribute("users", usersAttending);
        model.addAttribute("theEvent", theEvent);

        return "event/event-view";
    }

    @RequestMapping(value = "event-added/{id}", method = RequestMethod.GET)
    public String attendEventSubmission(Model model, @PathVariable int id, @CookieValue(value = "user", defaultValue = "none") String username) {

        if (username.equals("none")) {
            return "redirect:/user/login";
        }

        User u = userDao.findByUsername(username).get(0);
        Event addedEvent = eventDao.findOne(id);
        for (Event userEvent : u.getEvents()) {
            if (userEvent.getId() == addedEvent.getId()) {
                Event theEvent = eventDao.findOne(id);
                model.addAttribute("theEvent", theEvent);
                model.addAttribute("duplicate_event_error", "This event has already been added.");
                return "event/event-view";
            }
        }

        u.addEvent(addedEvent);
        userDao.save(u);
        return "event/event-added";
    }

    @RequestMapping(value = "my-events")
    String displayMyEvents(Model model, @CookieValue(value = "user", defaultValue = "none") String username) {
        if (username.equals("none")) {
            return "redirect:/user/login";
        }
        User u = userDao.findByUsername(username).get(0);
        List<Event> userEvents = u.getEvents();
        model.addAttribute("events", userEvents);


        return "event/my-events";
    }

    @RequestMapping(value = "remove-event", method = RequestMethod.GET)
    public String displayRemoveEventForm(Model model, @CookieValue(value = "user", defaultValue = "none") String username) {

        if (username.equals("none")) {
            return "redirect:/user/login";
        }

        User u = userDao.findByUsername(username).get(0);

        model.addAttribute("events", u.getEvents());
        model.addAttribute("title", "Remove Events");
        return "event/remove-event";
    }

    //TODO: fix remove process form.
    @RequestMapping(value = "remove-event", method = RequestMethod.POST)
    public String processRemoveCheeseForm(@CookieValue(value = "user", defaultValue = "none") String username, @RequestParam int[] ids) {
        User u = userDao.findByUsername(username).get(0);
        List<Event> userEventsEdit = u.getEvents();
        for (int id : ids) {
            userEventsEdit.remove(eventDao.findOne(id));
        }

        u.setEvents(userEventsEdit);
        userDao.save(u);

        return "redirect:";
    }
}



