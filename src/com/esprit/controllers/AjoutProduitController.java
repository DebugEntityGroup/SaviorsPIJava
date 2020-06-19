package com.esprit.controllers;

import com.esprit.models.CategorieProduit;
import com.esprit.models.Produit;
import com.esprit.services.ServiceCategorieProduit;
import com.esprit.services.ServiceProduit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.ImageCursor;
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
import mysql.mysqlConnect;
import org.apache.commons.lang.RandomStringUtils;

/**
 * FXML Controller class
 *
 * @author Ahmed
 */
public class AjoutProduitController implements Initializable {
   @FXML
    private Label labelIMG;
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
    @FXML
    private Label myRole;
    @FXML
    private Label usernameLabel;

    public Label getMyRole() {
        return myRole;
    }

    public void setMyRole(Label myRole) {
        this.myRole = myRole;
    }

    public Label getUsernameLabel() {
        return usernameLabel;
    }

    public void setUsernameLabel(Label usernameLabel) {
        this.usernameLabel = usernameLabel;
    }

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
        liste.forEach((l) -> {
            tfCategorie_nom.getItems().add(l.getNom());
        });
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
             labelIMG.setVisible(false);
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
        Connection cnx = mysqlConnect.getInstance().getCnx();
        Statement stm = cnx.createStatement();
        String req = "select * from fos_user f where f.username='"+usernameLabel.getText()+"'";
        ResultSet rs = stm.executeQuery(req);
        if (rs.next()) {
            int idUserP = rs.getInt("id");
        Image img = imageV.getImage();
        String nameImage1 = saveToFileImageNormal(img);
        String nom = tfNom.getText();
        //String user_id = tfUser_id.getText();
        String desc = tfDescription.getText();
        String prix = tfPrix.getText();
        boolean valid = true;
        

      try{      
        if (nom.equals("") || desc.equals("") || prix.equals("") || tfCategorie_nom.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("champ vide!!");
            alert.showAndWait();
            
            //valid=false;
        } 
      
        else if (Integer.valueOf(tfPrix.getText())<0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("le prix doit etre un entier positif !!");
            alert.showAndWait();
        }
        else {
            sp.ajouter(new Produit(idUserP, nom, nameImage1, Integer.parseInt(prix), desc, tfCategorie_nom.getSelectionModel().getSelectedItem().toString()));

            succesAjout();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/views/DetailsProduit.fxml"));

            Parent root = loader.load();
            tfNom.getScene().setRoot(root);

            DetailsProduitController dpc = loader.getController();
            dpc.setMyRole(myRole);
            dpc.setUsernameLabel(usernameLabel);
            //dpc.getUsernameLabel().setText(usernameLabel.getText());
            //dpc.setLbUser_id(Integer.parseInt(tfUser_id.getText()));
            dpc.setLbNom(tfNom.getText());
            dpc.setLbImage(nameImage1);
            dpc.setLbPrix(Integer.parseInt(tfPrix.getText()));
            dpc.setLbDescription(tfDescription.getText());
            dpc.setLbCategorie_nom(tfCategorie_nom.getSelectionModel().getSelectedItem().toString());
        }
         }catch (NumberFormatException e) {
                  Alert alert = new Alert(Alert.AlertType.ERROR);
                   alert.setTitle(null);
                   alert.setHeaderText(null);
                   alert.setContentText("Le prix doit etre un entier merci de vérifier !!");      
                   alert.showAndWait();
                    }
        
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
            System.out.println("aaaa" + usernameLabel.getText());
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            Image icon = new Image(getClass().getResourceAsStream("/homepage/images/saviorsIcon.png"));
            stage.getIcons().add(icon);
            stage.setScene(scene);
            Image mouseCursor = new Image("/saviorsda/images/mouseCursor.png");
            scene.setCursor(new ImageCursor(mouseCursor));
            stage.setTitle("Liste des Produits - Saviors");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();

        } catch (Exception e) {
            System.out.println(" Erreur  : " + e);
        }
    }

}
