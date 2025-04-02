package com.example.universite.repositories;

import com.example.universite.entities.Universite;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UniversiteRepository extends CrudRepository<Universite,Integer> {


}
