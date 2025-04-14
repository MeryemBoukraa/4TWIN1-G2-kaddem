package com.example.contrat.services;

import com.example.contrat.entities.Contrat;
import com.example.contrat.entities.HistoriqueModification;
import com.example.contrat.repositories.HistoriqueModificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HistoriqueModificationServiceTest {

    @InjectMocks
    private HistoriqueModificationService historiqueService;

    @Mock
    private HistoriqueModificationRepository historiqueRepo;

    private Contrat contrat;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        contrat = new Contrat();
        contrat.setIdContrat(1);
    }

    @Test
    void testAjouterHistorique() {
        doAnswer(invocation -> {
            HistoriqueModification historique = invocation.getArgument(0);
            assertEquals("Ajout", historique.getAction());
            assertEquals("Détail test", historique.getDetails());
            assertEquals(contrat, historique.getContrat());
            assertNotNull(historique.getDateModification());
            return null;
        }).when(historiqueRepo).save(any(HistoriqueModification.class));

        historiqueService.ajouterHistorique(contrat, "Ajout", "Détail test");

        verify(historiqueRepo, times(1)).save(any(HistoriqueModification.class));
    }

    @Test
    void testGetHistoriqueByContrat() {
        List<HistoriqueModification> historiqueList = Arrays.asList(new HistoriqueModification(), new HistoriqueModification());
        when(historiqueRepo.findByContratId(1)).thenReturn(historiqueList);

        List<HistoriqueModification> result = historiqueService.getHistoriqueByContrat(1);

        assertEquals(2, result.size());
        verify(historiqueRepo).findByContratId(1);
    }
}
