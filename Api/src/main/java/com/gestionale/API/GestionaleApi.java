package com.gestionale.API;

import com.gestionale.model.DisponibilitaRequestDTO;
import com.gestionale.model.Prenotazione;
import com.gestionale.model.VoidResponseDTO;

import java.net.URISyntaxException;
import java.util.List;

public interface GestionaleApi {

    List<Prenotazione> getAllPrenotazioni() throws URISyntaxException;

    void addPrenotazione(String nomeCliente, int numeroCani, String dataInizio, String dataFine) throws URISyntaxException;

    Integer getDisponibilita(DisponibilitaRequestDTO disponibilitaRequestDTO) throws URISyntaxException;

    public VoidResponseDTO eliminaPrenotazione(String id);
}
