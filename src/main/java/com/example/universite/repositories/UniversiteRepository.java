package com.example.universite.repositories;

import com.example.universite.entities.Universite;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UniversiteRepository extends CrudRepository<Universite,Integer> {
    List<Universite> findAll(); // <-- surcharge ici pour forcer le retour en List

}


