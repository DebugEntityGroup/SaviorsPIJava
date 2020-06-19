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
public class Produit {

    private int id;
    private int user_id;
    private String nom;
    private String image;
    private int prix;
    private String description;
    private String Categorie_nom;

      
    public Produit() {
    }

    public Produit(int user_id,String nom,  String image, int prix, String description, String Categorie_nom) {
    
        this.nom = nom;
        this.image = image;
        this.prix = prix;
        this.description = description;
        this.user_id = user_id;
        this.Categorie_nom = Categorie_nom;
    }

 
    public Produit(int id, int user_id, String nom, String image, int prix, String description, String Categorie_nom) {
        this.id = id;
        this.nom = nom;
        this.image = image;
        this.prix = prix;
        this.description = description;
        this.user_id = user_id;
        this.Categorie_nom = Categorie_nom;
    }

    @Override
    public String toString() {
        return " NOM : " + nom + " --- IMAGE : " + image + " --- PRIX :" + prix + " --- DESCRIPTION : " + description + " --- CATEGORIE : " + Categorie_nom ;
    }

 
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
 public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getCategorie_nom() {
        return Categorie_nom;
    }

    public void setCategorie_nom(String Categorie_nom) {
        this.Categorie_nom = Categorie_nom;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + this.id;
        hash = 89 * hash + this.user_id;
        hash = 89 * hash + Objects.hashCode(this.nom);
        hash = 89 * hash + Objects.hashCode(this.image);
        hash = 89 * hash + this.prix;
        hash = 89 * hash + Objects.hashCode(this.description);
        hash = 89 * hash + Objects.hashCode(this.Categorie_nom);
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
        final Produit other = (Produit) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.user_id != other.user_id) {
            return false;
        }
        if (this.prix != other.prix) {
            return false;
        }
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        if (!Objects.equals(this.image, other.image)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.Categorie_nom, other.Categorie_nom)) {
            return false;
        }
        return true;
    }

    


}
