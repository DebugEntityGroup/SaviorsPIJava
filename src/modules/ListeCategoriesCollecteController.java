package modules;

import homepage.UserAuthentifiedController;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import mysql.mysqlConnect;

public class ListeCategoriesCollecteController implements Initializable {
    
    @FXML
    private Label usernameLabel;
    
    @FXML
    private TextField searchField;
    
    @FXML
    private javafx.scene.control.Button exit;
    
    @FXML
    private Label logoutText;
    
    @FXML
    private Label myRole;
    
    @FXML
    private Label listCategories;
    
    @FXML
    private Label noCategories;
    
    @FXML
    private Button saviorsLogo;
    
    @FXML
    private Button gererCollecte;
    
    @FXML
    private Button editButton;
    
    @FXML
    private TableColumn typeCat;
    
    @FXML
    private TableColumn actionsCrud;
    
    @FXML
    private TableView allCategoriesListed;
    
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
            stage.setTitle("Créer une nouvelle Catégorie - Saviors");
            stage.show();
        } catch (Exception e) {
            System.out.println("Erreur de chargement de page !");
            System.out.println(e);
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
            stage.setTitle("Créer une nouvelle Collecte - Saviors");
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
    TableColumn col = new TableColumn("Type de la Categorie");
    
    public void Allcategories() {
        data = FXCollections.observableArrayList();
        try {
            Connection cnx = mysqlConnect.getInstance().getCnx();
            Statement stm = cnx.createStatement();
            String req = "select typeCategorie from categorie_collect;";
            //String req2 = "select count(*) as nbreColCat from categorie_collect";
            ResultSet rs = stm.executeQuery(req);
            
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                final int j = i;
                //TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                allCategoriesListed.getColumns().addAll(col);
                col.setStyle(
                        "-fx-background-color: white;"
                );
                //addButtonToTable();
                System.out.println("Column [" + i + "] ");
            }
            
            System.out.println("Liste des Catégories");
            
            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getString(i));
                }
                System.out.println(row);
                data.add(row);
            }
            
            allCategoriesListed.setItems(data);
            if (allCategoriesListed.getItems().size() == 0) {
                allCategoriesListed.setVisible(false);
                listCategories.setVisible(false);
                noCategories.setVisible(true);
            }
            System.out.println(allCategoriesListed.getItems().size());
        } catch (SQLException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
    
    public Button getGererCollecte() {
        return gererCollecte;
    }
    
    public void setGererCollecte(Button gererCollecte) {
        this.gererCollecte = gererCollecte;
    }
    
    private void addButtonToTable() {
        TableColumn<Data, Void> colBtn = new TableColumn("Actions");
        
        colBtn.setStyle(
                "-fx-background-color: white;"
        );
        
        Callback<TableColumn<Data, Void>, TableCell<Data, Void>> cellFactory = new Callback<TableColumn<Data, Void>, TableCell<Data, Void>>() {
            @Override
            public TableCell<Data, Void> call(final TableColumn<Data, Void> param) {
                final TableCell<Data, Void> cell = new TableCell<Data, Void>() {

                    //private final Button edit = new Button("Modifier");
                    private final Button delete = new Button("Supprimer");
                    
                    {

                        /*edit.setOnAction((ActionEvent event) -> {
                            Object data2 = allCategoriesListed.getItems().get(getIndex());
                            String c = (String) col.getCellObservableValue(data2).getValue();
                            System.out.println("Catégorie a modifier: " + c);
                            System.out.println(c);
                            try {
                            Stage stage = (Stage) exit.getScene().getWindow();
                            stage.close();
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modules/EditCategorieCollecte.fxml"));
                            Parent root = (Parent) loader.load();
                            EditCategorieCollecteController ec = loader.getController();
                            ec.getTypeCat().setText(c);
                            Stage stage2 = new Stage();
                            stage2.initStyle(StageStyle.UNDECORATED);
                            Scene scene = new Scene(root);
                            Image icon = new Image(getClass().getResourceAsStream("/modules/images/saviorsIcon.png"));
                            stage2.getIcons().add(icon);
                            stage2.setScene(scene);
                            stage2.setTitle("Modifier Catégorie - Saviors");
                            stage2.show();
                            } catch (Exception e) {
                                System.out.println("nope");
                            }
                        });

                        /*editButton.setOnAction((ActionEvent event) -> {
                            try {
                            Object data2 = allCategoriesListed.getItems().get(getIndex());
                            String c = (String) col.getCellObservableValue(data2).getValue();
                            Connection cnx = mysqlConnect.getInstance().getCnx();
                            Statement stm = cnx.createStatement();
                            String req = "update categorie_collect set typeCategorie = '"+categorieAModifier.getText()+"' where typeCategorie = '"+c+"'";
                            stm.executeUpdate(req);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        });*/
                        delete.setOnAction((ActionEvent event) -> {
                            try {
                                Connection cnx = mysqlConnect.getInstance().getCnx();
                                Object data2 = allCategoriesListed.getItems().get(getIndex());
                                String c = (String) col.getCellObservableValue(data2).getValue();
                                Statement stm = cnx.createStatement();
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Confirmer la suppression ?");
                                alert.setHeaderText(null);
                                alert.setContentText("Voulez-vous supprimer cette Catégorie ?");
                                Optional<ButtonType> action = alert.showAndWait();
                                String req = "delete from categorie_collect where typeCategorie = '" + c + "'";
                                if (action.get() == ButtonType.OK) {
                                    stm.executeUpdate(req);
                                    int selectdIndex = getTableRow().getIndex();
                                    data.remove(selectdIndex);
                                    JOptionPane.showMessageDialog(null, "Catégorie " + c + " supprimée avec succés !");
                                    System.out.println("La Catégorie " + c + " a été supprimée avec succés !");
                                }
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        });

                        /*edit.setStyle(
                                "-fx-background-color: #28a745;"
                                + "-fx-border-color: white;"
                                + "-fx-text-fill: white;"
                                + "-fx-font-weight: bold;"
                        );*/
                        delete.setStyle(
                                "-fx-background-color: #dc3545;"
                                + "-fx-border-color: white;"
                                + "-fx-text-fill: white;"
                                + "-fx-font-weight: bold;"
                        );
                    }
                    
                    HBox pane = new HBox(delete);
                    
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
        
        allCategoriesListed.getColumns().add(colBtn);
        
    }
    
    public TableView getAllCategoriesListed() {
        return allCategoriesListed;
    }
    
    public void setAllCategoriesListed(TableView allCategoriesListed) {
        this.allCategoriesListed = allCategoriesListed;
    }
    
    public TableColumn getCol() {
        return col;
    }
    
    public void setCol(TableColumn col) {
        this.col = col;
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
        Allcategories();
    }
    
}
