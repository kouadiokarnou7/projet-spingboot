package springdemo.springdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // Doit correspondre à login.html dans templates
    }

    @GetMapping("/liste-employes") 
    public String listeEmployes() {
        return "employe"; // Correspond à ton fichier employes.html
    }

    @GetMapping("/api/admin/dashboard")
    public String adminDashboard() {
        return "admin"; // La page pour Marie admin.html dans templates
    }
}