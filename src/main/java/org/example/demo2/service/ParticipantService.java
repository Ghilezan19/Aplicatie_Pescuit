package org.example.demo2.service;

import org.example.demo2.model.Participant;
import org.example.demo2.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParticipantService {

    private final ParticipantRepository participantRepository;

    @Autowired
    public ParticipantService(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    public void saveParticipant(Participant participant) {
        participantRepository.save(participant);  // Salvează participantul, inclusiv relațiile many-to-many
    }
    public Participant getParticipantById(Long id) {
        return participantRepository.findById(id).orElseThrow(() -> new RuntimeException("Participant not found"));
    }

}
