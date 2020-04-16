package com.esprit.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class DetailsCategorieController implements Initializable {

    @FXML
    private Label lbNom;
    @FXML
    private Label lbDescription;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    public void setLbNom(String nom) {
        this.lbNom.setText("Nom : " + nom);
    }

    public void setLbDescription(String description) {
        this.lbDescription.setText("Categorie : " + description);
    }
    
}
