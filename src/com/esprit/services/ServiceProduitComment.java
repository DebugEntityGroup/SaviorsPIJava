/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.services;

import com.esprit.models.Produit;
import com.esprit.models.ProduitComment;
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
public class ServiceProduitComment implements IService<ProduitComment> {

  
    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(ProduitComment t) {
        try {
            String requete = "INSERT INTO ProduitComment (user_id,content,produitPending_id) VALUES (?,?,?)";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, t.getUser_id());
            pst.setString(2, t.getContent());
            pst.setInt(3, t.getProduitPending_id());
            pst.executeUpdate();
            System.out.println("Commentaire ajoutée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(String id) {
        try {
            String requete = "DELETE FROM ProduitComment WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, id);
            pst.executeUpdate();
            System.out.println("Commentaire supprimée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }


    @Override
    public void modifier(ProduitComment t) {
        try {
            String requete = "UPDATE ProduitComment SET user_id=?,content=?,produitPending_id=? WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(4, t.getId());
            pst.setInt(1, t.getUser_id());
            pst.setString(2, t.getContent());
            pst.setInt(3, t.getProduitPending_id());
            pst.executeUpdate();
            System.out.println("Commentaire modifiée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public List<ProduitComment> afficher(Produit p) {
        List<ProduitComment> list = new ArrayList<>();
        try {
            String requete = "SELECT *,username FROM fos_user u,ProduitComment p where p.user_id=u.id and "+ p.getId()+"=p.produitPending_id ";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(new ProduitComment(rs.getString("content"),rs.getString("username")));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return list;
    }
}
