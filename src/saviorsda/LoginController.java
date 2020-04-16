package saviorsda;

import homepage.UserAuthentifiedController;
//import java.awt.event.MouseEvent;
import mysql.mysqlConnect;
import user.UserLogin;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

public class LoginController implements Initializable {

    @FXML
    private Label status;

    @FXML
    private Label requiredUsername;

    @FXML
    private Label requiredPassword;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private Button showHide;

    @FXML
    private Button icon;

    @FXML
    private Button icon1;

    @FXML
    private Button signupBtn;

    @FXML
    private Text plainPass;

    @FXML
    private ImageView eyeImage;

    @FXML
    private ImageView eyeImage1;

    @FXML
    private TextField passShown;

    @FXML
    private javafx.scene.control.Button exit;
    
    @FXML
    private AnchorPane connectMe;
    
    @FXML
    private Label connectToContinue;

    @FXML
    public void Login(ActionEvent event) throws Exception, NoSuchAlgorithmException {
        /*icon.setVisible(false);
        while(password.getText().length()<=0) {
            icon.setVisible(false);
        }*/
        if (username.getText().equals("") && (password.getText().equals(""))
                || (username.getText().equals("")) && (passShown.getText().equals(""))) {
            status.setVisible(false);
            requiredUsername.setVisible(true);
            requiredPassword.setVisible(true);
            requiredUsername.setText("Champs requis !");
            requiredPassword.setText("Champs requis !");
            //status.setText("Vous n'avez rien saisi !");
        } else if (!username.getText().equals("") && (!password.getText().equals(""))
                || (!username.getText().equals("")) && (!passShown.getText().equals(""))) {
            status.setVisible(false);
            requiredUsername.setVisible(true);
            requiredPassword.setVisible(true);
            requiredUsername.setText("");
            requiredPassword.setText("");
            UserLogin ul = new UserLogin(username.getText(), password.getText());
            /*Connection cnx = mysqlConnect.getInstance().getCnx();
        Statement stm = cnx.createStatement();
        String req = "select * from fos_user where username='"+username.getText()+"' and password='"+generatedSecuredPasswordHash+"'";
        ResultSet rs = stm.executeQuery(req);
        if (!rs.next()) {
            JOptionPane.showMessageDialog(null, "Incorrect username and/or Password !");
        }*/
            try {
                /*exitButtonAction();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        Parent root = FXMLLoader.load(getClass().getResource("/homepage/SaviorsHomepage.fxml"));
        Scene scene = new Scene(root);
        Image icon = new Image(getClass().getResourceAsStream("/homepage/images/saviorsIcon.png"));
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();*/
                Connection cnx = mysqlConnect.getInstance().getCnx();
                Statement stm = cnx.createStatement();
                String req = "select * from fos_user where username='" + username.getText() + "' and password='" + password.getText() + "'"
                        + "or username='" + username.getText() + "' and password='" + passShown.getText() + "'";
                ResultSet rs = stm.executeQuery(req);
                if (!rs.next()) {
                    JOptionPane.showMessageDialog(null, "Nom d'utilisateur et/ou Mot de Passe incorrect(s) !");
                } else {
                    //JOptionPane.showMessageDialog(null, "Vous êtes connecté avec succès !");
                    int id = rs.getInt("id");
                    String nomUser = rs.getString("username");
                    System.out.println("L'utilisateur "+nomUser+ " d'ID "+id+ " est authentifié !");
                    exitAction();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/homepage/userAuthentified.fxml"));
                    Parent root = (Parent) loader.load();
                    UserAuthentifiedController ua = loader.getController();
                    if("a:1:{i:0;s:10:\"ROLE_ASSOC\";}".equals(rs.getString("roles"))) {
                        ua.getMyRole().setText("Association");
                        ua.getMyRole().setVisible(false);
                    }
                    if("a:1:{i:0;s:10:\"ROLE_FOURN\";}".equals(rs.getString("roles"))) {
                        ua.getMyRole().setText("Fournisseur");
                        ua.getMyRole().setVisible(false);
                    }
                    if("a:1:{i:0;s:11:\"ROLE_MEMBER\";}".equals(rs.getString("roles"))) {
                        ua.getMyRole().setText("Membre");
                        ua.getMyRole().setVisible(false);
                    }
                    System.out.println(rs.getString("roles"));
                    ua.myUserNameFunction(username.getText());
                    //hc.getSeConnecter().setVisible(false);
                    Stage stage = new Stage();
                    stage.initStyle(StageStyle.UNDECORATED);
                    //Parent root = FXMLLoader.load(getClass().getResource("/homepage/SaviorsHomepage.fxml"));
                    Scene scene = new Scene(root);
                    Image icon = new Image(getClass().getResourceAsStream("/homepage/images/saviorsIcon.png"));
                    stage.getIcons().add(icon);
                    stage.setScene(scene);
                    stage.setTitle("Bonjour " + username.getText() + " - Saviors");
                    /*hc.getNavBar().getChildren().remove(hc.getSeConnecterBtn());
                      hc.getNavBar().getChildren().remove(hc.getSeConnecter());*/
                    stage.show();
                }
            } catch (Exception e) {
                System.out.println("Erreur de chargement de page !");
                System.out.println(e);
            }
        }

        if (username.getText().equals("") && (!password.getText().equals(""))
                || (username.getText().equals("")) && (!passShown.getText().equals(""))) {
            status.setVisible(false);
            requiredUsername.setVisible(true);
            requiredUsername.setText("Champs requis !");
            requiredPassword.setVisible(true);
            requiredPassword.setText("");
            //status.setText("Connexion échouée !");
        }

        if (password.getText().equals("") && (!username.getText().equals(""))
                || (!username.getText().equals("")) && (passShown.getText().equals(""))) {
            status.setVisible(false);
            requiredPassword.setVisible(true);
            requiredPassword.setText("Champs requis !");
            requiredUsername.setVisible(true);
            requiredUsername.setText("");
            //status.setText("Connexion échouée !");
        }

        if (password.getText().equals("") && (passShown.getText().equals(""))) {
            requiredPassword.setVisible(true);
            requiredPassword.setText("Champs requis !");
        } else {
            requiredPassword.setVisible(false);
            requiredPassword.setText("");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    public void signupAction(ActionEvent event) throws Exception {
        try {
        exitAction();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/saviorsda/SignUp.fxml"));
        Parent root = (Parent) loader.load();
        SignUpController sc = loader.getController();
        sc.getRoles().getItems().addAll("Association", "Membre", "Fournisseur");
        sc.getRoles().getSelectionModel().selectFirst();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root);
        Image icon = new Image(getClass().getResourceAsStream("/saviorsda/images/saviorsIcon.png"));
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.setTitle("Inscription - Saviors");
        /*hc.getNavBar().getChildren().remove(hc.getSeConnecterBtn());
                      hc.getNavBar().getChildren().remove(hc.getSeConnecter());*/
        stage.show();
        } catch (IOException e) {
            System.out.println("Erreur de chargement de page !");
        }
    }

    @FXML
    private void exitButtonAction() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
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

    @FXML
    private void exitAction() {
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void saviorsHomepage(ActionEvent event) throws Exception {
        try {
            exitAction();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            Parent root = FXMLLoader.load(getClass().getResource("/homepage/SaviorsHomepage.fxml"));
            Scene scene = new Scene(root);
            Image icon = new Image(getClass().getResourceAsStream("/homepage/images/saviorsIcon.png"));
            stage.getIcons().add(icon);
            stage.setScene(scene);
            stage.setTitle("Accueil - Saviors");
            stage.show();
        } catch (IOException e) {
            System.out.println("Erreur de chargement de page !");
        }
    }

    @FXML
    public void showHidePass(ActionEvent event) {
        //showHide.setVisible(false);
        //eyeImage.setVisible(false);
        //showHide.setVisible(true);
        //String passText = password.getText();
        //password.setText(passText);
        //eyeImage.setVisible(true);
        //password.setVisible(true);
        /*icon.setVisible(false);
        plainPass.setText(password.getText());
        System.out.println(plainPass.getText());
        password.setText(null);
        password.setPromptText(null);*/
        icon.setVisible(false);
        icon1.setVisible(true);
        passShown.setVisible(true);
        password.setVisible(false);
        eyeImage1.setVisible(true);
        eyeImage.setVisible(false);
        passShown.setText(password.getText());
        /*if(password.getText().length()>0) {
        plainPass.setText(null);
        }*/
        //password.setVisible(true);
    }

    @FXML
    public void hideShowPass(ActionEvent event) {
        icon.setVisible(true);
        icon1.setVisible(false);
        passShown.setVisible(false);
        password.setVisible(true);
        eyeImage1.setVisible(false);
        eyeImage.setVisible(true);
        password.setText(passShown.getText());
    }

    public Button getIcon() {
        return icon;
    }

    public void setIcon(Button icon) {
        this.icon = icon;
    }

    public ImageView getEyeImage() {
        return eyeImage;
    }

    public void setEyeImage(ImageView eyeImage) {
        this.eyeImage = eyeImage;
    }

    public TextField getPassword() {
        return password;
    }

    public void setPassword(TextField password) {
        this.password = password;
    }

    public AnchorPane getConnectMe() {
        return connectMe;
    }

    public void setConnectMe(AnchorPane connectMe) {
        this.connectMe = connectMe;
    }

    public Label getConnectToContinue() {
        return connectToContinue;
    }

    public void setConnectToContinue(Label connectToContinue) {
        this.connectToContinue = connectToContinue;
    }

}
