package com.esprit.microservice.departementmicroservice.controllers;

import com.esprit.microservice.departementmicroservice.entities.Departement;
import com.esprit.microservice.departementmicroservice.services.Impl.DepartementService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/departementMicroService")
public class DepartementController {
    @Autowired
     private  DepartementService departementService;
    // http://localhost:8089/service/departement/retrieve-all-departements
    @GetMapping("/retrieve-all-departements")
    public List<Departement> getDepartements() {
        List<Departement> listDepartements = this.departementService.retrieveAllDepartements();
        return listDepartements;
    }
    // http://localhost:8089/service/departement/retrieve-departement/8
    @GetMapping("/retrieve-departement/{departement-id}")
    public Departement retrieveDepartement(@PathVariable("departement-id") Integer departementId) {
        return this.departementService.retrieveDepartement(departementId);
    }

    // http://localhost:8089/service/departement/add-departement
    @PostMapping("/add-departement")
    public Departement addDepartement(@RequestBody Departement d){
        Departement departement = this.departementService.addDepartement(d);
        System.out.println("Received Departement: " + d.toString());
        return departement;
    }

    // http://localhost:8089/service/departement/remove-departement/1
    @DeleteMapping("/remove-departement/{departement-id}")
    public void removeDepartement(@PathVariable("departement-id") Integer departementId) {
        this.departementService.deleteDepartement(departementId);
    }

    // http://localhost:8089/service/departement/update-departement/1
    @PutMapping("/update-departement/{id}")
    public Departement updateDepartement(@RequestBody Departement e,@PathVariable Integer id) {
        Departement departement= this.departementService.updateDepartement(e,id);
        return departement;
    }
}
