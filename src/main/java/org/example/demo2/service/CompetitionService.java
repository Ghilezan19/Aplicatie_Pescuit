package org.example.demo2.service;
import org.example.demo2.model.Participant;

import org.example.demo2.model.Competition;
import org.example.demo2.repository.CompetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CompetitionService {

    private final CompetitionRepository competitionRepository;

    @Autowired
    public CompetitionService(CompetitionRepository competitionRepository) {
        this.competitionRepository = competitionRepository;
    }

    public List<Competition> getAllCompetitions() {
        return competitionRepository.findAll();
    }

    public Competition saveCompetition(Competition competition) {
        competition.updateTotalKg(); // Recalculează greutatea totală
        return competitionRepository.save(competition);
    }

    public Competition getCompetitionById(Long id) {
        Competition competition = competitionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Competition not found"));
        competition.updateTotalKg(); // Recalculează greutatea totală
        return competition;
    }

    public void deleteCompetition(Long id) {
        competitionRepository.deleteById(id);
    }
    public double getTotalKgForCompetition(Long competitionId) {
        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new IllegalArgumentException("Competition not found"));

        return competition.getParticipants().stream()
                .mapToDouble(Participant::getKg)
                .sum();
    }

    public Competition getCompetitionWithSortedParticipantsByKg(Long id) {
        Competition competition = competitionRepository.findById(id).orElseThrow(() -> new RuntimeException("Competition not found"));

        // Sortează participanții în serviciu
        competition.getParticipants().sort(Comparator.comparing(Participant::getKg).reversed());

        return competition;
    }

    public void updateTotalKg(Long competitionId) {
        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new IllegalArgumentException("Competition not found"));

        competition.updateTotalKg(); // Recalculează greutatea totală
        competitionRepository.save(competition);
    }


}
