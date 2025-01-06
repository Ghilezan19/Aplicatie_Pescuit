package org.example.demo2.controller;
import org.example.demo2.model.Participant;
import org.example.demo2.model.Competition;
import org.example.demo2.service.ParticipantService;
import org.example.demo2.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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
        return "add-participant";
    }
    @PostMapping("/save")
    public String saveParticipant(@ModelAttribute Participant participant,
                                  @RequestParam("competitionId") Long competitionId,
                                  Model model) {
        Competition competition = competitionService.getCompetitionById(competitionId);
        participant.addCompetition(competition);
        participantService.saveParticipant(participant);
        model.addAttribute("message", "Participantul a fost adăugat cu succes!");
        model.addAttribute("participant", new Participant());
        model.addAttribute("competitions", competitionService.getAllCompetitions());
        return "add-participant";
    }
    @PostMapping("/addKg/{id}")
    @ResponseBody
    public String addKg(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        double newKg = Double.parseDouble(payload.get("kg"));
        Participant participant = participantService.getParticipantById(id);
        participant.setKg(participant.getKg() + newKg);
        participantService.saveParticipant(participant);
        participant.getCompetitions().forEach(competition -> {
            competitionService.updateTotalKg(competition.getId());
        });
        return "Kilogramele au fost adăugate cu succes!";
    }
    @GetMapping("/details/{competitionId}")
    public String showCompetitionDetails(@PathVariable Long competitionId, Model model) {
        Competition competition = competitionService.getCompetitionById(competitionId);
        List<Participant> sortedParticipants = competition.getParticipants()
                .stream()
                .sorted(Comparator.comparingDouble(Participant::getKg).reversed())
                .toList();
        model.addAttribute("competition", competition);
        model.addAttribute("participants", sortedParticipants);
        model.addAttribute("totalKg", sortedParticipants.stream().mapToDouble(Participant::getKg).sum());
        return "competition-detailss";
    }
    @GetMapping("/edit/{id}")
    public String showEditParticipantForm(@PathVariable Long id, Model model) {
        Participant participant = participantService.getParticipantById(id);
        model.addAttribute("participant", participant);
        return "edit-participant";
    }
    @GetMapping("/competition-list-organizer")
    public String showCompetitionList() {
        return "competition-list-organizer";
    }
    @PostMapping("/update/{id}")
    public String updateParticipant(@PathVariable Long id, @ModelAttribute Participant updatedParticipant) {
        Participant existingParticipant = participantService.getParticipantById(id);
        existingParticipant.setName(updatedParticipant.getName());
        existingParticipant.setKg(updatedParticipant.getKg());
        participantService.saveParticipant(existingParticipant);
        Long competitionId = existingParticipant.getCompetitions().iterator().next().getId();
        return "redirect:/competitions/" + competitionId;
    }
    @PostMapping("/delete/{id}")
    public String deleteParticipant(@PathVariable Long id) {
        Participant participant = participantService.getParticipantById(id);
        Long competitionId = participant.getCompetitions().iterator().next().getId();
        participantService.deleteParticipant(id);
        return "redirect:/competitions/" + competitionId;
    }
}
