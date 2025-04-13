package com.example.contrat.repositories;

import com.example.contrat.entities.Contrat;
import com.example.contrat.entities.HistoriqueModification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoriqueModificationRepository extends CrudRepository<HistoriqueModification, Integer> {
    @Query("SELECT h FROM HistoriqueModification h WHERE h.contrat.idContrat = :contratId")
    List<HistoriqueModification> findByContratId(@Param("contratId") Integer contratId);


}
