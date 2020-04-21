package saviorsda;

import homepage.UserAuthentifiedController;
import mysql.mysqlConnect;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

public class SignUpController implements Initializable {

    @FXML
    private Label label;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label requiredUsername;

    @FXML
    private Label requiredPassword;

    @FXML
    private TextField passShown;

    @FXML
    private Button icon;

    @FXML
    private ImageView eyeImage;

    @FXML
    private Button icon1;

    @FXML
    private ImageView eyeImage1;

    @FXML
    private PasswordField password1;

    @FXML
    private TextField passShown1;

    @FXML
    private TextField email;

    @FXML
    private Button icon11;

    @FXML
    private ImageView eyeImage11;

    @FXML
    private Button icon2;

    @FXML
    private Button signupBtn;

    @FXML
    private ImageView eyeImage2;

    @FXML
    private Button exit;

    @FXML
    private Label status;

    @FXML
    private Button saviorsLogo;

    @FXML
    private ChoiceBox roles;

    @FXML
    private Label requiredEmail;

    @FXML
    private Label requiredConfirmPassword;

    private String roleSignUp;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    public void loginAction(ActionEvent event) throws Exception {
        try {
            exitAction();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/saviorsda/Login.fxml"));
            Parent root = (Parent) loader.load();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(root);
            Image icon = new Image(getClass().getResourceAsStream("/saviorsda/images/saviorsIcon.png"));
            stage.getIcons().add(icon);
            stage.setScene(scene);
            Image mouseCursor = new Image("/saviorsda/images/mouseCursor.png");
            scene.setCursor(new ImageCursor(mouseCursor));
            stage.setTitle("Connexion - Saviors");
            /*hc.getNavBar().getChildren().remove(hc.getSeConnecterBtn());
                      hc.getNavBar().getChildren().remove(hc.getSeConnecter());*/
            stage.show();
        } catch (IOException e) {
            System.out.println("Erreur de chargement de page !");
        }
    }

    @FXML
    public void Signup(ActionEvent event) {

        if (!password.getText().equals(password1.getText()) && (!password.getText().equals(""))
                && (!password1.getText().equals(""))) {
            JOptionPane.showMessageDialog(null, "Les deux mots de passes ne sont pas équivalents !");
        }

        if (username.getText().equals("")) {
            requiredUsername.setVisible(true);
            requiredUsername.setText("Champs requis !");
        }

        if (email.getText().equals("")) {
            requiredEmail.setVisible(true);
            requiredEmail.setText("Champs requis !");
        }

        if (password.getText().equals("")) {
            requiredPassword.setVisible(true);
            requiredPassword.setText("Champs requis !");
        }

        if (password1.getText().equals("")) {
            requiredConfirmPassword.setVisible(true);
            requiredConfirmPassword.setText("Champs requis !");
        }

        if (!username.getText().equals("")) {
            requiredUsername.setVisible(false);
            requiredUsername.setText("");
        }

        if (!email.getText().equals("")) {
            requiredEmail.setVisible(false);
            requiredEmail.setText("");
        }

        if (!password.getText().equals("")) {
            requiredPassword.setVisible(false);
            requiredPassword.setText("");
        }

        if (!password1.getText().equals("")) {
            requiredConfirmPassword.setVisible(false);
            requiredConfirmPassword.setText("");
        }

        if (username.getText().equals("") && (password.getText().equals(""))
                && (email.getText().equals("")) && (password1.getText().equals(""))) {
            status.setVisible(false);
            requiredUsername.setVisible(true);
            requiredPassword.setVisible(true);
            requiredConfirmPassword.setVisible(true);
            requiredEmail.setVisible(true);
            requiredUsername.setText("Champs requis !");
            requiredPassword.setText("Champs requis !");
            requiredEmail.setText("Champs requis !");
            requiredConfirmPassword.setText("Champs requis !");
            //status.setText("Vous n'avez rien saisi !");
        } else if (!username.getText().equals("") && (!password.getText().equals(""))
                && (!email.getText().equals("")) && (!password1.getText().equals(""))) {

            if (validateEmail() && password.getText().equals(password1.getText())) {
                status.setVisible(false);
                requiredUsername.setVisible(true);
                requiredPassword.setVisible(true);
                requiredConfirmPassword.setVisible(true);
                requiredEmail.setVisible(true);
                requiredUsername.setText("");
                requiredPassword.setText("");
                requiredEmail.setText("");
                requiredConfirmPassword.setText("");
                try {
                    Connection cnx = mysqlConnect.getInstance().getCnx();
                    Statement stm = cnx.createStatement();
                    final int enabled = 1;
                    java.util.Date dt = new java.util.Date();

                    java.text.SimpleDateFormat sdf
                            = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    String currentTime = sdf.format(dt);

                    //String role;
                    if (roles.getValue().equals("Association")) {
                        roleSignUp = "a:1:{i:0;s:10:\"ROLE_ASSOC\";}";
                    }

                    if (roles.getValue().equals("Membre")) {
                        roleSignUp = "a:1:{i:0;s:11:\"ROLE_MEMBER\";}";
                    }

                    if (roles.getValue().equals("Fournisseur")) {
                        roleSignUp = "a:1:{i:0;s:10:\"ROLE_FOURN\";}";
                    }

                    //String req = "insert into fos_user(username, username_canonical, email, email_canonical, enabled, salt, password, last_login, confirmation_token, password_requested_at, roles) values('" + username.getText() + "','" + username.getText() + "','" + email.getText() + "','" + email.getText() + "','"1"', '""', '"+password.getText() + "','" + instant + "','""','""','""');";
                    String req = "insert into fos_user (username, username_canonical, email, email_canonical, enabled, password, last_login, roles) values ('" + username.getText() + "','" + username.getText() + "','" + email.getText() + "','" + email.getText() + "','" + enabled + "','" + password.getText() + "','" + currentTime + "','" + roleSignUp + "')";
                    //System.out.println(roles.getValue());
                    int executeUpdate = stm.executeUpdate(req, Statement.RETURN_GENERATED_KEYS);
                    if (executeUpdate > 0) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Succès");
                        alert.setHeaderText(null);
                        alert.setContentText("Vous êtes maintenant inscrit. Si vous cliquez sur \"OK\", vous"
                                + " allez être redirigé vers la page d'authentification.");
                        Optional<ButtonType> action = alert.showAndWait();
                        int generatedKey = 0;
                        ResultSet rs = stm.getGeneratedKeys();
                        if (rs.next()) {
                            generatedKey = rs.getInt(1);
                        }
                        System.out.println("Une personne est inscrite ayant"
                                + " l'ID: " + generatedKey + ", le nom d'utilisateur: " + username.getText() + " et le rôle: " + roles.getValue());
                        if (action.get() == ButtonType.OK) {
                            exitAction();
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/saviorsda/Login.fxml"));
                            Parent root = (Parent) loader.load();
                            LoginController lc = loader.getController();
                            lc.getConnectMe().setVisible(true);
                            lc.getConnectToContinue().setVisible(true);
                            //UserAuthentifiedController ua = loader.getController();
                            //ua.myUserNameFunction(username.getText());
                            //hc.getSeConnecter().setVisible(false);
                            Stage stage = new Stage();
                            stage.initStyle(StageStyle.UNDECORATED);
                            //Parent root = FXMLLoader.load(getClass().getResource("/homepage/SaviorsHomepage.fxml"));
                            Scene scene = new Scene(root);
                            Image icon = new Image(getClass().getResourceAsStream("/homepage/images/saviorsIcon.png"));
                            stage.getIcons().add(icon);
                            stage.setScene(scene);
                            Image mouseCursor = new Image("/saviorsda/images/mouseCursor.png");
                            scene.setCursor(new ImageCursor(mouseCursor));
                            stage.setTitle("Connexion - Saviors");
                            //stage.setTitle("Bonjour " + username.getText() + " - Saviors");
                            /*hc.getNavBar().getChildren().remove(hc.getSeConnecterBtn());
                          hc.getNavBar().getChildren().remove(hc.getSeConnecter());*/
                            stage.show();
                            //System.out.println("Vous avez quitté l'application.");
                        } else if (action.get() == ButtonType.CANCEL) {
                            JOptionPane.showMessageDialog(null, "Vous pouvez vous authentifier quand vous voulez.");
                        }
                        //JOptionPane.showMessageDialog(null, "Vous êtes connecté avec succès !");

                    } else if (executeUpdate <= 0) {
                        JOptionPane.showMessageDialog(null, "Utilisateur existe déjà !");
                    }
                } catch (Exception e) {
                    System.out.println("Erreur de chargement de page !");
                    JOptionPane.showMessageDialog(null, "Utilisateur existe déjà !");
                    System.out.println(e);
                }

            }
            if (!username.getText().equals("") && (password.getText().equals(""))
                    && (email.getText().equals("")) && (password1.getText().equals(""))) {
                status.setVisible(false);
                requiredUsername.setVisible(true);
                requiredPassword.setVisible(true);
                requiredConfirmPassword.setVisible(true);
                requiredEmail.setVisible(true);
                requiredUsername.setText("");
                requiredPassword.setText("Champs requis !");
                requiredEmail.setText("Champs requis !");
                requiredConfirmPassword.setText("Champs requis !");
                //status.setText("Vous n'avez rien saisi !");
            }
            if (username.getText().equals("") && (password.getText().equals(""))
                    && (!email.getText().equals("")) && (password1.getText().equals(""))) {
                status.setVisible(false);
                requiredUsername.setVisible(true);
                requiredPassword.setVisible(true);
                requiredConfirmPassword.setVisible(true);
                requiredEmail.setVisible(true);
                requiredUsername.setText("Champs requis !");
                requiredPassword.setText("Champs requis !");
                requiredEmail.setText("");
                requiredConfirmPassword.setText("Champs requis !");
                //status.setText("Vous n'avez rien saisi !");
            }
        }
    }

    @FXML
    private void successFail() {

    }

    @FXML
    public void showHidePass(ActionEvent event) {
        icon.setVisible(false);
        icon1.setVisible(true);
        passShown.setVisible(true);
        password.setVisible(false);
        eyeImage1.setVisible(true);
        eyeImage.setVisible(false);
        passShown.setText(password.getText());
    }

    @FXML
    public void showHideConfirmPass(ActionEvent event) {
        icon2.setVisible(false);
        icon11.setVisible(true);
        passShown1.setVisible(true);
        password1.setVisible(false);
        eyeImage11.setVisible(true);
        eyeImage2.setVisible(false);
        passShown1.setText(password1.getText());
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

    @FXML
    public void hideShowConfirmPass(ActionEvent event) {
        icon2.setVisible(true);
        icon11.setVisible(false);
        passShown1.setVisible(false);
        password1.setVisible(true);
        eyeImage11.setVisible(false);
        eyeImage2.setVisible(true);
        password1.setText(passShown1.getText());
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
            System.out.println("Vous avez quitté l'application.");
        }
    }

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
            Image mouseCursor = new Image("/saviorsda/images/mouseCursor.png");
            scene.setCursor(new ImageCursor(mouseCursor));
            stage.show();
        } catch (IOException e) {
            System.out.println("Erreur de chargement de page !");
        }
    }

    private boolean validateEmail() {
        Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher m = p.matcher(email.getText());
        if (m.find() && m.group().equals(email.getText())) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Entrez une adresse email valide !");
            return false;
        }
    }

    public ChoiceBox getRoles() {
        return roles;
    }

    public void setRoles(ChoiceBox roles) {
        this.roles = roles;
    }

}
