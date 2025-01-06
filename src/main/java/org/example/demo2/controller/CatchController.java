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
    @PostMapping("/save-catch")
    public String saveCatch(Catch catchData) {
        catchService.saveCatch(catchData);
        return "redirect:/dashboard";
    }
    @GetMapping
    public String listCatches(Model model) {
        model.addAttribute("catches", catchService.findByTournamentId(1L));
        return "catches";
    }
}
