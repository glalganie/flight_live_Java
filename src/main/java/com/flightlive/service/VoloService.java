package com.flightlive.service;

import com.flightlive.entity.Volo;
import com.flightlive.repository.VoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional; 


@Service
public class VoloService {
    @Autowired 
    private VoloRepository voloRepository;

    public List<Volo> getVoliDisponibili() {
        return voloRepository.findByDataOraAfter(LocalDateTime.now());
    }
    
    public List<Volo> filtraPerDestinazione(String destinazione) {
        return voloRepository.findByAeroportoArrivoContainingIgnoreCaseAndDataOraAfter(destinazione, LocalDateTime.now());
}

    
    public List<Volo> getAllVoli() {
        return voloRepository.findAll();
    }

    public Optional<Volo> getVoloById(Long id) {
        return voloRepository.findById(id);
    }

    public Volo salvaVolo(Volo volo) {
        return voloRepository.save(volo);
    }

    public void eliminaVolo(Long id) {
        voloRepository.deleteById(id);
    }

    public List<Volo> cercaVoli(String destinazione, LocalDate data) {
		 boolean hasDestinazione = destinazione != null && !destinazione.isBlank();
	        boolean hasData = data != null;

	        if (hasDestinazione && hasData) {
	            LocalDateTime start = data.atStartOfDay();
	            LocalDateTime end = data.atTime(23, 59, 59);
	            return voloRepository.findByAeroportoArrivoContainingIgnoreCaseAndDataOraBetween(destinazione, start, end);
	        } else if (hasDestinazione) {
	            return voloRepository.findByAeroportoArrivoContainingIgnoreCaseAndDataOraAfter(destinazione, LocalDateTime.now());
	        } else if (hasData) {
	            LocalDateTime start = data.atStartOfDay();
	            LocalDateTime end = data.atTime(23, 59, 59);
	            return voloRepository.findByDataOraBetween(start, end);
	        } else {
	            return getVoliDisponibili();
	        }
	    }
}