package org.example.demo2.controller;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        try {
            // Obține utilizatorul autentificat din SecurityContext
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (principal instanceof UserDetails) {
                UserDetails currentUser = (UserDetails) principal;
                String username = currentUser.getUsername();
                String email = currentUser.getUsername(); // Presupunem că email-ul este același cu username-ul
                String roles = currentUser.getAuthorities().toString(); // Extragem rolurile

                // Adaugă aceste informații în model
                model.addAttribute("username", username);
                model.addAttribute("email", email);
                model.addAttribute("roles", roles);

                // Verifică rolurile utilizatorului și redirecționează către pagina corespunzătoare
                if (roles.contains("PARTICIPANT")) {
                    return "participant_dashboard"; // Pagina pentru participant
                } else if (roles.contains("ORGANIZER")) {
                    return "organizator_dashboard"; // Pagina pentru organizator
                } else {
                    return "access-denied"; // Pagina pentru acces interzis
                }
            } else {
                throw new RuntimeException("Utilizatorul nu este autentificat corect");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error"; // Afișează o pagină de eroare în caz de problemă
        }
    }
}
