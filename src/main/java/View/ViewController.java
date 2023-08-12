package View;

import Files.Export;
import Files.Import;
import Model.PresentationModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * @author Lombardelli Nathan 2I3. 2020.
 */
public class ViewController implements Initializable {

    private PresentationModel livre;
    private ObservableList<String> oListePara = FXCollections.observableArrayList();//list des élément observables dans la ListView.
    private ObservableList<String> oListeVerif = FXCollections.observableArrayList();//list des élément pour la vérification du livre.

    @FXML
    private Text title;//Text montrant le titre du livre actuel.
    @FXML
    private ListView vueLivre;//list des paragraphs du livre.
    @FXML
    private TextField titreLivre;//champs de modification du titre du livre.
    @FXML
    private Text numPara;//numéros du paragraph sélèctioner.
    @FXML
    private TextField textPara;//le text du paragraph sélectioner.
    @FXML
    private TextField textRedir; //choix des redirection du choix.
    @FXML
    private ChoiceBox choiceBoxNumChoix; //sélection du choix.
    @FXML
    private TextField textChoix; //text du choix selectioner.
    @FXML
    private Text textErreur; //notifie les éventuel erreur.
    @FXML
    private ListView vueVerif; //vue pour la verification.
    @FXML
    private BorderPane background; //changer la couleur de fond.
    @FXML
    private Text pathImg; //chemin d'accès a l'image sélectioner.

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //création du livre
        livre = new PresentationModel();

        //ajout d'un listener pour modifier le titre du livre.
        titreLivre.textProperty().addListener((observableValue, oldValue, newValue) -> {
            textErreur.setText("");
            if(!newValue.equals("")) {
                livre.setTitle(newValue);//défini le nouveau titre du livre.
                title.setText(newValue);//change le text au dessus des paragraph par le nouveau Titre.
            }else {
                textErreur.setText("Titre du livre inexistant");
            }
        });

        vueLivre.setItems(oListePara);//définit se que doit afficher la ListView vueLivre (oListePara).
        vueVerif.setItems(oListeVerif);//définit se que doit afficher la ListView vueVerif (oListVerif).

        //ajout d'un Listener au focus de la ListView (affiche les infos sur le para selectionner).
        vueLivre.getSelectionModel().selectedItemProperty().addListener((observableValue, o, t1) -> {
            textErreur.setText("");
            if(!vueLivre.getSelectionModel().isEmpty()) {//si il y a une selection.
                int indexDansView = vueLivre.getFocusModel().getFocusedIndex();//récupération de l'index du paragraphe sélectioner dans la ListView.
                String txtPara = oListePara.get(indexDansView);//récupération du text du paragraphe selectionner.

                //change le numéros du para
                int numP = livre.getNumPara(txtPara);//détermine le num du para.
                numPara.setText(numP + "");//change le numéros du para par celui sélectioner.

                //change le text du para
                textPara.setText(livre.getTextPara(numP));//défini le text du para sélectioner.

                //change les choix a chaque changement de selection de para.
                choiceBoxNumChoix.getItems().clear();//suppresion de l'ancienne list.
                for (int i = 0;i < livre.getNbrChoixPara(numP) ; i++){
                    choiceBoxNumChoix.getItems().add(i,i+1);//ajouter le numéros de tt les choix du para.
                }

                //change le chemin de l'image.
                pathImg.setText(livre.getImgPara(txtPara));

            }
        });

        //ajout d'un listener sur la selection du choix.
        choiceBoxNumChoix.getSelectionModel().selectedItemProperty().addListener((observableValue, o, t1) -> {
            textErreur.setText("");

            if(!choiceBoxNumChoix.getSelectionModel().isEmpty()) { //si il y a une selection du choix => modifier le text par rapport a la selection.

                int indexNumChoice = choiceBoxNumChoix.getSelectionModel().getSelectedIndex();
                int numChoix = (int) choiceBoxNumChoix.getItems().get(indexNumChoice);//récupère le num du choix.

                textChoix.setText(livre.getTextChoixPara(numChoix, Integer.parseInt(numPara.getText())));//définit le text du choix.

                textRedir.setText(livre.getRedirChoixPara(numChoix,Integer.parseInt(numPara.getText()))+"");
            }

        });

        //ajout d'un Listener pour la modification du text du choix.
        textChoix.textProperty().addListener((observableValue, oldValue, newValue) -> {
            textErreur.setText("");
            if(!choiceBoxNumChoix.getSelectionModel().isEmpty()) {//si un choix est sélectioner

                int indexNumChoice = choiceBoxNumChoix.getSelectionModel().getSelectedIndex();
                int numChoix = (int) choiceBoxNumChoix.getItems().get(indexNumChoice);//récupère le num du choix.

                livre.modifTextChoixPara(Integer.parseInt(numPara.getText()),numChoix,newValue);

            }

        });

        //ajout Listener pour la redirection du choix
        textRedir.textProperty().addListener((observableValue, oldValue, newValue) -> {
            textErreur.setText("");
            if(!choiceBoxNumChoix.getSelectionModel().isEmpty()) {//si un choix est sélectioner
                int newRedir;
                int indexNumChoice = choiceBoxNumChoix.getSelectionModel().getSelectedIndex();
                int numChoix = (int) choiceBoxNumChoix.getItems().get(indexNumChoice);//récupère le num du choix.

                if(!newValue.equals("")) { // sinon erreur si supprime le nombre pour le remplacer
                    if(!livre.numParaExist(Integer.parseInt(newValue))){//si le num de redirection n'est pas le numéros d'un para existant.
                        textErreur.setText("la redirection du choix pointe vers un paragraphe inexistant");
                    }
                     newRedir = Integer.parseInt(newValue);
                }else{
                     newRedir = 0;
                }

                livre.modifRedirChoixPara(numChoix,Integer.parseInt(numPara.getText()),newRedir);

            }
        });


        //listener pour modifier le text du paragraph.
        textPara.textProperty().addListener((observableValue, oldValue, newValue) -> {
            textErreur.setText("");
            if(!vueLivre.getSelectionModel().isEmpty()) {//si il un para est selectioner.
                int indexDansView = vueLivre.getFocusModel().getFocusedIndex();//récupération de l'index du paragraphe sélectioner dans la ListView.
                String txtPara = oListePara.get(indexDansView);//récupération du text du paragraphe selectionner.

                int numParaSelect = Integer.parseInt(numPara.getText());//numéros du para selectioner.

                //PQ les 2 condition avec un && ne fonctionne pas ?(affiche l'erreur dès que je selectionne un  nouveau para)
                if(!textPara.getText().equals(txtPara)){
                    if(!livre.textParaExist(newValue) ) {//le txt du paragraph n'existe pas dans le livre et se n'est pas celuit selectioner.
                        textErreur.setText("");
                        livre.modifTextPara(numParaSelect, newValue);
                        oListePara.set(indexDansView, newValue);
                    }else{
                        textErreur.setText("le texte du paragraph existe déjà");
                    }
                }
            }
        });

    }

    /**
     * crée et ajoute un paragraphe.
     */
    @FXML
    private void addPara() {

        int numNewPara = 0 ;
        boolean numOK = false;

        do{
            numNewPara++;
            if(!livre.numParaExist(numNewPara)){
                numOK = true;
            }
        }while(numOK == false);

        livre.addParagraph(numNewPara,"para num :" + numNewPara +"(To Do)");//ajout du nouveau paragraph dans le livre.
        oListePara.add(livre.getTextPara(numNewPara));//ajout du texte du paragraph a la ListView.

        //trier la list suite a l'ajout

        updateView();

    }

    /**
     * supprime le paragraphe sélèctioner.
     */
    @FXML
    public void suppPara() {
        int index = vueLivre.getFocusModel().getFocusedIndex();//récupération de l'index du paragrave sélectioner dans la ListView.
        String txtPara = oListePara.get(index);//récupération du text du paragraph selectionner.

        livre.suppPara(txtPara);//suppresion du paragraph dans le livre sur base de son text.
        oListePara.remove(txtPara);//suppresion du paragraph dans la ListView.
        vueLivre.getSelectionModel().selectFirst();//selectione le 1er.

    }

    /**
     * ajoute un choix au paragraphe sélèctioner.
     */
    @FXML
    public void addChoix() {
        int index = vueLivre.getFocusModel().getFocusedIndex();//récupération de l'index du paragrave sélectioner dans la ListView.
        String txtPara = oListePara.get(index);//récupération du text du paragraph selectionner.

        int numP = livre.getNumPara(txtPara);//récupère le numéros du para sélectioner.

        livre.addChoixPara(1,"(To Do)",numP);//ajoute le nouveaux choix au paragraph.

        //actualise les choix l'ors d'un ajout.
        choiceBoxNumChoix.getItems().clear();//suppresion de l'ancienne list.
        for (int i = 0;i < livre.getNbrChoixPara(numP) ; i++){
            choiceBoxNumChoix.getItems().add(i,i+1);//ajouter le numéros de tt les choix du para.
        }

    }

    /**
     * supprime le choix sélèctioner.
     */
    @FXML
    public void suppChoix() {

            int index = vueLivre.getFocusModel().getFocusedIndex();//récupération de l'index du para grave sélectioner dans la ListView.
            String txtPara = oListePara.get(index);//récupération du text du paragraph selectionner.
            int numP = livre.getNumPara(txtPara);//récupère le numéros du para sélectioner.

        int indexNumChoice = choiceBoxNumChoix.getSelectionModel().getSelectedIndex();
        int numChoix = (int) choiceBoxNumChoix.getItems().get(indexNumChoice);//récupère le num du choix.

        livre.suppChoixPara(numChoix,numP);

        //actualise les choix l'ors d'une suppresion.
        choiceBoxNumChoix.getItems().clear();//suppresion de l'ancienne list.
        for (int i = 0;i < livre.getNbrChoixPara(numP) ; i++){
            choiceBoxNumChoix.getItems().add(i,i+1);//ajouter le numéros de tt les choix du para.

        }
    }

    /**
     * vérifie le livre.
     */
    @FXML
    public void verif() { ///////////////////////////toujour erreur si verif alors que choix redirige vers para inexistant\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        textErreur.setText("");

        oListeVerif.clear();
        List<List<Integer>> graph = livre.graph();

        //affichage dans la listView.
        if(!graph.isEmpty() && graph.size() >= livre.getNbrPara() && graph.size() >= livre.getNbrChoix()) {
            Set<Integer> listNoeudAbsDeTtAction = livre.getNoeudsAbsentsDeTouteAction();
            oListeVerif.add(0, "NoeudsAbsentsDeTouteAction : " + listNoeudAbsDeTtAction);
            List<Integer> listNoeudTermInaccesAPrtirDuDebut = livre.getNoeudsTerminauxInaccessiblesAPartirDuDébut();
            oListeVerif.add(1, "NoeudsTerminauxInaccessiblesAPartirDuDébut : " + listNoeudTermInaccesAPrtirDuDebut);
        }else{
            textErreur.setText("il y a une ou plusieurs boucle(s) dans vos paragraphs");
        }
    }

    /**
     * génere le graph du livre.
     */
    @FXML
    public void graph() {
        if(!livre.isEmpty()) {
            oListeVerif.clear();
            String cheminNumP = "";
            List<List<Integer>> graph = livre.graph();

            //affichage dans la listView.
            if(!graph.isEmpty()) { //gestion boucles.(tout les para boucles).
                if(graph.size() < livre.getNbrPara() || graph.size() < livre.getNbrChoix()) { // gestion boucles.(qlq para boucles).        ////////////rajouter avec les choix en plus\\\\\\\\\\\\\\\\\\
                    textErreur.setText("il y a une boucle dans vos paragraphs");
                }
                    Iterator<List<Integer>> it = graph.iterator();
                    do {
                        List<Integer> chemin = it.next();
                        Iterator<Integer> itNumP = chemin.iterator();
                        do {
                            int i = itNumP.next();
                            if (itNumP.hasNext()) {
                                cheminNumP += i + "->";
                            } else {
                                cheminNumP += i + "";
                                oListeVerif.add(cheminNumP);
                                cheminNumP = "";
                            }

                        } while (itNumP.hasNext());

                    } while (it.hasNext());

            }else{
                textErreur.setText("vos paragraphs boucles entre eux");
            }
        }
    }

    /**
     * charge la sauvegarde d'édition d'un livre.
     */
    @FXML
    public void loadBook() {
        textErreur.setText("");

        Boolean result = Import.importBook(title.getScene(),livre);

        if(!result){
            textErreur.setText("Erreur lors du chargement du livre ");
        }else{
            textErreur.setText("chargement du livre réussi");
        }

        //mettre a jour par rapport au livre charger.
        updateView();

    }

    /**
     * permet de mettre a jour la view par rapport aux données du livre.
     */
    private void updateView(){
        oListePara.clear(); //suppresion de tt les éléments de la list.
        List<Integer> listNumPara = livre.getListAllNumPara();//récupération des numéros des paragraphs existant.
        Collections.sort(listNumPara);//tri de cette list.

        do{
            oListePara.add(livre.getTextPara(listNumPara.get(0)));//ajout du text du numéros du 1er élément de la list.
            listNumPara.remove(0);//suppresion du 1er élément de la list.
        }while(!listNumPara.isEmpty());

        //mettre a jour le titre
        titreLivre.setText(livre.getTitle());
    }

    /**
     * sauvegarde l'édition du livre.
     */
    @FXML
    public void saveBook() {
        textErreur.setText("");

        Boolean result = Export.saveBook(title.getScene(),livre);

        if(result){
            textErreur.setText("sauvegarde réussie");
        }else{
            textErreur.setText("erreur lors du chargement du livre");
        }


    }


    /**
     * exporte le livre au format json.
     */
    public void export() {

       Boolean result =  Export.exportJson(livre);

       if(result){
           textErreur.setText("export Json réussi");
       }else{
           textErreur.setText("erreur lors de l'export Json");
       }

    }

    //edit Menu

    /**
     * change la couleur de fond dela vue en rouge.
     */
    public void redMod() {
        background.setStyle("-fx-background-color: #870000;");
    }

    /**
     * change la couleur de fond dela vue en bleu.
     */
    public void blueMod() {
        background.setStyle("-fx-background-color: #203246;");
    }

    /**
     * permet de sélèctioner une image pour le paragraphe sélèctioner.
     */
    public void ChooseImg() {

        textErreur.setText("");

        try {
        int indexDansView = vueLivre.getFocusModel().getFocusedIndex();//récupération de l'index du paragraphe sélectioner dans la ListView.
        String txtPara = oListePara.get(indexDansView);//récupération du text du paragraphe selectionner.


            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Charger une image");
            File file = fileChooser.showOpenDialog(title.getScene().getWindow());
            String path = file.getPath();
            livre.setImgPara(txtPara,path);
            pathImg.setText(path);

        }catch(Exception e){
            textErreur.setText("sélectioner un paragraphe");
        }


    }
}
