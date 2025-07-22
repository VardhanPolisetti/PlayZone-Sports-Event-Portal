package com.capstone.playzone.controller;

import com.capstone.playzone.model.User;
import com.capstone.playzone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/redirect")
    public String redirectByRole(Authentication auth) {
        User user = userRepository.findByUsername(auth.getName());
        if (user.getRole().equals("ORGANIZER")) {
            return "redirect:/organizer/home";
        } else {
            return "redirect:/player/home";
        }
    }
}
