package com.example.ressource.service;

import com.example.ressource.entity.Ressource;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.junit.jupiter.api.*;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class SummaryServiceTest {

    private SummaryService summaryService;

    private File tempPdfFile;
    private String tempPdfFileName;

    @BeforeEach
    void setUp() throws IOException {
        summaryService = new SummaryService();

        // Créer un fichier PDF temporaire
        tempPdfFile = File.createTempFile("test", ".pdf");
        tempPdfFile.deleteOnExit(); // Supprimer automatiquement après le test
        tempPdfFileName = tempPdfFile.getName();

        // Écrire du texte dans le fichier PDF
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(50, 700);
                contentStream.showText("Ceci est un texte de test dans le fichier PDF pour générer un résumé.");
                contentStream.endText();
            }
            document.save(tempPdfFile);
        }

        // Injecter le chemin du répertoire contenant le fichier PDF
        ReflectionTestUtils.setField(summaryService, "uploadDir", tempPdfFile.getParent());
    }

    @Test
    void testGenerateSummary_success() throws IOException {
        Ressource ressource = new Ressource();
        ressource.setPdf(tempPdfFileName);

        String summary = summaryService.generateSummary(ressource);
        assertNotNull(summary);
        assertTrue(summary.contains("Ceci est un texte de test"));
    }

    @Test
    void testGenerateSummary_withNullRessource_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> summaryService.generateSummary(null));
    }

    @Test
    void testGenerateSummary_withEmptyFileName_throwsException() {
        Ressource ressource = new Ressource();
        ressource.setPdf("");

        assertThrows(IllegalArgumentException.class, () -> summaryService.generateSummary(ressource));
    }

    @Test
    void testGenerateSummary_withMissingFile_throwsIOException() {
        Ressource ressource = new Ressource();
        ressource.setPdf("fichier-inexistant.pdf");

        assertThrows(IOException.class, () -> summaryService.generateSummary(ressource));
    }
}
