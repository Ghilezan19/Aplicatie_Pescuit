package org.example.demo2.service;

import org.example.demo2.model.Competition;
import org.example.demo2.repository.CompetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        // Loghează competiția care urmează să fie salvată
        System.out.println("Saving competition: " + competition);

        // Salvează competiția în baza de date
        return competitionRepository.save(competition);
    }

    public Competition getCompetitionById(Long id) {
        return competitionRepository.findById(id).orElseThrow(() -> new RuntimeException("Competition not found"));
    }

    public void deleteCompetition(Long id) {
        competitionRepository.deleteById(id);
    }
}
