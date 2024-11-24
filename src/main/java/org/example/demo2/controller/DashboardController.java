package org.example.demo2.controller;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
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

            // Verifică dacă principalul este un obiect UserDetails
            if (principal instanceof UserDetails) {
                UserDetails currentUser = (UserDetails) principal;
                String username = currentUser.getUsername();
                String email = currentUser.getUsername(); // Presupunem că email-ul este același cu username-ul
                String roles = currentUser.getAuthorities().toString(); // Extragem rolurile

                // Adaugă aceste informații în model
                model.addAttribute("username", username);
                model.addAttribute("email", email);
                model.addAttribute("roles", roles);
            } else {
                // Dacă principalul nu este de tip UserDetails, lansează o excepție
                throw new RuntimeException("Utilizatorul nu este autentificat corect");
            }

            return "dashboard";  // Vei avea acces la aceste date în dashboard.html
        } catch (Exception e) {
            e.printStackTrace();
            return "error";  // Afișează o pagină de eroare în caz de problemă
        }
    }
}
