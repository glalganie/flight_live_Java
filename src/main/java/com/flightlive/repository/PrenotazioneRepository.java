package com.flightlive.repository;
import com.flightlive.entity.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;



public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
    List<Prenotazione> findByUtenteId(Long utenteId);
}