package org.example.demo2.controller;

import org.example.demo2.model.Catch;
import org.example.demo2.service.CatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/catches")
public class CatchController {

    @Autowired
    private CatchService catchService;

    // Metoda pentru a salva captura
    @PostMapping("/save-catch")
    public String saveCatch(Catch catchData) {
        catchService.saveCatch(catchData);
        return "redirect:/dashboard"; // Redirecționează la dashboard
    }

    // Metoda pentru a lista toate capturile
    @GetMapping
    public String listCatches(Model model) {
        model.addAttribute("catches", catchService.findByTournamentId(1L)); // Exemplu pentru turneu ID 1
        return "catches"; // Afișează pagina "catches.html"
    }
}
