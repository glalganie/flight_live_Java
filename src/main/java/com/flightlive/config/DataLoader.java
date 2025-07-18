package com.flightlive.config;

import com.flightlive.entity.Utente;
import com.flightlive.entity.Volo;
import com.flightlive.repository.UtenteRepository;
import com.flightlive.repository.VoloRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired private VoloRepository voloRepository;
    @Autowired private UtenteRepository utenteRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (utenteRepository.count() == 0) loadUtenti();
        if (voloRepository.count() == 0) loadVoli();
    }

    private void loadUtenti() {
        System.out.println("Populating users...");
        Utente admin = new Utente();
        admin.setNome("Admin User");
        admin.setEmail("admin@example.com");
        admin.setPassword(passwordEncoder.encode("password"));
        admin.setRuolo("ROLE_ADMIN");

        Utente user = new Utente();
        user.setNome("Regular User");
        user.setEmail("user@example.com");
        user.setPassword(passwordEncoder.encode("password"));
        user.setRuolo("ROLE_USER");

        utenteRepository.saveAll(List.of(admin, user));
    }

    private void loadVoli() {
        System.out.println("Populating flights...");
        String[] aeroportiITA = {"Roma FCO", "Milano MXP", "Venezia VCE", "Napoli NAP", "Bologna BLQ", "Torino TRN"};
        String[] destinazioniEUR = {"Londra LHR", "Parigi CDG", "Madrid MAD", "Amsterdam AMS", "Berlino BER"};
        String[] destinazioniExtra = {"New York JFK", "Dubai DXB", "Tokyo HND"};

        List<Volo> voli = new ArrayList<>();
        for (String partenza : aeroportiITA) {
            for (String arrivo : destinazioniEUR) {
                if (!partenza.equals(arrivo)) {
                    voli.add(createFlight(partenza, arrivo, "EZ", new BigDecimal("120.50")));
                }
            }
        }
        voli.add(createFlight("Roma FCO", "New York JFK", "AZ", new BigDecimal("580.00")));
        voli.add(createFlight("Milano MXP", "Dubai DXB", "EK", new BigDecimal("450.75")));
        voloRepository.saveAll(voli);
    }

    private Volo createFlight(String partenza, String arrivo, String sigla, BigDecimal prezzoBase) {
        Volo volo = new Volo();
        volo.setNumero(sigla + (100 + (int)(Math.random() * 900)));
        volo.setAeroportoPartenza(partenza);
        volo.setAeroportoArrivo(arrivo);
        int giorniFuturi = 2 + (int)(Math.random() * 60);
        int ora = 6 + (int)(Math.random() * 16);
        int minuti = ((int)(Math.random() * 4)) * 15;
        volo.setDataOra(LocalDateTime.now().plusDays(giorniFuturi).withHour(ora).withMinute(minuti).withSecond(0));
        volo.setPostiDisponibili(100 + (int)(Math.random() * 100));
        BigDecimal variazione = new BigDecimal(Math.random() * 50 - 25);
        volo.setPrezzo(prezzoBase.add(variazione).setScale(2, RoundingMode.HALF_UP));
        return volo;
    }
}