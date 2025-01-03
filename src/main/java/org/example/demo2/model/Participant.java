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

    // Relație Many-to-Many cu Competition
    @ManyToMany
    @JoinTable(
            name = "participant_competition",
            joinColumns = @JoinColumn(name = "participant_id"),
            inverseJoinColumns = @JoinColumn(name = "competition_id")
    )
    private Set<Competition> competitions = new HashSet<>();

    // Getter și Setter pentru competitions
    public Set<Competition> getCompetitions() {
        return competitions;
    }

    public void setCompetitions(Set<Competition> competitions) {
        this.competitions = competitions;
    }

    // Metoda addCompetition pentru a adăuga competiții la participant
    public void addCompetition(Competition competition) {
        this.competitions.add(competition);  // Adaugă competiția în setul de competiții
    }

    // Getter și Setter pentru id și name
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

    public void setKg(double kg) {
        this.kg = kg;
    }


}
