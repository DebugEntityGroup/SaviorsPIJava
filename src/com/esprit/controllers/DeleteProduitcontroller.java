/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.controllers;

import com.esprit.models.Produit;
import com.esprit.services.ServiceProduit;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Ahmed
 */
public class DeleteProduitcontroller {


    @FXML
    private Label closebutton;

    @FXML
    private Button supprimer;

    @FXML
    private Button anuuler;

    @FXML
    private Text altertText;

    private Stage stage;
    private Produit produit;
    
    @FXML
    public void close() {
        Stage stage = (Stage) closebutton.getScene().getWindow();
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
        altertText.setText(p.getNom());

    }

   @FXML
    void delete(ActionEvent event) {
        ServiceProduit ps=new ServiceProduit();
     
         
        System.out.println(produit);
        ps.supprimer(produit.getId()+"");
        System.out.println("produit supprimer avec succée");
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



