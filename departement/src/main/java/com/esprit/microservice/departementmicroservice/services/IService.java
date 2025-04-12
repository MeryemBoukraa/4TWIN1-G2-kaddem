package com.esprit.microservice.departementmicroservice.services;

import com.esprit.microservice.departementmicroservice.entities.Departement;

import java.util.List;

public interface IService {
    public List<Departement> retrieveAllDepartements();

    public Departement addDepartement (Departement d);

    public   Departement updateDepartement (Departement d, Integer id );

    public  Departement retrieveDepartement (Integer idDepart);

    public  void deleteDepartement(Integer idDepartement);
}
