package com.flightlive.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;


import com.flightlive.entity.Prenotazione;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;


@Service
public class PdfService {
	 public byte[] generaPdfPrenotazione(Prenotazione prenotazione) {
	        Document document = new Document();
	        ByteArrayOutputStream out = new ByteArrayOutputStream();

	        try {
	            PdfWriter.getInstance(document, out);
	            document.open();

	            document.add(new Paragraph("Riepilogo Prenotazione - FlightLive"));
	            document.add(new Paragraph("-------------------------------------"));
	                document.add(new Paragraph("Grazie per la tua prenotazione, " + prenotazione.getUtente().getNome() + "!"));
	                document.add(new Paragraph("ID Prenotazione: #" + prenotazione.getId()));
	                document.add(new Paragraph("Data Prenotazione: " + prenotazione.getDataPrenotazione()));
	                
	                document.add(new Paragraph("Dettagli del Volo"));
	                document.add(new Paragraph("Numero Volo: " + prenotazione.getVolo().getNumero()));
	                document.add(new Paragraph("Da: " + prenotazione.getVolo().getAeroportoPartenza()));
	                document.add(new Paragraph("A: " + prenotazione.getVolo().getAeroportoArrivo()));
	                document.add(new Paragraph("Data e Ora di Partenza: " + prenotazione.getVolo().getDataOra()));

	                document.add(new Paragraph("Numero Posti Prenotati: " + prenotazione.getnPosti()));
	                BigDecimal prezzoTotale = prenotazione.getVolo().getPrezzo().multiply(new BigDecimal(prenotazione.getnPosti()));
	                document.add(new Paragraph("Prezzo Totale: " + prezzoTotale + " €"));

	            document.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return out.toByteArray();
	    }
	}