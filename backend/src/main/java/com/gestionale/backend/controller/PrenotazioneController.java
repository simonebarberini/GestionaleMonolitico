package com.gestionale.backend.controller;

import com.gestionale.API.GestionaleApi;
import com.gestionale.model.DisponibilitaRequestDTO;
import com.gestionale.model.Prenotazione;
import com.gestionale.backend.service.DisponibilitaService;
import com.gestionale.backend.service.PrenotazioneService;
import com.gestionale.model.VoidResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/prenotazioneServices")
@RestController
public class PrenotazioneController implements GestionaleApi {

    private static final int NUMERO_BOX = 10;

    @Autowired
    PrenotazioneService prenotazioneService;

    @Autowired
    DisponibilitaService disponibilitaService;


    @Override
    @GetMapping("/prenotazioni")
    public List<Prenotazione> getAllPrenotazioni(){
        List<Prenotazione> result = prenotazioneService.getAllPrenotazioni();
        System.out.println("Prenotazioni restituite: " + result);
        return result;
    }

    @Override
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

    @Override
    @GetMapping("/verificaDisponibilita")
    public Integer getDisponibilita(DisponibilitaRequestDTO disponibilitaRequestDTO){
        return disponibilitaService.verificaDisponibilita(disponibilitaRequestDTO.getDataInizio(), disponibilitaRequestDTO.getDataFine(), NUMERO_BOX);
    }

    @DeleteMapping("/eliminaPrenotazione")
    public VoidResponseDTO eliminaPrenotazione(@RequestParam String id){
        return prenotazioneService.eliminaPrenotazione(id);
    }


}
