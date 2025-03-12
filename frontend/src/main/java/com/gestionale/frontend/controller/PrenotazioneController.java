package com.gestionale.frontend.controller;



import com.gestionale.model.PrenotazioneDTO;
import com.gestionale.model.VoidResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PrenotazioneController {

    @Autowired
    PrenotazioneControllerFe prenotazioneControllerFe;

    @GetMapping("/")
    public String home(Model model) {
        return "index";
    }

    @GetMapping("/prenotazioniView")
    public String listaPrenotazioni(Model model) throws URISyntaxException {
        List<PrenotazioneDTO> prenotazioni = new ArrayList<>();
        prenotazioni = prenotazioneControllerFe.getAllPrenotazioni();
        model.addAttribute("prenotazioni", prenotazioni);
        model.addAttribute("pageTitle", "Lista Prenotazioni");
        return "lista-prenotazioni";
    }

    @GetMapping("/nuovaPrenotazioneView")
    public String nuovaPrenotazioneForm(Model model) {
        model.addAttribute("prenotazione", new PrenotazioneDTO());
        return "nuova-prenotazione";
    }

    @PostMapping("/salvaPrenotazioneView")
    public String salvaPrenotazione(@ModelAttribute PrenotazioneDTO prenotazione) throws URISyntaxException {
        VoidResponseDTO responseDTO = prenotazioneControllerFe.addPrenotazione(prenotazione);
        if (!responseDTO.isSuccess()){
            return "redirect:/prenotazioniView";
        }
        return "redirect:/prenotazioniView";
    }

    @PostMapping("/elimina")
    public String eliminaPrenotazione(@RequestParam String id, RedirectAttributes redirectAttributes) throws URISyntaxException {
        VoidResponseDTO responseDTO = prenotazioneControllerFe.eliminaPrenotazione(id);

        if (!responseDTO.isSuccess()) {
            redirectAttributes.addFlashAttribute("error", responseDTO.getMessage() + responseDTO.getException().getMessage());
        } else {
            redirectAttributes.addFlashAttribute("success", responseDTO.getMessage());
        }

        return "redirect:/prenotazioniView";
    }
}
