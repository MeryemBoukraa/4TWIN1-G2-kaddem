package tn.esprit.microservice.formation.service;

import tn.esprit.microservice.formation.entitiy.Formation;

import java.util.List;

public interface IFormationService {
    public Formation addFormation(Formation formation);
    public Formation getFormation(int id);
    public List<Formation> getAllFormation();
    public Formation updateFormation(Formation formation);
    public void deleteFormation(int id);

}
