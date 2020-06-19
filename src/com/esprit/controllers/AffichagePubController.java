/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.controllers;

import com.esprit.models.Publication;
import com.esprit.services.ServicePublication;
import homepage.UserAuthentifiedController;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;
import modules.CollecteController;
import mysql.mysqlConnect;

/**
 *
 * @author lenovo
 */
public class AffichagePubController implements Initializable {

    @FXML
    private TextField search;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label myRole;
    @FXML
    private Button btn;
    private Button btnRetour;
    ObservableList<String> modulesM = FXCollections.observableArrayList("Evenement", "Publication", "Réclamation", "Collecte", "Produit", "Forum");

    @FXML
    private Button btnModifier;
    @FXML
    private Button btnSupprimer;

    private int selectIndex;
    @FXML
    private TableView<Publication> table;
    @FXML
    private TableColumn<Publication, String> titre;
    @FXML
    private TableColumn<Publication, String> description;
    @FXML
    private TableColumn<Publication, String> image;
    @FXML
    private TableColumn<Publication, String> video;
    private Publication publication;
    @FXML
    private Pane pan;
    @FXML
    private AnchorPane navBar;
    @FXML
    private Label closeButton;
    @FXML
    private Button logoutBtn;
    @FXML
    private Button saviorsLogo;
    @FXML
    private ComboBox<String> actionM;
    @FXML
    private Button saviorsLogo1;
    @FXML
    private Button btnDats;

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
        stage.close();
    }

    @FXML
    private void logoutAction(ActionEvent event) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Déconnexion");
        alert.setHeaderText(null);
        alert.setContentText("Voulez-vous vous déconnecter ?");
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/saviorsda/Login.fxml"));
            Parent parent = loader.load();
            Stage stage1 = new Stage();
            stage1.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(parent);
            Image mouseCursor = new Image("/saviorsda/images/mouseCursor.png");
            scene.setCursor(new ImageCursor(mouseCursor));
            Image icon = new Image(getClass().getResourceAsStream("/saviorsda/images/saviorsIcon.png"));
            stage1.getIcons().add(icon);
            stage1.setScene(scene);
            stage1.setTitle("Se Connecter - Saviors");
            stage1.show();
        }
    }

    @FXML
    private void moduleActionM(ActionEvent event) throws Exception {
        try {
            if (actionM.getValue() == "Collecte") {
                close();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/modules/Collecte.fxml"));
                Parent root = (Parent) loader.load();
                CollecteController cc = loader.getController();
                if (myRole.getText() == "Association") {
                    cc.getGererCollecte().setVisible(true);
                    cc.setMyRole(myRole);
                } else {
                    cc.getGererCollecte().setVisible(false);
                    cc.setMyRole(myRole);
                }
                cc.getUsernameLabel().setText(usernameLabel.getText());
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
                Scene scene = new Scene(root);
                Image icon = new Image(getClass().getResourceAsStream("/modules/images/saviorsIcon.png"));
                stage.getIcons().add(icon);
                stage.setScene(scene);
                Image mouseCursor = new Image("/saviorsda/images/mouseCursor.png");
                scene.setCursor(new ImageCursor(mouseCursor));
                stage.setTitle("Collecte - Saviors");
                stage.show();
                System.out.println("Consultation de la page \"Produit\" par " + usernameLabel.getText());
            } else if (actionM.getValue() == "Publication") {
                JOptionPane.showMessageDialog(null, "Vous êtes déjà dans la page \"Publication\".");
            } else {
                JOptionPane.showMessageDialog(null, "Rien ici !");
            }
        } catch (Exception e) {
            System.out.println("Erreur de chargement de page !");
            System.out.println(e);
        }
    }

    @FXML
    public void saviorsHomepage(ActionEvent event) throws Exception {
        try {
            Connection cnx = mysqlConnect.getInstance().getCnx();
            Statement stm = cnx.createStatement();
            String req = "select * from fos_user where username='" + usernameLabel.getText() + "'";
            ResultSet rs = stm.executeQuery(req);
            close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/homepage/userAuthentified.fxml"));
            Parent root = (Parent) loader.load();
            UserAuthentifiedController ua = loader.getController();
            if (rs.next()) {
                if ("a:1:{i:0;s:10:\"ROLE_ASSOC\";}".equals(rs.getString("roles"))) {
                    ua.getMyRole().setText("Association");
                    ua.getMyRole().setVisible(false);
                }
                if ("a:1:{i:0;s:10:\"ROLE_FOURN\";}".equals(rs.getString("roles"))) {
                    ua.getMyRole().setText("Fournisseur");
                    ua.getMyRole().setVisible(false);
                }
                if ("a:1:{i:0;s:11:\"ROLE_MEMBER\";}".equals(rs.getString("roles"))) {
                    ua.getMyRole().setText("Membre");
                    ua.getMyRole().setVisible(false);
                }
            }
            ua.getUsernameLabel().setText(usernameLabel.getText());
            ua.getMyRole().setText(myRole.getText());
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(root);
            Image icon = new Image(getClass().getResourceAsStream("/homepage/images/saviorsIcon.png"));
            stage.getIcons().add(icon);
            stage.setScene(scene);
            Image mouseCursor = new Image("/saviorsda/images/mouseCursor.png");
            scene.setCursor(new ImageCursor(mouseCursor));
            stage.setTitle("Accueil - Saviors");
            stage.show();
        } catch (IOException e) {
            System.out.println("Erreur de chargement de page !");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        actionM.setItems(modulesM);
        ServicePublication sp;
        sp = new ServicePublication();
        table.setEditable(false);
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        //   imageProducts.setCellFactory(c-> new SimpleObjectProperty<>(new ImageView(c.getValue()));
        titre.setCellValueFactory(new PropertyValueFactory<>("Titre"));
        description.setCellValueFactory(new PropertyValueFactory<>("Description"));

        video.setCellValueFactory(new PropertyValueFactory<>("Video"));
        image.setCellValueFactory(new PropertyValueFactory<>("Brochure_filename"));
        table.setItems(sp.afficher());
        table.setOnMouseClicked((MouseEvent event) -> {
            Publication A = table.getSelectionModel().getSelectedItem();
            selectIndex = table.getSelectionModel().getSelectedIndex();
            System.out.println(A);
            //  text_id.setText(A.getId() + "");
        });

        search.textProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                try {
                    filterPublication((String) oldValue, (String) newValue);
                } catch (SQLException ex) {
                    Logger.getLogger(AffichagePubController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        });
        if (search.getText() == null) {
            ServicePublication pss = new ServicePublication();
        }

    }

    public void filterPublication(String oldValue, String newValue) throws SQLException {
        ServicePublication ps = new ServicePublication();

        ObservableList<Publication> Publications = FXCollections.observableArrayList(ps.afficher());
        ObservableList<Publication> filteredList = FXCollections.observableArrayList();
        if (search == null || (newValue.length() < oldValue.length()) || newValue == null) {
            table.setItems(Publications);
        } else {
            newValue = newValue.toUpperCase();
            for (Publication p : table.getItems()) {
                String nom = p.getTitre();

                if (nom.toUpperCase().contains(newValue)) {
                    filteredList.add(p);
                }
            }
            table.setItems(filteredList);
        }
    }

    private void Retouraffiche(ActionEvent event) throws IOException {

        try {

            btnRetour.getScene().getWindow().hide();
            Stage produits = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/esprit/views/AffichagePublication.fxml"));
            Scene scene = new Scene(root);
            produits.setScene(scene);
            produits.show();
            produits.setResizable(false);

        } catch (Exception e) {
            System.out.println(" Error  : " + e);
        }
    }

    @FXML
    private void AjouterPublication(ActionEvent event) throws IOException {

        try {
            btn.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/views/AjoutPublication.fxml"));
            Parent root = (Parent) loader.load();
            AjoutPublicationController ap = loader.getController();
            ap.setMyRole(myRole);
            ap.setUsernameLabel(usernameLabel);
            Stage pub = new Stage();
            //Parent root = FXMLLoader.load(getClass().getResource("/com/esprit/views/AjoutPublication.fxml"));
            Scene scene = new Scene(root);
            pub.setScene(scene);
            pub.show();
            pub.setResizable(false);

        } catch (Exception e) {
            System.out.println(" Error  : " + e);
        }
    }

    @FXML
    private void deletePub(ActionEvent event) throws IOException {
        if (table.getSelectionModel().getSelectedItem() != null) {
            Stage stage2 = (Stage) btnSupprimer.getScene().getWindow();
            try {
                Publication p = table.getSelectionModel().getSelectedItem();
                ServicePublication ps = new ServicePublication();
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Supprimer Publication");
                alert.setHeaderText("voulez vous supprimer cette publication");
                alert.setContentText(p.getTitre());
                Optional<ButtonType> option = alert.showAndWait();
                if (option.get() == ButtonType.OK) {
                    ps.supprimer(p.getId());
                    System.out.println("Publication supprimer avec succée");
                    Stage primaryStage = new Stage();
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/views/AffichagePublication.fxml"));
                        Parent root = (Parent) loader.load();
                        AffichagePubController apub = loader.getController();
                        apub.getUsernameLabel().setText(usernameLabel.getText());
                        apub.setMyRole(myRole);
                        stage2.close();
                        //Parent root = FXMLLoader.load(getClass().getResource("/com/esprit/views/AffichagePublication.fxml"));
                        Scene scene = new Scene(root);
                        primaryStage.setTitle("Debug Entity");
                        primaryStage.setScene(scene);
                        primaryStage.show();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                } else if (option.get() == ButtonType.CANCEL) {
                    Stage primaryStage = new Stage();
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/views/AffichagePublication.fxml"));
                        Parent root = (Parent) loader.load();
                        AffichagePubController apub = loader.getController();
                        apub.getUsernameLabel().setText(usernameLabel.getText());
                        apub.setMyRole(myRole);
                        stage2.close();
                        //Parent root = FXMLLoader.load(getClass().getResource("/com/esprit/views/AffichagePublication.fxml"));
                        Scene scene = new Scene(root);
                        primaryStage.setTitle("Publications");
                        primaryStage.setScene(scene);
                        primaryStage.show();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            } catch (Exception e) {
                System.out.println(" Error  : " + e);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("merci de selectionner une publication");
            alert.showAndWait();
        }
    }

    @FXML
    private void DetailsPublication(ActionEvent event) throws IOException {
        if (table.getSelectionModel().getSelectedItem() != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/views/OnePublicationAffiche.fxml"));
                Parent parent = loader.load();
                OnePublicationAfficheController Spc = loader.getController();
                Publication p = table.getSelectionModel().getSelectedItem();
                Spc.setPublication(p);
                Scene scene = new Scene(parent);
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.showAndWait();

            } catch (Exception e) {
                System.out.println(" Error  : " + e);
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Merci de selectionner une publication");

            alert.showAndWait();
        }
    }

    @FXML
    private void ModifierPublication(ActionEvent event) throws IOException {
        if (table.getSelectionModel().getSelectedItem() != null) {
            Stage stage2 = (Stage) btnModifier.getScene().getWindow();
            stage2.close();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/views/ModifierPub.fxml"));
                Parent parent = loader.load();
                ModifierPublicationController Upc = loader.getController();
                Upc.setUsernameLabel(usernameLabel);
                Upc.setMyRole(myRole);
                Publication p = table.getSelectionModel().getSelectedItem();

                Upc.setPublication(p);

                Scene scene = new Scene(parent);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.setTitle("Debug Entity");
                Upc.setStage(stage);
                stage.showAndWait();

            } catch (Exception e) {
                System.out.println(" Error  : " + e);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("merci de selectionner une publication");

            alert.showAndWait();
        }

    }
}
