/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.services;



import com.esprit.models.CommentairePub;
import com.esprit.models.Publication;
import com.esprit.utils.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


 

public class ServiceCommentaire implements IServicepub<CommentairePub> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(CommentairePub t) {
        try {
            String requete = "INSERT INTO Commentairep (publication_id,description) VALUES (?,?)";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, t.getPublication_id());
            pst.setString(2, t.getDescription());
        
            pst.executeUpdate();
            System.out.println("CommentairePub ajoutée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        try {
            String requete = "DELETE FROM Commentairep WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("CommentairePub supprimée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(CommentairePub t) {
        try {
            String requete = "UPDATE Commentairep SET publication_id=?,description=? WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(3, t.getId());
            pst.setInt(1, t.getPublication_id());
            pst.setString(2, t.getDescription());
          
            pst.executeUpdate();
            System.out.println("CommentairePub modifiée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

     @Override
    public ObservableList<CommentairePub> afficher() {
ObservableList<CommentairePub> list = FXCollections.observableArrayList();

        try {
            String requete = "SELECT * FROM commentairep";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(new CommentairePub(rs.getInt(1), rs.getInt(2), rs.getString(3)));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return list;
    }
}

    

