package com.example.ressource.service;

import com.example.ressource.entity.Ressource;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class SummaryService {
    @Value("${file.upload-dir}") // Doit correspondre à votre propriété dans application.properties
    private String uploadDir;

    public String generateSummary(Ressource ressource) throws IOException {
        if (ressource == null || ressource.getPdf() == null || ressource.getPdf().isEmpty()) {
            throw new IllegalArgumentException("Aucun fichier PDF associé à cette ressource.");
        }

        // Chemin complet vers le fichier
        Path filePath = Paths.get(uploadDir).resolve(ressource.getPdf());
        File pdfFile = filePath.toFile();

        if (!pdfFile.exists()) {
            throw new IOException("Fichier PDF introuvable: " + filePath);
        }

        // Extraire le texte du PDF
        try (PDDocument document = PDDocument.load(pdfFile)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);

            // Générer un résumé (ex : 200 premiers caractères)
            return text.length() > 200 ? text.substring(0, 200) + "..." : text;
        }
    }
}