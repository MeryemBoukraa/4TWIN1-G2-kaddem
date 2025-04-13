package com.example.ressource.controller;

import com.example.ressource.entity.Ressource;
import com.example.ressource.entity.Type;
import com.example.ressource.service.IRessourceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/Ressource")
public class RessourceRestController {
    private final Path rootLocation = Paths.get("upload-dir");

    IRessourceService ressourceService;
    @GetMapping()
    public List<Ressource> getRessources() {
        return  ressourceService.retrieveAllRessources();

    }
    @GetMapping("/{ressource-id}")
    public Ressource retrieveRessource(@PathVariable("ressource-id") Long rId) {
        return   ressourceService.retrieveRessource(rId);

    }

    @PostMapping
    public Ressource addRessource(
            @RequestParam(required = false) String titre,
            @RequestParam(required = false) String url,
            @RequestParam(required = false) String description,
            @RequestParam Type type,
            @RequestParam(required = false) MultipartFile pdfFile) {

        Ressource ressource = new Ressource();
        ressource.setTitre(titre);
        ressource.setUrl(url);
        ressource.setDescription(description);
        ressource.setType(type);

        return ressourceService.addRessource(ressource, pdfFile);
    }

    @DeleteMapping("/{ressource-id}")
    public void removeRessource(@PathVariable("ressource-id") Long chId) {
        ressourceService.removeRessource(chId);
    }



    @PutMapping("/{id}")
    public ResponseEntity<Ressource> updateRessource(
            @PathVariable Long id,
            @RequestParam(required = false) String titre,
            @RequestParam(required = false) String url,
            @RequestParam(required = false) String description,
            @RequestParam Type type,
            @RequestParam(required = false) String currentPdf, // Pour savoir si on garde l'ancien fichier
            @RequestParam(required = false) MultipartFile pdfFile) {

        Ressource ressourceDetails = new Ressource();
        ressourceDetails.setTitre(titre);
        ressourceDetails.setUrl(url);
        ressourceDetails.setDescription(description);
        ressourceDetails.setType(type);
        ressourceDetails.setPdf("true".equals(currentPdf) ? "keep" : null);

        try {
            Ressource updatedRessource = ressourceService.modifyRessource(id, ressourceDetails, pdfFile);
            return ResponseEntity.ok(updatedRessource);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
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

