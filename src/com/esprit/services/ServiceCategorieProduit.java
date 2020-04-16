/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.services;

import com.esprit.models.CategorieProduit;
import com.esprit.utils.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ahmed
 */
public class ServiceCategorieProduit implements IService<CategorieProduit> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(CategorieProduit t) {
        try {
            String requete = "INSERT INTO Categorie (nom,description) VALUES (?,?)";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, t.getNom());
            pst.setString(2, t.getDescription());
            pst.executeUpdate();
            System.out.println("Categorie ajoutée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(String nom) {
        try {
            String requete = "DELETE FROM Categorie WHERE nom=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, nom);
            pst.executeUpdate();
            System.out.println("Categorie supprimée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }


    @Override
    public void modifier(CategorieProduit t) {
        try {
            String requete = "UPDATE Categorie SET nom=?,description=? WHERE nom=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(3, t.getNom());
            pst.setString(1, t.getNom());
            pst.setString(2, t.getDescription());
            pst.executeUpdate();
            System.out.println("Categorie modifiée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public List<CategorieProduit> afficher() {
        List<CategorieProduit> list = new ArrayList<>();

        try {
            String requete = "SELECT * FROM Categorie";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(new CategorieProduit(rs.getString(1), rs.getString("description")));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return list;
    }
}
