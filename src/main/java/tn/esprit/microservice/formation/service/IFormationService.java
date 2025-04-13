package tn.esprit.microservice.formation.service;

import tn.esprit.microservice.formation.entitiy.Formation;

import java.util.List;

public interface IFormationService {
    public Formation addFormation(Formation formation);
    public Formation getFormation(Long id);
    public List<Formation> getAllFormation();
    public Formation updateFormation(Long id,Formation formation);
    public void deleteFormation(Long id);

}
