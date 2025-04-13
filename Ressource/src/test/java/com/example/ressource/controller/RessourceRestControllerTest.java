package com.example.ressource.controller;

import com.example.ressource.entity.Ressource;
import com.example.ressource.entity.Type;
import com.example.ressource.service.IRessourceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class RessourceRestControllerTest {

    @InjectMocks
    private RessourceRestController ressourceRestController;

    @Mock
    private IRessourceService ressourceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test for addRessource with PDF file
    @Test
    void testAddRessourceWithPdf() throws Exception {
        Ressource ressource = new Ressource();
        ressource.setTitre("Test Ressource");
        ressource.setUrl("http://test.com");
        ressource.setDescription("Test Description");
        ressource.setType(Type.E_BOOK);

        MockMultipartFile pdfFile = new MockMultipartFile("file", "test.pdf", "application/pdf", "Test PDF content".getBytes());

        when(ressourceService.addRessource(any(Ressource.class), any())).thenReturn(ressource);

        Ressource response = ressourceRestController.addRessource("Test Ressource", "http://test.com", "Test Description", Type.E_BOOK, pdfFile);

        assertNotNull(response);
        assertEquals(ressource.getTitre(), response.getTitre());
        verify(ressourceService, times(1)).addRessource(any(Ressource.class), any());
    }

    // Test for addRessource without PDF file
    @Test
    void testAddRessourceWithoutPdf() throws Exception {
        Ressource ressource = new Ressource();
        ressource.setTitre("Test Ressource");
        ressource.setUrl("http://test.com");
        ressource.setDescription("Test Description");
        ressource.setType(Type.COURS);

        when(ressourceService.addRessource(any(Ressource.class), isNull())).thenReturn(ressource);

        Ressource response = ressourceRestController.addRessource("Test Ressource", "http://test.com", "Test Description", Type.COURS, null);

        assertNotNull(response);
        assertEquals(ressource.getTitre(), response.getTitre());
        verify(ressourceService, times(1)).addRessource(any(Ressource.class), isNull());
    }

    // Test for removeRessource
    @Test
    void testRemoveRessource() {
        doNothing().when(ressourceService).removeRessource(1L);

        ressourceRestController.removeRessource(1L);

        verify(ressourceService, times(1)).removeRessource(1L);
    }

    // Test for updateRessource
    @Test
    void testUpdateRessource() {
        Ressource input = new Ressource();
        input.setTitre("Updated Title");
        input.setUrl("http://updated.com");
        input.setDescription("Updated Description");
        input.setType(Type.ARTICLE);

        Ressource updatedRessource = new Ressource();
        updatedRessource.setIdRessource(1L);
        updatedRessource.setTitre("Updated Title");
        updatedRessource.setUrl("http://updated.com");
        updatedRessource.setDescription("Updated Description");
        updatedRessource.setType(Type.ARTICLE);

        when(ressourceService.modifyRessource(eq(1L), any(Ressource.class), isNull())).thenReturn(updatedRessource);

        ResponseEntity<Ressource> response = ressourceRestController.updateRessource(1L, input, null);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Updated Title", response.getBody().getTitre());
        verify(ressourceService, times(1)).modifyRessource(eq(1L), any(Ressource.class), isNull());
    }

    // Test for updateRessource when resource not found
    @Test
    void testUpdateRessourceNotFound() {
        Ressource input = new Ressource();
        input.setTitre("Non Existing Title");
        input.setUrl("http://nonexisting.com");
        input.setDescription("Non Existing Description");
        input.setType(Type.ARTICLE);

        when(ressourceService.modifyRessource(eq(99L), any(Ressource.class), isNull())).thenThrow(new RuntimeException("Ressource not found"));

        ResponseEntity<Ressource> response = ressourceRestController.updateRessource(99L, input, null);

        assertEquals(404, response.getStatusCodeValue());
        verify(ressourceService, times(1)).modifyRessource(eq(99L), any(Ressource.class), isNull());
    }

    // Test for getStatsParType
    @Test
    void testGetStatsParType() {
        Map<String, Long> stats = Map.of(
                "E_BOOK", 5L,
                "COURS", 10L
        );

        when(ressourceService.getNombreRessourcesParType()).thenReturn(stats);

        Map<String, Long> response = ressourceRestController.getStatsParType();

        assertEquals(2, response.size());
        assertEquals(5L, response.get("E_BOOK"));
        assertEquals(10L, response.get("COURS"));
        verify(ressourceService, times(1)).getNombreRessourcesParType();
    }

    // Test for getPdfSummary
    @Test
    void testGetPdfSummary() throws IOException {
        String expectedSummary = "This is a summary of the PDF content...";
        when(ressourceService.generateSummaryForRessource(1L)).thenReturn(expectedSummary);

        String summary = ressourceRestController.getPdfSummary(1L);

        assertNotNull(summary);
        assertEquals(expectedSummary, summary);
        verify(ressourceService, times(1)).generateSummaryForRessource(1L);
    }
}
