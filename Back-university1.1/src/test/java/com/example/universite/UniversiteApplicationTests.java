package com.example.universite;

import com.example.universite.entities.Universite;
import com.example.universite.repositories.UniversiteRepository;
import com.example.universite.services.UniversiteServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UniversiteApplicationTests {

    @Mock
    UniversiteRepository universiteRepository;

    @InjectMocks
    UniversiteServiceImpl universiteService;

    @Test
    void testFindUniversitiesWithinRadius_ShouldReturnCorrectUniversities() {
        Universite u1 = new Universite(1, "Univ A", 36.8, 10.2); // Tunis
        Universite u2 = new Universite(2, "Univ B", 35.7, 10.8); // Sfax
        Universite u3 = new Universite(3, "Univ C", 33.8, 10.1); // Tataouine

        when(universiteRepository.findAll()).thenReturn(List.of(u1, u2, u3));

        // Affichage des distances
        System.out.println("Distance Tunis → Sfax = " +
                universiteServiceTest_haversine(36.8, 10.2, 35.7, 10.8) + " km");

        System.out.println("Distance Tunis → Tataouine = " +
                universiteServiceTest_haversine(36.8, 10.2, 33.8, 10.1) + " km");

        List<Universite> result = universiteService.findUniversitiesWithinRadius(36.8, 10.2, 150);

        assertEquals(2, result.size());
        List<String> noms = result.stream().map(Universite::getNomUniv).toList();
        assertEquals(List.of("Univ A", "Univ B"), noms);
    }

    // Copie locale de la méthode haversine pour le test uniquement
    private double universiteServiceTest_haversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Rayon de la Terre en km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }
}
