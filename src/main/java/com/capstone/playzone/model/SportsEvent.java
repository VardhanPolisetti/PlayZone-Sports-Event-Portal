package com.capstone.playzone.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class SportsEvent {
    @Id @GeneratedValue
    private Long id;
    private String sportType;
    private String place;
    private LocalDateTime dateTime;
    private String prize1;
    private String prize2;
    private String prize3;
    private LocalDate registrationDeadline;

    @ManyToOne
    private User organizer;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<PlayerRegistration> registrations;

    // === Getters and Setters ===

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSportType() {
        return sportType;
    }

    public void setSportType(String sportType) {
        this.sportType = sportType;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getPrize1() {
        return prize1;
    }

    public void setPrize1(String prize1) {
        this.prize1 = prize1;
    }

    public String getPrize2() {
        return prize2;
    }

    public void setPrize2(String prize2) {
        this.prize2 = prize2;
    }

    public String getPrize3() {
        return prize3;
    }

    public void setPrize3(String prize3) {
        this.prize3 = prize3;
    }

    public LocalDate getRegistrationDeadline() {
        return registrationDeadline;
    }

    public void setRegistrationDeadline(LocalDate registrationDeadline) {
        this.registrationDeadline = registrationDeadline;
    }

    public User getOrganizer() {
        return organizer;
    }

    public void setOrganizer(User organizer) {
        this.organizer = organizer;
    }

    public List<PlayerRegistration> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(List<PlayerRegistration> registrations) {
        this.registrations = registrations;
    }
}
