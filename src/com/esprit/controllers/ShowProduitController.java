/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.controllers;

import com.esprit.models.Produit;
import com.esprit.models.ProduitComment;
import com.esprit.services.ServiceProduit;
import com.esprit.services.ServiceProduitComment;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import mysql.mysqlConnect;

/**
 *
 * @author Ahmed
 */
public class ShowProduitController {

       @FXML
    private Label closeButton;

    @FXML
    private Button retour;

    @FXML
    private ImageView image;

    @FXML
    private Text nom;

    @FXML
    private Text prix;

    @FXML
    private Text categorie;

    @FXML
    private Text description;
      @FXML
    private Label myRole;
    @FXML
    private Label usernameLabel;
      @FXML
    private ListView<ProduitComment> table;
    @FXML
    private TextArea tfCommentaire;
    @FXML
    private Button btn;
    private Stage stage;
    @FXML
    private Text user;
    private Produit produit;

    public Label getMyRole() {
        return myRole;
    }

    public void setMyRole(Label myRole) {
        this.myRole = myRole;
    }

    public Label getUsernameLabel() {
        return usernameLabel;
    }

    public void setUsernameLabel(Label usernameLabel) {
        this.usernameLabel = usernameLabel;
    }
    
    
       @FXML
    void Comment(ActionEvent event) throws SQLException {
     
         ServiceProduit sp = new ServiceProduit();
         
        Connection cnx = mysqlConnect.getInstance().getCnx();
        Statement stm = cnx.createStatement();
        String req = "select * from fos_user f where f.username='"+usernameLabel.getText()+"'";
        ResultSet rs = stm.executeQuery(req);
         if (rs.next()) {
            int idUserP = rs.getInt("id");
        ServiceProduitComment sp1 = new ServiceProduitComment();
        sp1.ajouter(new ProduitComment(idUserP,tfCommentaire.getText(),produit.getId()));
        
        JOptionPane.showMessageDialog(null, "Commentaire ajoutée !");
        }
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    /*private void setCommentaire(Produit p) throws SQLException {
        this.produit=p;
         ServiceProduit sp = new ServiceProduit();
        Connection cnx = mysqlConnect.getInstance().getCnx();
        Statement stm = cnx.createStatement();
        String req = "select * from fos_user f where f.username='"+usernameLabel.getText()+"'";
        ResultSet rs = stm.executeQuery(req);
         if (rs.next()) {
            int idUserP = rs.getInt("id");
        ServiceProduitComment sp1 = new ServiceProduitComment();
        sp1.ajouter(new ProduitComment(idUserP,tfCommentaire.getText(),produit.getId()));
        
        JOptionPane.showMessageDialog(null, "Commentaire ajoutée !");
         }
    }*/
     public void setComment() {
       ProduitComment c=new ProduitComment();
       ServiceProduitComment se=new ServiceProduitComment();
       table.getItems().setAll(se.afficher(produit));

    }
  public void setProduit(Produit p) {
        this.produit=p;
       
        nom.setText(p.getNom());
        putImageViewer(p.getImage());
        prix.setText(p.getPrix()+" DT");
        description.setText(p.getDescription());
        categorie.setText(p.getCategorie_nom());
        
    }
  
     @FXML
    public void close() {
        Stage stage = (Stage) retour.getScene().getWindow();
        stage.close();
    }
  
    public void putImageViewer(String path){
            FileInputStream input = null;
        try {
            input = new FileInputStream("src/Images/"+path);
            Image imageFile = new Image(input);
            image.setImage(imageFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DetailsProduitController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                input.close();
            } catch (IOException ex) {
                Logger.getLogger(DetailsProduitController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        }
}

