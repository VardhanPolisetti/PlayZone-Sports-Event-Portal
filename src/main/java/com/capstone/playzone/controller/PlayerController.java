package com.capstone.playzone.controller;

import com.capstone.playzone.model.PlayerRegistration;
import com.capstone.playzone.model.SportsEvent;
import com.capstone.playzone.model.User;
import com.capstone.playzone.repository.PlayerRegistrationRepository;
import com.capstone.playzone.repository.SportsEventRepository;
import com.capstone.playzone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    private SportsEventRepository eventRepository;

    @Autowired
    private PlayerRegistrationRepository registrationRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/home")
    public String playerHome(Model model, Authentication auth) {
        User player = userRepository.findByUsername(auth.getName());
        List<SportsEvent> events = eventRepository.findByRegistrationDeadlineAfter(LocalDate.now());
        model.addAttribute("events", events);
        model.addAttribute("player", player);
        return "player/home";
    }

    @GetMapping("/register/{eventId}")
    public String showRegisterForm(@PathVariable Long eventId, Model model) {
        model.addAttribute("registration", new PlayerRegistration());
        model.addAttribute("eventId", eventId);
        return "player/register-event";
    }

    @PostMapping("/register/{eventId}")
    public String submitRegistration(@ModelAttribute PlayerRegistration registration,
                                     @PathVariable Long eventId,
                                     Authentication auth) {
        User player = userRepository.findByUsername(auth.getName());
        SportsEvent event = eventRepository.findById(eventId).orElse(null);
        if (event == null) return "redirect:/player/home";

        registration.setPlayer(player);
        registration.setEvent(event);
        registrationRepository.save(registration);
        return "redirect:/player/home";
    }

    @GetMapping("/registered")
    public String viewRegisteredEvents(Model model, Authentication auth) {
        User player = userRepository.findByUsername(auth.getName());
        List<PlayerRegistration> registrations = registrationRepository.findByPlayer(player);
        model.addAttribute("registrations", registrations);
        return "player/registered-events";
    }
}
