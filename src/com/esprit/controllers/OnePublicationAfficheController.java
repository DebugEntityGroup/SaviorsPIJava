/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.controllers;

import com.esprit.models.CommentairePub;
import com.esprit.models.Publication;
import com.esprit.services.ServiceCommentaire;
import com.esprit.services.ServicePublication;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author lenovo
 */
public class OnePublicationAfficheController {

    @FXML
    private Label closeButton;
 
    @FXML
    private Button retour;

    @FXML
    private MediaView mediaView;
    @FXML
    private ImageView image;

    @FXML
    private Text titre;

    @FXML
    private Text video;

    @FXML
    private Text description;
    @FXML
    private TextArea tfCommentaire;

    private Publication publication;

    public void setPublication(Publication p) {
        this.publication = p;
        ServicePublication sp = new ServicePublication();
        try {
            /**
             * * Objets Media, MediaPlayer et MediaView ****
             */
            //Media
            Media media = new Media(publication.getVideo());
            //MediaPlayer
            MediaPlayer mediaplayer = new MediaPlayer(media);
            //MediaView
            mediaView.setMediaPlayer(mediaplayer);
            mediaView.addEventHandler(MouseEvent.MOUSE_ENTERED, (event) -> {
                mediaplayer.play();
            });
            mediaView.addEventHandler(MouseEvent.MOUSE_EXITED, (event) -> {
                mediaplayer.stop();
            });
        } catch (MediaException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText(ex + "");

            alert.showAndWait();
            sp.supprimer(p.getId());
            //  item.getChildren().clear();
        }
        titre.setText(p.getTitre());
        putImageViewer(p.getBrochure_filename());
        description.setText(p.getDescription());
        // video.setText(p.getVideo());

    }

    @FXML
    public void close() {
        Stage stage = (Stage) retour.getScene().getWindow();
        stage.close();
    }

    @FXML
    void Comment(ActionEvent event) {
        ServiceCommentaire sp = new ServiceCommentaire();
        CommentairePub p = new CommentairePub();
        sp.ajouter(new CommentairePub(publication.getId(), tfCommentaire.getText()));

        JOptionPane.showMessageDialog(null, "Commentaire ajoutée avec succès !");
    }

  
  

    public void putImageViewer(String path) {
        FileInputStream input = null;
        try {
            input = new FileInputStream("src/Images/" + path);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OnePublicationAfficheController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Image imageFile = new Image(input);
        image.setImage(imageFile);

        try {
            input.close();
        } catch (IOException ex) {
            Logger.getLogger(OnePublicationAfficheController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
