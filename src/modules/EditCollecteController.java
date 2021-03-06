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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import mysql.mysqlConnect;

public class EditCollecteController implements Initializable {

    @FXML
    private Button exit;
    @FXML
    private Label usernameLabel;
    @FXML
    private Button logoutBtn;
    @FXML
    private ComboBox<String> action;
    @FXML
    private Button saviorsLogo;
    @FXML
    private Button listeCollecte;
    @FXML
    private Button listeCategories;
    @FXML
    private TextField nomCollecte;
    @FXML
    private TextField budgetCollecte;
    @FXML
    private TextArea descriptionCollecte;
    @FXML
    private ChoiceBox catFieldCollecte;
    @FXML
    private Button uploadImage;
    @FXML
    private Label imageName;
    @FXML
    private Label myRole;
    @FXML
    private ImageView imageView;
    @FXML
    private ImageView imageCollect;
    @FXML
    private Label hiddenNC;

    ObservableList<String> modules = FXCollections.observableArrayList("Evenement", "Publication", "Réclamation", "Collecte", "Produit", "Forum");

    public Label getImageName() {
        return imageName;
    }

    public void setImageName(Label imageName) {
        this.imageName = imageName;
    }
    
    public ChoiceBox getCatFieldCollecte() {
        return catFieldCollecte;
    }

    public void setCatFieldCollecte(ChoiceBox catFieldCollecte) {
        this.catFieldCollecte = catFieldCollecte;
    }
    
    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public Label getHiddenNC() {
        return hiddenNC;
    }

    public void setHiddenNC(Label hiddenNC) {
        this.hiddenNC = hiddenNC;
    }

    public TextField getNomCollecte() {
        return nomCollecte;
    }

    public void setNomCollecte(TextField nomCollecte) {
        this.nomCollecte = nomCollecte;
    }

    public TextField getBudgetCollecte() {
        return budgetCollecte;
    }

    public void setBudgetCollecte(TextField budgetCollecte) {
        this.budgetCollecte = budgetCollecte;
    }

    public TextArea getDescriptionCollecte() {
        return descriptionCollecte;
    }

    public void setDescriptionCollecte(TextArea descriptionCollecte) {
        this.descriptionCollecte = descriptionCollecte;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        action.setItems(modules);
        try {
            Connection cnx = mysqlConnect.getInstance().getCnx();
            Statement stm = cnx.createStatement();
            String req = "select * from categorie_collect";
            ResultSet rs = stm.executeQuery(req);
            while (rs.next()) {
                catFieldCollecte.getItems().add(rs.getString("typeCategorie"));
                catFieldCollecte.getSelectionModel().selectFirst();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private void exitAction() {
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void exitButtonAction(ActionEvent event) {
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
    private void modifierCollecteAction(ActionEvent event) {
        try {
            Connection cnx = mysqlConnect.getInstance().getCnx();
            Statement stm = cnx.createStatement();
            //String req = "select * from fos_user where username = '" + usernameLabel.getText() + "'";
            //ResultSet rs = stm.executeQuery(req);
            //if (rs.next()) {
                int nbreAtteint = 0;
                int nbreParticipants = 0;

                String req2 = "update collectPending set nomCollecte='" + nomCollecte.getText() + "', budgetCollecte= '" + budgetCollecte.getText() + "', descriptionCollecte= '" + descriptionCollecte.getText() + "', image= '" + imageName.getText() + "', categorieCollect_typeCategorie= '" + catFieldCollecte.getValue() + "' where nomCollecte='"+hiddenNC.getText()+"'";
                if (validateBudget() && validateNomCollecte() && validateDescCollecte() && nomCollecte.getText().length() != 0 && descriptionCollecte.getText().length() != 0 && Integer.parseInt(budgetCollecte.getText()) >= 1000) {
                    System.out.println("src/modules/images/" + imageName.getText());
                    stm.executeUpdate(req2);
                    File dir = new File("C:\\wamp64\\www\\symfony\\web\\uploads");
                    File dir2 = new File("src/modules/images");
                    String name = imageName.getText();
                    File outputFile = new File(dir, name);
                    File outputFile2 = new File(dir2, name);
                    Image image = new Image(outputFile2.toURI().toString());
                    System.out.println(outputFile2.getAbsolutePath());
                    BufferedImage bimage = SwingFXUtils.fromFXImage(image, null);
                    ImageIO.write(bimage, "png", outputFile);
                    outputFile2.delete();
                    JOptionPane.showMessageDialog(null, "Collecte modifiée avec succés !");
                    /*imageName.setText("Aucun fichier choisi");
                    nomCollecte.setText("");
                    budgetCollecte.setText("");
                    descriptionCollecte.setText("");*/
                }
                
                if (budgetCollecte.getText().equals("")&& !imageName.getText().equals("Aucun fichier choisi") && nomCollecte.getText().length() != 0 && descriptionCollecte.getText().length() != 0) {
                    JOptionPane.showMessageDialog(null, "Vous avez encore un champs requis !");
                }
                /*if (imageName.getText().equals("Aucun fichier choisi") || nomCollecte.getText().length() == 0 || descriptionCollecte.getText().length() == 0 || budgetCollecte.getText().length() == 0) {
                    JOptionPane.showMessageDialog(null, "Champs requis !");
                }*/

                if (budgetCollecte.getText().length() != 0 && Integer.parseInt(budgetCollecte.getText()) < 0) {
                    JOptionPane.showMessageDialog(null, "Budget négatif !");
                }
                
                if (budgetCollecte.getText().length() != 0 && Integer.parseInt(budgetCollecte.getText()) == 0) {
                    JOptionPane.showMessageDialog(null, "Budget nul !");
                }
                
                if (budgetCollecte.getText().length() != 0 && Integer.parseInt(budgetCollecte.getText()) < 1000) {
                    JOptionPane.showMessageDialog(null, "Le Budget minimal est 1000 TND ! !");
                }
            //}

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
        
    }
    
    private boolean validateBudget() {
        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(budgetCollecte.getText());
        if (m.find() && m.group().equals(budgetCollecte.getText())) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Entrez un Budget valide !");
            return false;
        }
    }
    
    private boolean validateNomCollecte() {
        Pattern p = Pattern.compile("[A-Z] [a-z]*");
        Matcher m = p.matcher(nomCollecte.getText());
        if (m.find() && m.group().equals(nomCollecte.getText())) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Entrez un nom de la Collecte valide ! (Commence par un MAJ)");
            return false;
        }
    }
    
    private boolean validateDescCollecte() {
        Pattern p = Pattern.compile("[A-Z] [a-z]*");
        Matcher m = p.matcher(descriptionCollecte.getText());
        if (m.find() && m.group().equals(descriptionCollecte.getText())) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Entrez une description valide ! (Commence par un MAJ)");
            return false;
        }
    }
    
    @FXML
    public String saveFile(Image image, File file) throws Exception {
        File dir = new File("src/modules/images");
        String name = file.getName();
        File outputFile = new File(dir, name);
        BufferedImage bimage = SwingFXUtils.fromFXImage(image, null);
        ImageIO.write(bimage, "png", outputFile);
        return name;
    }

    @FXML
    private void uploadImage() throws Exception {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File("C:\\wamp64\\www\\symfony\\web\\uploads"));
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG Images", "*.png"), new FileChooser.ExtensionFilter("JPG Images", "*.jpg"));
        fc.setTitle("Choisir une Image.");
        File selectedImage = fc.showOpenDialog(null);
        if (selectedImage != null) {
            imageName.setText(selectedImage.getName());
            imageName.setVisible(true);
            Image image = new Image(selectedImage.toURI().toString());
            imageView.setImage(image);
            imageCollect.setImage(image);
            saveFile(image, selectedImage);
            System.out.println(selectedImage.toURI().toString());
            /*selectedImage = fc.showSaveDialog(null);
            File f = new File("C:\\wamp64\\www\\symfony\\web\\uploads");
            fc.setInitialDirectory(f);
            if (selectedImage.getAbsoluteFile() != f) {
                JOptionPane.showMessageDialog(null, "L'image n'est pas valide !");
            }*/
        } else {
            JOptionPane.showMessageDialog(null, "L'image n'est pas valide !");
        }
        //File selectedImage2 = fc2.showSaveDialog(null);
        //fc2.setInitialDirectory(new File("C:\\wamp64\\www\\symfony\\web\\uploads"));

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

    public ImageView getImageCollect() {
        return imageCollect;
    }

    public void setImageCollect(ImageView imageCollect) {
        this.imageCollect = imageCollect;
    }

}
