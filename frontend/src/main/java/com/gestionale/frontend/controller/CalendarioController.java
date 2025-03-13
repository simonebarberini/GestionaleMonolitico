package com.gestionale.frontend.controller;

import com.gestionale.frontend.service.CalendarioService;
import com.gestionale.model.PrenotazioneDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@Controller
public class CalendarioController {

    @Autowired
    private CalendarioService calendarioService;

    @GetMapping("/calendarioView")
    public String calendarioView(Model model) {
        model.addAttribute("pageTitle", "Calendario Prenotazioni");
        return "calendario";
    }

    @GetMapping("/disponibilitaMese")
    @ResponseBody
    public List<Map<String, Object>> getDisponibilitaMese(
            @RequestParam String start,
            @RequestParam String end) throws URISyntaxException {
        
        // Converti le stringhe in LocalDate
        LocalDate startDate = LocalDate.parse(start.substring(0, 10));
        LocalDate endDate = LocalDate.parse(end.substring(0, 10));
        
        return calendarioService.getDisponibilitaMese(startDate, endDate);
    }
    
    @GetMapping("/prenotazioniSettimana")
    @ResponseBody
    public List<Map<String, Object>> getPrenotazioniSettimana(
            @RequestParam String start,
            @RequestParam String end) throws URISyntaxException {
        
        // Converti le stringhe in LocalDate
        LocalDate startDate = LocalDate.parse(start.substring(0, 10));
        LocalDate endDate = LocalDate.parse(end.substring(0, 10));
        
        return calendarioService.getPrenotazioniSettimana(startDate, endDate);
    }
    
    @GetMapping("/prenotazioniGiorno")
    @ResponseBody
    public List<Map<String, Object>> getPrenotazioniGiorno(
            @RequestParam String start,
            @RequestParam String end) throws URISyntaxException {
        
        // Converti le stringhe in LocalDate
        LocalDate startDate = LocalDate.parse(start.substring(0, 10));
        LocalDate endDate = LocalDate.parse(end.substring(0, 10));
        
        return calendarioService.getPrenotazioniGiorno(startDate, endDate);
    }

    @GetMapping("/prenotazioniGiornoDettaglio")
    public String getPrenotazioniGiornoDettaglio(
            @RequestParam String data,
            Model model) throws URISyntaxException {
        
        // Converti la stringa in LocalDate
        LocalDate localDate = LocalDate.parse(data.substring(0, 10));
        
        List<PrenotazioneDTO> prenotazioni = calendarioService.getPrenotazioniGiornoDettaglio(localDate);
        model.addAttribute("prenotazioni", prenotazioni);
        return "prenotazioni-giorno";
    }
}