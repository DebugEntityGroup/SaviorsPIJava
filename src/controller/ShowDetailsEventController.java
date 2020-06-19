/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import static controller.ShowEventClientController.idE;
import entities.BadWords;
import entities.CommentEvenement;
import entities.Evenement;
import entities.Participer;
import entities.User;
import entities.Vote;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLDataException;
import java.sql.SQLException;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import service.CommentService;
import service.EvenementService;
//import service.ParticiperService;
//import service.ServiceVote;

/**
 * FXML Controller class
 *
 * @author dell
 */
public class ShowDetailsEventController implements Initializable {

    @FXML
    private ListView<CommentEvenement> listViewQR;
    ObservableList<CommentEvenement> data;
    int i,i2 ;
    public static int idC ;

    @FXML
    private Text question;
    private Button Update;
    @FXML
    private Button type_vote_oui;
    @FXML
    private Button type_vote_non;
    @FXML
    private Button Back;
    @FXML
    private Button addComment;
    @FXML
    private Button back;
    @FXML
    private ImageView img_ev;
    @FXML
    private TextField commentText;
    @FXML
    private Label nbrLike;
    @FXML
    private Label nbrdeslike;
    @FXML
    private Button participer;
    @FXML
    private Button quiter;

    public ImageView getImg_ev() {
        return img_ev;
    }

    public void setImg_ev(ImageView img_ev) {
        this.img_ev = img_ev;
    }
 
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
CommentService cs = new CommentService();
FileInputStream input = null;
            try {
                input = new FileInputStream("src/Images/" +new CommentService().findEvenementById(idE).getImage());
                Image imageFile = new Image(input);
                img_ev.setImage(imageFile);
            } catch (FileNotFoundException ex) {
                System.out.println("File not found EXCEPTION !!!");
            }

//ServiceVote v = new ServiceVote();
//int lik = v.NumLike(idE);
//like.setText(String.valueOf(lik));
//int des =  v.NumdeLike(idE);
//deslike.setText(String.valueOf(des));
         EvenementService es = new EvenementService();
         
         question.setText(cs.findEvenementById(idE).getName());
        try {
            data = cs.getAllCommentByEvent(cs.findEvenementById(idE));
        } catch (SQLDataException ex) {
            Logger.getLogger(ShowDetailsEventController.class.getName()).log(Level.SEVERE, null, ex);
        }
         //supprimer.setVisible(false);
        // Question quest = new Question();
         //quest.setId_client(LoginController.session.getId());

     /*    if (quest.getId_client()==idU){
       supprimer.setVisible(true);
    }*/
         
     

        listViewQR.setItems(data);
        listViewQR.setCellFactory((ListView<CommentEvenement> param) -> new ListViewCommentEvent());
    }

        
        
        
        // TODO
   /*    

    private void DeleteQuestion(ActionEvent event) throws SQLDataException {
        
    CommentService q = new CommentService();
    ObservableList<CommentEvenement> e;
        e = listViewQR.getSelectionModel().getSelectedItems();
        
         for (CommentEvenement m : e) {
            idC=m.getIdComment();
              
        }
         System.out.println("controller.ShowDetailsEventController.DeleteQuestion(ddddddddd)"+i);
        q.deleteComment(idC);
         Parent root ;
        try {
          root = FXMLLoader.load(getClass().getResource("/gui/ShowDetailsEvent.fxml"));
            Stage myWindow =  (Stage) question.getScene().getWindow();
            Scene sc = new Scene(root);
            myWindow.setScene(sc);
            myWindow.setTitle("");
            myWindow.show();
           
        } catch (IOException ex) {
           // Logger.getLogger(ReponseItemController.class.getName()).log(Level.SEVERE,null,ex);
        }
    
    }

    private void UpdateReponse(ActionEvent event) {
        
           ObservableList<CommentEvenement> c;
        c = listViewQR.getSelectionModel().getSelectedItems();
        
      
        for (CommentEvenement m : c) {
            idC=m.getIdComment();
            
              
        }
        
         Parent root ;
        try {
          root = FXMLLoader.load(getClass().getResource("/gui/UpdateComment.fxml"));
            Stage myWindow =  (Stage) question.getScene().getWindow();
            Scene sc = new Scene(root);
            myWindow.setScene(sc);
            myWindow.setTitle("Modifier Reponse");
            myWindow.show();
            
           
        } catch (IOException ex) {
           // Logger.getLogger(ReponseItemController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }   */

    @FXML
    private void Like(ActionEvent event) throws SQLException {
        
        //ServiceVote sv  = new ServiceVote();
        
        Vote v = new Vote ();
        v.setId_client(1);
        v.setType_vote(1);
        v.setId_evenement(idE);
       // sv.addVote(v);
        
         /*Notifications notificationBuilder = Notifications.create()
                .title("Done").text("votre vote est bien enregistrer").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
               .position(Pos.CENTER_LEFT)
               .onAction(new EventHandler<ActionEvent>(){
                   public void handle(ActionEvent event)
                   {
                       System.out.println("clicked ON ");
               }});
       notificationBuilder.darkStyle();
       notificationBuilder.show();*/
                       
           Parent root ;
        try {
          root = FXMLLoader.load(getClass().getResource("/gui/ShowDetailsEvent.fxml"));
            Stage myWindow =  (Stage) commentText.getScene().getWindow();
            Scene sc = new Scene(root);
            myWindow.setScene(sc);
            myWindow.setTitle("comment");
            myWindow.show();
           
        } catch (IOException ex) {
            Logger.getLogger(CommentItemController.class.getName()).log(Level.SEVERE,null,ex);
           
        }
        
    }

    @FXML
    private void DesLike(ActionEvent event) throws SQLException {
        
        
        //ServiceVote sv  = new ServiceVote();
        
        Vote v = new Vote ();
        v.setId_client(1);
        v.setType_vote(2);
        v.setId_evenement(idE);
        //sv.addVote(v);
        
         /*Notifications notificationBuilder = Notifications.create()
                .title("Done").text("votre vote est bien enregistrer").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
               .position(Pos.CENTER_LEFT)
               .onAction(new EventHandler<ActionEvent>(){
                   public void handle(ActionEvent event)
                   {
                       System.out.println("clicked ON ");
               }});
       notificationBuilder.darkStyle();
       notificationBuilder.show();*/
                       
           Parent root ;
        try {
          root = FXMLLoader.load(getClass().getResource("/gui/ShowDetailsEvent.fxml"));
            Stage myWindow =  (Stage) commentText.getScene().getWindow();
            Scene sc = new Scene(root);
            myWindow.setScene(sc);
            myWindow.setTitle("comment");
            myWindow.show();
           
        } catch (IOException ex) {
            Logger.getLogger(CommentItemController.class.getName()).log(Level.SEVERE,null,ex);
           
        }
        
    }

    @FXML
    private void BackQuestion(ActionEvent event) {
    }

    @FXML
    private void AddComment(ActionEvent event) throws SQLDataException {
           
        CommentEvenement e = new CommentEvenement();
        CommentService cs = new CommentService();

        //e.setId(cs.findUserById(18));
        e.setIdEvt(cs.findEvenementById(idE));

        BadWords.loadConfigs();
      
            {
                
                if (BadWords.filterText(commentText.getText())) {

                       
           Parent root ;
        try {
          root = FXMLLoader.load(getClass().getResource("/gui/ShowDetailsEvent.fxml"));
            Stage myWindow =  (Stage) commentText.getScene().getWindow();
            Scene sc = new Scene(root);
            myWindow.setScene(sc);
            myWindow.setTitle("comment");
            myWindow.show();
           
        } catch (IOException ex) {
            Logger.getLogger(CommentItemController.class.getName()).log(Level.SEVERE,null,ex);
           
        }

                  
                } 
                else
                
                {
               
                   e.setComment(commentText.getText());
                   cs.addComment(e);
                   
                   // Alert alert = new Alert(Alert.AlertType.WARNING);
                   
                   /* alert.setTitle("gros mot");
                    alert.setHeaderText("gros mot trouve");
                    alert.setContentText("cette application n'autorise pas ces termes ");
                    alert.showAndWait();*/
                    
                    
            
           Parent root ;
        try {
          root = FXMLLoader.load(getClass().getResource("/gui/ShowDetailsEvent.fxml"));
            Stage myWindow =  (Stage) commentText.getScene().getWindow();
            Scene sc = new Scene(root);
            myWindow.setScene(sc);
            myWindow.setTitle("comment");
            myWindow.show();
             
        } catch (IOException ex) {
            Logger.getLogger(CommentItemController.class.getName()).log(Level.SEVERE,null,ex);
           
        }
        
    
            }
    
    }}
      /*    Parent root ;
        try {
          root = FXMLLoader.load(getClass().getResource("/gui/ShowDetailsEvent.fxml"));
            Stage myWindow =  (Stage) Update.getScene().getWindow();
            Scene sc = new Scene(root);
            myWindow.setScene(sc);
            myWindow.setTitle("Ajouter Comment");
            myWindow.show();
           
        } catch (IOException ex) {
           // Logger.getLogger(ReponseItemController.class.getName()).log(Level.SEVERE,null,ex);
        }
        
        
    } */
    


    @FXML
    private void back(ActionEvent event) {
         try {
           Parent root = FXMLLoader.load(getClass().getResource("/gui/ShowEventClient.fxml"));
            Stage myWindow =  (Stage) addComment.getScene().getWindow();
            Scene sc = new Scene(root);
            myWindow.setScene(sc);
            myWindow.setTitle("Ajouter Comment");
            myWindow.show();
           
        } catch (IOException ex) {
           // Logger.getLogger(ReponseItemController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    @FXML
    private void Participer(ActionEvent event) throws SQLDataException {
         CommentService cd = new CommentService();
         Evenement e= cd.findEvenementById(idE);
         EvenementService ES = new EvenementService();
         e.setNbrInterest(e.getNbrInterest()+1);
         ES.ModifierEvenenmentPlace(e);

        
         try {
       Parent root = FXMLLoader.load(getClass().getResource("/gui/ShowDetailsEvent.fxml"));
            Stage myWindow =  (Stage) question.getScene().getWindow();
            Scene sc = new Scene(root);
            myWindow.setScene(sc);
            myWindow.setTitle("");
            myWindow.show();
           
        } catch (IOException ex) {
           // Logger.getLogger(ReponseItemController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    @FXML
    private void quiter(ActionEvent event) {
    }
    
}
