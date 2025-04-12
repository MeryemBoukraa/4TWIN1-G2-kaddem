package com.esprit.microservice.departementmicroservice.repositories;

import com.esprit.microservice.departementmicroservice.entities.Departement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartementRepository extends JpaRepository<Departement,Integer> {
}
