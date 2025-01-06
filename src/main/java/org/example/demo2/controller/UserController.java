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
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
    @GetMapping("/home")
    public String showHomePage() {
        return "home";
    }
    @GetMapping("/add-tournament")
    public String showAddTournamentForm() {
        return "tournaments";
    }
    @GetMapping("/add-participant")
    public String showAddParticipantForm() {
        return "users";
    }
    @GetMapping("/live-ranking")
    public String showLiveRanking() {
        return "ranking";
    }
    @GetMapping("/add-catch")
    public String showAddCatchForm() {
        return "catch";
    }
    @PostMapping("/register")
    public String registerUser(User user, @RequestParam String role, @RequestParam(required = false) String organizerKey) {
        if ("ORGANIZER".equals(role)) {
            if (!"19022003".equals(organizerKey)) {
                return "redirect:/register?error=invalid_organizer_key";
            }
        }
        user.setRoles(Set.of(role));
        userService.saveUser(user);
        return "redirect:/login";
    }
}