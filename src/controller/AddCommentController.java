/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static controller.ShowEventClientController.idE;
import entities.BadWords;
import entities.CommentEvenement;
import entities.Evenement;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLDataException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import service.CommentService;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.StackPane;


/**
 * FXML Controller class
 *
 * @author dell
 */
public class AddCommentController implements Initializable {

    @FXML
    private TextArea contenu_reponse;
    @FXML
    private Button Ajout_reponse;
    @FXML
    private Button Cancel;
    @FXML
    private Button backbtn;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void AjouterReponses(ActionEvent event) throws SQLDataException {
        
        CommentEvenement e = new CommentEvenement();
        CommentService cs = new CommentService();

        e.setId(cs.findUserById(1));
        e.setIdEvt(cs.findEvenementById(idE));

        BadWords.loadConfigs();
      
            {
                
                if (BadWords.filterText(contenu_reponse.getText())) {

                       
           Parent root ;
        try {
          root = FXMLLoader.load(getClass().getResource("/gui/ShowDetailsEvent.fxml"));
            Stage myWindow =  (Stage) contenu_reponse.getScene().getWindow();
            Scene sc = new Scene(root);
            myWindow.setScene(sc);
            myWindow.setTitle("comment");
            myWindow.show();
           
        } catch (IOException ex) {
            Logger.getLogger(CommentItemController.class.getName()).log(Level.SEVERE,null,ex);
           
        }

                  
                } else
                
                {
                    

                   e.setComment(contenu_reponse.getText());
                   cs.addComment(e);
                   
                   // Alert alert = new Alert(Alert.AlertType.WARNING);
                   
                   /* alert.setTitle("gros mot");
                    alert.setHeaderText("gros mot trouve");
                    alert.setContentText("cette application n'autorise pas ces termes ");
                    alert.showAndWait();*/
                    
                    
            
           Parent root ;
        try {
          root = FXMLLoader.load(getClass().getResource("/gui/ShowDetailsEvent.fxml"));
            Stage myWindow =  (Stage) contenu_reponse.getScene().getWindow();
            Scene sc = new Scene(root);
            myWindow.setScene(sc);
            myWindow.setTitle("comment");
            myWindow.show();
             
        } catch (IOException ex) {
            Logger.getLogger(CommentItemController.class.getName()).log(Level.SEVERE,null,ex);
           
        }
        
    
            }
    
    }}
   @FXML
    private void handleClose(ActionEvent event) {
                  
           Parent root ;
        try {
          root = FXMLLoader.load(getClass().getResource("/gui/ShowDetailsEvent.fxml"));
            Stage myWindow =  (Stage) contenu_reponse.getScene().getWindow();
            Scene sc = new Scene(root);
            myWindow.setScene(sc);
            myWindow.setTitle("");
            myWindow.show();
           
        } catch (IOException ex) {
           // Logger.getLogger(ReponseItemController.class.getName()).log(Level.SEVERE,null,ex);
        }
        
    
            }
    
    

    @FXML
    private void back(ActionEvent event) {
        
                
        Parent root ;
        try {
          root = FXMLLoader.load(getClass().getResource("/gui/ShowDetailsEvent.fxml"));
            Stage myWindow =  (Stage) contenu_reponse.getScene().getWindow();
            Scene sc = new Scene(root);
            myWindow.setScene(sc);
            myWindow.setTitle("");
            myWindow.show();
           
        } catch (IOException ex) {
           // Logger.getLogger(ReponseItemController.class.getName()).log(Level.SEVERE,null,ex);
        }
        
    
            }
      
   }
    

