package com.capstone.playzone.repository;

import com.capstone.playzone.model.PlayerRegistration;
import com.capstone.playzone.model.SportsEvent;
import com.capstone.playzone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRegistrationRepository extends JpaRepository<PlayerRegistration, Long> {
    List<PlayerRegistration> findByEvent(SportsEvent event);
    List<PlayerRegistration> findByPlayer(User player);
    boolean existsByPlayerAndEvent(User player, SportsEvent event);
}
