package com.example.ressource.controller;

import com.example.ressource.entity.Ressource;
import com.example.ressource.entity.Type;
import com.example.ressource.service.IRessourceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RessourceRestControllerTest {

    @InjectMocks
    private RessourceRestController ressourceRestController;

    @Mock
    private IRessourceService ressourceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetRessources() {
        List<Ressource> mockList = Arrays.asList(new Ressource(), new Ressource());
        when(ressourceService.retrieveAllRessources()).thenReturn(mockList);

        List<Ressource> result = ressourceRestController.getRessources();
        assertEquals(2, result.size());
        verify(ressourceService).retrieveAllRessources();
    }

    @Test
    void testRetrieveRessource() {
        Ressource mock = new Ressource();
        when(ressourceService.retrieveRessource(1L)).thenReturn(mock);

        Ressource result = ressourceRestController.retrieveRessource(1L);
        assertEquals(mock, result);
    }

    @Test
    void testAddRessource() {
        Ressource ressource = new Ressource();
        ressource.setTitre("titre");
        ressource.setDescription("desc");
        ressource.setType(Type.Cours);

        MockMultipartFile file = new MockMultipartFile("file", "test.pdf", "application/pdf", "Dummy content".getBytes());
        when(ressourceService.addRessource(any(Ressource.class), any())).thenReturn(ressource);

        Ressource result = ressourceRestController.addRessource("titre", null, "desc", Type.Cours, file);
        assertEquals("titre", result.getTitre());
    }

    @Test
    void testRemoveRessource() {
        ressourceRestController.removeRessource(1L);
        verify(ressourceService).removeRessource(1L);
    }


    @Test
    void testGetStatsParType() {
        Map<String, Long> mockStats = Map.of("COURS", 3L, "ARTICLE", 1L);
        when(ressourceService.getNombreRessourcesParType()).thenReturn(mockStats);

        Map<String, Long> result = ressourceRestController.getStatsParType();
        assertEquals(2, result.size());
        assertEquals(3L, result.get("COURS"));
    }

    @Test
    void testGetPdfSummary() throws IOException {
        when(ressourceService.generateSummaryForRessource(1L)).thenReturn("Résumé du PDF");

        String result = ressourceRestController.getPdfSummary(1L);
        assertEquals("Résumé du PDF", result);
    }
}
