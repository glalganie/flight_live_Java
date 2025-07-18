package com.flightlive.repository;
import com.flightlive.entity.Volo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;



public interface VoloRepository extends JpaRepository<Volo, Long> {
    List<Volo> findByDataOraAfter(LocalDateTime data);
    List<Volo> findByAeroportoArrivoContainingIgnoreCaseAndDataOraAfter(String destinazione, LocalDateTime data);
   
    
   	List<Volo> findByDataOraBetween(LocalDateTime start, LocalDateTime end);
   	List<Volo> findByAeroportoArrivoContainingIgnoreCaseAndDataOraBetween(String destinazione, LocalDateTime start,
   			LocalDateTime end);
    
    
    
    @Query("SELECT v, SUM(p.nPosti) as totalePosti FROM Prenotazione p JOIN p.volo v WHERE p.dataPrenotazione >= :startOfWeek GROUP BY v.id, v.numero, v.aeroportoPartenza, v.aeroportoArrivo ORDER BY totalePosti DESC")
    List<Object[]> findTopVoliSettimanali(@Param("startOfWeek") LocalDate startOfWeek);
}