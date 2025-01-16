package org.example.demo2.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double kg;
    private String name;
    private int points;
    private String sector; // Nou
    private String stand;  // Nou
    @ManyToMany
    @JoinTable(
            name = "participant_competition",
            joinColumns = @JoinColumn(name = "participant_id"),
            inverseJoinColumns = @JoinColumn(name = "competition_id")
    )
    private Set<Competition> competitions = new HashSet<>();
    public Set<Competition> getCompetitions() {
        return competitions;
    }
    public void setCompetitions(Set<Competition> competitions) {
        this.competitions = competitions;
    }
    public void addCompetition(Competition competition) {
        this.competitions.add(competition);
    }
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
    public double getKg() {
        return kg;
    }
    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }
    public String getStand() {
        return stand;
    }

    public void setStand(String stand) {
        this.stand = stand;
    }
    public void setKg(double kg) {
        this.kg = kg;
    }
}
