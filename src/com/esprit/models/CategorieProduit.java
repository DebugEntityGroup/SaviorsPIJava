/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.models;

import java.util.Objects;

/**
 *
 * @author Ahmed
 */
public class CategorieProduit {

    private String nom;
    private String description;

    public CategorieProduit() {
    }

    public CategorieProduit(String nom, String description) {
        this.nom = nom;
        this.description = description;
    }

    public CategorieProduit( String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "nom=" + nom + ", description=" + description + '}';
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.nom);
        hash = 97 * hash + Objects.hashCode(this.description);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CategorieProduit other = (CategorieProduit) obj;
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        return true;
    }

   
}
