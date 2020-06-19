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
import javafx.beans.property.ReadOnlyObjectWrapper;
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
import javafx.util.Callback;
import javax.swing.JOptionPane;
import modules.CollecteController;
import mysql.mysqlConnect;

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
    private TextField search;
    @FXML
    private Button btn;
    @FXML
    private Button esm;
    @FXML
    private Button btnRetour;
    @FXML
    private Button btnModifier;
    @FXML
    private Button btnSupprimer;
    @FXML
    private Label usernameLabel;
    @FXML
    private Button btnDats;
    @FXML
    private ComboBox<String> actionM;

    private int selectIndex;
    private Label myRole;
    @FXML
    private TableView<Produit> table;
    @FXML
    private TableColumn<Produit, String> nomproduit;
    @FXML
    private TableColumn<Produit, String> description;
    @FXML
    private TableColumn<Produit, String> prix;
    @FXML
    private TableColumn<Produit, String> categorie;
    @FXML
    private AnchorPane anchor;
    // @FXML
    // private TableColumn<Produit, String> image;

    ObservableList<String> modulesM = FXCollections.observableArrayList("Evenement", "Publication", "Réclamation", "Collecte", "Produit", "Forum");
    @FXML
    private AnchorPane action;
    @FXML
    private AnchorPane navBar;
    @FXML
    private Button logoutBtn;
    @FXML
    private Button saviorsLogo;
    @FXML
    private Pane pan;

    public Button getBtnRetour() {
        return btnRetour;
    }

    public void setBtnRetour(Button btnRetour) {
        this.btnRetour = btnRetour;
    }

    public Label getMyRole() {
        return myRole;
    }

    public void setMyRole(Label myRole) {
        this.myRole = myRole;
    }

    public TextField getSearch() {
        return search;
    }

    public void setSearch(TextField search) {
        this.search = search;
    }

    public Button getBtnDats() {
        return btnDats;
    }

    public void setBtnDats(Button btnDats) {
        this.btnDats = btnDats;
    }

    public TableView<Produit> getTable() {
        return table;
    }

    public void setTable(TableView<Produit> table) {
        this.table = table;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    public Button getBtnModifier() {
        return btnModifier;
    }

    public void setBtnModifier(Button btnModifier) {
        this.btnModifier = btnModifier;
    }

    public Button getBtnSupprimer() {
        return btnSupprimer;
    }

    public void setBtnSupprimer(Button btnSupprimer) {
        this.btnSupprimer = btnSupprimer;
    }

    public Label getUsernameLabel() {
        return usernameLabel;
    }

    public void setUsernameLabel(Label usernameLabel) {
        this.usernameLabel = usernameLabel;
    }

    /* public void getAll(ActionEvent event) throws SQLException {
        ServiceProduit Gds = new ServiceProduit();

        table.getItems().setAll(Gds.afficher());
        Produit d = table.getSelectionModel().getSelectedItem();
        selectIndex = table.getSelectionModel().getSelectedIndex();
        text_id.setText(d.getId() + "");

    }*/
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
            } else if (actionM.getValue() == "Produit") {
                JOptionPane.showMessageDialog(null, "Vous êtes déjà dans la page \"Produit\".");
            } else {
                JOptionPane.showMessageDialog(null, "Ce module n'est pas encore réalisé !");
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

        try {
            actionM.setItems(modulesM);

            ServiceProduit prod = new ServiceProduit();

            table.setEditable(false);
            table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            //   imageProducts.setCellFactory(c-> new SimpleObjectProperty<>(new ImageView(c.getValue()));
            nomproduit.setCellValueFactory(new PropertyValueFactory<>("nom"));
            description.setCellValueFactory(new PropertyValueFactory<>("Description"));
            prix.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Produit, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Produit, String> p) {
                    String prix = p.getValue().getPrix() + " DT";
                    ObservableValue<String> obsInt = new ReadOnlyObjectWrapper<>(prix);
                    return obsInt;
                }
            });
            //prix.setCellValueFactory(new PropertyValueFactory<>("Prix"));
            categorie.setCellValueFactory(new PropertyValueFactory<>("Categorie_nom"));
            //image.setCellValueFactory(new PropertyValueFactory<>("Image"));
            table.setItems(prod.afficher());
        } catch (Exception e) {
        }

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
                String cat = p.getCategorie_nom();
                if (nom.toUpperCase().contains(newValue) || cat.toUpperCase().contains(newValue)) {
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
            //Stage produits = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/views/AjoutProduit.fxml"));
            Parent root = (Parent) loader.load();
            AjoutProduitController ap = loader.getController();
            System.out.println("aaaaaaaaaaaaaa" + usernameLabel.getText() + "nulllllllll " + myRole.getText());
            ap.setMyRole(myRole);
            ap.setUsernameLabel(usernameLabel);
            /*ap.getUsernameLabel().setText(usernameLabel.getText());
            System.out.println("aaaaaaaaaaaaaaaaaa"+usernameLabel.getText());*/
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            Image icon = new Image(getClass().getResourceAsStream("/homepage/images/saviorsIcon.png"));
            stage.getIcons().add(icon);
            stage.setScene(scene);
            Image mouseCursor = new Image("/saviorsda/images/mouseCursor.png");
            scene.setCursor(new ImageCursor(mouseCursor));
            stage.setTitle("Ajouter un Produit - Saviors");
            //Parent root = FXMLLoader.load(getClass().getResource("/com/esprit/views/AjoutProduit.fxml"));
            //Scene scene = new Scene(root);
            //produits.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
            stage.setResizable(false);

        } catch (Exception e) {
            System.out.println("ERREUR: " + e);
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
                Upc.setUsernameLabel(usernameLabel);
                Upc.setMyRole(myRole);
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
                Spc.setUsernameLabel(usernameLabel);
                Spc.setMyRole(myRole);
                Produit p = table.getSelectionModel().getSelectedItem();

                Spc.setProduit(p);

                Spc.setComment();
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
                Spc.setUsernameLabel(usernameLabel);
                Spc.setMyRole(myRole);
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

    @FXML
    private void RetourCateg(ActionEvent event) throws IOException {

        try {

            btnRetour.getScene().getWindow().hide();
            Stage produits = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/views/AjoutCategorie.fxml"));
            Parent parent = loader.load();
            AjoutCategorieController Spc = loader.getController();
            Spc.setUsernameLabel(usernameLabel);
            Spc.setMyRole(myRole);
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);

            produits.setScene(scene);
            produits.initStyle(StageStyle.UNDECORATED);
            produits.show();
            produits.setResizable(false);

        } catch (Exception e) {
            System.out.println(" Error  : " + e);
        }
    }

    @FXML
    private void Code(ActionEvent event) throws IOException {

        if (table.getSelectionModel().getSelectedItem() != null) {

            FXMLLoader Loader = new FXMLLoader();
            Loader.setLocation(getClass().getResource("/com/esprit/views/QrCode.fxml"));
            Parent par = Loader.load();
            Produit p = table.getSelectionModel().getSelectedItem();
            QrCodeController cont = Loader.getController();
            try {
                cont.ini(p);
            } catch (SQLException ex) {
                Logger.getLogger(ListProduitController.class.getName()).log(Level.SEVERE, null, ex);
            }

           // AnchorPane pane = (AnchorPane) anchor.getParent().getParent().getParent();
           // pane.getChildren().clear();
           // pane.getChildren().setAll(par);

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
