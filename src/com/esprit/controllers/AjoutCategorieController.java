/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.controllers;

import com.esprit.models.CategorieProduit;
import com.esprit.services.ServiceCategorieProduit;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Ahmed
 */
public class AjoutCategorieController implements Initializable {

    @FXML
    private TextField tfNom;
    @FXML
    private TextArea tfDescription;
    @FXML
    private Button btn;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void AjouterCategorie(ActionEvent event) throws IOException {
        ServiceCategorieProduit sp = new ServiceCategorieProduit();
        sp.ajouter(new CategorieProduit(tfNom.getText(), tfDescription.getText()));
        
        JOptionPane.showMessageDialog(null, "Categorie ajoutée !");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/views/DetailsCategorie.fxml"));
        
        Parent root = loader.load();
        tfNom.getScene().setRoot(root);
        
        DetailsCategorieController dpc = loader.getController();
        dpc.setLbNom(tfNom.getText());
        dpc.setLbDescription(tfDescription.getText());
    }

}
