package Verification;


import Domain.Book;
import Domain.Choix;
import Domain.Paragraph;

import java.util.*;

/**
 * Lombardelli Nathan 2I3. 2020.
 */
public class Verification {


    /**
     * récupère un Set des noeuds absent de toute action.
     * @param livre livre.
     * @return un Set des noeuds absent de toute action.
     * J'ai choisis d'utiliser l'interface Set et la classe HashSet car, la CTT de la méthode add est de O(1)(seul méthode utilise) et pour éviter les doublons.
     * La CTT de cette méthode est de O(n+n) n = au nombre de paragraphes.
     * En effet, je parcourt une 1ére fois les paragraphes pour actualiser les paragraphes précédents de chaque paragraphe, puis je reparcourt les paragraphes et ajoute au Set les paragraphes ne possaidant pas de paragraphe précédent.
     */
    public static Set<Integer> getNoeudsAbsentsDeTouteAction(Book livre){ //set,Hashset car add = O(1) et n'authorise pas 2 fois le meme nombre.

        //remplir paraPrec.
        if(!livre.isEmpty()) {
        Iterator<Paragraph> itP = livre.getItPara();
            do {
                Paragraph p = itP.next();
                if(!p.getChoix().isEmpty()) {
                    Iterator<Choix> itC = p.getChoix().iterator();

                    do {
                        Choix c = itC.next();
                        livre.addParaPrecPara(c.getRedir(), p.getNum());
                    } while (itC.hasNext());
                }
            } while (itP.hasNext());


            //calcul


            Set<Integer> noeudsAbsentsDeTouteAction = new HashSet<>();

            Iterator<Paragraph> itP2 = livre.getItPara();
            do {
                Paragraph p = itP2.next();

                if (p.getNumParaPrec() == null || p.getNumParaPrec().isEmpty()) {
                    if (p.getNum() != 1) {  // on ne prend pas en compte le 1er.
                        noeudsAbsentsDeTouteAction.add(p.getNum());
                    }
                }
            } while (itP2.hasNext());

            return noeudsAbsentsDeTouteAction;
        }else{
            return new HashSet<>();
        }

    }


    /**
     * récupère la liste des noeuds terminaux inaccessibles a partir du début.
     * @param livre livre.
     * @return la liste des noeuds terminaux inaccessibles a partir du début.
     * J'ai choisi d'utiliser une List pour que les noeuds restent ordoner.
     * La CTT de la méthode est O(P+P+C+P) ou P = aux paragraphes et C aux choix. en effet, j'utilise d'abord la méthode getListAllNumPara qui parcourt tout les paragraphes.
     * puis,j'utilise la méthode getParagraphs qui parcourt tt les paragraphes et renvoie celuis avce le numéros mentioner en paramètre.
     * puis j'utilise une méthode récursif qui parcourt tout les choix.
     * et finalement je reparcourt ma liste paragraphs.(qui pourrait contenir tout les paragraphes).
     */
    public static List<Integer> getNoeudsTerminauxInaccessiblesAPartirDuDébut(Book livre){
       if(!livre.isEmpty()) {
           Set<Integer> NoeudsAccessiblesAPartirDuDébut = new HashSet<>(); //set pour ne pas avoir de doublon.
           List<Integer> paragraphs = livre.getListAllNumPara(); // tout les paragraphs du livre


           Paragraph firstPara = livre.getParagraphs(1); //défini le 1er paragraphe.

           methodeRecursif(firstPara, NoeudsAccessiblesAPartirDuDébut, livre);//ajouter a NoeudsAccessiblesAPartirDuDébut tout les paragraphs disponible a partir du para 1.


           paragraphs.removeAll(NoeudsAccessiblesAPartirDuDébut);

           ///////////supprimer tout les noeud non-teminaux dans paragraphs.
           if (!paragraphs.isEmpty()) {
               Iterator<Integer> itP = paragraphs.iterator();

               do {
                   int p = itP.next();
                   if (livre.getParagraphs(p).getChoix() != null && !livre.getParagraphs(p).getChoix().isEmpty()) {
                       itP.remove(); //supprimer tout les numéros de paragraphs qui ne sont pas terminaux.
                   }

               } while (itP.hasNext());
           }/////////////////////////////////////////////
           return paragraphs;
       }else{
           return new ArrayList<>();
       }
    }

    private static void methodeRecursif(Paragraph p, Set<Integer> NoeudsAccessiblesAPartirDuDébut, Book livre) {

        if (!p.getChoix().isEmpty()) {
            Iterator<Choix> itC = p.getChoix().iterator(); //parcourt les choix du paragraph
            do {
                Choix c = itC.next();
                if(livre.numParaExist(c.getRedir())) {//erreur si un choix redirige vers un para inexistant.
                    NoeudsAccessiblesAPartirDuDébut.add(c.getRedir()); //ajoute chaque redirection au noeud accesible a partir du début
                    methodeRecursif(livre.getParagraphs(c.getRedir()), NoeudsAccessiblesAPartirDuDébut, livre);                            ///////////faire une condition si invalide\\\\\\\\
                }
            } while (itC.hasNext());
        }
    }


    /**
     * génere le graph du livre.
     * @param livre livre.
     * @return le graph du livre.
     * j'ai choisis d'utiliser l'interface List pour garder les éléments ordonnés.
     * La CTT de cette méthode est O(P.C.P) ou P = au paragraphes et C le nombre de choix.
     * car je parcourt mes paragraphes et dans cette boucle j'appel une méthode récursif qui boucle sur les choix, dans cette méthode récursif j'appel aussi la méthode getParagraphs qui boucles les paragraphes.
     */
    public static List<List<Integer>> graph(Book livre){

        List<List<Integer>> graph = new ArrayList<>(); //graphique.
        List<Integer> line = new ArrayList<>(); //1ère ligne du graph.

        if(!livre.isEmpty()){

            Iterator<Paragraph> itP = livre.getItPara();
            do{
                Paragraph p = itP.next();

                recursif(graph,line,p,livre);

                line = new ArrayList<>();

            }while(itP.hasNext());
        }
            return graph;
    }

    private static boolean recursif(List<List<Integer>> graph,List<Integer> line,Paragraph p,Book livre){

        if(line.contains(p.getNum())){ // gestion boucles.
            return true;
        }

        line.add(p.getNum()); //ajout du numéros du paragraph courant dans la ligne.

        if(p.getNbrChoix() != 0 ){

            for(int i = 1; i <= p.getNbrChoix() ;i++){

                List<Integer> newLine = new ArrayList<>(); // pour chaque choix:
                newLine.addAll(line);                      // créé une liste avec comme base line.
                Paragraph redir = livre.getParagraphs(p.getRedirChoix(i)); // utiliser le paragraph de redirection dans l'appel suivant.

                recursif(graph, newLine, redir, livre); // appel récursif.
            }

        }else{
            graph.add(line); // si pas de choix (para terminaux) ajout de la ligne au graph.
        }

        return false;

    }




}
