package tn.esprit.microservice.formation.controller;

import lombok.AllArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tn.esprit.microservice.formation.entitiy.*;
import tn.esprit.microservice.formation.service.FormationService;
import tn.esprit.microservice.formation.service.IFormationService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
@ToString
@RestController
@AllArgsConstructor
@RequestMapping("/")
class FormationController {
    @Autowired
    private FormationService formationService;

    @PostMapping
    public ResponseEntity<Formation> addFormation(@RequestBody Formation formation) {
        if (formation == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
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
        formationService.addFormation(formation);
        return ResponseEntity.status(HttpStatus.CREATED).body(formation);
    }

    @GetMapping
public ResponseEntity<List<Formation>> getAllFormations() {
    List<Formation> formations = formationService.getAllFormation();
        System.out.println("Received formation: " + formations); // Debugging line
    return ResponseEntity.ok(formations);
}



    @GetMapping("/{id}")
    Formation getFormation(@PathVariable Long id) {
        return formationService.getFormation(id);
    }
    @DeleteMapping("/{id}")
    void deleteFormation(@PathVariable Long id) {
        formationService.deleteFormation(id);
    }
    @PatchMapping("/{id}")
    public Formation updateFormation(@PathVariable Long id, @RequestBody Formation formation) {
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
        return formationService.updateFormation(id, formation);
    }



}
