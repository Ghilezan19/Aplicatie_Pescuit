package org.example.demo2.controller;

import org.example.demo2.model.Competition;
import org.example.demo2.model.Participant;
import org.example.demo2.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

        // Sortează participanții după kilograme, de exemplu, în ordine descrescătoare
        competition.getParticipants().sort(Comparator.comparing(Participant::getKg).reversed());

        model.addAttribute("competition", competition);
        model.addAttribute("participants", competition.getParticipants());
        return "competition-details";
    }


    @GetMapping("/delete/{id}")
    public String deleteCompetition(@PathVariable Long id) {
        competitionService.deleteCompetition(id);
        return "redirect:/competitions";
    }
    @GetMapping("/details/{id}")
    public String showCompetitionDetails(@PathVariable Long id, Model model) {
        Competition competition = competitionService.getCompetitionById(id);
        double totalKg = competitionService.getTotalKgForCompetition(id); // Calculează totalul
        model.addAttribute("competition", competition);
        model.addAttribute("participants", competition.getParticipants());
        model.addAttribute("totalKg", totalKg); // Adaugă greutatea totală în model
        return "competition-details";
    }


    @GetMapping("/sorted/{id}")
    public String getSortedParticipants(@PathVariable Long id, Model model) {
        Competition competition = competitionService.getCompetitionWithSortedParticipantsByKg(id);

        model.addAttribute("competition", competition);
        model.addAttribute("participants", competition.getParticipants());
        return "competition-details";
    }

    @GetMapping("/recalculate/{id}")
    public String recalculateTotalKg(@PathVariable Long id) {
        competitionService.updateTotalKg(id); // Recalculează greutatea totală
        return "redirect:/competitions/" + id;
    }

}
