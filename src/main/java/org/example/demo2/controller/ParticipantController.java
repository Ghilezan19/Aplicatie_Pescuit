package org.example.demo2.controller;

import org.example.demo2.model.Participant;
import org.example.demo2.model.Competition;
import org.example.demo2.service.ParticipantService;
import org.example.demo2.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping("/participants")
public class ParticipantController {

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private CompetitionService competitionService;

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("participant", new Participant());
        model.addAttribute("competitions", competitionService.getAllCompetitions());
        return "add-participant"; // Numele fișierului Thymeleaf
    }

    @PostMapping("/save")
    public String saveParticipant(@ModelAttribute Participant participant, @RequestParam("competitionId") Long competitionId, Model model) {
        Competition competition = competitionService.getCompetitionById(competitionId);
        participant.addCompetition(competition);
        participantService.saveParticipant(participant);

        // Adaugă un mesaj de succes
        model.addAttribute("message", "Participantul a fost adăugat cu succes!");

        return "redirect:/dashboard";  // Sau o altă pagină de vizualizare
    }


}
