package com.example.contrat.controllers;

import com.example.contrat.entities.Contrat;
import com.example.contrat.entities.HistoriqueModification;
import com.example.contrat.repositories.HistoriqueModificationRepository;
import com.example.contrat.services.HistoriqueModificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.mockito.Mockito.*;

class HistoriqueModificationServiceTest {

    @InjectMocks
    private HistoriqueModificationService historiqueModificationService;

    @Mock
    private HistoriqueModificationRepository historiqueModificationRepository;

    @Mock
    private Contrat contrat;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialisation des mocks
    }

    @Test
    void testAjouterHistorique() {
        // Simuler le comportement du contrat
        when(contrat.getIdContrat()).thenReturn(1);

        // Appeler la méthode que vous voulez tester
        historiqueModificationService.ajouterHistorique(contrat, "Modification de statut", "Ajout d'un document");

        // Vérifier que la méthode save() a bien été appelée
        verify(historiqueModificationRepository, times(1)).save(any(HistoriqueModification.class));
    }
}
