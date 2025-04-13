package tn.esprit.microservice.formation.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.microservice.formation.entitiy.Formation;
import tn.esprit.microservice.formation.entitiy.Statut;
import tn.esprit.microservice.formation.repository.FormationRepo;

import java.time.LocalDate;
import java.time.ZoneId;
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
    public Formation getFormation(Long id) {
        return formationRepo.findById(id).orElse(null);
    }
    public List<Formation> getAllFormation()
    {
        return formationRepo.findAll();
    }

    public Formation updateFormation(Long id,Formation formation)
    {
        Formation existing = formationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Formation not found with id " + id));
        LocalDate today = LocalDate.now();
        LocalDate localDateFormation = formation.getDateFormation().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        if (localDateFormation.isBefore(today)) {
            formation.setStatut(Statut.TERMINEE);
        }
        else if (localDateFormation.isAfter(today)) {
            formation.setStatut(Statut.PLANNIFIEE);
        }
        else
            formation.setStatut(Statut.EN_COURS);
        existing.setDateFormation(formation.getDateFormation());
        existing.setDescription(formation.getDescription());
        existing.setNomFormation(formation.getNomFormation());
        existing.setNombrePlace(formation.getNombrePlace());
        existing.setPrix(formation.getPrix());
        existing.setStatut(formation.getStatut());

        return formationRepo.save(existing);
    }
    public void deleteFormation(Long id)
    {
        formationRepo.deleteById(id);
    }

}
