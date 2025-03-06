package com.gestionale.frontend.controller;

import com.gestionale.API.GestionaleApi;
import com.gestionale.model.DisponibilitaRequestDTO;
import com.gestionale.model.Prenotazione;
import com.gestionale.model.VoidResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
@Service
public class PrenotazioneControllerFe implements GestionaleApi {
    RestTemplate restTemplate;
    @Value("${gestionepensione.api.base-url}")
    private String BASE_URL;

    private static final String SERVICE_PATH="prenotazioneServices";

    @Override
    public List<Prenotazione> getAllPrenotazioni() throws URISyntaxException {
        String service = "/prenotazioni";
        URI uri = new URI(BASE_URL+SERVICE_PATH+service);
        ResponseEntity<List<Prenotazione>> prenotazioni = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Prenotazione>>() {});

        return prenotazioni.getBody();
    }

    @Override
    public void addPrenotazione(String nomeCliente, int numeroCani, String dataInizio, String dataFine) throws URISyntaxException {
        String service = "/nuovaPrenotazione";
        URI uri = new URI(BASE_URL + SERVICE_PATH+ service);

        Prenotazione prenotazione = new Prenotazione(nomeCliente, numeroCani, dataInizio, dataFine);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Prenotazione> requestEntity = new HttpEntity<>(prenotazione, headers);

        try {
            ResponseEntity<VoidResponseDTO> response = restTemplate.exchange(
                    uri,
                    HttpMethod.POST,
                    requestEntity,
                    VoidResponseDTO.class
            );

            if (response.getBody() != null) {
                System.out.println("Messaggio: " + response.getBody().getMessage());
            }
        } catch (RestClientException e) {
            System.err.println("Errore durante la richiesta: " + e.getMessage());
        }
    }

    @Override
    public Integer getDisponibilita(DisponibilitaRequestDTO disponibilitaRequestDTO) throws URISyntaxException {
        String service = "/nuovaPrenotazione";
        URI uri = new URI(BASE_URL +SERVICE_PATH+ service);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<DisponibilitaRequestDTO> requestEntity = new HttpEntity<>(disponibilitaRequestDTO, headers);
        ResponseEntity<Integer> response;

        response = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, Integer.class);

        return response.getBody();

    }

    @Override
    public VoidResponseDTO eliminaPrenotazione(String id) {
        return null;
    }

}
