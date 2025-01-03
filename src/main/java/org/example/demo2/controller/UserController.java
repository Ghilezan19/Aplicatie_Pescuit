
package org.example.demo2.controller;
import org.example.demo2.service.UserService;

import org.example.demo2.model.User;
import org.example.demo2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@Controller
public class UserController {

    private final UserService userService;

    // Constructorul pentru injectarea dependenței UserService
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Metoda pentru a arăta formularul de înregistrare
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // Adaugă un obiect 'user' în model pentru a fi folosit în formular
        model.addAttribute("user", new User());
        return "register"; // Întoarce pagina "register.html"
    }

    // Metoda pentru a arăta pagina principală
    @GetMapping("/home")
    public String showHomePage() {
        return "home"; // Întoarce pagina "home.html"
    }

    @GetMapping("/add-tournament")
    public String showAddTournamentForm() {
        return "tournaments"; // Creează un formular pentru competiții
    }

    @GetMapping("/add-participant")
    public String showAddParticipantForm() {
        return "users"; // Creează un formular pentru participanți
    }

    @GetMapping("/live-ranking")
    public String showLiveRanking() {
        return "ranking"; // Creează o pagină pentru clasament live
    }

    @GetMapping("/add-catch")
    public String showAddCatchForm() {
        return "catch"; // Creează un formular pentru cantitatea de pește
    }

    // Metoda pentru a gestiona cererea POST pentru înregistrare
    @PostMapping("/register")
    public String registerUser(User user, @RequestParam String role, @RequestParam(required = false) String organizerKey) {
        if ("ORGANIZER".equals(role)) {
            if (!"19022003".equals(organizerKey)) {
                // Dacă parola specială este incorectă, redirecționăm utilizatorul înapoi la formular
                return "redirect:/register?error=invalid_organizer_key";
            }
        }

        // Setăm rolul utilizatorului
        user.setRoles(Set.of(role));
        // Salvăm utilizatorul în baza de date
        userService.saveUser(user);

        // Redirecționăm utilizatorul la pagina de login
        return "redirect:/login";
    }


}