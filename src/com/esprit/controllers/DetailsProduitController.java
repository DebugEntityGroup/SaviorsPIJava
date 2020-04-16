/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Ahmed
 */
public class DetailsProduitController implements Initializable {

    @FXML
    private Label lbUser_id;
    @FXML
    private Label lbNom;
    @FXML
    private ImageView lbImage;
    @FXML
    private Label lbPrix;
    @FXML
    private Label lbDescription;
    @FXML
    private Label lbCategorie_nom;
    @FXML
    private Button btnRetour;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void setLbUser_id(int user_id) {
        this.lbUser_id.setText("User_id : " + user_id);
    }

    public void setLbNom(String nom) {
        this.lbNom.setText("Nom : " + nom);
    }
    public void setLbImage(String url) {
        
         FileInputStream input = null;
        try {
            input = new FileInputStream("src/Images/"+url);
            Image image = new Image(input);
            lbImage.setImage(image);
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

    public void setLbPrix(int prix) {
        this.lbPrix.setText("Prix : " + prix);
    }

    public void setLbDescription(String description) {
        this.lbDescription.setText("Description : " + description);
    }

    public void setLbCategorie_nom(String Categorie_nom) {
        this.lbCategorie_nom.setText("Categorie_nom : " + Categorie_nom);
    }
    @FXML
 private void Retouraffiche(ActionEvent event) throws IOException {
        
try {
              
            btnRetour.getScene().getWindow().hide();
            Stage produits = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/esprit/views/AfficheProduit.fxml"));
            Scene scene = new Scene(root);
            produits.setScene(scene);
            produits.initStyle(StageStyle.UNDECORATED);
            produits.show();
            produits.setResizable(false);

        } catch (Exception e) {
            System.out.println(" Error  : " + e);
        }
    }
}
