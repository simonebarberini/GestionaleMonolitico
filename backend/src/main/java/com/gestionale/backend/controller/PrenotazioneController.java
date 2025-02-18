package com.gestionale.backend.controller;

import com.gestionale.backend.model.Prenotazione;
import com.gestionale.backend.service.DisponibilitaService;
import com.gestionale.backend.service.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class PrenotazioneController {

    private static final int NUMERO_BOX = 10;

    @Autowired
    PrenotazioneService prenotazioneService;

    @Autowired
    DisponibilitaService disponibilitaService;


    @GetMapping("/prenotazioni")
    public List<Prenotazione> getAllPrenotazioni(){
        List<Prenotazione> result = prenotazioneService.getAllPrenotazioni();
        System.out.println("Prenotazioni restituite: " + result);
        return result;
    }

    @PostMapping("/nuovaPrenotazione")
    public void addPrenotazione(@RequestParam String nomeCliente, @RequestParam int numeroCani, @RequestParam String dataInizio, @RequestParam String dataFine){

        int disponibilita = disponibilitaService.verificaDisponibilita(LocalDate.parse(dataInizio), LocalDate.parse(dataFine), NUMERO_BOX);

        if (disponibilita<=0){
            throw new RuntimeException("Non ci sono box disponibili in queste date");
        }
        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setNomeCliente(nomeCliente);
        prenotazione.setNumeroCani(numeroCani);
        prenotazione.setDataInizio(LocalDate.parse(dataInizio));
        prenotazione.setDataFine(LocalDate.parse(dataFine));
        try {
            prenotazioneService.addPrenotazione(prenotazione);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("/verificaDisponibilita")
    public Integer getDisponibilita(@RequestParam String dataInizio, @RequestParam String dataFine){
        return disponibilitaService.verificaDisponibilita(LocalDate.parse(dataInizio), LocalDate.parse(dataFine), NUMERO_BOX);
    }

    @DeleteMapping("/eliminaPrenotazione")
    public void eliminaPrenotazione(@RequestParam String id){
        try {
            prenotazioneService.eliminaPrenotazione(id);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }


}
