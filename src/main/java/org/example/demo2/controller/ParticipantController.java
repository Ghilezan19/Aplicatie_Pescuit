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
    public String showAddParticipantForm(Model model) {
        model.addAttribute("competitions", competitionService.getAllCompetitions());
        model.addAttribute("participant", new Participant());
        return "add-participant";
    }

    @PostMapping("/add")
    public String addParticipant(@ModelAttribute Participant participant, @RequestParam("competitionId") Long competitionId) {
        Competition competition = competitionService.getCompetitionById(competitionId);
        participant.getCompetitions().add(competition); // Asociem competiția cu participantul
        participantService.saveParticipant(participant);
        return "redirect:/dashboard"; // Redirecționare după salvare
    }
}
