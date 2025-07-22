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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String showRegisterForm(@PathVariable Long eventId,
                                   Model model,
                                   Authentication auth,
                                   RedirectAttributes redirectAttributes) {
        User player = userRepository.findByUsername(auth.getName());
        SportsEvent event = eventRepository.findById(eventId).orElse(null);
        if (event == null) return "redirect:/player/home";

        // Prevent showing the form if already registered
        boolean alreadyRegistered = registrationRepository.existsByPlayerAndEvent(player, event);
        if (alreadyRegistered) {
            redirectAttributes.addFlashAttribute("error", "You have already registered for this event.");
            return "redirect:/player/home";
        }

        PlayerRegistration registration = new PlayerRegistration();
        registration.setGender("Male"); // default gender if you want
        model.addAttribute("registration", registration);
        model.addAttribute("eventId", eventId);
        return "player/register-event";
    }


    @PostMapping("/register/{eventId}")
    public String submitRegistration(@ModelAttribute PlayerRegistration registration,
                                     @PathVariable Long eventId,
                                     Authentication auth,
                                     RedirectAttributes redirectAttributes) {
        User player = userRepository.findByUsername(auth.getName());
        SportsEvent event = eventRepository.findById(eventId).orElse(null);
        if (event == null) return "redirect:/player/home";

        // Prevent duplicate registration
        boolean alreadyRegistered = registrationRepository.existsByPlayerAndEvent(player, event);
        if (alreadyRegistered) {
            redirectAttributes.addFlashAttribute("error", "You have already registered for this event.");
            return "redirect:/player/home";
        }

        registration.setPlayer(player);
        registration.setEvent(event);
        registrationRepository.save(registration);
        redirectAttributes.addFlashAttribute("success", "Registered successfully!");
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
