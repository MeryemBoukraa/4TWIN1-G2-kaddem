package tn.esprit.microservice.kassil.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.microservice.kassil.entities.Equipe;
import tn.esprit.microservice.kassil.repositories.EquipeRepository;
import tn.esprit.microservice.kassil.services.EquipeServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
 class EquipeServiceImplTest {

    @Mock
    private EquipeRepository equipeRepository;

    @InjectMocks
    private EquipeServiceImpl equipeService;

    private Equipe testEquipe;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testEquipe = new Equipe();
        testEquipe.setIdEquipe(1);
        testEquipe.setNomEquipe("Equipe AI");
    }

    @Test
    void testRetrieveEquipeSuccess() {
        when(equipeRepository.findById(1)).thenReturn(Optional.of(testEquipe));
        Equipe result = equipeService.retrieveEquipe(1);
        assertNotNull(result);
        assertEquals("Equipe AI", result.getNomEquipe());
    }

    @Test
    void testAddEquipe() {
        when(equipeRepository.save(any(Equipe.class))).thenReturn(testEquipe);
        Equipe result = equipeService.addEquipe(new Equipe());
        assertNotNull(result);
        verify(equipeRepository, times(1)).save(any(Equipe.class));
    }
}

