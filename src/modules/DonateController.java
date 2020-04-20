package modules;

import homepage.UserAuthentifiedController;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;
import mysql.mysqlConnect;

public class DonateController implements Initializable {

    @FXML
    private Label usernameLabel;

    @FXML
    private Label username;

    @FXML
    private Label myRole;

    @FXML
    private Label nomDeLaCollecte;

    @FXML
    private Label justCollecte;

    @FXML
    private Label faireUnDon;

    @FXML
    private javafx.scene.control.Button exit;

    @FXML
    private Label logoutText;

    @FXML
    private Label you;

    @FXML
    private Label test;

    @FXML
    private Label nomCollecte;

    @FXML
    private Label budgetCollecte;

    @FXML
    private Label descriptionCollecte;

    @FXML
    private Label fondAtteint;

    @FXML
    private Label nbreParticipants;

    @FXML
    private Label userAssoc;

    @FXML
    private Label hiddenID;

    @FXML
    private Label champsRequis;

    @FXML
    private Label closeCollect;

    @FXML
    private Button saviorsLogo;

    @FXML
    private Button gererCollecte;

    @FXML
    private Button donateDon;

    @FXML
    private Button cancelButton;

    @FXML
    private ImageView collectImage;

    @FXML
    private Button voirDetails;

    @FXML
    private Button commentBtn;

    @FXML
    private Button hideComments;

    @FXML
    private Button listComments;

    @FXML
    private Button donateButton;

    @FXML
    private TextField moneyDonated;

    @FXML
    private TextArea commentaireField;

    @FXML
    private ComboBox<String> action;

    private ObservableList<ObservableList> data;

    @FXML
    private TableView imagesCollects;

    @FXML
    private ListView listeCommentaires;

    ObservableList<String> modules = FXCollections.observableArrayList("Evenement", "Publication", "Réclamation", "Collecte", "Produit", "Forum");

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

    public Label getTest() {
        return test;
    }

    public void setTest(Label test) {
        this.test = test;
    }

    public Label getUserAssoc() {
        return userAssoc;
    }

    public void setUserAssoc(Label userAssoc) {
        this.userAssoc = userAssoc;
    }

    public Label getHiddenID() {
        return hiddenID;
    }

    public void setHiddenID(Label hiddenID) {
        this.hiddenID = hiddenID;
    }

    public TextArea getCommentaireField() {
        return commentaireField;
    }

    public void setCommentaireField(TextArea commentaireField) {
        this.commentaireField = commentaireField;
    }

    public Button getCommentBtn() {
        return commentBtn;
    }

    public void setCommentBtn(Button commentBtn) {
        this.commentBtn = commentBtn;
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

                if (myRole.getText() == "Association") {
                    cc.getGererCollecte().setVisible(true);
                    cc.setMyRole(myRole);
                } else {
                    cc.getGererCollecte().setVisible(false);
                    cc.setMyRole(myRole);
                }
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
                Scene scene = new Scene(root);
                Image icon = new Image(getClass().getResourceAsStream("/modules/images/saviorsIcon.png"));
                stage.getIcons().add(icon);
                stage.setScene(scene);
                stage.setTitle("Collecte - Saviors");
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
            stage.setTitle("Accueil - Saviors");
            stage.show();
        } catch (IOException e) {
            System.out.println("Erreur de chargement de page !");
        }
    }

    @FXML
    public void gestionCollecte(ActionEvent event) throws Exception {
        try {
            exitAction();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modules/ManageCollecte.fxml"));
            Parent root = (Parent) loader.load();
            ManageCollecteController mc = loader.getController();
            mc.getUsernameLabel().setText(usernameLabel.getText());
            mc.setMyRole(myRole);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(root);
            Image icon = new Image(getClass().getResourceAsStream("/modules/images/saviorsIcon.png"));
            stage.getIcons().add(icon);
            stage.setScene(scene);
            stage.setTitle("Gérer mes Collectes - Saviors");
            stage.show();
        } catch (IOException e) {
            System.out.println("Erreur de chargement de page !");
        }
    }

    @FXML
    private void resetAction(ActionEvent event) throws Exception {
        moneyDonated.setText("");
    }

    @FXML
    private void donateAction(ActionEvent event) throws Exception {
        try {
            Connection cnx = mysqlConnect.getInstance().getCnx();
            Statement stm = cnx.createStatement();
            String req = "select * from collectPending c, fos_user f where c.nomCollecte = '" + nomCollecte.getText() + "' and f.username='" + usernameLabel.getText() + "'";
            ResultSet rs = stm.executeQuery(req);
            java.util.Date dt = new java.util.Date();
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = sdf.format(dt);
            if (rs.next()) {

                if (moneyDonated.getText().isEmpty()) {
                    moneyDonated.setStyle(
                            "-fx-border-color: red;"
                    );
                    champsRequis.setVisible(true);
                    //JOptionPane.showMessageDialog(null, "Champs requis !");
                }
                if (Integer.parseInt(moneyDonated.getText()) < 50 && rs.getInt("c.budgetCollecte")-rs.getInt("c.nombreAtteint")>=50) {
                    JOptionPane.showMessageDialog(null, "Veuillez entrer un montant supérieur ou égal à 50 TND !");
                    champsRequis.setVisible(false);
                    moneyDonated.setStyle(
                            "-fx-border-color: red;"
                    );
                    /*if (rs.getInt("c.budgetCollecte")-rs.getInt("c.nombreAtteint")<50) {
                    //stm.executeUpdate(req2);
                    JOptionPane.showMessageDialog(null, "Vous ne pouvez plus contribuer à cette Collecte !\n Elle est cloturée maintenant !");
                    closeCollect.setVisible(true);
                    moneyDonated.setDisable(true);
                    donateButton.setDisable(true);
                    cancelButton.setDisable(true);
                }*/
                } if (rs.getInt("c.budgetCollecte")-rs.getInt("c.nombreAtteint")<50) {
                    //stm.executeUpdate(req2);
                    JOptionPane.showMessageDialog(null, "Vous ne pouvez plus contribuer à cette Collecte !\n Elle est cloturée maintenant !");
                    closeCollect.setVisible(true);
                    moneyDonated.setDisable(true);
                    donateButton.setDisable(true);
                    cancelButton.setDisable(true);
                    moneyDonated.setStyle(
                            "-fx-border-color: none;"
                    );
                    moneyDonated.setText("");
                } //else if (rs.getInt("c.budgetCollecte") - rs.getInt("c.nombreAtteint") < 50) {
                /*moneyDonated.setPromptText("Dans ce cas, un montant inférieur à 50 TND est possible");
                    stm.executeUpdate(req2);
                    int total = rs.getInt("nombreAtteint") + Integer.parseInt(moneyDonated.getText());
                    JOptionPane.showMessageDialog(null, "Vous avez participé à la contribution de cette Collecte !");
                    String req3 = "update collectPending c set nombreAtteint='" + total + "' where c.nomCollecte='" + nomCollecte.getText() + "'";
                    stm.executeUpdate(req3);*/ /*closeCollect.setVisible(true);
                    moneyDonated.setDisable(true);
                    donateButton.setDisable(true);
                }*/ else {
                    if (Integer.parseInt(moneyDonated.getText()) >= 50) {
                    int total = rs.getInt("nombreAtteint") + Integer.parseInt(moneyDonated.getText());
                    int nbreParticipants = rs.getInt("nombreParticipantsCollecte")+1;
                    String req3 = "update collectPending c set nombreAtteint='" + total + "', nombreParticipantsCollecte='"+nbreParticipants+"' where c.nomCollecte='" + nomCollecte.getText() + "'";
                    String req2 = "insert into don(user_id, moneyDonated, dateHour, collectPending_id) values ('" + rs.getInt("f.id") + "', '" + Integer.parseInt(moneyDonated.getText()) + "', '" + currentTime + "', '" + rs.getInt("c.id") + "')";
                    stm.executeUpdate(req2);
                    JOptionPane.showMessageDialog(null, "Vous avez participé à la contribution de cette Collecte !");
                    stm.executeUpdate(req3);
                    moneyDonated.setText("");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Label getCloseCollect() {
        return closeCollect;
    }

    public void setCloseCollect(Label closeCollect) {
        this.closeCollect = closeCollect;
    }

    public Button getDonateButton() {
        return donateButton;
    }

    public void setDonateButton(Button donateButton) {
        this.donateButton = donateButton;
    }

    public TextField getMoneyDonated() {
        return moneyDonated;
    }

    public void setMoneyDonated(TextField moneyDonated) {
        this.moneyDonated = moneyDonated;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public void setCancelButton(Button cancelButton) {
        this.cancelButton = cancelButton;
    }

    private boolean validateBudget() {
        Pattern p = Pattern.compile("[0-9][0-9]*");
        Matcher m = p.matcher(moneyDonated.getText());
        if (m.find() && m.group().equals(moneyDonated.getText())) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Entrez un montant valide ! Minimum: 50 TND");
            return false;
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
        //System.out.println(Integer.parseInt(test.getText()));
    }

    public Button getGererCollecte() {
        return gererCollecte;
    }

    public void setGererCollecte(Button gererCollecte) {
        this.gererCollecte = gererCollecte;
    }

    public ImageView getCollectImage() {
        return collectImage;
    }

    public void setCollectImage(ImageView collectImage) {
        this.collectImage = collectImage;
    }

    public Label getNomCollecte() {
        return nomCollecte;
    }

    public void setNomCollecte(Label nomCollecte) {
        this.nomCollecte = nomCollecte;
    }

    public Label getBudgetCollecte() {
        return budgetCollecte;
    }

    public void setBudgetCollecte(Label budgetCollecte) {
        this.budgetCollecte = budgetCollecte;
    }

    public Label getDescriptionCollecte() {
        return descriptionCollecte;
    }

    public void setDescriptionCollecte(Label descriptionCollecte) {
        this.descriptionCollecte = descriptionCollecte;
    }

    public Label getFondAtteint() {
        return fondAtteint;
    }

    public void setFondAtteint(Label fondAtteint) {
        this.fondAtteint = fondAtteint;
    }

    public Label getNbreParticipants() {
        return nbreParticipants;
    }

    public void setNbreParticipants(Label nbreParticipants) {
        this.nbreParticipants = nbreParticipants;
    }

    public Button getDonateDon() {
        return donateDon;
    }

    public void setDonateDon(Button donateDon) {
        this.donateDon = donateDon;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        action.setItems(modules);
        try {
            Connection cnx = mysqlConnect.getInstance().getCnx();
            Statement stm = cnx.createStatement();
            String req = "select * from collectPending c";
            ResultSet rs = stm.executeQuery(req);
            if (rs.next()) {
                if (rs.getInt("c.budgetCollecte") - rs.getInt("c.nombreAtteint") < 50) {
                    closeCollect.setVisible(true);
                    moneyDonated.setDisable(true);
                    donateButton.setDisable(true);
                }

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
