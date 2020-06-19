package com.esprit.services;


import com.esprit.models.AimePub;
import com.esprit.utils.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aissa
 */
public class ServiceAime implements IServicepub<AimePub> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(AimePub t) {
        try {
            String requete = "INSERT INTO publication_aime ( publication_id,aime_id) VALUES (?,?)";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, t.getPublication_id());
             pst.setInt(1, t.getAime_id());
            
            pst.executeUpdate();
            System.out.println("AimePub ajoutée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        try {
            String requete = "DELETE FROM publication_aime WHERE aime_id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            
            pst.executeUpdate();
            System.out.println("AimePub supprimée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(AimePub t) {
        try {
            String requete = "UPDATE publication_aime SET pulication_id=? WHERE aime_id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(2, t.getAime_id());
           pst.setInt(1, t.getPublication_id());
            
            pst.executeUpdate();
            System.out.println("AimePub modifiée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public List<AimePub> afficher() {
        List<AimePub> list = new ArrayList<>();

        try {
            String requete = "SELECT * FROM publication_aime";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(new AimePub( rs.getInt(1), rs.getInt(2)));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return list;
    }
}

