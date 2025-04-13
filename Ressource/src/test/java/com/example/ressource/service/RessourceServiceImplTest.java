package com.example.ressource.service;

import com.example.ressource.entity.Ressource;
import com.example.ressource.entity.Type;
import com.example.ressource.repository.RessourceRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class RessourceServiceImplTest {

    @Mock
    private RessourceRepository ressourceRepository;

    @Mock
    private SummaryService summaryService;

    @InjectMocks
    private RessourceServiceImpl ressourceService;

    private Ressource ressource;

    @BeforeEach
    void setUp() {
        ressource = new Ressource();
        ressource.setIdRessource(1L);
        ressource.setTitre("Titre Test");
        ressource.setDescription("Description");
        ressource.setType(Type.valueOf("ARTICLE"));
        ressource.setPdf("test.pdf");
    }

    @Test
    void testRetrieveAllRessources() {
        List<Ressource> ressources = Arrays.asList(ressource);
        when(ressourceRepository.findAll()).thenReturn(ressources);

        List<Ressource> result = ressourceService.retrieveAllRessources();

        assertEquals(1, result.size());
        verify(ressourceRepository).findAll();
    }

    @Test
    void testRetrieveRessource_WhenExists() {
        when(ressourceRepository.findById(1L)).thenReturn(Optional.of(ressource));
        Ressource found = ressourceService.retrieveRessource(1L);

        assertNotNull(found);
        assertEquals("Titre Test", found.getTitre());
    }

    @Test
    void testRetrieveRessource_WhenNotExists() {
        when(ressourceRepository.findById(2L)).thenReturn(Optional.empty());
        Ressource found = ressourceService.retrieveRessource(2L);
        assertNull(found);
    }

    @Test
    void testAddRessource_WithPdf() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "test.pdf", "application/pdf", "PDF Content".getBytes());
        when(ressourceRepository.save(any(Ressource.class))).thenReturn(ressource);

        Ressource saved = ressourceService.addRessource(ressource, file);

        assertNotNull(saved);
        verify(ressourceRepository).save(any(Ressource.class));
    }

    @Test
    void testRemoveRessource() {
        doNothing().when(ressourceRepository).deleteById(1L);
        ressourceService.removeRessource(1L);
        verify(ressourceRepository).deleteById(1L);
    }

    @Test
    void testGetNombreRessourcesParType() {
        List<Object[]> data = List.of(new Object[]{"PDF", 3L}, new Object[]{"VIDEO", 2L});
        when(ressourceRepository.countRessourcesByType()).thenReturn(data);

        Map<String, Long> stats = ressourceService.getNombreRessourcesParType();

        assertEquals(2, stats.size());
        assertEquals(3L, stats.get("PDF"));
        assertEquals(2L, stats.get("VIDEO"));
    }

    @Test
    void testGenerateSummaryForRessource_Success() throws IOException {
        when(ressourceRepository.findById(1L)).thenReturn(Optional.of(ressource));
        when(summaryService.generateSummary(ressource)).thenReturn("Résumé");

        String result = ressourceService.generateSummaryForRessource(1L);
        assertEquals("Résumé", result);
        verify(summaryService).generateSummary(ressource);
    }

    @Test
    void testGenerateSummaryForRessource_NotFound() {
        when(ressourceRepository.findById(2L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> ressourceService.generateSummaryForRessource(2L));
        assertEquals("Ressource introuvable", exception.getMessage());
    }
}
