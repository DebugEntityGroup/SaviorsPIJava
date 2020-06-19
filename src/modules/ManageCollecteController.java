package modules;

import homepage.UserAuthentifiedController;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import mysql.mysqlConnect;

public class ManageCollecteController implements Initializable {

    @FXML
    private Label usernameLabel;

    @FXML
    private javafx.scene.control.Button exit;

    @FXML
    private Label logoutText;

    @FXML
    private Label myRole;

    @FXML
    private Label listCollectes;

    @FXML
    private Label noCollects;

    @FXML
    private Button saviorsLogo;

    @FXML
    private Button gererCollecte;

    @FXML
    private TableView allCollectes;

    private ObservableList<ObservableList> data;

    @FXML
    private ComboBox<String> action;

    ObservableList<String> modules = FXCollections.observableArrayList("Evenement", "Publication", "Réclamation", "Collecte", "Produit", "Forum");

    public Label getUsernameLabel() {
        return usernameLabel;
    }

    public void setUsernameLabel(Label usernameLabel) {
        this.usernameLabel = usernameLabel;
    }

    public TableView getAllCollectes() {
        return allCollectes;
    }

    public void setAllCollectes(TableView allCollectes) {
        this.allCollectes = allCollectes;
    }

    public ObservableList<ObservableList> getData() {
        return data;
    }

    public void setData(ObservableList<ObservableList> data) {
        this.data = data;
    }

    @FXML
    private void moduleAction(ActionEvent event) throws Exception {
        try {
            if (action.getValue() == "Collecte") {
                exitAction();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/modules/Collecte.fxml"));
                Parent root = (Parent) loader.load();
                CollecteController cc = loader.getController();
                cc.getGererCollecte().setVisible(true);
                cc.getUsernameLabel().setText(usernameLabel.getText());
                cc.setMyRole(myRole);
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
                Scene scene = new Scene(root);
                Image icon = new Image(getClass().getResourceAsStream("/modules/images/saviorsIcon.png"));
                stage.getIcons().add(icon);
                stage.setScene(scene);
                stage.setTitle("Collecte - Saviors");
                Image mouseCursor = new Image("/saviorsda/images/mouseCursor.png");
                scene.setCursor(new ImageCursor(mouseCursor));
                stage.show();
                System.out.println("Consultation de la page \"Collecte\" par " + usernameLabel.getText());
            } else {
                JOptionPane.showMessageDialog(null, "Ce module n'est pas encore réalisé !");
            }
        } catch (Exception e) {
            System.out.println("Erreur de chargement de page !");
            System.out.println(e);
        }
    }

    @FXML
    private void allCollectes() {
        data = FXCollections.observableArrayList();
        try {
            Connection cnx = mysqlConnect.getInstance().getCnx();
            Statement stm = cnx.createStatement();
            String req = "select c.nomCollecte, c.budgetCollecte, c.nombreAtteint, c.descriptionCollecte, c.nombreParticipantsCollecte, c.categorieCollect_typeCategorie from collectPending c, fos_user f where f.id = c.user_id";
            ResultSet rs = stm.executeQuery(req);
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                col.setStyle(
                        "-fx-background-color: white;"
                );
                allCollectes.getColumns().addAll(col);
                System.out.println("Column [" + i + "] ");
            }
            System.out.println("Liste des Collectes");

            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getString(i));
                }
                System.out.println(row);
                data.add(row);
            }
            allCollectes.setItems(data);
            if (allCollectes.getItems().size() == 0) {
                allCollectes.setVisible(false);
                listCollectes.setVisible(false);
                noCollects.setVisible(true);
            }
        } catch (Exception e) {
            System.out.println("Erreur de l'affichage des Collectes !");
            e.printStackTrace();
        }
    }

    @FXML
    private void createCollecteAction(ActionEvent event) throws Exception {
        try {
            exitAction();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modules/CreateCollecte.fxml"));
            Parent root = (Parent) loader.load();
            CreateCollecteController cc = loader.getController();
            cc.getUsernameLabel().setText(usernameLabel.getText());
            cc.setMyRole(myRole);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(root);
            Image icon = new Image(getClass().getResourceAsStream("/modules/images/saviorsIcon.png"));
            stage.getIcons().add(icon);
            stage.setScene(scene);
            Image mouseCursor = new Image("/saviorsda/images/mouseCursor.png");
            scene.setCursor(new ImageCursor(mouseCursor));
            stage.setTitle("Créer une nouvelle Collecte - Saviors");
            stage.show();
        } catch (Exception e) {
            System.out.println("Erreur de chargement de page !");
            System.out.println(e);
        }
    }

    @FXML
    private void createCategorieAction(ActionEvent event) throws Exception {
        try {
            exitAction();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modules/CreateCategorieCollecte.fxml"));
            Parent root = (Parent) loader.load();
            CreateCategorieCollecteController ct = loader.getController();
            ct.getUsernameLabel().setText(usernameLabel.getText());
            ct.setMyRole(myRole);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(root);
            Image icon = new Image(getClass().getResourceAsStream("/modules/images/saviorsIcon.png"));
            stage.getIcons().add(icon);
            stage.setScene(scene);
            Image mouseCursor = new Image("/saviorsda/images/mouseCursor.png");
            scene.setCursor(new ImageCursor(mouseCursor));
            stage.setTitle("Créer une nouvelle Catégorie - Saviors");
            stage.show();
        } catch (Exception e) {
            System.out.println("Erreur de chargement de page !");
            System.out.println(e);
        }
    }

    @FXML
    private void exitAction() {
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void saviorsHomepage(ActionEvent event) throws Exception {
        try {
            Connection cnx = mysqlConnect.getInstance().getCnx();
            Statement stm = cnx.createStatement();
            String req = "select * from fos_user where username='" + usernameLabel.getText() + "'";
            ResultSet rs = stm.executeQuery(req);
            exitAction();
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

    @FXML
    private void disconnectAction() throws Exception {
        Connection cnx = mysqlConnect.getInstance().getCnx();
        Statement stm = cnx.createStatement();
        String req = "select * from fos_user where username = '" + usernameLabel.getText() + "'";
        ResultSet rs = stm.executeQuery(req);
        if (rs.next()) {
            int id = rs.getInt("id");
            String nomUser = rs.getString("username");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Déconnexion");
            alert.setHeaderText(null);
            alert.setContentText("Voulez-vous vous déconnecter ?");
            Optional<ButtonType> action = alert.showAndWait();
            if (action.get() == ButtonType.OK) {
                try {
                    Stage stage = (Stage) exit.getScene().getWindow();
                    stage.close();
                    System.out.println("L'utilisateur " + nomUser + " d'ID " + id + " est déconnecté !");
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/saviorsda/Login.fxml"));
                    Parent root = (Parent) loader.load();
                    //hc.getSeConnecter().setVisible(false);
                    Stage stage2 = new Stage();
                    stage2.initStyle(StageStyle.UNDECORATED);
                    //Parent root = FXMLLoader.load(getClass().getResource("/homepage/SaviorsHomepage.fxml"));
                    Scene scene = new Scene(root);
                    Image icon = new Image(getClass().getResourceAsStream("/saviorsda/images/saviorsIcon.png"));
                    stage2.getIcons().add(icon);
                    stage2.setScene(scene);
                    Image mouseCursor = new Image("/saviorsda/images/mouseCursor.png");
                    scene.setCursor(new ImageCursor(mouseCursor));
                    stage2.setTitle("Se Connecter - Saviors");
                    /*hc.getNavBar().getChildren().remove(hc.getSeConnecterBtn());
              hc.getNavBar().getChildren().remove(hc.getSeConnecter());*/
                    stage2.show();
                } catch (Exception e) {
                    System.out.println("Erreur de chargement de page !");
                }
            }
        }
    }

    @FXML
    private void exitButtonAction() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quitter");
        alert.setHeaderText(null);
        alert.setContentText("Voulez-vous quitter l'application ?");
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
            Stage stage = (Stage) exit.getScene().getWindow();
            stage.close();
            System.out.println("Déconnexion de la Base de données !");
            System.out.println("Fermeture de l'application.");
        }
    }

    public Button getGererCollecte() {
        return gererCollecte;
    }

    public void setGererCollecte(Button gererCollecte) {
        this.gererCollecte = gererCollecte;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        action.setItems(modules);
        allCollectes();
    }

    public Label getMyRole() {
        return myRole;
    }

    public void setMyRole(Label myRole) {
        this.myRole = myRole;
    }

}
