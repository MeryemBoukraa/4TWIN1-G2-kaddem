package tn.esprit.microservice.kassil.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
public class Equipe implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEquipe;

    private String nomEquipe;

    @Enumerated(EnumType.STRING)
    private Niveau niveau;

    private Integer detailEquipeId;

    // 📊 New fields for AI
    private Integer nbMembres;
    private Double ageMoyen;
    private Integer projetsLivres;

    // 🧠 Prediction output (optional)
    private Boolean prochaineEvolution;
}


