package org.example.demo2.service;
import org.example.demo2.model.Tournament;
import org.example.demo2.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TournamentService {
    @Autowired
    private TournamentRepository tournamentRepository;

    public Tournament saveTournament(Tournament tournament) {
        return tournamentRepository.save(tournament);
    }

    public List<Tournament> findAllTournaments() {
        return tournamentRepository.findAll();
    }

    public void deleteTournament(Long id) {
        tournamentRepository.deleteById(id);
    }
}
