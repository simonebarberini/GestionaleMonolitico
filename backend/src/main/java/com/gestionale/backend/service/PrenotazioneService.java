package com.gestionale.backend.service;

import com.gestionale.model.Prenotazione;
import com.gestionale.backend.repo.PrenotazioneRepository;
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

    public void addPrenotazione(Prenotazione prenotazione) {
        try {
            prenotazioneRepo.save(prenotazione);
        }catch (Exception e){
            throw new RuntimeException("Non é stato possibile salvare la prenotazione");
        }

    }

    public void eliminaPrenotazione(String id) {
        try {
            prenotazioneRepo.deleteById(id);
        }catch (IllegalArgumentException e){
            throw new RuntimeException("Non é stato possibile eliminare la prenotazione");
        }
    }
}
