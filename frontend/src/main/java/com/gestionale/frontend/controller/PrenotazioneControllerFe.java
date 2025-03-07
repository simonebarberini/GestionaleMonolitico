package com.gestionale.frontend.controller;

import com.gestionale.API.GestionaleApi;
import com.gestionale.model.DisponibilitaRequestDTO;
import com.gestionale.model.PrenotazioneDTO;
import com.gestionale.model.VoidResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
@Service
public class PrenotazioneControllerFe implements GestionaleApi {

    RestTemplate restTemplate = new RestTemplate();
    @Value("${gestionepensione.api.base-url}")
    private String BASE_URL;

    private static final String SERVICE_PATH="/prenotazioneServices";

    @Override
    public List<PrenotazioneDTO> getAllPrenotazioni() throws URISyntaxException {
        String service = "/prenotazioni";
        URI uri = new URI(BASE_URL+SERVICE_PATH+service);
        ResponseEntity<List<PrenotazioneDTO>> prenotazioni = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<PrenotazioneDTO>>() {});

        return prenotazioni.getBody();
    }

    @Override
    public VoidResponseDTO addPrenotazione(PrenotazioneDTO prenotazioneDTO) throws URISyntaxException {
        String service = "/nuovaPrenotazione";
        URI uri = new URI(BASE_URL + SERVICE_PATH+ service);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PrenotazioneDTO> requestEntity = new HttpEntity<>(prenotazioneDTO, headers);

        ResponseEntity<VoidResponseDTO> response = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, VoidResponseDTO.class);
        return response.getBody();
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
