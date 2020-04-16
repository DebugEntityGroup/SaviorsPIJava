package homepage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
//import javafx.scene.control.Label;
import javafx.scene.image.Image;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HomePageController implements Initializable {

    @FXML
    private javafx.scene.control.Button exit;

    @FXML
    private Button seConnecterBtn;

    /*@FXML
    private AnchorPane navBar;*/
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
        alert.setContentText("Voulez vous quitter l'application ?");
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
            Stage stage = (Stage) exit.getScene().getWindow();
            stage.close();
            System.out.println("Déconnexion de la Base de données !");
            System.out.println("Fermeture de l'application.");
        }
    }

    public void openLogin(ActionEvent event) throws Exception {
        try {
            exitAction();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            Parent root = FXMLLoader.load(getClass().getResource("/saviorsda/Login.fxml"));
            Scene scene = new Scene(root);
            Image icon = new Image(getClass().getResourceAsStream("/saviorsda/images/saviorsIcon.png"));
            stage.getIcons().add(icon);
            stage.setScene(scene);
            stage.setTitle("Se Connecter - Saviors");
            stage.show();
        } catch (IOException e) {
            System.out.println("Erreur de chargement de page !");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public Button getSeConnecterBtn() {
        return seConnecterBtn;
    }

    public void setSeConnecterBtn(Button seConnecterBtn) {
        this.seConnecterBtn = seConnecterBtn;
    }

    /*public Label getSeConnecter() {
        return seConnecter;
    }

    public void setSeConnecter(Label seConnecter) {
        this.seConnecter = seConnecter;
    }*/

    /*public AnchorPane getNavBar() {
        return navBar;
    }

    public void setNavBar(AnchorPane navBar) {
        this.navBar = navBar;
    }*/
}
