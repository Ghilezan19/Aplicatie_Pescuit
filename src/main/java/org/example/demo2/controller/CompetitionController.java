package org.example.demo2.controller;
import org.example.demo2.model.Competition;
import org.example.demo2.model.Participant;
import org.example.demo2.repository.CompetitionRepository;
import org.example.demo2.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/competitions")
public class CompetitionController {
    private final CompetitionService competitionService;
    private final CompetitionRepository competitionRepository;
    @Autowired
    public CompetitionController(CompetitionService competitionService, CompetitionRepository competitionRepository) {
        this.competitionService = competitionService;
        this.competitionRepository = competitionRepository;
    }
    @GetMapping("/competitions")
    public String getCompetitions(Model model) {
        List<Competition> competitions = competitionRepository.findAllByOrderByDateAsc();
        System.out.println("Competitions sorted by date:");
        competitions.forEach(c -> System.out.println(c.getName() + " - " + c.getDate()));
        model.addAttribute("competitions", competitions);
        return "competitions";
    }
    @GetMapping
    public String listCompetitions(Model model) {
        List<Competition> competitions = competitionService.getAllCompetitions();
        model.addAttribute("competitions", competitions);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails currentUser = (UserDetails) principal;
            String roles = currentUser.getAuthorities().toString();
            if (roles.contains("PARTICIPANT")) {
                return "competition-list-participant";
            } else if (roles.contains("ORGANIZER")) {
                return "competition-list-organizer";
            } else {
                return "access-denied";
            }
        } else {
            return "error";
        }
    }
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("competition", new Competition());
        return "competition-form";
    }
    @PostMapping("/save")
    public String saveCompetition(@ModelAttribute Competition competition) {
        competitionRepository.save(competition);
        return "redirect:/competitions";
    }
    @GetMapping("/{id}")
    public String viewCompetitionDetails(@PathVariable Long id, Model model) {
        Competition competition = competitionService.getCompetitionById(id);
        competition.getParticipants().sort(Comparator.comparing(Participant::getKg).reversed());
        model.addAttribute("competition", competition);
        model.addAttribute("participants", competition.getParticipants());
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails currentUser = (UserDetails) principal;
            String roles = currentUser.getAuthorities().toString();
            if (roles.contains("PARTICIPANT")) {
                return "competition_details_participant";
            } else if (roles.contains("ORGANIZER")) {
                return "competition_details_organizer";
            } else {
                return "access-denied";
            }
        } else {
            return "error";
        }
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
        model.addAttribute("totalKg", competition.getTotalKg());
        return "competition-detailss";
    }
    @GetMapping("/sorted/{id}")
    public String getSortedParticipants(@PathVariable Long id, Model model) {
        Competition competition = competitionService.getCompetitionWithSortedParticipantsByKg(id);
        model.addAttribute("competition", competition);
        model.addAttribute("participants", competition.getParticipants());
        return "competition-detailss";
    }
    @GetMapping("/recalculate/{id}")
    public String recalculateTotalKg(@PathVariable Long id) {
        competitionService.updateTotalKg(id);
        return "redirect:/competitions/" + id;
    }
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Competition competition = competitionService.getCompetitionById(id);
        model.addAttribute("competition", competition);
        return "competition-edit-form";
    }
    @PostMapping("/update/{id}")
    public String updateCompetition(@PathVariable Long id, @ModelAttribute("competition") Competition updatedCompetition) {
        Competition existingCompetition = competitionService.getCompetitionById(id);
        existingCompetition.setName(updatedCompetition.getName());
        existingCompetition.setLocation(updatedCompetition.getLocation());
        existingCompetition.setDate(updatedCompetition.getDate());
        existingCompetition.setSectors(updatedCompetition.getSectors());
        existingCompetition.setDays(updatedCompetition.getDays());
        existingCompetition.setNumberOfParticipants(updatedCompetition.getNumberOfParticipants());
        competitionService.saveCompetition(existingCompetition);
        return "redirect:/competitions";
    }
    @PostMapping("/create")
    public String createCompetition(@ModelAttribute Competition competition) {
        competitionService.saveCompetition(competition);
        return "redirect:/competitions";
    }
    @GetMapping("/search")
    public String searchCompetition(@RequestParam("code") String code, Model model) {
        Optional<Competition> competitionOptional = competitionService.findByCode(code);
        if (competitionOptional.isPresent()) {
            Competition competition = competitionOptional.get();
            model.addAttribute("competition", competition);
            model.addAttribute("participants", competition.getParticipants());
            return "competition_details_participant.html";
        } else {
            model.addAttribute("error", "Competiția cu codul " + code + " nu a fost găsită.");
            return "participant_dashboard";
        }
    }
}