package com.example.ressource.service;

import com.example.ressource.entity.Ressource;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
@Service
public class SummaryService {
    public String generateSummary(Ressource ressource) throws IOException {
        if (ressource == null || ressource.getPdf() == null || ressource.getPdf().isEmpty()) {
            throw new IllegalArgumentException("Aucun fichier PDF associé à cette ressource.");
        }

        // Télécharger le fichier PDF depuis l'URL stockée en base
        File pdfFile = downloadFile(ressource.getPdf());

        if (!pdfFile.exists()) {
            throw new IOException("Impossible de télécharger le fichier PDF.");
        }

        // Extraire le texte du PDF
        try (PDDocument document = PDDocument.load(pdfFile)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);

            // Générer un résumé (ex : on prend les 200 premiers caractères)
            return text.length() > 200 ? text.substring(0, 200) + "..." : text;
        }
    }

    private File downloadFile(String fileUrl) throws IOException {
        URL url = new URL(fileUrl);
        File tempFile = File.createTempFile("pdf_resource", ".pdf");
        try (var inputStream = url.openStream(); var outputStream = java.nio.file.Files.newOutputStream(tempFile.toPath())) {
            inputStream.transferTo(outputStream);
        }
        return tempFile;
    }
}
