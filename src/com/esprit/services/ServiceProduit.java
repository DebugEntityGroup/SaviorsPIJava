/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.services;

import com.esprit.models.Produit;
import com.esprit.utils.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Ahmed
 */
public class ServiceProduit implements IService<Produit> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Produit t) {
        try {
            String requete = "INSERT INTO ProduitPending (user_id,nomProduit,image,prix,description,Categorie_nom) VALUES (?,?,?,?,?,?)";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, t.getUser_id());
            pst.setString(2, t.getNom());
            pst.setString(3, t.getImage());
            pst.setInt(4, t.getPrix());
            pst.setString(5, t.getDescription());
            pst.setString(6, t.getCategorie_nom());
            pst.executeUpdate();
            System.out.println("Produit ajoutée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void supprimer(String id) {
        try {
            String requete = "DELETE FROM ProduitPending WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, id);
            pst.executeUpdate();
            System.out.println("Produit supprimée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Produit t) {
        try {
            String requete = "UPDATE ProduitPending SET user_id=?,nomProduit=?,image=?,prix=?,description=?,Categorie_nom=? WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(7, t.getId());
            pst.setInt(1, t.getUser_id());
            pst.setString(2, t.getNom());
            pst.setString(3, t.getImage());
            pst.setInt(4, t.getPrix());
            pst.setString(5, t.getDescription());
            pst.setString(6, t.getCategorie_nom());
            pst.executeUpdate();
            System.out.println("Produit modifiée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public ObservableList<Produit> afficher() {
ObservableList<Produit> lista = FXCollections.observableArrayList();
        try {
            String requete = "SELECT * FROM ProduitPending";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                lista.add(new Produit(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6), rs.getString(7)));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return lista;
    }


    public Produit seulProduit(int id) {
        Produit produit = new Produit();
        String sql = "select * from ProduitPending P  where  P.id like ? ";
        try {

            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                produit.setId(rs.getInt("id"));
                produit.setUser_id(rs.getInt("user_id"));
                produit.setNom(rs.getString("nomProduit"));
                produit.setImage(rs.getString("image"));
                produit.setPrix(rs.getInt("prix"));
                produit.setDescription(rs.getString("description"));
                produit.setCategorie_nom(rs.getString("categorie_nom"));

            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return produit;
    }

    
}
