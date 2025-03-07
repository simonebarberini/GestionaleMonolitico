package com.gestionale.backend.repo;

import com.gestionale.model.PrenotazioneDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PrenotazioneRepository extends MongoRepository<PrenotazioneDTO, String> {

    @Query("{ 'dataFine' : { $gte: ?0 }, 'dataInizio' : { $lte: ?1 } }")
    List<PrenotazioneDTO> findSovrapposizionePrenotaioni(LocalDate startDate, LocalDate endDate);
}
