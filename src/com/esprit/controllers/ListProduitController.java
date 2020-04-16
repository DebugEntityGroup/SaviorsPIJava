/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.esprit.models.Produit;
import com.esprit.services.ServiceProduit;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Ahmed
 */
public class ListProduitController implements Initializable {

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @FXML
    private Label closeButton;
    @FXML
    private TextField text_id;
    @FXML
    private TextField search;
    @FXML
    private Button btn;
    @FXML
    private Button btnRetour;
    @FXML
    private Button btnModifier;
    @FXML
    private Button btnSupprimer;
    @FXML
    private Label usernameLabel;

    private int selectIndex;
    @FXML
    private ListView<Produit> table;

    public Label getUsernameLabel() {
        return usernameLabel;
    }

    public void setUsernameLabel(Label usernameLabel) {
        this.usernameLabel = usernameLabel;
    }

    public void getAll(ActionEvent event) throws SQLException {
        ServiceProduit Gds = new ServiceProduit();

        table.getItems().setAll(Gds.afficher());
        Produit d = table.getSelectionModel().getSelectedItem();
        selectIndex = table.getSelectionModel().getSelectedIndex();
        text_id.setText(d.getId() + "");

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ServiceProduit sp;
        sp = new ServiceProduit();
        table.getItems().setAll(sp.afficher());
        table.setOnMouseClicked((MouseEvent event) -> {
            Produit A = table.getSelectionModel().getSelectedItem();
            selectIndex = table.getSelectionModel().getSelectedIndex();
            System.out.println(A);
            //  text_id.setText(A.getId() + "");
        });

        search.textProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                try {
                    filterProduit((String) oldValue, (String) newValue);
                } catch (SQLException ex) {
                    Logger.getLogger(ListProduitController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        });
        if (search.getText() == null) {
            ServiceProduit pss = new ServiceProduit();
        }

    }

    @FXML
    public void close() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();

    }

    public void filterProduit(String oldValue, String newValue) throws SQLException {
        ServiceProduit ps = new ServiceProduit();

        ObservableList<Produit> Produits = FXCollections.observableArrayList(ps.afficher());
        ObservableList<Produit> filteredList = FXCollections.observableArrayList();
        if (search == null || (newValue.length() < oldValue.length()) || newValue == null) {
            table.setItems(Produits);
        } else {
            newValue = newValue.toUpperCase();
            for (Produit p : table.getItems()) {
                String nom = p.getNom();
                if (nom.toUpperCase().contains(newValue)) {
                    filteredList.add(p);
                }
            }
            table.setItems(filteredList);
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

    @FXML
    private void AjouterProduit(ActionEvent event) throws IOException {

        try {

            btn.getScene().getWindow().hide();
            Stage produits = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/esprit/views/AjoutProduit.fxml"));
            Scene scene = new Scene(root);
            produits.setScene(scene);
            produits.initStyle(StageStyle.UNDECORATED);
            produits.show();
            produits.setResizable(false);

        } catch (Exception e) {
            System.out.println(" Error  : " + e);
        }
    }

    @FXML
    private void ModifierProduit(ActionEvent event) throws IOException {
        if (table.getSelectionModel().getSelectedItem() != null) {
            Stage stage2 = (Stage) btnModifier.getScene().getWindow();
            stage2.close();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/views/UpdateProduit.fxml"));
                Parent parent = loader.load();
                UpdateProduitController Upc = loader.getController();
                Produit p = table.getSelectionModel().getSelectedItem();

                Upc.setProduit(p);

                Scene scene = new Scene(parent);
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                Upc.setStage(stage);
                stage.showAndWait();

            } catch (Exception e) {
                System.out.println(" Error  : " + e);
            }
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/views/Alert.fxml"));
                Parent parent = loader.load();

                Scene scene = new Scene(parent);
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.showAndWait();

            } catch (Exception e) {
                System.out.println(" Error  : blabla");
            }
        }

    }

    @FXML
    private void DetailsProduit(ActionEvent event) throws IOException {
        if (table.getSelectionModel().getSelectedItem() != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/views/ShowProduit.fxml"));
                Parent parent = loader.load();
                ShowProduitController Spc = loader.getController();
                Produit p = table.getSelectionModel().getSelectedItem();

                Spc.setProduit(p);

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
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/views/Alert.fxml"));
                Parent parent = loader.load();

                Scene scene = new Scene(parent);
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.showAndWait();

            } catch (Exception e) {
                System.out.println(" Error  : " + e);
            }
        }
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
    private void SupprimerProduit(ActionEvent event) throws IOException {
        if (table.getSelectionModel().getSelectedItem() != null) {
            Stage stage2 = (Stage) btnSupprimer.getScene().getWindow();
            stage2.close();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/views/DeleteProduit.fxml"));
                Parent parent = loader.load();
                DeleteProduitcontroller Spc = loader.getController();
                Produit p = table.getSelectionModel().getSelectedItem();

                Spc.setProduit(p);

                Scene scene = new Scene(parent);
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                Spc.setStage(stage);
                stage.showAndWait();

            } catch (Exception e) {
                System.out.println(" Error  : " + e);
            }
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/views/Alert.fxml"));
                Parent parent = loader.load();

                Scene scene = new Scene(parent);
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.showAndWait();

            } catch (Exception e) {
                System.out.println(" Error  : " + e);
            }
        }
    }
}
