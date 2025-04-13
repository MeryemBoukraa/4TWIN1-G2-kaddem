package com.example.ressource.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Ressource {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    long idRessource ;
    String titre;
    String url;
    String pdf;
    String description;
    @Enumerated(EnumType.STRING)
    private Type type;


}
