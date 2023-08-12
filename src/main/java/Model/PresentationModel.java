package Model;

import Domain.Book;
import Domain.Choix;
import Domain.Paragraph;
import Verification.Verification;


import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author Lombardelli Nathan 2I3. 2020.
 */
public class PresentationModel {

    private Book livre;

    /**
     * Constructeur.
     */
    public PresentationModel(){

        livre = new Book("Titre du Livre",new ArrayList<>());

    }

    /**
     * récupère le titre du livre.
     * @return le titre du livre.
     */
    public String getTitle(){
        return livre.getTitle();
    }

    public Boolean setBook(File file){

        try(final ObjectInputStream in = new ObjectInputStream(new FileInputStream(file.getPath())))
            {
                this.livre = (Book) in.readObject();
            }catch (Exception e){
                return false;
            }
        return true;
    }
    /**
     * récupère le livre.
     * @return le livre.
     */
    public Book getLivre(){
        return this.livre;
    }

    /**
     * définit le titre du livre.
     * @param Titre nouveaux titre du livre.
     */
    public void setTitle(String Titre){
        this.livre.setTitle(Titre);
    }

    /**
     * Ajoute le paragraphe numPara avecle texte txtPara au livre.
     * @param numPara numéros du paragraphe.
     * @param txtPara texte du paragraphe.
     */
    public void addParagraph(int numPara,String txtPara){
        this.livre.addParagraph(new Paragraph(numPara,txtPara));
    }

    /**
     * détermine si le numéros du paragraphe numPara existe dans le livre.
     * @param numPara numéros du paragraphe.
     * @return true si le numéros du paragraphe est déjà pris.sinon,return false.
     */
    public boolean numParaExist(int numPara){
        return this.livre.numParaExist(numPara);
    }

    /**
     * récupère le texte du paragraphe numP.
     * @param numP numéros du paragraphe.
     * @return le texte du paragraphe numP.
     */
    public String getTextPara(int numP){
        return this.livre.getTextPara(numP);
    }

    /**
     * supprime le paragraphe qui possaide le texte textPara.
     * @param txtPara texte du paragraphe.
     */
    public void suppPara(String txtPara){
        this.livre.suppPara(txtPara);
    }

    /**
     * détermine si le livre est vide.(sans paragraphe).
     * @return true si le livre ne contient pas de paragraphe.sinon, return false.
     */
    public boolean isEmpty(){
        return this.livre.isEmpty();
    }

    /**
     * récupère le numéros du paragraphe avec le texte txtPara.
     * @param txtPara texte du paragraphe.
     * @return le numéros du paragraphe avec le texte txtPara.sinon, return -1.
     */
    public int getNumPara(String txtPara){
        return this.livre.getNumPara(txtPara);
    }

    /**
     * ajoute le choix avec la redirection redirection et comme texte txtChoix au paragraphe numP.
     * @param redirection redirection du choix.
     * @param txtChoix texte du choix.
     * @param numP paragraphe auquelle ajouter le choix.
     */
    public void addChoixPara(int redirection,String txtChoix, int numP){

        this.livre.addChoixPara(new Choix(redirection,txtChoix),numP);
    }

    /**
     * récupère le nombre de choix dans le paragraphe numP.
     * @param numP numéros du paragraphe.
     * @return le nombre de choix dans le paragraphe numP.sinon return 0.
     */
    public int getNbrChoixPara(int numP){
        return this.livre.getNbrChoixPara(numP);
    }

    /**
     * récupère le texte du choix numChoix dans le paragraphe numPara.
     * @param numChoix numéros du paragraphe.
     * @param numPara numéros du paragraphe.
     * @return le texte du choix numChoix dans le paragraphe numPara.sinon,return une chaine vide.
     */
    public String getTextChoixPara(int numChoix, int numPara){
        return this.livre.getTextChoixPara(numChoix,numPara);
    }


    /**
     * récupère la redirection du choix numChoix dans NumPara.
     * @param numChoix numéros du choix.
     * @param numPara numéros du paragraphe.
     * @return la redirection du choix numChoix dans NumPara.sinon, return -1.
     */
    public int getRedirChoixPara(int numChoix, int numPara){
        return this.livre.getRedirChoixPara(numChoix,numPara);
    }

    /**
     * modifie le texte du choix numChoix dans le paragraphe numPara par newValue.
     * @param numPara numéros du paragraphe.
     * @param numChoix numéros du choix.
     * @param newValue nouveaux texte du choix.
     */
    public void modifTextChoixPara(int numPara, int numChoix, String newValue){
        this.livre.modifTextChoixPara(numPara,numChoix,newValue);
    }

    /**
     * modifie la redirection du choix numChoix dans NumPara par newRedir.
     * @param numChoix numéros du choix.
     * @param numPara numéros du paragraphe.
     * @param newRedir nouvelle redirection du choix.
     */
    public void modifRedirChoixPara(int numChoix, int numPara, int newRedir){
        this.livre.modifRedirChoixPara(numChoix,numPara,newRedir);
    }

    /**
     * supprime le choix numChoix du paragraphe numP.
     * @param numChoix numéros du choix.
     * @param numP numéros du paragraphe.
     */
    public void suppChoixPara(int numChoix, int numP){
        this.livre.suppChoixPara(numChoix,numP);
    }

    /**
     * modifie le texte duparagraphe numP par newValue.
     * @param numP numéros du paragraphe.
     * @param newValue nouveaux texte du paragraphe.
     */
    public void modifTextPara(int numP, String newValue){
        this.livre.modifTextPara(numP,newValue);
    }

    /**
     * détermine si le texte textePara appartient a un paragraphe livre.
     * @param textPara texte du paragraphe.
     * @return true si le texte textePara appartient a un paragraphe livre.sinon, return false.
     */
    public boolean textParaExist(String textPara){
        return this.livre.textParaExist(textPara);
    }

    /**
     * récupère le nombre de paragraphe dans le livre.
     * @return le nombre de paragraphe dans le livre.
     */
    public int getNbrPara(){
        return this.livre.getNbrPara();
    }

    /**
     * récupère une liste contenant tout les numéros des paragraphes du livre.
     * @return une liste contenant tout les numéros des paragraphes du livre.
     */
    public List<Integer> getListAllNumPara(){
        return this.livre.getListAllNumPara();
    }

    /**
     * récupère un Set des Noeuds Absents de toute action.
     * @return un Set des Noeuds Absents de toute action.
     */
    public Set<Integer> getNoeudsAbsentsDeTouteAction(){
        return Verification.getNoeudsAbsentsDeTouteAction(livre);
    }

    /**
     * récupère une liste des noeuds Terminaux inaccessible a partir du début du livre.
     * @return une liste des noeuds Terminaux inaccessible a partir du début du livre.
     */
    public List<Integer> getNoeudsTerminauxInaccessiblesAPartirDuDébut(){
        return Verification.getNoeudsTerminauxInaccessiblesAPartirDuDébut(livre);
    }

    /**
     * récupère une liste de liste reprèsentant le graphique du livre.
     * @return une liste de liste reprèsentant le graphique du livre.
     */
    public List<List<Integer>> graph(){
        return Verification.graph(livre);
    }

    /**
     * défini l'image du paragraphe txtPara para newValue.
     * @param txtPara texte du paragraphe.
     * @param newValue nouveaux chemin d'accès a l'image pour se paragraphe.
     */
    public void setImgPara(String txtPara, String newValue) {
        this.livre.setImgPara(txtPara,newValue);
    }

    /**
     * récupère le chemin d'accès a l'image du paragraphe txtPara.
     * @param txtPara texte du paragraphe
     * @return le chemin d'accès a l'image du paragraphe txtPara.sinon, return une chaine vide.
     */
    public String getImgPara(String txtPara) {
        return this.livre.getImgPara(txtPara);
    }

    /**
     *
     * @return Int le nombre de choix dans le livre
     */
    public int getNbrChoix() {

        int nbrChoix = 0;
        Iterator<Paragraph>it = livre.getItPara();
        while(it.hasNext()){
            Paragraph p = it.next();
            nbrChoix += p.getNbrChoix();
        }

        return nbrChoix;
    }
}
