package tn.esprit.microservice.formation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.microservice.formation.entitiy.Formation;

@Repository
public interface FormationRepo extends JpaRepository<Formation, Long> {
}
