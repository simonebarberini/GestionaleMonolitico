package com.gestionale.API;

import com.gestionale.model.Prenotazione;

import java.net.URISyntaxException;
import java.util.List;

public interface GestionaleApi {

    List<Prenotazione> getAllPrenotazioni() throws URISyntaxException;

    public void addPrenotazione(String nomeCliente, int numeroCani, String dataInizio, String dataFine) throws URISyntaxException;
}
