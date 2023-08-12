package Files;

import Model.PresentationModel;
import javafx.scene.Scene;
import javafx.stage.FileChooser;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import java.io.*;

public class Export {

    /**
     * @param livre PresentatioModel ou récupérer les données
     * @return true si l'export réussi,sinon,return false
     */
    public static Boolean exportJson(PresentationModel livre){

        File output = new File(livre.getTitle()+" GameBook.json");
        try(FileWriter writer = new FileWriter(output)) {
            writer.write(toJson(livre));
            writer.flush();
            return true;
        } catch(IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * transforme le PresentationmModel en json.
     * @param livre PrésentationModel a transformer.
     * @return le contenu du fichier json.
     */
    private static String toJson(PresentationModel livre) { //transformes les infos du livre en json, +- même forme de json que Projet C#.

        return Json.createObjectBuilder()
                .add("Titre", livre.getTitle())
                .add("Paragraphs", paraToJson(livre))
                .build().toString();
    }

    /**
     * transforme les paragraphes au format json.
     * @param livre PrésentationModel a transformer.
     * @return le contenu des paragraphes au format json.
     */
    private static JsonArrayBuilder paraToJson(PresentationModel livre) {
        JsonArrayBuilder json = Json.createArrayBuilder();
        for(int i = 1 ; i <= livre.getNbrPara() ; i++) {
            json.add(Json.createObjectBuilder()
                    .add("NumPara", i)
                    .add("Text", livre.getTextPara(i))
                    .add("Choix", choixToJson(livre, i))
                    .add("Image",livre.getImgPara(livre.getTextPara(i))));
        }
        return json;
    }

    /**
     * transforme les Choix au format json.
     * @param livre PrésentationModel a transformer.
     * @param numPara numéros du paragraphe.
     * @return le contenu des Choix au format json.
     */
    private static JsonArrayBuilder choixToJson(PresentationModel livre, int numPara) {
        JsonArrayBuilder json = Json.createArrayBuilder();
        for(int i = 1 ; i <= livre.getNbrChoixPara(numPara) ; i++) {
            json.add(Json.createObjectBuilder()
                    .add("Redirection", livre.getRedirChoixPara(i, numPara))
                    .add("ChoixTxt", livre.getTextChoixPara(i, numPara)));
        }
        return json;

    }

    /**
     * @param scene scene utiliser pour ouvrir une nouvelle fenêtre
     * @param livre PrésentationModel ou récupérer les données
     * @return true si la sauvegarde réussi,sinon,return false
     */
    public static Boolean saveBook(Scene scene,PresentationModel livre){

        //fenetre pour selectioner l'emplacement du livre a sauvegarder.
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sauver le livre");
        File file = fileChooser.showSaveDialog(scene.getWindow());

        //creation du fichier.
        if(file != null){
            try(final ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file.getPath()))){
                out.writeObject(livre.getLivre());
            }catch(Exception e){
                return false;
            }
            return true;
        }else{
            return false;
        }

    }


}
