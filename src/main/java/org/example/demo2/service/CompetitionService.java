package org.example.demo2.service;

import org.example.demo2.model.Participant;
import org.example.demo2.model.Competition;
import org.example.demo2.repository.CompetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class CompetitionService {
    private final CompetitionRepository competitionRepository;
    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int CODE_LENGTH = 6;
    private final SecureRandom random = new SecureRandom();
    @Autowired
    public CompetitionService(CompetitionRepository competitionRepository) {
        this.competitionRepository = competitionRepository;
    }
    public List<Competition> getAllCompetitions() {
        return competitionRepository.findAll();
    }
    public Competition saveCompetition(Competition competition) {
        if (competition.getName() == null || competition.getName().isEmpty()) {
            throw new IllegalArgumentException("Numele competiției nu poate fi gol!");
        }
        if (competition.getParticipants() == null || !competition.getParticipants().isEmpty()) {
            competition.setParticipants(new ArrayList<>());
        }
        if (competition.getCode() == null || competition.getCode().isEmpty()) {
            competition.setCode(generateUniqueCode());
        }
        competition.updateTotalKg();
        return competitionRepository.save(competition);
    }
    public Competition getCompetitionById(Long id) {
        Competition competition = competitionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Competition not found"));
        competition.updateTotalKg();
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
        Competition competition = competitionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Competition not found"));
        competition.getParticipants().sort(Comparator.comparing(Participant::getKg).reversed());
        return competition;
    }
    public void updateTotalKg(Long competitionId) {
        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new IllegalArgumentException("Competition not found"));

        competition.updateTotalKg();
        competitionRepository.save(competition);
    }
    public String generateUniqueCode() {
        String code;
        do {
            StringBuilder sb = new StringBuilder(CODE_LENGTH);
            for (int i = 0; i < CODE_LENGTH; i++) {
                sb.append(LETTERS.charAt(random.nextInt(LETTERS.length())));
            }
            code = sb.toString();
        } while (competitionRepository.existsByCode(code));
        return code;
    }

    public Competition getCompetitionByCode(String code) {
        return competitionRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Competition not found for code: " + code));
    }
    public Optional<Competition> findByCode(String code) {
        return competitionRepository.findByCode(code);
    }
}
