package com.flightlive.controller;

import com.flightlive.entity.Utente;
import com.flightlive.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    @Autowired private UtenteRepository utenteRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("utente", new Utente());
        return "auth/register";
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute Utente utente) {
        if (utenteRepository.findByEmail(utente.getEmail()) != null) {
            return "redirect:/register?error";
        }
        utente.setPassword(passwordEncoder.encode(utente.getPassword()));
        utente.setRuolo("ROLE_USER");
        utenteRepository.save(utente);
        return "redirect:/login?success";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }
}