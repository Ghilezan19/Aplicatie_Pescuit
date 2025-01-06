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
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails currentUser = (UserDetails) principal;
                String username = currentUser.getUsername();
                String email = currentUser.getUsername();
                String roles = currentUser.getAuthorities().toString();
                model.addAttribute("username", username);
                model.addAttribute("email", email);
                model.addAttribute("roles", roles);
                if (roles.contains("PARTICIPANT")) {
                    return "participant_dashboard";
                } else if (roles.contains("ORGANIZER")) {
                    return "organizator_dashboard";
                } else {
                    return "access-denied";
                }
            } else {
                throw new RuntimeException("Utilizatorul nu este autentificat corect");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}
