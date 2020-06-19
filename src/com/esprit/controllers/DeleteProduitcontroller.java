package com.esprit.controllers;

import com.esprit.models.Produit;
import com.esprit.services.ServiceProduit;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DeleteProduitcontroller {


    @FXML
    private Label closebutton;

    @FXML
    private Button supprimer;

    @FXML
    private Button anuuler;

    @FXML
    private Text altertText;
    
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
    
    private Stage stage;
    private Produit produit;
    
    @FXML
    public void close() {
        Stage stage = (Stage) closebutton.getScene().getWindow();
        stage.close();
        Stage primaryStage=new Stage();
        try{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/views/AfficheProduit.fxml"));
            Parent root = (Parent) loader.load();
            ListProduitController lp = loader.getController();
            //ap.getUsernameLabel().setText(usernameLabel.getText());
            lp.getUsernameLabel().setText(usernameLabel.getText());
            lp.setMyRole(myRole);
            System.out.println("aaaa"+usernameLabel.getText()+"-----------"+myRole.getText());
            Scene scene = new Scene(root);
            Image icon = new Image(getClass().getResourceAsStream("/homepage/images/saviorsIcon.png"));
            primaryStage.getIcons().add(icon);
            primaryStage.setScene(scene);
            Image mouseCursor = new Image("/saviorsda/images/mouseCursor.png");
            scene.setCursor(new ImageCursor(mouseCursor));
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setTitle("Liste des Produits - Saviors");
            primaryStage.show();
        }catch(Exception e){
            System.out.println(e);
        }
    }


    public void setProduit(Produit p) {
        this.produit=p;
        altertText.setText(p.getNom());

    }

   @FXML
    void delete(ActionEvent event) {
        ServiceProduit ps=new ServiceProduit();
     
         
        System.out.println(produit);
        ps.supprimer(produit.getId()+"");
        System.out.println("Produit supprimé avec succées");
        //Stage primaryStage=new Stage();
        try{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/views/AfficheProduit.fxml"));
            Parent root = (Parent) loader.load();
            ListProduitController lp = loader.getController();
            //ap.getUsernameLabel().setText(usernameLabel.getText());
            lp.getUsernameLabel().setText(usernameLabel.getText());
            lp.setMyRole(myRole);
            System.out.println("aaaa"+usernameLabel.getText()+"-----------"+myRole.getText());
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            Image icon = new Image(getClass().getResourceAsStream("/homepage/images/saviorsIcon.png"));
            stage.getIcons().add(icon);
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            Image mouseCursor = new Image("/saviorsda/images/mouseCursor.png");
            scene.setCursor(new ImageCursor(mouseCursor));
            stage.setTitle("Liste des Produits - Saviors");
            stage.show();
        }catch(Exception e){
            System.out.println(e);
        }
        stage.close();
        }
    public void setStage(Stage stage) {
        this.stage = stage;
    }

}



