package com.example.contrat.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class HistoriqueModification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne
    @JoinColumn(name = "contrat_id")
    @JsonBackReference
    private Contrat contrat;

    private String action; // Exemple : "Modification de statut", "Ajout de document"
    private String details; // Détails sur ce qui a été modifié
   // private String utilisateur; // L'utilisateur qui a effectué l'action
    private Date dateModification; // Date de la modification

}
