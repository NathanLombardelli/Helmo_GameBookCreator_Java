package Domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lombardelli Nathan 2I3. 2020.
 */
public class Paragraph implements Comparable<Paragraph>, Serializable {

    private int numP;
    private String Text;
    private List<Choix> choix;
    private List<Integer> numParaPrec;
    private String illustration;

    /**
     * Constructeur.
     * @param numPara numéros du paragraphe.
     * @param txt texte du paragraphe.
     */
    public Paragraph(int numPara, String txt) { //constructeur quand on ne connait ni les num des para précédents ni les choix.

        this.numP = numPara;
        this.Text = txt;
        this.choix = new ArrayList<>();
        this.numParaPrec = new ArrayList<>();
        this.illustration = "";
    }

    /**
     * Constructeur.
     * @param numPara numéros du paragraphe.
     * @param txt texte du paragraphe.
     * @param choix liste des différents choix du paragraphe.
     */
    public Paragraph(int numPara, String txt, List<Choix> choix) { //constructeur quand on ne connait pas les num des para précédents

        this.numP = numPara;
        this.Text = txt;
        this.choix = choix;
        this.numParaPrec = new ArrayList<>();
        this.illustration = "";
    }

    /**
     * Constructeur.
     * @param numPara numéros du paragraphe.
     * @param txt texte du paragraphe.
     * @param choix liste des différents choix du paragraphe.
     * @param numParaPrec liste des paragraphes précédent cellui-ci.
     * @param imagePath chemin d'accès al'image de se paragraphe.
     */
    public Paragraph(int numPara, String txt, List<Choix> choix, List<Integer> numParaPrec, String imagePath) { //constructeur quand on connait les num des para précédent.Ex modif d'un para.

        this.numP = numPara;
        this.Text = txt;
        this.choix = choix;
        this.numParaPrec = numParaPrec;
        this.illustration = imagePath;
    }

    /**
     * définit l'illustrationde se livre.
     * @param path chemin d'accès a l'illustrationde se livre.
     */
    public void setIllustration(String path){
        this.illustration = path;
    }

    /**
     * récupère le chemin d`accès a l'image du paragraphe.
     * @return le chemin d`accès a l'image du paragraphe.
     */
    public String getIllustration(){
        return this.illustration;
    }

    /**
     * récupère le numéros de se paragraphe.
     * @return le numéros de se paragraphe.
     */
    public int getNum() {
        return this.numP;
    }

    /**
     * récupère le texte de se paragraphe.
     * @return le texte de se paragraphe.
     */
    public String getTxt() {
        return this.Text;
    }

    /**
     * récupère la liste des choix de se paragraphe.
     * @return la liste des choix de se paragraphe.
     */
    public List<Choix> getChoix() {
        return this.choix;
    }

    /**
     * récupère la liste des paragraphes précédent a se paragraphe.
     * @return la liste des paragraphes précédent a se paragraphe.
     */
    public List<Integer> getNumParaPrec(){
        return this.numParaPrec;
    }

    /**
     * définit le texte du paragraphe par text.
     * @param text nouveaux texte de se paragraphe.
     */
    public void setText(String text) {
        this.Text = text;
    }

    /**
     * ajoute numPara a la liste des paragraphes précédent de se paragraphe.
     * @param numPara numéros du paragraphe a ajouter aux précédent de se paragraphe.
     */
    public void addParaPrec(int numPara) {
        this.numParaPrec.add(numPara);
    }

    /**
     * supprime le choix a la position posChoix de se paragraphe.
     * @param posChoix positiondu choix.
     */
    public void suppChoix(int posChoix) {
        choix.remove(posChoix);
    }

    /**
     * modifie le texte du choix a la position posChoix par txt.
     * @param posChoix position du choix.
     * @param txt nouveaux texte du choix.
     */
    public void modifTxtChoix(int posChoix, String txt) {
        this.choix.get(posChoix).setTxt(txt);
    }

    /**
     * modifie la redirection du choix a la position posC par newRedir.
     * @param posC positiondu choix.
     * @param newRedir nouvelle redirection.
     */
    public void modifRedirChoix(int posC, int newRedir) {
        this.choix.get(posC).setRedir(newRedir);
    }

    /**
     * ajpute le choix newChoix au paragraphe.
     * @param newChoix le choix a ajouter.
     */
    public void addChoix(Choix newChoix) {

        if (this.choix == null) {
            List<Choix> listChoix = new ArrayList<Choix>();
            this.choix = listChoix;
        }
        this.choix.add(newChoix);
    }

    /**
     * récupère le nombre de choix dans se chapitre.
     * @return le nombre de choix dans se chapitre.
     */
    public int getNbrChoix(){
        if(this.choix != null && !this.choix.isEmpty() ){
            return this.choix.size();
        }
        return 0;
    }

    @Override
    public int compareTo(Paragraph p) {
        return (this.numP - p.getNum());
    }//trier les paragraphs par leur numéros.

    /**
     * récupère le texte du choix numChoix.
     * @param numChoix numéros du choix.
     * @return le texte du choix numChoix.
     */
    public String getTxtChoix(int numChoix) {
        return this.choix.get(numChoix-1).getTxt();

    }

    /**
     * récupère la redirection du choix numChoix.
     * @param numChoix numéros du choix.
     * @return la redirection du choix numChoix.
     */
    public int getRedirChoix(int numChoix) {
        return this.choix.get(numChoix-1).getRedir();
    }
}
