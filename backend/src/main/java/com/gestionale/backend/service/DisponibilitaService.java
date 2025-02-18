package com.gestionale.backend.service;

import com.gestionale.backend.model.Prenotazione;
import com.gestionale.backend.repo.PrenotazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DisponibilitaService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;


    public int calcolaMassimoBoxOccupati(LocalDate dataInizio, LocalDate dataFine) {
        List<Prenotazione> prenotazioni = prenotazioneRepository.findSovrapposizionePrenotaioni(dataInizio, dataFine);

        Map<LocalDate, Integer> occupazionePerGiorno = new HashMap<>();

        for (Prenotazione prenotazione : prenotazioni) {
            LocalDate inizioPrenotazione = prenotazione.getDataInizio();
            LocalDate finePrenotazione = prenotazione.getDataFine();

            LocalDate inizioSovrapposizione = inizioPrenotazione.isBefore(dataInizio) ? dataInizio : inizioPrenotazione;
            LocalDate fineSovrapposizione = finePrenotazione.isAfter(dataFine) ? dataFine : finePrenotazione;

            for (LocalDate data = inizioSovrapposizione; !data.isAfter(fineSovrapposizione); data = data.plusDays(1)) {
                occupazionePerGiorno.put(data, occupazionePerGiorno.getOrDefault(data, 0) + 1);
            }
        }

        return occupazionePerGiorno.values().stream().max(Integer::compareTo).orElse(0);
    }

    public int verificaDisponibilita(LocalDate dataInizio, LocalDate dataFine, int boxTotali) {
        int massimoBoxOccupati = calcolaMassimoBoxOccupati(dataInizio, dataFine);

        return boxTotali - massimoBoxOccupati;
    }


}
