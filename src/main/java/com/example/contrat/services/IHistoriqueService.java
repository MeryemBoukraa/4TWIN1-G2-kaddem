package com.example.contrat.services;

import com.example.contrat.entities.Contrat;

public interface IHistoriqueService {

    public void ajouterHistorique(Contrat contrat, String action, String details);
}
