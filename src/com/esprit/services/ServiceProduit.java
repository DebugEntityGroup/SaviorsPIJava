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

/**
 *
 * @author Ahmed
 */
public class ServiceProduit implements IService<Produit> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Produit t) {
        try {
            String requete = "INSERT INTO ProduitPending (user_id,nom,image,prix,description,Categorie_nom) VALUES (?,?,?,?,?,?)";
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
            String requete = "UPDATE ProduitPending SET user_id=?,nom=?,image=?,prix=?,description=?,Categorie_nom=? WHERE id=?";
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

    @Override
    public List<Produit> afficher() {
        List<Produit> list = new ArrayList<>();

        try {
            String requete = "SELECT * FROM ProduitPending";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(new Produit(rs.getInt("id"), rs.getInt("user_id"), rs.getString("nom"), rs.getString("image"), rs.getInt("prix"), rs.getString("description"), rs.getString("categorie_nom")));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return list;
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
                produit.setNom(rs.getString("nom"));
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

    public List<Produit> produitsParMC(String mc) {

        String sql = "select * from ProduitPending P  inner join categorie C  on P.categorie_nom = C.nom "
                + "where  P.nom like ? or C.nom like ? or C.description like ? order by P.prix asc ";
        List<Produit> produits = new ArrayList<Produit>();
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setString(1, "%" + mc + "%");
            ps.setString(2, "%" + mc + "%");
            ps.setString(3, "%" + mc + "%");

            ResultSet rs = ps.executeQuery();
            if (rs.next() == false) {
                System.out.println("Produit non trouvé -_- ");
            } else {
                do {
                    Produit produit = new Produit();
                    produit.setId(rs.getInt("id"));
                    produit.setUser_id(rs.getInt("user_id"));
                    produit.setNom(rs.getString("nom"));
                    produit.setImage(rs.getString("image"));
                    produit.setPrix(rs.getInt("prix"));
                    produit.setDescription(rs.getString("description"));
                    produit.setCategorie_nom(rs.getString("categorie_nom"));
                    produits.add(produit);
                } while (rs.next());
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return produits;
    }

    public List<Produit> TriAscendant() {
        List<Produit> produits = new ArrayList<>();
        Produit produit = new Produit();
        try {
            String requete = "SELECT * FROM `ProduitPending` order by prix";
            PreparedStatement statement = this.cnx.prepareStatement(requete);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                produit = new Produit(results.getInt("id"), results.getInt("user_id"), results.getString("nom"), results.getString("image"), results.getInt("prix"), results.getString("description"), results.getString("Categorie_nom"));
                produits.add(produit);
            }
            statement.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return produits;
    }

    public List<Produit> TriDescendant() {
        List<Produit> produits = new ArrayList<>();
        Produit produit = new Produit();
        try {
            String requete = "SELECT * FROM `ProduitPending` order by prix desc";
            PreparedStatement stmt = this.cnx.prepareStatement(requete);
            ResultSet results = stmt.executeQuery();
            while (results.next()) {
                produit = new Produit(results.getInt("id"), results.getInt("user_id"), results.getString("nom"), results.getString("image"), results.getInt("prix"), results.getString("description"), results.getString("Categorie_nom"));
                produits.add(produit);
            }
            stmt.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return produits;
    }

    /* public int SumPrix(String categorie) {

        int s = 0;
        try {
            String requete = "SELECT sum(prix)as p FROM ProduitPending where Categorie_nom like '%" + categorie + "%'";
            PreparedStatement results = cnx.prepareStatement(requete);
            ResultSet rs = results.executeQuery();
            while (rs.next()) {
                s = rs.getInt("p");
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return s;
    }
     */
 
}
