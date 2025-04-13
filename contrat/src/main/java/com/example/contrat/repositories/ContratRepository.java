package com.example.contrat.repositories;

import com.example.contrat.entities.Contrat;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ContratRepository extends CrudRepository<Contrat, Integer> {




}
