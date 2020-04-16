/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Ahmed
 */
public class DetailsCategorieController implements Initializable {

    @FXML
    private Label lbNom;
    @FXML
    private Label lbDescription;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void setLbNom(String nom) {
        this.lbNom.setText("Nom : " + nom);
    }

    public void setLbDescription(String description) {
        this.lbDescription.setText("Categorie : " + description);
    }
    
}
