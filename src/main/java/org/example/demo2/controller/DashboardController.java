package org.example.demo2.controller;

import org.example.demo2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService;

    // Endpoint pentru dashboard
    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        // Obține utilizatorul autentificat
        org.springframework.security.core.userdetails.User currentUser =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username = currentUser.getUsername();

        // Aici poți adăuga mai multe informații despre utilizator
        model.addAttribute("username", username);
        model.addAttribute("user", userService.findByUsername(username)); // Dacă vrei să adaugi informații suplimentare din baza de date
        // Returnează pagina dashboard.html
        return "dashboard";
    }
}
