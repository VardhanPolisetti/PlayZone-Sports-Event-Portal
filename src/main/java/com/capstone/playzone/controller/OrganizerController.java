package com.capstone.playzone.controller;

import com.capstone.playzone.model.PlayerRegistration;
import com.capstone.playzone.model.SportsEvent;
import com.capstone.playzone.model.User;
import com.capstone.playzone.repository.PlayerRegistrationRepository;
import com.capstone.playzone.repository.SportsEventRepository;
import com.capstone.playzone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/organizer")
public class OrganizerController {

    @Autowired
    private SportsEventRepository eventRepository;

    @Autowired
    private PlayerRegistrationRepository registrationRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/home")
    public String organizerHome(Model model, Authentication auth) {
        User user = userRepository.findByUsername(auth.getName());
        List<SportsEvent> events = eventRepository.findByOrganizer(user);
        model.addAttribute("events", events);
        return "organizer/home";
    }

    @GetMapping("/create-event")
    public String showEventForm(Model model) {
        System.out.println("Loading create event form...");
        model.addAttribute("event", new SportsEvent());
        return "organizer/create-event";
    }

    @PostMapping("/create-event")
    public String createEvent(@ModelAttribute SportsEvent event, Authentication auth) {
        User organizer = userRepository.findByUsername(auth.getName());
        event.setOrganizer(organizer);
        eventRepository.save(event);
        return "redirect:/organizer/home";
    }

    @GetMapping("/event/{id}/registrations")
    public String viewRegistrations(@PathVariable Long id, Model model) {
        SportsEvent event = eventRepository.findById(id).orElse(null);
        List<PlayerRegistration> registrations = registrationRepository.findByEvent(event);
        model.addAttribute("registrations", registrations);
        model.addAttribute("event", event);
        return "organizer/view-players";
    }
}

