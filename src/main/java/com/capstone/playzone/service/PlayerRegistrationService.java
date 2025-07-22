package com.capstone.playzone.service;

import com.capstone.playzone.model.PlayerRegistration;
import com.capstone.playzone.model.SportsEvent;
import com.capstone.playzone.model.User;
import com.capstone.playzone.repository.PlayerRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerRegistrationService {

    @Autowired
    private PlayerRegistrationRepository registrationRepository;

    public PlayerRegistration registerPlayer(PlayerRegistration registration) {
        return registrationRepository.save(registration);
    }

    public List<PlayerRegistration> getRegistrationsForEvent(SportsEvent event) {
        return registrationRepository.findByEvent(event);
    }

    public List<PlayerRegistration> getRegistrationsByPlayer(User player) {
        return registrationRepository.findByPlayer(player);
    }

    public boolean isAlreadyRegistered(User player, SportsEvent event) {
        return registrationRepository.existsByPlayerAndEvent(player, event);
    }
}
