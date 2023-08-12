package View;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class View extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/GUI.fxml"));
        Scene scene = new Scene(root , 800,560);
        stage.setTitle ("GameBook Creation");
        stage.setScene(scene);

        //empeche de changer la taille de la fenetre.
        stage.setResizable(false);

        stage.show();

    }

    public static void main(String[] args){
        launch(args);
    }

}
