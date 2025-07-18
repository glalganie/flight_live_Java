package com.flightlive.service;

import com.flightlive.entity.Prenotazione;
import com.flightlive.entity.Utente;
import com.flightlive.entity.Volo;
import com.flightlive.repository.PrenotazioneRepository;
import com.flightlive.repository.UtenteRepository;
import com.flightlive.repository.VoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PrenotazioneService {
    @Autowired private PrenotazioneRepository prenotazioneRepository;
    @Autowired private VoloRepository voloRepository;
    @Autowired private UtenteRepository utenteRepository;

    public List<Prenotazione> getPrenotazioniByUtenteEmail(String email) {
        Utente utente = utenteRepository.findByEmail(email);
        return prenotazioneRepository.findByUtenteId(utente.getId());
    }
    
    @Transactional
    public boolean creaPrenotazione(Long voloId, String emailUtente, int nPosti) {
        Volo volo = voloRepository.findById(voloId).orElseThrow(() -> new RuntimeException("Volo non trovato"));
        Utente utente = utenteRepository.findByEmail(emailUtente);

        if (volo.getPostiDisponibili() < nPosti) {
            return false; // Posti non sufficienti
        }

        volo.setPostiDisponibili(volo.getPostiDisponibili() - nPosti);
        voloRepository.save(volo);

        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setUtente(utente);
        prenotazione.setVolo(volo);
        prenotazione.setnPosti(nPosti);
        prenotazione.setDataPrenotazione(LocalDate.now());

        prenotazioneRepository.save(prenotazione);
        return true;
    }
    
    @Transactional
    public void cancellaPrenotazione(Long prenotazioneId) {
        // Trova la prenotazione, altrimenti lancia un'eccezione
        Prenotazione prenotazione = prenotazioneRepository.findById(prenotazioneId)
            .orElseThrow(() -> new RuntimeException("Prenotazione non trovata con ID: " + prenotazioneId));

        Volo volo = prenotazione.getVolo();
        int postiDaRipristinare = prenotazione.getnPosti();

        // Ripristina i posti disponibili sul volo
        volo.setPostiDisponibili(volo.getPostiDisponibili() + postiDaRipristinare);
        voloRepository.save(volo);

        // Cancella la prenotazione
        prenotazioneRepository.delete(prenotazione);
    }
}