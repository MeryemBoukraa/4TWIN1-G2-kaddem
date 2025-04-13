package com.example.ressource.service;

import com.example.ressource.entity.Ressource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IRessourceService {
    public List<Ressource> retrieveAllRessources();
    public Ressource retrieveRessource(Long rId);

    Ressource addRessource(Ressource ressource, MultipartFile pdfFile);

    public void removeRessource(Long rId);



    Ressource modifyRessource(Long id, Ressource ressourceDetails, MultipartFile pdfFile);

    Map<String, Long> getNombreRessourcesParType();
    String generateSummaryForRessource(Long id) throws IOException;


}
