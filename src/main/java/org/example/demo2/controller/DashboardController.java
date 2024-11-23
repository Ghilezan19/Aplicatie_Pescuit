package org.example.demo2.controller;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.example.demo2.service.UserService;

import java.util.List;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        try {
            // Obține utilizatorul autentificat
            UserDetails currentUser = (UserDetails) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();

            String username = currentUser.getUsername();
            String email = currentUser.getUsername(); // Poți extrage email-ul dacă este același cu username-ul sau dintr-o altă sursă

            // Extrage rolurile utilizatorului
            String roles = currentUser.getAuthorities().toString();  // Obține rolurile ca string

            // Adaugă datele utilizatorului în model
            model.addAttribute("email", email);
            model.addAttribute("username", username);
            model.addAttribute("roles", roles);

            return "dashboard";  // Vei avea acces la toate aceste date în dashboard.html
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}