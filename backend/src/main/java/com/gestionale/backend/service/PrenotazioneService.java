package com.gestionale.backend.service;

import com.gestionale.model.PrenotazioneDTO;
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

    public List<PrenotazioneDTO> getAllPrenotazioni(){
        List<PrenotazioneDTO> prenotazioneDTOList = prenotazioneRepo.findAll();
        if (prenotazioneDTOList == null){
            return new ArrayList<>();
        }
        else {
            return prenotazioneDTOList;
        }
    }

    public VoidResponseDTO addPrenotazione(PrenotazioneDTO prenotazioneDTO) {
        VoidResponseDTO voidResponseDTO;
        try {
            prenotazioneRepo.save(prenotazioneDTO);
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
