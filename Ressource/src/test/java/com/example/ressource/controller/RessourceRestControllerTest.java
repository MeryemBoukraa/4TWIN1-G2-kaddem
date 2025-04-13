package com.example.ressource.controller;

import com.example.ressource.entity.Ressource;
import com.example.ressource.entity.Type;
import com.example.ressource.service.IRessourceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
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
        ressource.setType(Type.COURS);

        MockMultipartFile file = new MockMultipartFile("file", "test.pdf", "application/pdf", "Dummy content".getBytes());
        when(ressourceService.addRessource(any(Ressource.class), any())).thenReturn(ressource);

        Ressource result = ressourceRestController.addRessource("titre", null, "desc", Type.COURS, file);
        assertEquals("titre", result.getTitre());
    }

    @Test
    void testRemoveRessource() {
        ressourceRestController.removeRessource(1L);
        verify(ressourceService).removeRessource(1L);
    }

    @Test
    void testUpdateRessourceSuccess() {
        Ressource input = new Ressource();
        input.setTitre("updated");

        Ressource updated = new Ressource();
        updated.setTitre("updated");

        when(ressourceService.modifyRessource(eq(1L), any(), any())).thenReturn(updated);

        ResponseEntity<Ressource> response = ressourceRestController.updateRessource(1L, input, null);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("updated", response.getBody().getTitre());
    }

    @Test
    void testUpdateRessourceNotFound() {
        when(ressourceService.modifyRessource(eq(1L), any(), any()))
                .thenThrow(new RuntimeException("Ressource not found"));

        ResponseEntity<Ressource> response = ressourceRestController.updateRessource(1L, new Ressource(), null);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testUpdateRessourceError() {
        when(ressourceService.modifyRessource(eq(1L), any(), any()))
                .thenThrow(new RuntimeException("Erreur lors de la mise à jour"));

        ResponseEntity<Ressource> response = ressourceRestController.updateRessource(1L, new Ressource(), null);
        assertEquals(500, response.getStatusCodeValue());
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
