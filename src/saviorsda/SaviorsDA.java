package saviorsda;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SaviorsDA extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = (Parent) loader.load();
        //LoginController lc = loader.getController();
        /*if(lc.getPassword().getText().length()==0) {
            lc.getIcon().setDisable(true);
            lc.getEyeImage().setDisable(true);
        } else {
            lc.getIcon().setDisable(false);
            lc.getEyeImage().setDisable(false);
        }*/
        
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root);
        Image mouseCursor = new Image("/saviorsda/images/mouseCursor.png");
        scene.setCursor(new ImageCursor(mouseCursor));
        Image icon = new Image(getClass().getResourceAsStream("/saviorsda/images/saviorsIcon.png"));
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.setTitle("Se Connecter - Saviors");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
