package com.gestionale.backend.repo;

import com.gestionale.model.Prenotazione;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PrenotazioneRepository extends MongoRepository<Prenotazione, String> {

    @Query("{ 'dataFine' : { $gte: ?0 }, 'dataInizio' : { $lte: ?1 } }")
    List<Prenotazione> findSovrapposizionePrenotaioni(LocalDate startDate, LocalDate endDate);
}
