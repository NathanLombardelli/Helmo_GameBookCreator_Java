package Domain;

import java.io.Serializable;
import java.util.*;

/**
 * @author Lombardelli Nathan 2I3. 2020.
 */
public class Book implements Serializable {

    private String Title;
    private List<Paragraph> paragraphs;

    /**
     * Constructeur.
     * @param Titre titre du livre.
     * @param para list des différents paragraphes du livre.
     */
    public Book(String Titre, List<Paragraph> para) {
        this.Title = Titre;
        this.paragraphs = para;
    }

    /**
     * retourne le titre du livre.
     * @return le titre du livre.
     */
    public String getTitle() {
        return this.Title;
    }

    /**
     * Change le titre dulivre par la valeur de Titre.
     * @param Titre nouveau titre du livre.
     */
    public void setTitle(String Titre) {
        this.Title = Titre;
    }

    /**
     *Ajoute le paragraphe paragraph au livre.
     * @param paragraph paragraph à ajouter.
     */
    public void addParagraph(Paragraph paragraph) {
        paragraphs.add(paragraph);
    }

    /**
     * Permet de savoir si numPara correspond a un numéros de paragraphe du livre.
     * @param numPara numéros a vérifier.
     * @return true si le numéros du paragraphe existe déjà dans le livre,sinon return false.
     */
    public boolean numParaExist(int numPara) {

        if (!isEmpty()) {
            Iterator<Paragraph> it = paragraphs.iterator();
            do {
                Paragraph p = it.next();
                if (p.getNum() == numPara) {
                    return true;
                }

            } while (it.hasNext());
            return false;
        } else {
            return false;
        }
    }

    /**
     * récupère la liste des paragraphes du livre.
     * @return la liste des paragraphes du livre.
     */
    public List<Paragraph> getParagraphs() {
        return this.paragraphs;
    }

    /**
     * récupère le paragraph numP.
     * @param numP numéros du paragraph rechercher.
     * @return le paragraph si le numéros correspond a un paragraph du livre.sinon return null.
     */
    public Paragraph getParagraphs(int numP) {  //numéros du para pas toujour égale a sa position dans la liste
        Iterator<Paragraph> it = this.paragraphs.iterator();
        do{
            Paragraph p = it.next();
            if(p.getNum() == numP){
                return p;
            }
        }while(it.hasNext());
        return null;
    }

    /**
     * récupère une list avec le numéros de tout les paragraphs présent dans le livre.
     * @return une list avec le numéros de tout les paragraphs présent dans le livre.
     */
    public List<Integer> getListAllNumPara(){

        List<Integer> ListAllNumPara = new ArrayList<>();
        Iterator<Paragraph> it = this.paragraphs.iterator();
        do{
            Paragraph p = it.next();
            ListAllNumPara.add(p.getNum());

        }while(it.hasNext());

        return ListAllNumPara;
    }

    /**
     * récupère le text du paragraph numéros num.
     * @param num numéros du paragraph.
     * @return le text du paragraph numéros num.si num n'existe pas dans le livre, return une chaine vide.
     */
    public String getTextPara(int num) {

        if(this.paragraphs != null && !this.paragraphs.isEmpty()) {
            Iterator<Paragraph> it = this.paragraphs.iterator();
            do {
                Paragraph p = it.next();
                if(p.getNum() == num){
                    return p.getTxt();
                }
            } while (it.hasNext());
        }
        return "";
    }

    /**
     * supprime un paragraphe du livre sur base de son text.
     * @param txtPara texte du paragraphe que l'on veux supprimer.
     */
    public void suppPara(String txtPara) {
        if(this.paragraphs != null && !this.paragraphs.isEmpty()) {
            Iterator<Paragraph> itP = this.paragraphs.iterator();
            do {
                Paragraph p = itP.next();

                if (p.getTxt().equals(txtPara)) {
                    itP.remove();//oblige de remove iterator sinon conflit
                }

            } while (itP.hasNext());
        }
    }

    /**
     * vérifie si le livre est vide.(ne possaide pas de paragraphe).
     * @return true si le livre ne contient pas de paragraphe.
     */
    public boolean isEmpty(){
        if(this.paragraphs == null){
            return true;
        }else {
            return this.paragraphs.isEmpty();
        }
    }

    /**
     * récupère le numeros du paragraphe sur base de son texte.
     * @param txtPara texte du paragraphe.
     * @return le numeros du paragraphe sur base de son texte.si aucun paragraphe n'a se texte, return -1.
     */
    public int getNumPara(String txtPara) {
        if(this.paragraphs != null && !this.paragraphs.isEmpty()) {
            Iterator<Paragraph> it = this.paragraphs.iterator();
            do {
                Paragraph p = it.next();
                if(p.getTxt().equals(txtPara)){
                    return p.getNum();
                }

            } while (it.hasNext());
        }
        return -1;

    }

    /**
     * ajoute le choix newChoix au paragraph numP.
     * @param newChoix le nouveau choix a ajouter.
     * @param numP le numéros du paragraph auquel ajouter newChoix.
     */
    public void addChoixPara(Choix newChoix, int numP) {
        if(this.paragraphs != null && !this.paragraphs.isEmpty()) {
            Iterator<Paragraph> it = this.paragraphs.iterator();
            do {
                Paragraph p = it.next();
                if(p.getNum() == numP){
                    p.addChoix(newChoix);
                }
            } while (it.hasNext());
        }
    }

    /**
     * récupère le nombre dechoix dand le paragraph numP.
     * @param numP numéros du paragraph.
     * @return le nombre dechoix dand le paragraph numP.si numP n'existe pas,return 0.
     */
    public int getNbrChoixPara(int numP) {
        if(this.paragraphs != null && !this.paragraphs.isEmpty()) {
            Iterator<Paragraph> it = this.paragraphs.iterator();
            do {
                Paragraph p = it.next();
                if(p.getNum() == numP){
                    return p.getNbrChoix();
                }
            } while (it.hasNext());
        }
        return 0;
    }

    /**
     * récupère le texte du choix numéros numChoix dans le paragraph numPara.
     * @param numChoix numéros du choix.
     * @param numPara numéros du paragraphe
     * @return le texte du choix numéros numChoix dans le paragraph numPara.sinon return une chaine vide.
     */
    public String getTextChoixPara(int numChoix, int numPara) {
        if(this.paragraphs != null && !this.paragraphs.isEmpty()) {
            Iterator<Paragraph> it = this.paragraphs.iterator();
            do {
                Paragraph p = it.next();
                if(p.getNum() == numPara){
                    return p.getTxtChoix(numChoix);
                }
            } while (it.hasNext());
        }
        return "";

    }

    /**
     * récupère le numéros du paragraphe de redirectionduchoix numéros numChoix dans le paragraphe numéros numPara.
     * @param numChoix numéros du choix.
     * @param numPara numéros du paragraphe.
     * @return le numéros du paragraphe de redirectionduchoix numéros numChoix dans le paragraphe numéros numPara.sinon return -1.
     */
    public int getRedirChoixPara(int numChoix, int numPara) {
        if(this.paragraphs != null && !this.paragraphs.isEmpty()) {
            Iterator<Paragraph> it = this.paragraphs.iterator();
            do {
                Paragraph p = it.next();
                if(p.getNum() == numPara){
                    return p.getRedirChoix(numChoix);
                }
            } while (it.hasNext());
        }
        return -1;

    }

    /**
     * modifie le texte du choix numChoix dans le paragraphe numPara par newValue.
     * @param numPara numéros du paagraphe.
     * @param numChoix numéros du choix.
     * @param newValue nouveaux texte du choix numChoix.
     */
    public void modifTextChoixPara(int numPara, int numChoix, String newValue) {
            if(this.paragraphs != null && !this.paragraphs.isEmpty()) {
                Iterator<Paragraph> it = this.paragraphs.iterator();
                do {
                    Paragraph p = it.next();
                    if(p.getNum() == numPara){
                        p.modifTxtChoix(numChoix-1,newValue);
                    }
                } while (it.hasNext());
            }


    }

    /**
     * modifie la redirection du choix numChoix dans le paragraphe numPara par newRedir.
     * @param numChoix numéros du choix.
     * @param numPara numéros du paagraphe.
     * @param newRedir nouveaux numéros du paragraphe cible redirection.
     */
    public void modifRedirChoixPara(int numChoix, int numPara, int newRedir) {
        if(this.paragraphs != null && !this.paragraphs.isEmpty()) {
            Iterator<Paragraph> it = this.paragraphs.iterator();
            do {
                Paragraph p = it.next();
                if(p.getNum() == numPara){
                    p.modifRedirChoix(numChoix-1,newRedir);
                }
            } while (it.hasNext());
        }


    }

    /**
     * supprime le choix numChoix du paragraphe numP.
     * @param numChoix numéros du choix.
     * @param numP numéros du paragraphe.
     */
    public void suppChoixPara(int numChoix, int numP) {
        if(this.paragraphs != null && !this.paragraphs.isEmpty()) {
            Iterator<Paragraph> it = this.paragraphs.iterator();
            do {
                Paragraph p = it.next();
                if(p.getNum() == numP){
                    p.suppChoix(numChoix-1);
                }
            } while (it.hasNext());
        }
    }

    /**
     * modifie le texte du paragraphe numP par newValue.
     * @param numP numéros du paragraphe.
     * @param newValue nouveaux texte du paragraphe.
     */
    public void modifTextPara(int numP, String newValue) {
        if(this.paragraphs != null && !this.paragraphs.isEmpty()) {
            Iterator<Paragraph> it = this.paragraphs.iterator();
            do {
                Paragraph p = it.next();
                if(p.getNum() == numP){
                    p.setText(newValue);
                }
            } while (it.hasNext());
        }


    }

    /**
     * permet de savoir si textPara existe comme texte de paragraphe dans le livre.
     * @param textPara texte du paragraphe.
     * @return true si textPara existe comme texte de paragraphe dans le livre.sinon,return false.
     */
    public boolean textParaExist(String textPara) {
        if(this.paragraphs != null && !this.paragraphs.isEmpty()) {
            Iterator<Paragraph> it = this.paragraphs.iterator();
            do {
                Paragraph p = it.next();
                if(p.getTxt().equals(textPara)){
                    return true;
                }
            } while (it.hasNext());
        }
        return false;
    }

    /**
     * ajoute le paragraphe précédent paraPrec au paragraphe numPara.
     * @param numPara numéros du paragraphe.
     * @param paraPrec numéros du paragraphe précédent.
     */
    public void addParaPrecPara(int numPara, int paraPrec) {
        if(this.paragraphs != null && !this.paragraphs.isEmpty()) {
            Iterator<Paragraph> it = this.paragraphs.iterator();
            do {
                Paragraph p = it.next();
                if(p.getNum() == numPara){
                    p.addParaPrec(paraPrec);
                }
            } while (it.hasNext());
        }
    }

    /**
     * récupère l'itérateur des paragraphes du livre.
     * @return l'itérateur des paragraphes du livre.
     */
    public Iterator<Paragraph> getItPara() {
        return paragraphs.iterator();
    }

    /**
     * récupère le nombre de paragraphe dans le livre.
     * @return le nombre de paragraphe dans le livre.
     */
    public int getNbrPara() {
        return paragraphs.size();
    }

    /**
     * définit le chemin de l'image du texte txtPara par newValue.
     * @param txtPara texte du paragraphe.
     * @param newValue chemin de l'image.
     */
    public void setImgPara(String txtPara, String newValue) {

        if(this.paragraphs != null && !this.paragraphs.isEmpty()) {
            Iterator<Paragraph> it = this.paragraphs.iterator();
            do {
                Paragraph p = it.next();
                if(p.getTxt().equals(txtPara)){
                    p.setIllustration(newValue);
                }
            } while (it.hasNext());
        }

    }

    /**
     * récupère le chemin de l'image du paragraphe txtPara.
     * @param txtPara texte du paragraphe.
     * @return le chemin de l'image du paragraphe txtPara.sinon,returne une chaine vide.
     */
    public String getImgPara(String txtPara) {

        if(this.paragraphs != null && !this.paragraphs.isEmpty()) {
            Iterator<Paragraph> it = this.paragraphs.iterator();
            do {
                Paragraph p = it.next();
                if(p.getTxt().equals(txtPara)){
                    return p.getIllustration();
                }
            } while (it.hasNext());
        }
        return "";
    }
}
