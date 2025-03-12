package com.gestionale.API;

import com.gestionale.model.DisponibilitaRequestDTO;
import com.gestionale.model.PrenotazioneDTO;
import com.gestionale.model.VoidResponseDTO;

import java.net.URISyntaxException;
import java.util.List;

public interface GestionaleApi {

    List<PrenotazioneDTO> getAllPrenotazioni() throws URISyntaxException;

    VoidResponseDTO addPrenotazione(PrenotazioneDTO prenotazioneDTO) throws URISyntaxException;

    Integer getDisponibilita(DisponibilitaRequestDTO disponibilitaRequestDTO) throws URISyntaxException;

    public VoidResponseDTO eliminaPrenotazione(String id) throws URISyntaxException;
}
