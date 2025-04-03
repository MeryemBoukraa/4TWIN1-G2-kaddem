package com.example.ressource.service;

import com.example.ressource.entity.Ressource;
import com.example.ressource.repository.RessourceRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class RessourceServiceImpl implements IRessourceService {
    RessourceRepository ressourceRepository;
    SummaryService summary;
    @Override
    public List<Ressource> retrieveAllRessources() {
        return ressourceRepository.findAll();
           }

    @Override
    public Ressource retrieveRessource(Long rId) {
        return ressourceRepository.findById(rId).get();
            }

    @Override
    public Ressource addRessource(Ressource r) {
        return ressourceRepository.save(r);
    }

    @Override
    public void removeRessource(Long rId) {
        ressourceRepository.deleteById(rId);

    }

    @Override
    public Ressource modifyRessource(Ressource r) {
        return ressourceRepository.save(r);
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
