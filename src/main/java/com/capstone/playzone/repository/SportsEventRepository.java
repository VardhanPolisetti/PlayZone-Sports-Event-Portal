package com.capstone.playzone.repository;

import com.capstone.playzone.model.SportsEvent;
import com.capstone.playzone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SportsEventRepository extends JpaRepository<SportsEvent, Long> {
    List<SportsEvent> findByOrganizer(User user);
    List<SportsEvent> findByRegistrationDeadlineAfter(LocalDate date);
}
