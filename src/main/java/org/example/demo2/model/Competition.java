package org.example.demo2.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
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
    @Column(name = "participants")
    private Integer numberOfParticipants=0;
    @Column(unique = true, nullable = false)
    private String code;

    @ElementCollection
    private List<String> days; // Ziua 1, Ziua 2, etc.
    private double totalKg; // Noul câmp pentru greutatea totală

    // Relație many-to-many cu Participant
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name = "participant_competition",
            joinColumns = @JoinColumn(name = "competition_id"),
            inverseJoinColumns = @JoinColumn(name = "participant_id")
    )
    private List<Participant> participants = new ArrayList<>();
    public Competition() {
        this.code = generateDefaultCode(); // Setează un cod implicit
    }
    private String generateDefaultCode() {
        return "TEMP-" + System.currentTimeMillis(); // Cod temporar
    }
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

    public List<String> getDays() {
        return days;
    }

    public void setDays(List<String> days) {
        this.days = days;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }
    public double getTotalKg() {
        return totalKg;
    }

    public void setTotalKg(double totalKg) {
        this.totalKg = totalKg;
    }

    public void updateTotalKg() {
        this.totalKg = participants.stream()
                .mapToDouble(Participant::getKg)
                .sum();
    }

    public Integer getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setNumberOfParticipants(Integer numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
