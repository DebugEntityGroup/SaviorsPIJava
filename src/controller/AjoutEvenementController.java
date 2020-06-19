/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static com.esprit.controllers.AjoutProduitController.saveToFileImageNormal;
import entities.Evenement;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import service.EvenementService;
import service.EvenementServiceInterface;
import java.time.LocalDate;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.control.DatePicker;
import javax.imageio.ImageIO;
import org.apache.commons.lang.RandomStringUtils;

/**
 * FXML Controller class
 *
 * @author dell
 */
public class AjoutEvenementController implements Initializable {

    @FXML
    private TextField titre;
    @FXML
    private TextField lieu;
    @FXML
    private DatePicker datedebut;

    @FXML
    private Button ajout;
    @FXML
    private Pane Pane;

    EvenementServiceInterface evt;

    int c;
    int file;
    File pDir;
    File pfile;
    String lien;
    @FXML
    private ImageView Event_img;
    @FXML
    private TextField description;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        c = (int) (Math.random() * (300000 - 2 + 1)) + 2;
        pDir = new File("src/gui/img/Profile" + c + ".jpg");
        lien = "Profile" + c + ".jpg";

    }

    public static String saveToFileImageNormal(Image image) throws SQLException, IOException {

        String ext = "jpg";
        File dir = new File("src/Images");
        File fir = new File("C:\\wamp64\\www\\symfony\\web\\events");
        String name = String.format("%s.%s", RandomStringUtils.randomAlphanumeric(8), ext);
        File outputFile = new File(dir, name);
        File outputFile1 = new File(fir, name);
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        ImageIO.write(bImage, "png", outputFile);
        ImageIO.write(bImage, "png", outputFile1);
        return name;
    }

    @FXML
    private void uploadImage(ActionEvent event) throws MalformedURLException, SQLDataException {
        FileChooser fc = new FileChooser();

        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (.png)", "*.PNG");
        fc.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
        File selectedFile = fc.showOpenDialog(null);
        try {
            BufferedImage bufferedImage = ImageIO.read(selectedFile);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            Event_img.setImage(image);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void AjouterEvent(ActionEvent event) throws SQLDataException, SQLException, IOException {

        LocalDate d = LocalDate.now();
        Date date = java.sql.Date.valueOf(d);
        Date dd = new java.sql.Date(new Date(datedebut.getEditor().getText()).getTime());

        long diff = dd.getTime() - date.getTime();
        float res1 = (diff / (1000 * 60 * 60 * 24));

        if (titre.getText().equals("") || lieu.getText().equals("") || datedebut.getValue() == null || description.getText().equals("")) {

            
        }else {

            String t = titre.getText();
            String l = lieu.getText();
            //copier(pfile, pDir);
            Image img = Event_img.getImage();
            String nameImage1 = saveToFileImageNormal(img);

            EvenementService Es = new EvenementService();
            Evenement e = new Evenement(0, 0, titre.getText(), description.getText(), nameImage1, lieu.getText(), (java.sql.Date) dd);

            Es.addEvenement(e);

            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("/gui/listeEvenement.fxml"));
                Stage myWindow = (Stage) datedebut.getScene().getWindow();
                Scene sc = new Scene(root);
                myWindow.setScene(sc);
                myWindow.setTitle("page name");
                //myWindow.setFullScreen(true);
                myWindow.show();
            } catch (IOException ex) {
                Logger.getLogger(ModifiEvenementController.class.getName()).log(Level.SEVERE, null, ex);
            }

            /*   FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ListeEvenement.fxml"));
                try {
                    Parent root = loader.load();
                    ListeEvenementController le=loader.getController();
                    
                    
                    titre.getScene().setRoot(root);
                    
                }catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
             */
        }
    }

    public static boolean copier(File source, File dest) {
        try (InputStream sourceFile = new java.io.FileInputStream(source);
                OutputStream destinationFile = new FileOutputStream(dest)) {
            // Lecture par segment de 0.5Mo  
            byte buffer[] = new byte[512 * 1024];
            int nbLecture;
            while ((nbLecture = sourceFile.read(buffer)) != -1) {
                destinationFile.write(buffer, 0, nbLecture);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false; // Erreur 
        }
        return true; // Résultat OK   
    }

}
