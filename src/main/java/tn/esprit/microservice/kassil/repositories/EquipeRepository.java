package tn.esprit.microservice.kassil.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.microservice.kassil.entities.Equipe;

@Repository
public interface EquipeRepository extends CrudRepository<Equipe, Integer> {
}
