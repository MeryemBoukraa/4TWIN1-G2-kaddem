package tn.esprit.microservice.kassil.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import tn.esprit.microservice.kassil.entities.Equipe;
import tn.esprit.microservice.kassil.services.IEquipeService;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/equipe")
public class EquipeRestController {
    private final IEquipeService equipeService;

    @GetMapping("/retrieve-all-equipes")
    public ResponseEntity<List<Equipe>> getEquipes() {
        List<Equipe> listEquipes = equipeService.retrieveAllEquipes();
        return ResponseEntity.ok(listEquipes);
    }

    @GetMapping("/retrieve-equipe/{equipe-id}")
    public ResponseEntity<Equipe> retrieveEquipe(@PathVariable("equipe-id") Integer equipeId) {
        Equipe equipe = equipeService.retrieveEquipe(equipeId);
        return ResponseEntity.ok(equipe);
    }

    @PostMapping("/add-equipe")
    public ResponseEntity<Equipe> addEquipe(@RequestBody Equipe e) {
        Equipe equipe = equipeService.addEquipe(e);
        return ResponseEntity.ok(equipe);
    }

    @DeleteMapping("/remove-equipe/{equipe-id}")
    public ResponseEntity<Void> removeEquipe(@PathVariable("equipe-id") Integer equipeId) {
        equipeService.deleteEquipe(equipeId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update-equipe")
    public ResponseEntity<Equipe> updateEtudiant(@RequestBody Equipe e) {
        Equipe equipe = equipeService.updateEquipe(e);
        return ResponseEntity.ok(equipe);
    }

}