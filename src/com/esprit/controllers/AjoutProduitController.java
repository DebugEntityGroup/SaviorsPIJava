/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.controllers;

import com.esprit.models.CategorieProduit;
import com.esprit.models.Produit;
import com.esprit.services.ServiceCategorieProduit;
import com.esprit.services.ServiceProduit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import org.apache.commons.lang.RandomStringUtils;

/**
 * FXML Controller class
 *
 * @author Ahmed
 */
public class AjoutProduitController implements Initializable {
    
    @FXML
    private TextField tfUser_id;
    @FXML
    private TextField tfNom;
    
    @FXML
    private TextField tfPrix;
    @FXML
    private TextArea tfDescription;
    @FXML
    private ComboBox<String> tfCategorie_nom;
    @FXML
    private Button btn;
    @FXML
    private Button btnRetour;
    List<CategorieProduit> liste = new ArrayList<>();
    @FXML
    private ImageView imageV;
    
    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ServiceCategorieProduit es = new ServiceCategorieProduit();
        ObservableList<String> list = FXCollections.observableArrayList();
        liste = es.afficher();
        tfCategorie_nom.getItems().add("");
        liste.forEach((l) -> {
            tfCategorie_nom.getItems().add(l.getNom());
        });
    }
      
    public static String saveToFileImageNormal(Image image) throws SQLException, IOException {
        
        String ext = "jpg";
        File dir = new File("src/Images");
        String name = String.format("%s.%s", RandomStringUtils.randomAlphanumeric(8), ext);
        File outputFile = new File(dir, name);
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        ImageIO.write(bImage, "png", outputFile);
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
        alert.setContentText("Produit ajouté  avec succés");
        
        alert.showAndWait();
    }
    
    private void erreurAjout() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText("Veuillez remplir tout le formulaire !!");
        
        alert.showAndWait();
    }
      
    @FXML
    private void AjouterProduit(ActionEvent event) throws IOException, SQLException {
        ServiceProduit sp = new ServiceProduit();
        Image img = imageV.getImage();
        String nameImage1 = saveToFileImageNormal(img);
        String nom = tfNom.getText();
        String user_id = tfUser_id.getText();
        String desc = tfDescription.getText();
        String prix = tfPrix.getText();
        boolean valid = true;
        
        if (nom.equals("") || desc.equals("") || prix.equals("") || user_id.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("champ vide!!");
            alert.showAndWait();

            //valid=false;
        } else {
            
            sp.ajouter(new Produit(Integer.parseInt(user_id), nom, nameImage1, Integer.parseInt(prix), desc, tfCategorie_nom.getSelectionModel().getSelectedItem().toString()));
            
            succesAjout();
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/views/DetailsProduit.fxml"));
            
            Parent root = loader.load();
            tfNom.getScene().setRoot(root);
            
            DetailsProduitController dpc = loader.getController();
            dpc.setLbUser_id(Integer.parseInt(tfUser_id.getText()));
            dpc.setLbNom(tfNom.getText());
            dpc.setLbImage(nameImage1);
            dpc.setLbPrix(Integer.parseInt(tfPrix.getText()));
            dpc.setLbDescription(tfDescription.getText());
            dpc.setLbCategorie_nom(tfCategorie_nom.getSelectionModel().getSelectedItem().toString());
        }
        
    }
    
    @FXML
    private void Retouraffiche(ActionEvent event) throws IOException {
        
        try {
            
            btnRetour.getScene().getWindow().hide();
            Stage produits = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/esprit/views/AfficheProduit.fxml"));
            Scene scene = new Scene(root);
            produits.setScene(scene);
            produits.initStyle(StageStyle.UNDECORATED);
            produits.show();
            produits.setResizable(false);
            
        } catch (Exception e) {
            System.out.println(" Error  : " + e);
        }
    }
    
}
