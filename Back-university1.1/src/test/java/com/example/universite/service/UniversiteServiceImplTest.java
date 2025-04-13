package com.example.universite.service;

import com.example.universite.entities.Universite;
import com.example.universite.repositories.UniversiteRepository;
import com.example.universite.services.UniversiteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UniversiteServiceImplTest {

    @Mock
    private UniversiteRepository universiteRepository;

    @InjectMocks
    private UniversiteServiceImpl universiteService;

    private Universite testUniversite;

    @BeforeEach
    void setUp() {
        testUniversite = new Universite(1, "Université de Tunis", 36.8, 10.2);
    }

    @Test
    void testFindUniversiteById_ShouldReturnUniversite() {
        // Simulation du comportement de la méthode findById du repository
        when(universiteRepository.findById(1)).thenReturn(Optional.of(testUniversite));

        // Exécution du service
        Universite result = universiteService.findUniversiteById(1);  // Appel de la méthode avec l'ID

        // Vérifications
        assertNotNull(result);
        assertEquals("Université de Tunis", result.getNomUniv());
        assertEquals(36.8, result.getLatitude());
        assertEquals(10.2, result.getLongitude());

        // Vérifier que la méthode findById a été appelée
        verify(universiteRepository, times(1)).findById(1);
    }

    @Test
    void testFindUniversiteById_WhenUniversiteNotFound_ShouldThrowException() {
        // Simulation du comportement lorsque l'université n'est pas trouvée
        when(universiteRepository.findById(1)).thenReturn(Optional.empty());

        // Vérification de l'exception levée
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> universiteService.findUniversiteById(1));

        assertEquals("Université introuvable", exception.getMessage());

        // Vérifier que la méthode findById a été appelée
        verify(universiteRepository, times(1)).findById(1);
    }
}
