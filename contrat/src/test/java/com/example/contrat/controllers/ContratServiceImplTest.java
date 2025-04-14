package com.example.contrat.controllers;

import com.example.contrat.entities.Contrat;
import com.example.contrat.repositories.ContratRepository;
import com.example.contrat.services.ContratServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContratServiceImplTest {

    @InjectMocks
    private ContratServiceImpl contratService;

    @Mock
    private ContratRepository contratRepository;

    private Contrat contrat;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        contrat = new Contrat();
        contrat.setIdContrat(1);
    }

    @Test
    void testRetrieveAllContrats() {
        List<Contrat> mockList = Arrays.asList(new Contrat(), new Contrat());
        when(contratRepository.findAll()).thenReturn(mockList);

        List<Contrat> result = contratService.retrieveAllContrats();

        assertEquals(2, result.size());
        verify(contratRepository, times(1)).findAll();
    }

    @Test
    void testUpdateContrat() {
        when(contratRepository.save(contrat)).thenReturn(contrat);

        Contrat updated = contratService.updateContrat(contrat);

        assertNotNull(updated);
        verify(contratRepository).save(contrat);
    }

    @Test
    void testAddContrat() {
        when(contratRepository.save(contrat)).thenReturn(contrat);

        Contrat added = contratService.addContrat(contrat);

        assertNotNull(added);
        verify(contratRepository).save(contrat);
    }

    @Test
    void testRetrieveContrat() {
        when(contratRepository.findById(1)).thenReturn(Optional.of(contrat));

        Contrat result = contratService.retrieveContrat(1);

        assertNotNull(result);
        assertEquals(1, result.getIdContrat());
        verify(contratRepository).findById(1);
    }

    @Test
    void testRetrieveContratNotFound() {
        when(contratRepository.findById(2)).thenReturn(Optional.empty());

        Contrat result = contratService.retrieveContrat(2);

        assertNull(result);
        verify(contratRepository).findById(2);
    }

    @Test
    void testRemoveContrat() {
        doNothing().when(contratRepository).deleteById(1);

        contratService.removeContrat(1);

        verify(contratRepository, times(1)).deleteById(1);
    }
}
