package com.flightlive.controller;

import com.flightlive.entity.Volo;
import com.flightlive.service.VoloService;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VoloController {
   /* @Autowired private VoloService voloService;

    @GetMapping({"/", "/voli"})
    public String listaVoli(@RequestParam(required = false) String destinazione, Model model) {
        model.addAttribute("voli", voloService.getVoliDisponibili(destinazione));
        return "index";
    }*/
	
	
	  @Autowired private VoloService voloService;

	    @GetMapping("/")
	    public String home() {
	        return "redirect:/voli";
	    }

	   /* @GetMapping("/voli")
	    public String listaVoli(@RequestParam(required = false) String destinazione, Model model) {
	        List<Volo> voli;
	        if (destinazione != null && !destinazione.isBlank()) {
	            voli = voloService.filtraPerDestinazione(destinazione);
	        } else {
	            voli = voloService.getVoliDisponibili();
	        }
	        model.addAttribute("voli", voli);
	        return "index";*/
	    
	    @GetMapping("/voli")
	    public String listaVoli(
	            @RequestParam(required = false) String destinazione,
	            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
	            Model model) {

	        List<Volo> voli = voloService.cercaVoli(destinazione, data); // Creiamo un metodo di ricerca nel service
	        model.addAttribute("voli", voli);
	        return "index";
	    }
	}
