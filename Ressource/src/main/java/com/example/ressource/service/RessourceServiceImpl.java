package com.example.ressource.service;

import com.example.ressource.entity.Ressource;
import com.example.ressource.repository.RessourceRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class RessourceServiceImpl implements IRessourceService {
    RessourceRepository ressourceRepository;
    SummaryService summary;
    private final Path rootLocation = Paths.get("upload-dir");

    @Override
    public List<Ressource> retrieveAllRessources() {
        return ressourceRepository.findAll();
           }

    @Override
    public Ressource retrieveRessource(Long rId) {
        Optional<Ressource> optionalRessource = ressourceRepository.findById(rId);
    
    if (optionalRessource.isPresent()) {
        return optionalRessource.get();
    } else {
        return null; // ou gérer autrement si tu veux, par exemple un message ou log
    }
            }

    @Override
public Ressource addRessource(Ressource ressource, MultipartFile pdfFile) {
    if (pdfFile != null && !pdfFile.isEmpty()) {
        try {
            // Créer le répertoire s'il n'existe pas
            if (!Files.exists(rootLocation)) {
                Files.createDirectories(rootLocation);
            }

            // Générer un nom de fichier unique
            String filename = UUID.randomUUID() + "-" + pdfFile.getOriginalFilename();
            Files.copy(pdfFile.getInputStream(), this.rootLocation.resolve(filename));
            ressource.setPdf(filename);
        } catch (IOException e) {
            System.err.println("Erreur lors de l'enregistrement du fichier : " + e.getMessage());
            // Tu peux soit continuer sans fichier, soit mettre une valeur par défaut
            ressource.setPdf(null);
        }
    }
    return ressourceRepository.save(ressource);
}



    @Override
    public void removeRessource(Long rId) {
        ressourceRepository.deleteById(rId);

    }

    @Override
public Ressource modifyRessource(Long id, Ressource ressourceDetails, MultipartFile pdfFile) {
    return ressourceRepository.findById(id)
            .map(ressource -> {
                updateSimpleFields(ressource, ressourceDetails);
                handlePdfFile(ressource, pdfFile, ressourceDetails);
                return ressourceRepository.save(ressource);
            })
            .orElseThrow(() -> new RuntimeException("Ressource not found with id " + id));
}

private void updateSimpleFields(Ressource ressource, Ressource ressourceDetails) {
    ressource.setTitre(ressourceDetails.getTitre());
    ressource.setUrl(ressourceDetails.getUrl());
    ressource.setDescription(ressourceDetails.getDescription());
    ressource.setType(ressourceDetails.getType());
}

private void handlePdfFile(Ressource ressource, MultipartFile pdfFile, Ressource ressourceDetails) {
    if (pdfFile != null && !pdfFile.isEmpty()) {
        handleNewPdfFile(ressource, pdfFile);
    } else if (ressourceDetails.getPdf() == null) {
        handlePdfDeletion(ressource);
    }
}

private void handleNewPdfFile(Ressource ressource, MultipartFile pdfFile) {
    try {
        deleteExistingPdfFile(ressource);
        String filename = storeNewPdfFile(pdfFile);
        ressource.setPdf(filename);
    } catch (IOException e) {
        System.err.println("Erreur lors de la mise à jour du fichier PDF : " + e.getMessage());
        // Optionnel : garder l'ancien fichier ou vider le champ
        // ressource.setPdf(null);
    }
}


private void handlePdfDeletion(Ressource ressource) {
    try {
        deleteExistingPdfFile(ressource);
        ressource.setPdf(null);
    } catch (IOException e) {
        System.err.println("Erreur lors de la suppression de l'ancien fichier : " + e.getMessage());
        // Tu peux aussi ignorer, ou logger plus proprement si besoin
    }
}


private void deleteExistingPdfFile(Ressource ressource) throws IOException {
    if (ressource.getPdf() != null) {
        Path oldFile = rootLocation.resolve(ressource.getPdf());
        Files.deleteIfExists(oldFile);
    }
}

private String storeNewPdfFile(MultipartFile pdfFile) throws IOException {
    String filename = UUID.randomUUID() + "-" + pdfFile.getOriginalFilename();
    Files.copy(pdfFile.getInputStream(), this.rootLocation.resolve(filename));
    return filename;
}



    @Override
    public Map<String, Long> getNombreRessourcesParType() {
        List<Object[]> result = ressourceRepository.countRessourcesByType();
        Map<String, Long> stats = new HashMap<>();
        for (Object[] row : result) {
            stats.put(row[0].toString(), (Long) row[1]);
        }
        return stats;
    }
    @Override
    public String generateSummaryForRessource(Long id) throws IOException {
        Ressource ressource = ressourceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ressource introuvable"));

        return summary.generateSummary(ressource);
    }
}
