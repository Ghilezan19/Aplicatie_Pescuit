package org.example.demo2.model;

import jakarta.persistence.*;

@Entity
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String location;
    private String date;
    private String rules;
    private Double entryFee;
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
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getRules() {
        return rules;
    }
    public void setRules(String rules) {
        this.rules = rules;
    }
    public Double getEntryFee() {
        return entryFee;
    }
    public void setEntryFee(Double entryFee) {
        this.entryFee = entryFee;
    }
}
