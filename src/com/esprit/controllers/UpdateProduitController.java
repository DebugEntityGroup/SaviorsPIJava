/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.controllers;

import com.esprit.models.Produit;
import com.esprit.services.ServiceCategorieProduit;
import com.esprit.services.ServiceProduit;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.StageStyle;

/**
 *
 * @author Ahmed
 */
public class UpdateProduitController {

private Produit produit;
    @FXML
    private Label closeButton;

    @FXML
    private TextField user;

    @FXML
    private TextField nom;

    @FXML
    private TextField image;

    @FXML
    private TextField prix;

    @FXML
    private TextArea description;

    @FXML
    private ComboBox<String> categorie;

    @FXML
    private Button update;
    
    private Stage stage;


    @FXML
    public void close() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
        Stage primaryStage=new Stage();
        try{
        Parent  root = FXMLLoader.load(getClass().getResource("/com/esprit/views/AfficheProduit.fxml"));
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("Debug Entity");
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
        }catch(Exception e){
            System.out.println(e);
        }
    }


    
  
    public void setProduit(Produit p) {
        this.produit=p;
          ServiceCategorieProduit es = new ServiceCategorieProduit();
        categorie.getItems().add("");
        es.afficher().forEach((l) -> {
            categorie.getItems().add(l.getNom());
        });
        
      user.setText(p.getUser_id()+"");
        nom.setText(p.getNom());
        image.setText(p.getImage());
        prix.setText(p.getPrix()+"");
        description.setText(p.getDescription());
        categorie.setValue(p.getCategorie_nom());
        
        
    }
  
    @FXML
    void update(ActionEvent event) {
        ServiceProduit ps=new ServiceProduit();
        Produit p= new Produit();
        p.setUser_id(Integer.parseInt(user.getText()));
        p.setNom(nom.getText());
        p.setImage(image.getText());
        p.setPrix(Integer.parseInt(prix.getText()));
        p.setDescription(description.getText());
        p.setCategorie_nom(categorie.getValue());
        p.setId(produit.getId());
         
        System.out.println(p);
        ps.modifier(p);
        System.out.println("produit modifiée avec succée");
        Stage primaryStage=new Stage();
        try{
        Parent  root = FXMLLoader.load(getClass().getResource("/com/esprit/views/AfficheProduit.fxml"));
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("Debug Entity");
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
        }catch(Exception e){
            System.out.println(e);
        }
        stage.close();
        }
    public void setStage(Stage stage) {
        this.stage = stage;
    }

}


