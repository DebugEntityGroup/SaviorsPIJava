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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import mysql.mysqlConnect;

public class CollecteController implements Initializable {

    @FXML
    private Label usernameLabel;

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
    private Button saviorsLogo;

    @FXML
    private Button gererCollecte;

    @FXML
    private ImageView collectImage;

    @FXML
    private Button voirDetails;

    @FXML
    private ComboBox<String> action;

    private ObservableList<ObservableList> data;

    @FXML
    private TableView imagesCollects;

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

    private void addButtonToTable() {
        TableColumn<PieChart.Data, Void> colBtn = new TableColumn("Actions");

        colBtn.setStyle(
                "-fx-background-color: white;"
        );

        Callback<TableColumn<PieChart.Data, Void>, TableCell<PieChart.Data, Void>> cellFactory = new Callback<TableColumn<PieChart.Data, Void>, TableCell<PieChart.Data, Void>>() {
            @Override
            public TableCell<PieChart.Data, Void> call(final TableColumn<PieChart.Data, Void> param) {
                final TableCell<PieChart.Data, Void> cell = new TableCell<PieChart.Data, Void>() {

                    //private final Button edit = new Button("Modifier");
                    private final Button seeImage = new Button("Voir l'image");

                    {
                        seeImage.setOnAction((ActionEvent event) -> {
                            Object data2 = imagesCollects.getItems().get(getIndex());
                            String c = (String) col.getCellObservableValue(data2).getValue();
                            File file = new File("C:\\wamp64\\www\\symfony\\web\\uploads");
                            File out = new File(file, c);
                            Image image = new Image(out.toURI().toString(), 2400, 2800, false, false);
                            collectImage.setImage(image);
                            try {
                                Connection cnx = mysqlConnect.getInstance().getCnx();
                                Statement stm = cnx.createStatement();
                                String req = "select * from collectPending c, fos_user f where c.image='" + c + "' and c.user_id = f.id";
                                ResultSet rs = stm.executeQuery(req);
                                if (rs.next()) {
                                    justCollecte.setVisible(true);
                                    nomDeLaCollecte.setText(rs.getString("nomCollecte"));
                                    nomDeLaCollecte.setVisible(true);
                                    voirDetails.setVisible(true);
                                    voirDetails.setOnAction((ActionEvent event2) -> {
                                        exitAction();
                                        try {
                                            int id = rs.getInt("user_id");
                                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modules/DetailsCollecte.fxml"));
                                            Parent root = (Parent) loader.load();
                                            DetailsCollecteController dc = loader.getController();
                                            dc.getUsernameLabel().setText(usernameLabel.getText());
                                            dc.getTest().setText(Integer.toString(id));
                                            /*dc.getCollectImage().setFitHeight(300);
                                            dc.getCollectImage().setFitWidth(300);*/
                                            dc.getCollectImage().setImage(image);
                                            System.out.println(myRole.getText());
                                            dc.setMyRole(myRole);
                                            dc.getNomCollecte().setText(rs.getString("c.nomCollecte"));
                                            dc.getDescriptionCollecte().setText(rs.getString("c.descriptionCollecte"));
                                            dc.getUserAssoc().setText(rs.getString("f.username"));
                                            if (usernameLabel.getText().equals(rs.getString("f.username"))) {
                                                dc.getEditCollecteBtn().setVisible(true);
                                                if (rs.getInt("c.budgetCollecte") - rs.getInt("c.nombreAtteint") < 50) {
                                                    dc.getEditCollecteBtn().setDisable(true);
                                                    dc.getNoMoreModified().setVisible(true);
                                                }
                                            }
                                            dc.getCategorieCollecte().setText(rs.getString("c.categorieCollect_typeCategorie"));
                                            String hiddenID = String.valueOf(rs.getInt("c.id"));
                                            dc.getHiddenID().setText(hiddenID);
                                            dc.getHiddenID().setVisible(false);
                                            int budget = rs.getInt("c.budgetCollecte");
                                            int n = rs.getInt("c.nombreAtteint");
                                            int nbreParticipantsCollecte = rs.getInt("c.nombreParticipantsCollecte");
                                            if (myRole.getText().equals("Association")) {
                                                dc.getDonateDon().setText("Dons");
                                                dc.getCommentaireField().setVisible(false);
                                                dc.getCommentBtn().setVisible(false);
                                            } else {
                                                dc.getDonateDon().setVisible(true);
                                                dc.getCommentaireField().setVisible(true);
                                                dc.getCommentBtn().setVisible(true);
                                            }
                                            dc.getBudgetCollecte().setText(String.valueOf(budget));
                                            dc.getFondAtteint().setText(String.valueOf(n));
                                            dc.getNbreParticipants().setText(String.valueOf(nbreParticipantsCollecte));
                                            Stage stage = new Stage();
                                            stage.initStyle(StageStyle.UNDECORATED);
                                            Scene scene = new Scene(root);
                                            Image icon = new Image(getClass().getResourceAsStream("/modules/images/saviorsIcon.png"));
                                            stage.getIcons().add(icon);
                                            stage.setScene(scene);
                                            Image mouseCursor = new Image("/saviorsda/images/mouseCursor.png");
                                            scene.setCursor(new ImageCursor(mouseCursor));
                                            stage.setTitle("Détails de la Collecte - Saviors");
                                            stage.show();
                                        } catch (Exception e) {
                                            System.out.println(e);
                                        }
                                    });
                                }
                            } catch (Exception e) {
                                System.out.println(e);
                            }

                        });
                        seeImage.setStyle(
                                "-fx-background-color: #28a745;"
                                + "-fx-border-color: white;"
                                + "-fx-text-fill: white;"
                                + "-fx-font-weight: bold;"
                        );
                    }

                    HBox pane = new HBox(seeImage);

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(pane);
                        }
                    }
                };
                cell.setStyle(
                        "-fx-background-color: white;"
                );
                if (cell.toString() == "") {
                    cell.setVisible(false);
                } else {
                    cell.setVisible(true);
                }
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        imagesCollects.getColumns().add(colBtn);

    }

    TableColumn col = new TableColumn("Nom de l'image");

    @FXML
    private void imagesCollects() throws Exception {
        data = FXCollections.observableArrayList();
        Connection cnx = mysqlConnect.getInstance().getCnx();
        Statement stm = cnx.createStatement();
        String req = "select image from collectPending";
        ResultSet rs = stm.executeQuery(req);
        for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
            final int j = i;

            col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                    return new SimpleStringProperty(param.getValue().get(j).toString());
                }
            });
            imagesCollects.getColumns().addAll(col);
            addButtonToTable();

            col.setStyle(
                    "-fx-background-color: white;"
            );
            System.out.println("Column [" + i + "] ");
        }
        System.out.println("Liste des Collectes");

        while (rs.next()) {
            ObservableList<String> row = FXCollections.observableArrayList();
            //File file = new File("C:\\wamp64\\www\\symfony\\web\\uploads");

            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                /*File out = new File(file, rs.getString(i));
                Image image = new Image(out.toURI().toString());
                ImageView imageview = new ImageView(image);
                imageview.setImage(image);*/
                row.add(rs.getString(i));
            }
            System.out.println(row);
            data.add(row);
        }
        imagesCollects.setItems(data);
    }

    @FXML
    private void moduleAction(ActionEvent event) throws Exception {
        try {
            if (action.getValue() == "Collecte") {
                JOptionPane.showMessageDialog(null, "Vous êtes déjà dans la page \"Collecte\".");
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
        try {
            imagesCollects();
        } catch (Exception ex) {
            Logger.getLogger(CollecteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
