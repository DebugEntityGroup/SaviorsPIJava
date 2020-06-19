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
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import org.apache.commons.lang.RandomStringUtils;

/**
 * FXML Controller class
 *
 * @author Lonovo
 */
public class AjoutPublicationController implements Initializable {

    @FXML
    private TextField Titre;
private Stage stage;
    @FXML
    private TextArea Description;
    @FXML
    private Button btn;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label myRole;
    @FXML
    private Button btnRetour;
    @FXML
    private ImageView imageV;
    @FXML
    private Button video;
    @FXML
    private Button closeButton;
    @FXML
    private Button saker;
    @FXML
    private Label videotitle;
    @FXML
    private AnchorPane ancorPane;

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

    private void succesAjout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText("publication ajouté  avec succés");

        alert.showAndWait();
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
        Path path = Paths.get(System.getProperty("user.home") + "/Desktop/SaviorsVideos");
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
    
    @FXML
    public void close() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void AjouterPublication(ActionEvent event) throws IOException, SQLException {
        ServicePublication sp = new ServicePublication();
        Image img = imageV.getImage();
        String nameImage1 = saveToFileImageNormal(img);
        String titre = Titre.getText();
        String description = Description.getText();
       
        if (titre.equals("") || description.equals("") || videotitle.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("svp remplissez tout les champs");
            alert.showAndWait();
        } else {
            sp.ajouter(new Publication(titre, description, nameImage1, videotitle.getText()));
            succesAjout();
       Stage primaryStage = new Stage();
        try {
            close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/views/AffichagePublication.fxml"));
            Parent root = (Parent) loader.load();
            AffichagePubController ap = loader.getController();
            ap.setMyRole(myRole);
            ap.setUsernameLabel(usernameLabel);
            //Parent root = FXMLLoader.load(getClass().getResource("/com/esprit/views/AffichagePublication.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Debug Entity");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            System.out.println(e);
        }
        }
        
    }
 public void setStage(Stage stage) {
        this.stage = stage;
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/views/AffichagePublication.fxml"));
            Parent root = (Parent) loader.load();
            AffichagePubController ap = loader.getController();
            //ap.getUsernameLabel().setText(usernameLabel.getText());
            ap.setMyRole(myRole);
            ap.getUsernameLabel().setText(usernameLabel.getText());
            System.out.println(usernameLabel.getText()+ "pub no name");
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            Image icon = new Image(getClass().getResourceAsStream("/homepage/images/saviorsIcon.png"));
            stage.getIcons().add(icon);
            stage.setScene(scene);
            Image mouseCursor = new Image("/saviorsda/images/mouseCursor.png");
            scene.setCursor(new ImageCursor(mouseCursor));
            stage.setTitle("Publications - Saviors");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();

        } catch (Exception e) {
            System.out.println(" Erreur  : " + e);
        }
    }

}
