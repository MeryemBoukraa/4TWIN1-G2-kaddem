package tn.esprit.microservice.formation.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.microservice.formation.entitiy.Formation;
import tn.esprit.microservice.formation.repository.FormationRepo;

import java.util.List;
@Service
@AllArgsConstructor
public class FormationService implements IFormationService {
    @Autowired
    FormationRepo formationRepo;
    public Formation addFormation(Formation formation)
    {
        return formationRepo.save(formation);
    }
    public Formation getFormation(Long id)
    {
        return formationRepo.getReferenceById(id);
    }
    public List<Formation> getAllFormation()
    {
        return formationRepo.findAll();
    }

    public Formation updateFormation(Formation formation)
    {
        return formationRepo.save(formation);
    }
    public void deleteFormation(Long id)
    {
        formationRepo.deleteById(id);
    }

}
