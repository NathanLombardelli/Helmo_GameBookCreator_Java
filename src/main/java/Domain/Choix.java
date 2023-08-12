package Domain;

import java.io.Serializable;

/**
 * @author Lombardelli Nathan 2I3. 2020.
 */
public class Choix implements Serializable {


    private int RedirectionP;
    private String choixTxt;

    /**
     * constructeur.
     * @param redirection numéros du paragraphe cible.
     * @param txt texte du choix.
     */
    public Choix(int redirection, String txt) {

        this.RedirectionP = redirection;
        this.choixTxt = txt;

    }

    /**
     * récupère la redirection du choix.
     * @return la redirection du choix.
     */
    public int getRedir() {
        return this.RedirectionP;
    }

    /**
     * récupère le texte du choix.
     * @return le texte du choix.
     */
    public String getTxt() {
        return this.choixTxt;
    }

    /**
     * défini la redirection du choix.
     * @param redir la nouvelle redirection du choix.
     */
    public void setRedir(int redir) {
        this.RedirectionP = redir;
    }

    /**
     * définit le texte du choix.
     * @param txt le nouveaux texte du choix.
     */
    public void setTxt(String txt) {
        this.choixTxt = txt;
    }


}
