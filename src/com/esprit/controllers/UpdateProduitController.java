/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.controllers;

import com.esprit.models.Produit;
import com.esprit.services.ServiceCategorieProduit;
import com.esprit.services.ServiceProduit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import mysql.mysqlConnect;
import org.apache.commons.lang.RandomStringUtils;

/**
 *
 * @author Ahmed
 */
public class UpdateProduitController {

    private Produit produit;
    @FXML
    private Label closeButton;
   @FXML
    private ImageView imageV;
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
    
    @FXML
    private Button btnRetour;

    @FXML
    private Label usernameLabel;
    
    @FXML
    private Button exit;

    @FXML
    private Label myRole;

    private Stage stage;

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
    
    @FXML
    public void close() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    

    public void setProduit(Produit p) {
        this.produit = p;
        ServiceCategorieProduit es = new ServiceCategorieProduit();
        es.afficher().forEach((l) -> {
            categorie.getItems().add(l.getNom());
        });

        //user.setText(p.getUser_id() + "");
        nom.setText(p.getNom());
        putImageViewer(p.getImage());
        prix.setText(p.getPrix() + "");
        description.setText(p.getDescription());
        categorie.setValue(p.getCategorie_nom());

    }
 public void putImageViewer(String path){
            FileInputStream input = null;
        try {
            input = new FileInputStream("src/Images/"+path);
            Image imageFile = new Image(input);
            imageV.setImage(imageFile);
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
    @FXML
    void update(ActionEvent event) {
          try{ 
        if (nom.getText().equals("") || description.getText().equals("") || prix.equals("") || categorie.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("champ vide!!");
            alert.showAndWait();
            
            //valid=false;
        } 
      
        else if (Integer.valueOf(prix.getText())<0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("le prix doit etre un entier positif !!");
            alert.showAndWait();
        }
        else {
        ServiceProduit ps = new ServiceProduit();
        try {
        Connection cnx = mysqlConnect.getInstance().getCnx();
        Statement stm = cnx.createStatement();
        String req = "select * from fos_user f where f.username='"+usernameLabel.getText()+"'";
        ResultSet rs = stm.executeQuery(req);
        if (rs.next()) {
        Produit p = new Produit();
        p.setUser_id(rs.getInt("id"));
        p.setNom(nom.getText());
          Image img = imageV.getImage();
        String nameImage1 = saveToFileImageNormal(img);
        p.setImage(nameImage1);
        p.setPrix(Integer.parseInt(prix.getText()));
        p.setDescription(description.getText());
        p.setCategorie_nom(categorie.getValue());
        p.setId(produit.getId());

        System.out.println(p);
        ps.modifier(p);
        System.out.println("produit modifiée avec succée");
        }} catch (Exception e) {
            System.out.println(e);
        }
           
        }}catch (NumberFormatException e) {
                  Alert alert = new Alert(Alert.AlertType.ERROR);
                   alert.setTitle(null);
                   alert.setHeaderText(null);
                   alert.setContentText("Le prix doit etre un entier merci de vérifier !!");      
                   alert.showAndWait();
                    }
        
    
        
        //Stage primaryStage = new Stage();
        try {
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
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            Image mouseCursor = new Image("/saviorsda/images/mouseCursor.png");
            scene.setCursor(new ImageCursor(mouseCursor));
            stage.setTitle("Liste des Produits - Saviors");
            stage.show();
        } catch (Exception e) {
            System.out.println(e);
        }
        stage.close();
    }
  public static String saveToFileImageNormal(Image image) throws SQLException, IOException {

        String ext = "jpg";
        File dir = new File("src/Images");
        File fir=new File("C:\\wamp64\\www\\symfony\\web\\events");
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
            
          //  labelIMG.setVisible(false);
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
