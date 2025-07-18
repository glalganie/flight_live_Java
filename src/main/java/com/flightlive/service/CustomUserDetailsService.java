package com.flightlive.service;

import com.flightlive.entity.Utente;
import com.flightlive.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService, UserDetailsPasswordService {
    
    @Autowired private UtenteRepository utenteRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utente utente = utenteRepository.findByEmail(email);
        if (utente == null) throw new UsernameNotFoundException("Utente non trovato: " + email);
        return User.builder()
                .username(utente.getEmail())
                .password(utente.getPassword())
                .roles(utente.getRuolo().replace("ROLE_", ""))
                .build();
    }

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        Utente utente = utenteRepository.findByEmail(user.getUsername());
        if (utente != null) {
            utente.setPassword(newPassword);
            utenteRepository.save(utente);
        }
        return user;
    }
}