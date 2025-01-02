package org.example.demo2.controller;

import org.example.demo2.model.Competition;
import org.example.demo2.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/competitions")
public class CompetitionController {

    private final CompetitionService competitionService;

    @Autowired
    public CompetitionController(CompetitionService competitionService) {
        this.competitionService = competitionService;
    }

    @GetMapping
    public String listCompetitions(Model model) {
        model.addAttribute("competitions", competitionService.getAllCompetitions());
        return "competition-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("competition", new Competition());
        return "competition-form";
    }

    @PostMapping("/save")
    public String saveCompetition(@ModelAttribute("competition") Competition competition) {
        competitionService.saveCompetition(competition);
        return "redirect:/competitions";
    }
    @GetMapping("/{id}")
    public String viewCompetitionDetails(@PathVariable Long id, Model model) {
        Competition competition = competitionService.getCompetitionById(id);
        model.addAttribute("competition", competition);
        model.addAttribute("participants", competition.getParticipantsSet());
        return "competition-details"; // Numele fișierului Thymeleaf pentru detalii
    }

    @GetMapping("/delete/{id}")
    public String deleteCompetition(@PathVariable Long id) {
        competitionService.deleteCompetition(id);
        return "redirect:/competitions";
    }
    @GetMapping("/details/{id}")
    public String showCompetitionDetails(@PathVariable Long id, Model model) {
        Competition competition = competitionService.getCompetitionById(id);
        model.addAttribute("competition", competition);
        model.addAttribute("participants", competition.getParticipants());
        model.addAttribute("totalKg", competitionService.getTotalKgForCompetition(id)); // Kilograme totale
        return "competition-details";
    }

}
