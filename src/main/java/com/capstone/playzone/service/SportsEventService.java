package com.capstone.playzone.service;

import com.capstone.playzone.model.SportsEvent;
import com.capstone.playzone.model.User;
import com.capstone.playzone.repository.SportsEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SportsEventService {

    @Autowired
    private SportsEventRepository eventRepository;

    public List<SportsEvent> getUpcomingEvents() {
        return eventRepository.findByRegistrationDeadlineAfter(LocalDate.now());
    }

    public List<SportsEvent> getEventsByOrganizer(User organizer) {
        return eventRepository.findByOrganizer(organizer);
    }

    public SportsEvent saveEvent(SportsEvent event) {
        return eventRepository.save(event);
    }
}
