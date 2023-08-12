package Files;

import Model.PresentationModel;
import javafx.scene.Scene;
import javafx.stage.FileChooser;

import java.io.File;

public class Import {

    /**
     * @param scene scene utiliser pour ouvrir une nouvelle fenêtre
     * @param livre PrésentationModel pour récupérer les données
     * @return true si l'ímport réussi,sinon,return false
     */
    public static Boolean importBook(Scene scene, PresentationModel livre){

        //fenetre pour selectioner le livre a charger.
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Charger un livre");
        File file = fileChooser.showOpenDialog(scene.getWindow());

        //charger le livre sélectioner.
        Boolean result = livre.setBook(file);

        return result;
    }

}
