package com.example.ressource.repository;

import com.example.ressource.entity.Ressource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RessourceRepository extends JpaRepository<Ressource,Long> {
    @Query("SELECT r.type, COUNT(r) FROM Ressource r GROUP BY r.type")
    List<Object[]> countRessourcesByType();

}
