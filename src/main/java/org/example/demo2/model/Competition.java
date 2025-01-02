package org.example.demo2.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Entity
public class Competition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
    private LocalDate date;

    private Integer sectors;
    private Integer participants;

    @ElementCollection
    private List<String> days; // Ziua 1, Ziua 2, etc.

    // Relație many-to-many cu Participant
    @ManyToMany
    @JoinTable(
            name = "participant_competition", // Numele tabelului intermediar
            joinColumns = @JoinColumn(name = "competition_id"),
            inverseJoinColumns = @JoinColumn(name = "participant_id")
    )
    private Set<Participant> participantsSet = new HashSet<>();  // Corect: Set<Participant>

    // Getters și Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getSectors() {
        return sectors;
    }

    public void setSectors(Integer sectors) {
        this.sectors = sectors;
    }

    public Integer getParticipants() {
        return participants;
    }

    public void setParticipants(Integer participants) {
        this.participants = participants;
    }

    public List<String> getDays() {
        return days;
    }

    public void setDays(List<String> days) {
        this.days = days;
    }

    public Set<Participant> getParticipantsSet() {
        return participantsSet; // Asigură-te că ai această metodă
    }

    public void setParticipantsSet(Set<Participant> participantsSet) {
        this.participantsSet = participantsSet;
    }

}
