package com.flightlive.controller;

import com.flightlive.entity.Volo;
import com.flightlive.repository.UtenteRepository;
import com.flightlive.service.ReportService;
import com.flightlive.service.VoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired private VoloService voloService;
    @Autowired private ReportService reportService;
    @Autowired private UtenteRepository utenteRepository;

    @GetMapping("/voli")
    public String gestisciVoli(Model model) {
        model.addAttribute("voli", voloService.getAllVoli());
        return "admin/voli";
    }

    @GetMapping("/voli/new")
    public String nuovoVoloForm(Model model) {
        model.addAttribute("volo", new Volo());
        return "admin/form-volo";
    }

    @PostMapping("/voli/save")
    public String salvaVolo(@ModelAttribute("volo") Volo volo) {
        voloService.salvaVolo(volo);
        return "redirect:/admin/voli";
    }

    @GetMapping("/voli/edit/{id}")
    public String modificaVoloForm(@PathVariable Long id, Model model) {
        Volo volo = voloService.getVoloById(id).orElseThrow(() -> new IllegalArgumentException("Volo non valido: " + id));
        model.addAttribute("volo", volo);
        return "admin/form-volo";
    }

    @GetMapping("/voli/delete/{id}")
    public String eliminaVolo(@PathVariable Long id) {
        voloService.eliminaVolo(id);
        return "redirect:/admin/voli";
    }

    @GetMapping("/report")
    public String reportSettimanale(Model model) {
        model.addAttribute("reportData", reportService.getReportVoliSettimanali());
        return "admin/report";
    }
    @GetMapping("/utenti")
    public String gestisciUtenti(Model model) {
        model.addAttribute("utenti", utenteRepository.findAll());
        return "admin/utenti"; // Nuova vista
    }
    
}