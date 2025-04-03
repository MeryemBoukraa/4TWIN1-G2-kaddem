package com.example.contrat.services;

import com.example.contrat.entities.Contrat;
import com.example.contrat.entities.HistoriqueModification;
import com.example.contrat.repositories.HistoriqueModificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class HistoriqueModificationService implements IHistoriqueService {

    private final HistoriqueModificationRepository historiqueModificationRepository;
    public List<HistoriqueModification> getHistoriqueByContrat(Integer contratId) {
        return historiqueModificationRepository.findByContratId(contratId);
    }

    public void ajouterHistorique(Contrat contrat, String action, String details) {
        HistoriqueModification historique = new HistoriqueModification();
        historique.setContrat(contrat);
        historique.setAction(action);
        historique.setDetails(details);
        historique.setDateModification(new Date());

        historiqueModificationRepository.save(historique);  // Sauvegarder l'historique
    }

}
