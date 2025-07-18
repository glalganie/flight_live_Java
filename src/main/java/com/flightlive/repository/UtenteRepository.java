package com.flightlive.repository;
import com.flightlive.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;




public interface UtenteRepository extends JpaRepository<Utente, Long> {
    Utente findByEmail(String email);
}