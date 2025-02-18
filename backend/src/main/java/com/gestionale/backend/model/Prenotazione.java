package com.gestionale.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;


@Document(collection = "prenotazione")
public class Prenotazione {
    @Id
    private String id;
    private String nomeCliente;
    private int numeroCani;
    private LocalDate dataInizio;
    private LocalDate dataFine;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public LocalDate getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
    }

    public int getNumeroCani() {
        return numeroCani;
    }

    public void setNumeroCani(int numeroCani) {
        this.numeroCani = numeroCani;
    }

    public LocalDate getDataFine() {
        return dataFine;
    }

    public void setDataFine(LocalDate dataFine) {
        this.dataFine = dataFine;
    }
}
