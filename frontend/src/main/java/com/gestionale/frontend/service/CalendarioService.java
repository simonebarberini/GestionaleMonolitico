package com.gestionale.frontend.service;

import com.gestionale.frontend.controller.PrenotazioneControllerFe;
import com.gestionale.model.DisponibilitaRequestDTO;
import com.gestionale.model.PrenotazioneDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CalendarioService {

    private static final int NUMERO_BOX_TOTALI = 10;
    
    @Autowired
    private PrenotazioneControllerFe prenotazioneControllerFe;

    public List<Map<String, Object>> getDisponibilitaMese(LocalDate start, LocalDate end) throws URISyntaxException {
        List<PrenotazioneDTO> prenotazioni = prenotazioneControllerFe.getAllPrenotazioni();
        List<Map<String, Object>> events = new ArrayList<>();
        
        LocalDate currentDate = start;
        while (!currentDate.isAfter(end)) {
            final LocalDate date = currentDate;
            
            // Calcola disponibilità per il giorno corrente
            int boxOccupati = (int) prenotazioni.stream()
                    .filter(p -> !p.getDataInizio().isAfter(date) && !p.getDataFine().isBefore(date))
                    .mapToInt(PrenotazioneDTO::getNumeroCani)
                    .sum();
            
            int boxDisponibili = NUMERO_BOX_TOTALI - boxOccupati;
            
            // Crea evento per il calendario
            Map<String, Object> event = new HashMap<>();
            event.put("title", "Box disponibili: " + boxDisponibili);
            event.put("start", date.toString());
            event.put("allDay", true);
            
            // Imposta colore in base alla disponibilità
            if (boxDisponibili >= 7) {
                event.put("backgroundColor", "#d4edda"); // verde chiaro - alta disponibilità
                event.put("borderColor", "#c3e6cb");
            } else if (boxDisponibili >= 3) {
                event.put("backgroundColor", "#fff3cd"); // giallo chiaro - media disponibilità
                event.put("borderColor", "#ffeeba");
            } else {
                event.put("backgroundColor", "#f8d7da"); // rosso chiaro - bassa disponibilità
                event.put("borderColor", "#f5c6cb");
            }
            
            events.add(event);
            currentDate = currentDate.plusDays(1);
        }
        
        return events;
    }
    
    public List<Map<String, Object>> getPrenotazioniSettimana(LocalDate start, LocalDate end) throws URISyntaxException {
        List<PrenotazioneDTO> prenotazioni = prenotazioneControllerFe.getAllPrenotazioni();
        List<Map<String, Object>> events = new ArrayList<>();
        
        // Filtra le prenotazioni che rientrano nel periodo richiesto
        List<PrenotazioneDTO> prenotazioniPeriodo = prenotazioni.stream()
                .filter(p -> (p.getDataInizio().isEqual(start) || p.getDataInizio().isAfter(start)) && 
                             (p.getDataInizio().isEqual(end) || p.getDataInizio().isBefore(end)) ||
                             (p.getDataFine().isEqual(start) || p.getDataFine().isAfter(start)) && 
                             (p.getDataFine().isEqual(end) || p.getDataFine().isBefore(end)) ||
                             (p.getDataInizio().isBefore(start) && p.getDataFine().isAfter(end)))
                .collect(Collectors.toList());
        
        // Crea eventi per ogni prenotazione
        for (PrenotazioneDTO prenotazione : prenotazioniPeriodo) {
            Map<String, Object> event = new HashMap<>();
            event.put("title", prenotazione.getNomeCliente());
            
            // Assicurati che le date dell'evento siano all'interno del periodo richiesto
            LocalDate eventStart = prenotazione.getDataInizio().isBefore(start) ? start : prenotazione.getDataInizio();
            LocalDate eventEnd = prenotazione.getDataFine().isAfter(end) ? end : prenotazione.getDataFine();
            
            event.put("start", eventStart.toString());
            event.put("end", eventEnd.plusDays(1).toString()); // Aggiungi un giorno per inclusività
            event.put("allDay", true);
            
            // Imposta colore per le prenotazioni settimanali
            event.put("backgroundColor", "#007bff"); // blu
            event.put("borderColor", "#0056b3");
            
            events.add(event);
        }
        
        return events;
    }

    public List<Map<String, Object>> getPrenotazioniGiorno(LocalDate start, LocalDate end) throws URISyntaxException {
        List<PrenotazioneDTO> prenotazioni = prenotazioneControllerFe.getAllPrenotazioni();
        List<Map<String, Object>> events = new ArrayList<>();
        
        // Per la vista giornaliera, mostriamo solo le prenotazioni del giorno specifico
        LocalDate data = start; // Nella vista giornaliera, start e end sono uguali
        
        // Filtra le prenotazioni per il giorno specificato - corretto per includere il giorno di inizio
        List<PrenotazioneDTO> prenotazioniGiorno = prenotazioni.stream()
                .filter(p -> (p.getDataInizio().isEqual(data) || p.getDataInizio().isBefore(data)) 
                          && (p.getDataFine().isEqual(data) || p.getDataFine().isAfter(data)))
                .collect(Collectors.toList());
        
        // Crea eventi per ogni prenotazione
        for (PrenotazioneDTO prenotazione : prenotazioniGiorno) {
            Map<String, Object> event = new HashMap<>();
            
            // Formato più leggibile con data inizio inclusa
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataInizioStr = prenotazione.getDataInizio().format(formatter);
            String dataFineStr = prenotazione.getDataFine().format(formatter);
            
            event.put("title", prenotazione.getNomeCliente() + 
                      " - Inizio: " + dataInizioStr + 
                      " - Fine: " + dataFineStr + 
                      " - Cani: " + prenotazione.getNumeroCani());
            
            event.put("start", data.toString());
            event.put("allDay", true);
            event.put("dataInizio", dataInizioStr);
            event.put("dataFine", dataFineStr);
            event.put("numeroCani", prenotazione.getNumeroCani());
            
            // Imposta colore per le prenotazioni giornaliere
            event.put("backgroundColor", "#28a745"); // verde
            event.put("borderColor", "#1e7e34");
            event.put("textColor", "#ffffff");
            
            events.add(event);
        }
        
        return events;
    }

    public List<PrenotazioneDTO> getPrenotazioniGiornoDettaglio(LocalDate data) throws URISyntaxException {
        List<PrenotazioneDTO> tutte = prenotazioneControllerFe.getAllPrenotazioni();
        
        // Debug log to see what's happening
        System.out.println("Data richiesta: " + data);
        for (PrenotazioneDTO p : tutte) {
            System.out.println("Prenotazione: " + p.getNomeCliente() + 
                               ", inizio: " + p.getDataInizio() + 
                               ", fine: " + p.getDataFine() + 
                               ", match: " + ((p.getDataInizio().isEqual(data) || p.getDataInizio().isBefore(data)) && 
                                             (p.getDataFine().isEqual(data) || p.getDataFine().isAfter(data))));
        }
        
        // Filtra le prenotazioni per il giorno specificato
        return tutte.stream()
                .filter(p -> (p.getDataInizio().isEqual(data) || p.getDataInizio().isBefore(data)) && 
                             (p.getDataFine().isEqual(data) || p.getDataFine().isAfter(data)))
                .collect(Collectors.toList());
    }
}