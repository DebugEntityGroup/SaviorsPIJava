package homepage;

import com.esprit.controllers.AffichagePubController;
import com.esprit.controllers.ListProduitController;
import controller.ListeEvenementController;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;
import modules.CollecteController;
import mysql.mysqlConnect;
import saviorsda.LoginController;

public class UserAuthentifiedController implements Initializable {

    @FXML
    private javafx.scene.control.Button exit;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label myRole;

    @FXML
    private ComboBox<String> action;

    @FXML
    private Label logoutText;

    @FXML
    private Button saviorsLogo;

    ObservableList<String> modules = FXCollections.observableArrayList("Evenement", "Publication", "Réclamation", "Collecte", "Produit", "Forum");

    public void myUserNameFunction(String text) {
        usernameLabel.setText(text);
    }

    @FXML
    public void saviorsHomepage(ActionEvent event) throws Exception {
        JOptionPane.showMessageDialog(null, "Vous êtes déjà dans la page d'accueil.");
    }

    @FXML
    private void moduleAction(ActionEvent event) throws Exception {
        try {
            if (action.getValue() == "Collecte") {
                exitAction();
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
                System.out.println("Consultation de la page \"Collecte\" par " + usernameLabel.getText());
            } else if (action.getValue() == "Publication") {
                exitAction();
                Stage pub = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/views/AffichagePublication.fxml"));
                Parent root = (Parent) loader.load();
                AffichagePubController apub = loader.getController();
                apub.getUsernameLabel().setText(usernameLabel.getText());
                apub.setMyRole(myRole);
                Scene scene = new Scene(root);
                pub.setScene(scene);
                Image mouseCursor = new Image("/saviorsda/images/mouseCursor.png");
                scene.setCursor(new ImageCursor(mouseCursor));
                pub.initStyle(StageStyle.UNDECORATED);
                pub.show();
                pub.setResizable(false);
                System.out.println("Consultation de la page \"Publication\" par " + usernameLabel.getText());
            } else if (action.getValue() == "Produit") {
                exitAction();
                Stage produits = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/views/AfficheProduit.fxml"));
                Parent root = (Parent) loader.load();
                ListProduitController lp = loader.getController();
                lp.getUsernameLabel().setText(usernameLabel.getText());
                lp.setMyRole(myRole);
                if (myRole.getText() != "Fournisseur") {
                    lp.getBtn().setVisible(false);
                    lp.getBtnModifier().setVisible(false);
                    lp.getBtnSupprimer().setVisible(false);
                    lp.getBtnRetour().setVisible(false);
                }
                if(lp.getTable().getItems().size() == 0) {
                    lp.getBtnDats().setVisible(false);
                    lp.getBtnModifier().setVisible(false);
                    lp.getBtnSupprimer().setVisible(false);
                    lp.getSearch().setVisible(false);
                }
                Scene scene = new Scene(root);
                produits.setScene(scene);
                Image mouseCursor = new Image("/saviorsda/images/mouseCursor.png");
                scene.setCursor(new ImageCursor(mouseCursor));
                produits.initStyle(StageStyle.UNDECORATED);
                produits.show();
                produits.setResizable(false);
                System.out.println("Consultation de la page \"Collecte\" par " + usernameLabel.getText());
            } else if (action.getValue() == "Evenement") {
                exitAction();
               // Parent root = FXMLLoader.load(getClass().getResource("/gui/listeEvenement.fxml"));
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/listeEvenement.fxml"));
                Parent root = (Parent) loader.load();
                //ListeEvenementController le = loader.getController();
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
                Scene scene = new Scene(root);
                Image icon = new Image(getClass().getResourceAsStream("/modules/images/saviorsIcon.png"));
                stage.getIcons().add(icon);
                stage.setScene(scene);
                Image mouseCursor = new Image("/saviorsda/images/mouseCursor.png");
                scene.setCursor(new ImageCursor(mouseCursor));
                stage.setTitle("Evenements - Saviors");
                stage.show();
                System.out.println("Consultation de la page \"Evenement\" par " + usernameLabel.getText());
            }
            
            else {
                JOptionPane.showMessageDialog(null, "Ce module n'est pas encore réalisé !");
            }
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

    //@FXML
    /*public void logout(ActionEvent event) throws Exception {
        try {
            exitButtonAction();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/saviorsda/Login.fxml"));
            Parent root = (Parent) loader.load();*/
    //hc.getSeConnecter().setVisible(false);
    /*Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);*/
    //Parent root = FXMLLoader.load(getClass().getResource("/homepage/SaviorsHomepage.fxml"));
    /*Scene scene = new Scene(root);
            Image icon = new Image(getClass().getResourceAsStream("/saviorsda/images/saviorsIcon.png"));
            stage.getIcons().add(icon);
            stage.setScene(scene);*/
 /*hc.getNavBar().getChildren().remove(hc.getSeConnecterBtn());
              hc.getNavBar().getChildren().remove(hc.getSeConnecter());*/
 /*stage.show();
        } catch (Exception e) {
            System.out.println("Erreur de chargement de page !");
        }
    }*/
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        action.setItems(modules);
    }

}
