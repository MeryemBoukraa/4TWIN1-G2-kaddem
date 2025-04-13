package tn.esprit.microservice.formation.entitiy;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Formation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom_formation")
    private String nomFormation;

    @Column(name = "description")
    private String description;

    @Column(name = "date_formation")
    @JsonFormat(pattern = "yyyy-MM-dd") // To ensure proper date format mapping
    private Date dateFormation;

    @Column(name = "nombre_place")
    private int nombrePlace;
    @Enumerated(EnumType.STRING)
    private Statut statut;

    private float prix;
}
