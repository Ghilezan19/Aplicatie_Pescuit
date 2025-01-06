package org.example.demo2.service;

import org.example.demo2.model.Catch;
import org.example.demo2.repository.CatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatchService {
    @Autowired
    private CatchRepository catchRepository;
    public Catch saveCatch(Catch fishCatch) {
        return catchRepository.save(fishCatch);
    }
    public List<Catch> findByTournamentId(Long tournamentId) {
        return catchRepository.findAll().stream()
                .filter(c -> c.getTournament().getId().equals(tournamentId))
                .toList();
    }
    public void validateCatch(Long id) {
        Catch fishCatch = catchRepository.findById(id).orElseThrow();
        fishCatch.setValidated(true);
        catchRepository.save(fishCatch);
    }
}
