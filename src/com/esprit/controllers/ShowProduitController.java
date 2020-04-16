/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.controllers;

import com.esprit.models.Produit;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
    private Text user;
    private Produit produit;
    
  public void setProduit(Produit p) {
        this.produit=p;
       
        user.setText(p.getUser_id()+"");
        nom.setText(p.getNom());
        putImageViewer(p.getImage());
        prix.setText(p.getPrix()+" TND");
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

