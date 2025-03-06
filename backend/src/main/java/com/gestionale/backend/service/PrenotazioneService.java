package com.gestionale.backend.service;

import com.gestionale.model.Prenotazione;
import com.gestionale.backend.repo.PrenotazioneRepository;
import com.gestionale.model.VoidResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrenotazioneService {
    @Autowired
    private PrenotazioneRepository prenotazioneRepo;

    public List<Prenotazione> getAllPrenotazioni(){
        List<Prenotazione> prenotazioneList = prenotazioneRepo.findAll();
        if (prenotazioneList== null){
            return new ArrayList<>();
        }
        else {
            return prenotazioneList;
        }
    }

    public VoidResponseDTO addPrenotazione(Prenotazione prenotazione) {
        VoidResponseDTO voidResponseDTO;
        try {
            prenotazioneRepo.save(prenotazione);
            voidResponseDTO = new VoidResponseDTO("Prenotazione Inserita con successo!", true, null);
        }catch (Exception e){
            voidResponseDTO = new VoidResponseDTO("Errore nell'eliminnazione della prenotazione", false, e);
            return voidResponseDTO;
        }

        return voidResponseDTO;
    }

    public VoidResponseDTO eliminaPrenotazione(String id) {
        VoidResponseDTO voidResponseDTO;
        try {
            prenotazioneRepo.deleteById(id);
            voidResponseDTO = new VoidResponseDTO("Prenotazione eliminata con successo!", true, null);
        }catch (IllegalArgumentException e){
            voidResponseDTO = new VoidResponseDTO("Errore nell'eliminnazione della prenotazione", false, e);
            return voidResponseDTO;
        }
        return voidResponseDTO;
    }
}
