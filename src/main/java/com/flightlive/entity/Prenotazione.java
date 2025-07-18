package com.flightlive.entity;
import jakarta.persistence.*;
import java.time.LocalDate;



@Entity 
@Table(name = "prenotazioni")
public class Prenotazione {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY)@JoinColumn(name = "id_utente") private Utente utente;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "id_volo") private Volo volo;
   
    
    private LocalDate dataPrenotazione;
    private int nPosti;
    // Getters and setters...
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Utente getUtente() {
		return utente;
	}
	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	public Volo getVolo() {
		return volo;
	}
	public void setVolo(Volo volo) {
		this.volo = volo;
	}
	public LocalDate getDataPrenotazione() {
		return dataPrenotazione;
	}
	public void setDataPrenotazione(LocalDate dataPrenotazione) {
		this.dataPrenotazione = dataPrenotazione;
	}
	public int getnPosti() {
		return nPosti;
	}
	public void setnPosti(int nPosti) {
		this.nPosti = nPosti;
	}
    
    
}