package com.gestionale.backend.controller;

import com.gestionale.API.GestionaleApi;
import com.gestionale.model.DisponibilitaRequestDTO;
import com.gestionale.model.PrenotazioneDTO;
import com.gestionale.backend.service.DisponibilitaService;
import com.gestionale.backend.service.PrenotazioneService;
import com.gestionale.model.VoidResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<PrenotazioneDTO> getAllPrenotazioni(){
        List<PrenotazioneDTO> result = prenotazioneService.getAllPrenotazioni();
        System.out.println("Prenotazioni restituite: " + result);
        return result;
    }

    @Override
    @PostMapping("/nuovaPrenotazione")
    public VoidResponseDTO addPrenotazione(@RequestBody PrenotazioneDTO prenotazioneDTO){

        int disponibilita = disponibilitaService.verificaDisponibilita(prenotazioneDTO.getDataInizio(), prenotazioneDTO.getDataFine(), NUMERO_BOX);

        if (disponibilita<=0){
            String message = "Non ci sono Box disponibili";
            return new VoidResponseDTO(message, false, new RuntimeException(message));
        }
        VoidResponseDTO response = new VoidResponseDTO();
        try {
            response = prenotazioneService.addPrenotazione(prenotazioneDTO);
        } catch (RuntimeException e) {
            return response;
        }
        return response;
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
