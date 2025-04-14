package tn.esprit.microservice.formation.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import tn.esprit.microservice.formation.entitiy.Formation;
import tn.esprit.microservice.formation.repository.FormationRepo;
import tn.esprit.microservice.formation.service.FormationService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FormationServiceTest {

    @Mock
    private FormationRepo formationRepo;

    @InjectMocks
    private FormationService formationService;

    private Formation sampleFormation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleFormation = new Formation();
        sampleFormation.setId(1L);
        sampleFormation.setNomFormation("Spring Boot Basics");
        sampleFormation.setDescription("Intro to Spring Boot");
        sampleFormation.setPrix(100);
        sampleFormation.setNombrePlace(25);
        sampleFormation.setDateFormation(new Date()); // Today
    }

    @Test
    void testAddFormation() {
        when(formationRepo.save(any(Formation.class))).thenReturn(sampleFormation);

        Formation result = formationService.addFormation(sampleFormation);
        assertNotNull(result);
        assertEquals("Spring Boot Basics", result.getNomFormation());
        verify(formationRepo, times(1)).save(sampleFormation);
    }

    @Test
    void testGetFormation() {
        when(formationRepo.findById(1L)).thenReturn(Optional.of(sampleFormation));

        Formation result = formationService.getFormation(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(formationRepo, times(1)).findById(1L);
    }

    @Test
    void testGetAllFormation() {
        List<Formation> formations = List.of(sampleFormation);
        when(formationRepo.findAll()).thenReturn(formations);

        List<Formation> result = formationService.getAllFormation();
        assertEquals(1, result.size());
        verify(formationRepo).findAll();
    }

    @Test
    void testUpdateFormation() {
        Formation updated = new Formation();
        updated.setDateFormation(new Date());
        updated.setDescription("Advanced Spring");
        updated.setNomFormation("Spring Boot Advanced");
        updated.setNombrePlace(30);
        updated.setPrix(200);

        when(formationRepo.findById(1L)).thenReturn(Optional.of(sampleFormation));
        when(formationRepo.save(any(Formation.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Formation result = formationService.updateFormation(1L, updated);
        assertEquals("Spring Boot Advanced", result.getNomFormation());
        verify(formationRepo).findById(1L);
        verify(formationRepo).save(any(Formation.class));
    }

    @Test
    void testDeleteFormation() {
        doNothing().when(formationRepo).deleteById(1L);

        formationService.deleteFormation(1L);
        verify(formationRepo, times(1)).deleteById(1L);
    }
}
