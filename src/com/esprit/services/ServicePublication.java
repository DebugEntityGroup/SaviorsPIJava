/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.services;


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

/**
 *
 * @author aissa
 */
public class ServicePublication implements IServicepub<Publication> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Publication t) {
        try {
            String requete = "INSERT INTO Publication (titre,description,brochure_filename,video) VALUES (?,?,?,?)";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, t.getTitre());
            pst.setString(2, t.getDescription());
            pst.setString(3, t.getBrochure_filename());
            pst.setString(4, t.getVideo());
            pst.executeUpdate();
            System.out.println("Publication ajoutée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        
    }

    @Override
    public void supprimer(int id) {
        try {
            String requete = "DELETE FROM Publication WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Publication supprimée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Publication t) {
        try {
            String requete = "UPDATE Publication SET titre=?,description=?,brochure_filename=?,video=? WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(5, t.getId());
            pst.setString(1, t.getTitre());
            pst.setString(2, t.getDescription());
            pst.setString(3, t.getBrochure_filename());
            pst.setString(4, t.getVideo());
            pst.executeUpdate();
            System.out.println("Publication modifiée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public ObservableList<Publication> afficher() {
ObservableList<Publication> list = FXCollections.observableArrayList();

        try {
            String requete = "SELECT * FROM Publication";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(new Publication(rs.getInt("id"), rs.getString(2), rs.getString(3),rs.getString(4),rs.getString(5)));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return list;
    }
    public List<Publication> Tri() {
        List<Publication> publications = new ArrayList<>();
        Publication publication= new Publication();
        try {
            String requete = "SELECT * FROM `Publication` order by titre";
            PreparedStatement statement = this.cnx.prepareStatement(requete);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                publication = new Publication(results.getInt("id"), results.getString("titre"), results.getString("description"), results.getString("brochure_filename"), results.getString("video"));
                publications.add(publication);
            }
            statement.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return publications;
    }
        public List<Publication> RecherchePublication(String titre) {
        List<Publication> lstpub = new ArrayList<>();
        try {
            String requete = "SELECT  * FROM Publication where titre LIKE '%" + titre + "%' ;";

            PreparedStatement pst = cnx.prepareStatement(requete);

            ResultSet result = pst.executeQuery(requete);

            if (result.next() == false) {
                System.out.println("publication inconnu");
            } else {
                do {
                    Publication p = new Publication();
                    p.setTitre(result.getString("titre"));
                    p.setDescription(result.getString("description"));
                    p.setBrochure_filename(result.getString("brochure_filename"));
                    p.setVideo(result.getString("video"));
                    p.setId(result.getInt("id"));

                    lstpub.add(p);
                } while (result.next());
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return lstpub;
        }
         public Publication AfficheOnePublication(int id) {
        Publication publication = new Publication();
        String sql = "select * from Publication P  where  P.id like ? ";
        try {

            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                publication.setId(rs.getInt("id"));
                publication.setTitre(rs.getString("titre"));
                publication.setDescription(rs.getString("description"));
                publication.setBrochure_filename(rs.getString("brochure_filename"));
                publication.setVideo(rs.getString("video"));

            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return publication;
    }
    
    }
