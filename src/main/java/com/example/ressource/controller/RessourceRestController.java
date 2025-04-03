package com.example.ressource.controller;

import com.example.ressource.entity.Ressource;
import com.example.ressource.service.IRessourceService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/Ressource")
@CrossOrigin(origins = "http://localhost:4200")
public class RessourceRestController {

    IRessourceService ressourceService;
    @GetMapping()
    public List<Ressource> getRessources() {
        return  ressourceService.retrieveAllRessources();

    }
    @GetMapping("/{ressource-id}")
    public Ressource retrieveRessource(@PathVariable("ressource-id") Long rId) {
        return   ressourceService.retrieveRessource(rId);

    }

    @PostMapping()
    public Ressource addRessource(@RequestBody Ressource r) {
        Ressource ressource = ressourceService.addRessource(r);
        return ressource;
    }

    @DeleteMapping("/{ressource-id}")
    public void removeRessource(@PathVariable("ressource-id") Long chId) {
        ressourceService.removeRessource(chId);
    }

    @PutMapping()
    public Ressource modifyRessource(@RequestBody Ressource r) {
        Ressource ressource = ressourceService.modifyRessource(r);
        return ressource;
    }

    @GetMapping("/stats")
    public Map<String, Long> getStatsParType() {
        return ressourceService.getNombreRessourcesParType();
    }

    @GetMapping("/{id}/summary")
    public String getPdfSummary(@PathVariable Long id) throws IOException {
        return ressourceService.generateSummaryForRessource(id);
    }
}
