package com.flightlive.controller;

import com.flightlive.entity.Prenotazione;
import com.flightlive.entity.Volo;
import com.flightlive.repository.PrenotazioneRepository;
import com.flightlive.service.PdfService;
import com.flightlive.service.PrenotazioneService;
import com.flightlive.service.VoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PrenotazioneController {
    @Autowired private PrenotazioneService prenotazioneService;
    @Autowired private VoloService voloService;
    @Autowired private PrenotazioneRepository prenotazioneRepository;
    @Autowired private PdfService pdfService;

    @GetMapping("/prenotazioni")
    public String getMiePrenotazioni(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("prenotazioni", prenotazioneService.getPrenotazioniByUtenteEmail(userDetails.getUsername()));
        return "prenotazioni";
    }

 // Per semplicità, la prenotazione è per 1 posto.
    @GetMapping("/book/{voloId}")
    public String prenotaVolo(@PathVariable Long voloId, @AuthenticationPrincipal UserDetails userDetails, RedirectAttributes redirectAttributes) {
        boolean successo = prenotazioneService.creaPrenotazione(voloId, userDetails.getUsername(), 1);
        if (successo) {
            redirectAttributes.addFlashAttribute("messaggioSuccesso", "Prenotazione effettuata con successo!");
        } else {
            redirectAttributes.addFlashAttribute("messaggioErrore", "Impossibile prenotare: posti non disponibili.");
        }
        return "redirect:/prenotazioni";
    }
    
       
    
    @GetMapping("/prenotazioni/delete/{id}")
    public String cancellaPrenotazione(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            prenotazioneService.cancellaPrenotazione(id);
            redirectAttributes.addFlashAttribute("messaggioCancellazioneSuccesso", "Prenotazione cancellata con successo!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("messaggioCancellazioneErrore", "Errore durante la cancellazione della prenotazione.");
        }
        return "redirect:/prenotazioni";
    }
    
    
 // Mostra il form per scegliere il numero di posti
    @GetMapping("/prenotazioni/new/{voloId}")
    public String showPrenotazioneForm(@PathVariable Long voloId, Model model) {
    	Volo volo = voloService.getVoloById(voloId).orElseThrow();
        model.addAttribute("volo", volo);
        model.addAttribute("prenotazione", new Prenotazione()); // Oggetto per il form
        return "form-prenotazione"; // Nuova vista da creare
    }
    
    // Processa il form e crea la prenotazione
    @PostMapping("/prenotazioni/create")
    public String creaPrenotazioneMultipla(
            @RequestParam Long voloId,
            @RequestParam int nPosti,
            @AuthenticationPrincipal UserDetails userDetails,
            RedirectAttributes redirectAttributes) {

        boolean successo = prenotazioneService.creaPrenotazione(voloId, userDetails.getUsername(), nPosti);
        if (successo) {
            redirectAttributes.addFlashAttribute("messaggioSuccesso", "Prenotazione per " + nPosti + " posti effettuata!");
        } else {
            redirectAttributes.addFlashAttribute("messaggioErrore", "Impossibile prenotare: posti non disponibili.");
        }
        return "redirect:/prenotazioni";
    }
    
    
    
    
    @GetMapping("/prenotazioni/pdf/{id}")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable Long id) {
        var prenotazione = prenotazioneRepository.findById(id).orElseThrow(() -> new RuntimeException("Prenotazione non trovata"));
        byte[] pdfBytes = pdfService.generaPdfPrenotazione(prenotazione);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", "prenotazione-" + prenotazione.getId() + ".pdf");
        headers.setContentType(MediaType.APPLICATION_PDF);
        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }
}