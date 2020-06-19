/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.controllers;

/**
 * FXML Controller class
 *
 * @author Ahmed
 */
import com.esprit.models.CategorieProduit;
import com.esprit.services.ServiceCategorieProduit;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
        String nom = tfNom.getText();
        String desc = tfDescription.getText();
        boolean valid = true;
        if (nom.equals("") || desc.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("champ vide!!");
            alert.showAndWait();
        } else {
            sp.ajouter(new CategorieProduit(nom, desc));

            JOptionPane.showMessageDialog(null, "Categorie ajoutée !");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/views/DetailsCategorie.fxml"));

            Parent root = loader.load();
            tfNom.getScene().setRoot(root);
           
            DetailsCategorieController dpc = loader.getController();
            dpc.setMyRole(myRole);
            dpc.setUsernameLabel(usernameLabel);
            dpc.setLbNom(tfNom.getText());
            dpc.setLbDescription(tfDescription.getText());
        }
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
            System.out.println("aaaa"+usernameLabel.getText());
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            Image icon = new Image(getClass().getResourceAsStream("/homepage/images/saviorsIcon.png"));
            stage.getIcons().add(icon);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            Image mouseCursor = new Image("/saviorsda/images/mouseCursor.png");
            scene.setCursor(new ImageCursor(mouseCursor));
            stage.setTitle("Liste des Produits - Saviors");
            stage.show();
            
        } catch (Exception e) {
            System.out.println("Erreur: " + e);
        }
    }

}
