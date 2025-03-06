package com.gestionale.model;

import java.time.LocalDate;

public class DisponibilitaRequestDTO {
    private LocalDate dataInizio;
    private LocalDate dataFine;

    public DisponibilitaRequestDTO(LocalDate dataInizio, LocalDate dataFine) {
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
    }

    public LocalDate getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
    }

    public LocalDate getDataFine() {
        return dataFine;
    }

    public void setDataFine(LocalDate dataFine) {
        this.dataFine = dataFine;
    }
}
