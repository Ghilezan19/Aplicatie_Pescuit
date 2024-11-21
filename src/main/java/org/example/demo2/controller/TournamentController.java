package org.example.demo2.controller;

import org.example.demo2.model.Tournament;
import org.example.demo2.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tournaments")
public class TournamentController {
    @Autowired
    private TournamentService tournamentService;

    @GetMapping
    public String listTournaments(Model model) {
        model.addAttribute("tournaments", tournamentService.findAllTournaments());
        return "tournaments";
    }

    @PostMapping("/save-tournament")
    public String saveTournament(Tournament tournament) {
        tournamentService.saveTournament(tournament);
        return "redirect:/dashboard"; // Redirecționează înapoi la dashboard
    }

    @PostMapping
    public String createTournament(Tournament tournament) {
        tournamentService.saveTournament(tournament);
        return "redirect:/tournaments";
    }
}
