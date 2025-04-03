package com.example.ressource.service;

import com.example.ressource.entity.Ressource;
import com.example.ressource.repository.RessourceRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class RessourceServiceImplTest {
    @Mock
    private RessourceRepository ressourceRepository;

    @Mock
    private SummaryService summaryService;

    @InjectMocks
    private RessourceServiceImpl ressourceService;

    private Ressource testRessource;

    @BeforeEach
    void setUp() {
        testRessource = new Ressource();
        testRessource.setIdRessource(1L);
        testRessource.setPdf("https://example.com/sample.pdf");  // URL factice
    }

    @Test
    void generateSummaryForRessource_ShouldReturnSummary() throws IOException {
        // Simulation du résumé généré par SummaryService
        String mockSummary = "Ceci est un résumé de test...";
        when(ressourceRepository.findById(1L)).thenReturn(Optional.of(testRessource));
        when(summaryService.generateSummary(testRessource)).thenReturn(mockSummary);

        // Exécution du service
        String summary = ressourceService.generateSummaryForRessource(1L);

        // Vérifications
        assertNotNull(summary);
        assertEquals(mockSummary, summary);
        verify(ressourceRepository, times(1)).findById(1L);
        verify(summaryService, times(1)).generateSummary(testRessource);
    }

    @Test
    void generateSummaryForRessource_WhenRessourceNotFound_ShouldThrowException() {
        when(ressourceRepository.findById(2L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> ressourceService.generateSummaryForRessource(2L));

        assertEquals("Ressource introuvable", exception.getMessage());
        verify(ressourceRepository, times(1)).findById(2L);
        verifyNoInteractions(summaryService); // Ne doit pas appeler le service de résumé
    }

    @Test
    void generateSummaryForRessource_WhenPdfIsEmpty_ShouldThrowException() {
        testRessource.setPdf("");

        when(ressourceRepository.findById(1L)).thenReturn(Optional.of(testRessource));

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> ressourceService.generateSummaryForRessource(1L));

        assertEquals("Aucun fichier PDF associé à cette ressource.", exception.getMessage());
        verify(ressourceRepository, times(1)).findById(1L);
    }

    @Test
    void generateSummaryForRessource_WhenPdfDownloadFails_ShouldThrowIOException() throws IOException {
        when(ressourceRepository.findById(1L)).thenReturn(Optional.of(testRessource));
        when(summaryService.generateSummary(testRessource)).thenThrow(new IOException("Téléchargement échoué"));

        Exception exception = assertThrows(IOException.class,
                () -> ressourceService.generateSummaryForRessource(1L));

        assertEquals("Téléchargement échoué", exception.getMessage());
        verify(summaryService, times(1)).generateSummary(testRessource);
    }

    @Test
    void generateSummaryForRessource_WhenPdfContainsLargeText_ShouldTruncate() throws IOException {
        // Création d’un faux fichier PDF avec du texte
        File tempPdf = File.createTempFile("test_pdf", ".txt");
        try (FileWriter writer = new FileWriter(tempPdf)) {
            writer.write("Texte de test pour un PDF qui est très long et doit être tronqué après un certain nombre de caractères...");
        }

        String extractedText = "Texte de test pour un PDF qui est très long et doit être tronqué après un certain nombre de caractères...";
        String expectedSummary = extractedText.substring(0, 200) + "...";

        when(ressourceRepository.findById(1L)).thenReturn(Optional.of(testRessource));
        when(summaryService.generateSummary(testRessource)).thenReturn(expectedSummary);

        String summary = ressourceService.generateSummaryForRessource(1L);

        assertNotNull(summary);
        assertEquals(expectedSummary, summary);
    }
}
