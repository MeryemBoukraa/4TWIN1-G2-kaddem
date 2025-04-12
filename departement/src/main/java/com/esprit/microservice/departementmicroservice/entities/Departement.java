package com.esprit.microservice.departementmicroservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Departement implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer idDepart;

    @JsonProperty("nomDepart")
    private String nomDepart;

    @JsonProperty("description")
    private String description;

    @JsonProperty("code")
    private String code; // e.g., DEPT-001

    @JsonProperty("createdDate")
    private LocalDate createdDate; // When the department was established

    @JsonProperty("email")
    private String email; // Department contact email

    @JsonProperty("phone")
    private String phone; // Department phone number

    @JsonProperty("active")
    private boolean active; // Is the department currently active

    public Integer getIdDepart() {
        return idDepart;
    }

    public void setIdDepart(Integer idDepart) {
        this.idDepart = idDepart;
    }

    public String getNomDepart() {
        return nomDepart;
    }

    public void setNomDepart(String nomDepart) {
        this.nomDepart = nomDepart;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}