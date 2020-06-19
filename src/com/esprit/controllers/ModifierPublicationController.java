/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.controllers;

import com.esprit.models.Publication;
import com.esprit.services.ServicePublication;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
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
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import org.apache.commons.lang.RandomStringUtils;

/**
 *
 * @author lenovo
 */
public class ModifierPublicationController {

    private Publication publication;
    @FXML
    private Label closeButton;

    @FXML
    private TextField titre;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label myRole;
@FXML
    private MediaView mediaView;
    @FXML
    private TextArea description;
      @FXML
    private Button video;
    @FXML
    private Label videotitle;
    @FXML
    private AnchorPane ancorPane;

    @FXML
    private Button update;

    private Stage stage;

    @FXML
    private ImageView imageV;

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

    @FXML
    public void close() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
        Stage primaryStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/views/AffichagePublication.fxml"));
            Parent root = (Parent) loader.load();
            AffichagePubController lp = loader.getController();
            //ap.getUsernameLabel().setText(usernameLabel.getText());
            lp.getUsernameLabel().setText(usernameLabel.getText());
            lp.setMyRole(myRole);
            //Parent root = FXMLLoader.load(getClass().getResource("/com/esprit/views/AffichagePublication.fxml"));
            Scene scene = new Scene(root);

            primaryStage.setTitle("Debug Entity");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
@FXML
    private void uploadVideo(ActionEvent event) throws IOException {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choisir un video");
        Stage s = (Stage) ancorPane.getScene().getWindow();
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Videos", "*.*"),
                new FileChooser.ExtensionFilter("MP4", "*.mp4"),
                new FileChooser.ExtensionFilter("AVI", "*.avi"),
                new FileChooser.ExtensionFilter("WMV", "*.wmv")
        );
        File f = chooser.showOpenDialog(s);
        Path path = Paths.get(System.getProperty("user.home") + "/Desktop/TGTVideos");
        Files.createDirectories(path);
        File publicationFile = new File(path.toString() + "/" + f.getName());
        try {
            copyFile(f, publicationFile);
        } catch (FileAlreadyExistsException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("fichier Existant !!");

            alert.showAndWait();
        }
        videotitle.setText(publicationFile.toURI().toString());
    }

    private static void copyFile(File src, File dest) throws IOException {
        Files.copy(src.toPath(), dest.toPath());
    }
    public static String saveToFileImageNormal(Image image) throws SQLException, IOException {

        String ext = "jpg";
        File dir = new File("src/Images");
        File fir=new File("C:\\wamp64\\www\\symfony\\web\\uploads");
        String name = String.format("%s.%s", RandomStringUtils.randomAlphanumeric(8), ext);
        File outputFile = new File(dir, name);
        File outputFile1 = new File(fir, name);
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        ImageIO.write(bImage, "png", outputFile);
        ImageIO.write(bImage, "png", outputFile1);
        return name;
    }

    @FXML
    private void addImage(MouseEvent event) throws IOException {
        FileChooser fc = new FileChooser();

        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (.png)", "*.PNG");
        fc.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
        File selectedFile = fc.showOpenDialog(null);
        try {
            BufferedImage bufferedImage = ImageIO.read(selectedFile);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            imageV.setImage(image);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void setPublication(Publication p) {
        this.publication = p;
 ServicePublication sp=new ServicePublication();
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
        alert.setContentText(ex+"");

        alert.showAndWait();
            sp.supprimer(p.getId());
          //  item.getChildren().clear();
        }   
        titre.setText(p.getTitre());
        putImageViewer(p.getBrochure_filename());
        videotitle.setText(p.getVideo());
        description.setText(p.getDescription());

    }

    public void putImageViewer(String path) {
        FileInputStream input = null;

        try {
            input = new FileInputStream("src/Images/" + path);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ModifierPublicationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Image imageFile = new Image(input);
        imageV.setImage(imageFile);

        try {
            input.close();
        } catch (IOException ex) {
            Logger.getLogger(ModifierPublicationController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void update(ActionEvent event) throws SQLException, IOException {

        if (titre.getText().equals("") || description.getText().equals("")||videotitle.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("veuiller saisir tout les champs!!");
            alert.showAndWait();
        } else {
            ServicePublication ps = new ServicePublication();
            Publication p = new Publication();

            p.setTitre(titre.getText());
            Image img = imageV.getImage();
            String nameImage1 = saveToFileImageNormal(img);
            p.setBrochure_filename(nameImage1);
            p.setDescription(description.getText());
            p.setVideo(videotitle.getText());
            p.setId(publication.getId());
            
            System.out.println(p);
            ps.modifier(p);
            System.out.println("publication modifiée avec succée");

        }

        Stage primaryStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/views/AffichagePublication.fxml"));
            Parent root = (Parent) loader.load();
            AffichagePubController lp = loader.getController();
            //ap.getUsernameLabel().setText(usernameLabel.getText());
            lp.getUsernameLabel().setText(usernameLabel.getText());
            lp.setMyRole(myRole);
            //Parent root = FXMLLoader.load(getClass().getResource("/com/esprit/views/AffichagePublication.fxml"));
            Scene scene = new Scene(root);

            primaryStage.setTitle("Debug Entity");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            System.out.println(e);
        }
        stage.close();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
