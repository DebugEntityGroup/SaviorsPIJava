/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.esprit.controllers.DetailsProduitController;
import com.esprit.controllers.ListProduitController;
import entities.Evenement;
import entities.Participer;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLDataException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import service.CommentService;
import service.EvenementService;
//import service.ParticiperService;

/**
 * FXML Controller class
 *
 * @author dell
 */
public class ShowEventClientController implements Initializable {

    @FXML
    private ListView<Evenement> listView;

    ObservableList<Evenement> data;

    public static int idE;

    @FXML
    private Button details;
    @FXML
    private Button participer;
    @FXML
    private Button partager;
    @FXML
    private Button aaa;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            EvenementService cs = new EvenementService();
            data = (ObservableList<Evenement>) cs.getAllEvenement();
            listView.setItems(data);
            listView.setCellFactory((ListView<Evenement> param) -> new ListViewEvent());

            // TODO
        } catch (SQLDataException ex) {
            Logger.getLogger(ShowEventClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void ShowDetail(ActionEvent event) {

        ObservableList<Evenement> e;
        e = listView.getSelectionModel().getSelectedItems();
        for (Evenement m : e) {
            idE = m.getId();
        }
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ShowDetailsEvent.fxml"));
            //Parent root = (Parent) loader.load();
            ShowDetailsEventController sd = loader.getController();
            root = (Parent) loader.load();
            CommentService es = new CommentService();
            /*String imageUrl = "file:///C:\\wamp64\\www\\symfony\\web\\events\\"+es.findEvenementById(idE).getImage();
            Image image = new Image(imageUrl);
            ImageView imv = new ImageView(image);*/
            //sd.setImg_ev(imv);
            System.out.println(es.findEvenementById(idE).getImage());
            Stage myWindow = (Stage) details.getScene().getWindow();
            Scene sc = new Scene(root);
            myWindow.setScene(sc);
            myWindow.setTitle("Les details");
            myWindow.show();

        } catch (Exception ex) {
            System.out.println(ex +"blablabla");
            System.out.println("C:\\wamp64\\www\\symfony\\web\\events\\" + new CommentService().findEvenementById(idE).getImage());
        }
    }

    @FXML
    private void handleClose(ActionEvent event) {
    }

    @FXML
    private void participation(ActionEvent event) throws SQLDataException {

        CommentService cs = new CommentService();
        EvenementService es = new EvenementService();
        // ParticiperService ps = new ParticiperService();
        int nbr = 0;

        ObservableList<Evenement> e;
        e = listView.getSelectionModel().getSelectedItems();

        for (Evenement m : e) {
            idE = m.getId();

        }
        Participer p = new Participer(1, idE);

        Evenement e1 = cs.findEvenementById(idE);
        //nbr = e1.getNbreparticipants()+1;
        //e1.setNbreparticipants(nbr);
        System.out.println("controller.ShowEventClientController.participation()++++++++++" + nbr);
        es.ModifierEvenenmentPlace(e1);
        // ps.addParticiper(p);

       // Notifications notificationBuilder = Notifications.create()
           //     .title("Success").text("Votre Participation et accepter").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
              //  .position(Pos.CENTER_LEFT)
              //  .onAction(new EventHandler<ActionEvent>() {
                  /*  public void handle(ActionEvent event) {
                        System.out.println("clicked ON ");
                    }
                });*/
        /*notificationBuilder.darkStyle();
        notificationBuilder.show();*/

    }

    @FXML
    private void partage(ActionEvent event) {

        /* String authUrl="https://www.facebook.com/sharer/sharer.php?u="+"http://mes-questions-reponses.nathan.fr/"+"&xhpc_message_text="+"hello";
     System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
    WebDriver driver =new ChromeDriver();
     driver.get(authUrl);
        System.out.println(ev.getSite());
        
    }
         */
    }

    @FXML
    private void aa(ActionEvent event) {
    }
}
