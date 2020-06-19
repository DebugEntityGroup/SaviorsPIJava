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
import javafx.scene.ImageCursor;
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
    @FXML
    private Label usernameLabel;
    @FXML
    private Label myRole;

    public Label getUsernameLabel() {
        return usernameLabel;
    }

    public void setUsernameLabel(Label usernameLabel) {
        this.usernameLabel = usernameLabel;
    }

    public Label getMyRole() {
        return myRole;
    }

    public void setMyRole(Label myRole) {
        this.myRole = myRole;
    }
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
        this.lbCategorie_nom.setText("Categorie : " + Categorie_nom);
    }
    @FXML
    private void Retouraffiche(ActionEvent event) throws IOException {
        
        try {
            
            btnRetour.getScene().getWindow().hide();
            //Stage produits = new Stage();
            //Parent root = FXMLLoader.load(getClass().getResource("/com/esprit/views/AfficheProduit.fxml"));
            //Scene scene = new Scene(root);
            /*produits.setScene(scene);
            produits.initStyle(StageStyle.UNDECORATED);
            produits.show();
            produits.setResizable(false);*/
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/views/AfficheProduit.fxml"));
            Parent root = (Parent) loader.load();
            ListProduitController lp = loader.getController();
            //ap.getUsernameLabel().setText(usernameLabel.getText());
            lp.getUsernameLabel().setText(usernameLabel.getText());
            lp.setMyRole(myRole);
            System.out.println("aaaa"+usernameLabel.getText()+"------------"+myRole.getText());
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            Image icon = new Image(getClass().getResourceAsStream("/homepage/images/saviorsIcon.png"));
            stage.getIcons().add(icon);
            stage.setScene(scene);
            Image mouseCursor = new Image("/saviorsda/images/mouseCursor.png");
            scene.setCursor(new ImageCursor(mouseCursor));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Liste des Produits - Saviors");
            stage.show();
            
        } catch (Exception e) {
            System.out.println("Erreur: " + e);
        }
    }
}
