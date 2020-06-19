package modules;

import homepage.UserAuthentifiedController;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import mysql.mysqlConnect;

public class DetailsCollecteController implements Initializable {

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
    private Label categorieCollecte;

    @FXML
    private Label userAssoc;

    @FXML
    private Label hiddenID;
    
    @FXML
    private Label noMoreModified;

    @FXML
    private Label champsRequis;

    @FXML
    private Button saviorsLogo;

    @FXML
    private Button gererCollecte;

    @FXML
    private Button donateDon;

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
    private Button editCollecteBtn;

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

    public Label getNoMoreModified() {
        return noMoreModified;
    }

    public void setNoMoreModified(Label noMoreModified) {
        this.noMoreModified = noMoreModified;
    }
    
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

    public Button getEditCollecteBtn() {
        return editCollecteBtn;
    }

    public void setEditCollecteBtn(Button editCollecteBtn) {
        this.editCollecteBtn = editCollecteBtn;
    }
    
    @FXML
    private void editInfoCollecte(ActionEvent event) throws Exception {
        try {
            exitAction();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modules/EditCollecte.fxml"));
            Parent root = (Parent) loader.load();
            EditCollecteController ec = loader.getController();
            ec.getUsernameLabel().setText(usernameLabel.getText());
            ec.setMyRole(myRole);
            //ec.setImageCollect(collectImage);
            ec.getImageCollect().setImage(collectImage.getImage());
            ec.getNomCollecte().setText(nomCollecte.getText());
            ec.getBudgetCollecte().setText(budgetCollecte.getText());
            ec.getDescriptionCollecte().setText(descriptionCollecte.getText());
            ec.getHiddenNC().setText(nomCollecte.getText());
            ec.getCatFieldCollecte().getSelectionModel().select(categorieCollecte.getText());
            ec.getImageView().setImage(collectImage.getImage());
            Connection cnx = mysqlConnect.getInstance().getCnx();
            Statement stm = cnx.createStatement();
            String req = "select image from collectPending where nomCollecte = '" +nomCollecte.getText()+"'";
            ResultSet rs = stm.executeQuery(req);
            if (rs.next()) {
                ec.getImageName().setText(rs.getString("image"));
            }
            
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(root);
            Image icon = new Image(getClass().getResourceAsStream("/modules/images/saviorsIcon.png"));
            stage.getIcons().add(icon);
            stage.setScene(scene);
            Image mouseCursor = new Image("/saviorsda/images/mouseCursor.png");
            scene.setCursor(new ImageCursor(mouseCursor));
            stage.setTitle("Modifier la Collecte - Saviors");
            stage.show();
        } catch (Exception e) {
            System.out.println("Erreur de chargement de page !");
            System.out.println(e);
        }
    }

    @FXML
    private void commenterAction(ActionEvent event) throws Exception {
        try {
            Connection cnx = mysqlConnect.getInstance().getCnx();
            Statement stm = cnx.createStatement();
            String req = "select * from collectPending c, fos_user f where c.nomCollecte = '" + nomCollecte.getText() + "' and f.username='" + usernameLabel.getText() + "'";
            ResultSet rs = stm.executeQuery(req);
            if (rs.next()) {
                String req2 = "insert into commentaire(user_id, contenu, collectPending_id) values ('" + rs.getInt("f.id") + "', '" + commentaireField.getText() + "', '" + rs.getInt("c.id") + "')";
                if (commentaireField.getText().equals("")) {
                    commentaireField.setStyle(
                            "-fx-border-color: red;"
                    );
                    champsRequis.setVisible(true);
                } else {
                    stm.executeUpdate(req2);
                    JOptionPane.showMessageDialog(null, "Commentaire ajoutée avec succés !");
                    commentaireField.setText("");
                    champsRequis.setVisible(false);
                    commentaireField.setStyle(
                            "-fx-border-color: none;"
                    );
                    System.out.println("L'utilisateur " + usernameLabel.getText() + " a ajouté un commentaire sur la Collecte: " + nomCollecte.getText());
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private void hideComments(ActionEvent event) {
        listeCommentaires.setVisible(false);
        hideComments.setVisible(false);
        listComments.setDisable(false);
    }

    @FXML
    public void listView(ActionEvent event) throws Exception {
        try {
            Connection cnx = mysqlConnect.getInstance().getCnx();
            Statement stm = cnx.createStatement();
            String req = "select * from fos_user f, commentaire c, collectPending cp where cp.nomCollecte='" + nomCollecte.getText() + "' and c.collectPending_id=cp.id and f.id = c.user_id";
            ResultSet rs = stm.executeQuery(req);
            ObservableList<String> items = FXCollections.observableArrayList();
            listeCommentaires.setItems(items);
            listeCommentaires.setVisible(true);
            hideComments.setVisible(true);
            listComments.setDisable(true);
            while (rs.next()) {
                String r;
                items.add("\"" + rs.getString("c.contenu") + "\"");
                if (rs.getString("f.roles").equals("a:1:{i:0;s:11:\"ROLE_MEMBER\";}")) {
                    r = "Membre";
                } else if (rs.getString("f.roles").equals("a:1:{i:0;s:10:\"ROLE_FOURN\";}")) {
                    r = "Fournisseur";
                } else {
                    r = "";
                }
                items.add("Commentaire publié par: " + rs.getString("f.username") + " (" + r + ")");
                items.add("");
            }
            listeCommentaires.setStyle(
                    "-fx-background-color: white;"
                    + "-fx-font-weight: bold;"
            );
            if (items.size() == 0) {
                items.add("Il n'y a pas encore de commentaires pour cette collecte.");
                hideComments.setVisible(false);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private void donateDon(ActionEvent event) throws Exception {
        try {
            exitAction();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modules/Donate.fxml"));
            Parent root = (Parent) loader.load();
            DonateController dc = loader.getController();
            dc.setMyRole(myRole);
            dc.getNomCollecte().setText(nomCollecte.getText());
            dc.getUserAssoc().setText(userAssoc.getText());
            dc.getDescriptionCollecte().setText(descriptionCollecte.getText());
            dc.getBudgetCollecte().setText(budgetCollecte.getText());
            dc.getFondAtteint().setText(fondAtteint.getText());
            dc.getNbreParticipants().setText(nbreParticipants.getText());
            //dc.setCollectImage(collectImage);
            dc.getCollectImage().setImage(collectImage.getImage());
            dc.getUsernameLabel().setText(usernameLabel.getText());
            dc.getHiddenID().setVisible(false);
            if (Integer.parseInt(dc.getBudgetCollecte().getText()) - Integer.parseInt(dc.getFondAtteint().getText()) < 50) {
                dc.getCloseCollect().setVisible(true);
                dc.getMoneyDonated().setDisable(true);
                dc.getDonateButton().setDisable(true);
                dc.getCancelButton().setDisable(true);
                dc.getDeadC1().setVisible(true);
                dc.getDeadC2().setVisible(true);
            } else {
                dc.getCloseCollect().setVisible(false);
                dc.getMoneyDonated().setDisable(false);
                dc.getDonateButton().setDisable(false);
                dc.getCancelButton().setDisable(false);
            }
            if (myRole.getText().equals("Association")) {
                dc.getMoneyDonated().setDisable(true);
                dc.getDonateButton().setDisable(true);
                dc.getCancelButton().setDisable(true);
                dc.getNoForAssoc().setVisible(true);
                dc.getCloseCollect().setText("Collecte Clôturée");
            }
            
            if (!myRole.getText().equals("Association")) {
                dc.getDonsButton().setVisible(false);
                dc.getListeDons().setVisible(false);
            }
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(root);
            Image icon = new Image(getClass().getResourceAsStream("/modules/images/saviorsIcon.png"));
            stage.getIcons().add(icon);
            stage.setScene(scene);
            Image mouseCursor = new Image("/saviorsda/images/mouseCursor.png");
            scene.setCursor(new ImageCursor(mouseCursor));
            stage.setTitle("Faire un Don - Saviors");
            stage.show();
        } catch (Exception e) {
            System.out.println(e);
        }
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
                Image mouseCursor = new Image("/saviorsda/images/mouseCursor.png");
                scene.setCursor(new ImageCursor(mouseCursor));
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
            Image mouseCursor = new Image("/saviorsda/images/mouseCursor.png");
            scene.setCursor(new ImageCursor(mouseCursor));
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
            Image mouseCursor = new Image("/saviorsda/images/mouseCursor.png");
            scene.setCursor(new ImageCursor(mouseCursor));
            stage.setTitle("Gérer mes Collectes - Saviors");
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
        System.out.println(Integer.parseInt(test.getText()));
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

    public Label getCategorieCollecte() {
        return categorieCollecte;
    }

    public void setCategorieCollecte(Label categorieCollecte) {
        this.categorieCollecte = categorieCollecte;
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
    }
}
