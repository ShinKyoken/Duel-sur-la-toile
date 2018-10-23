package Plateforme.Profil;

import Plateforme.Jeux.Jeu;
import java.util.Comparator;
import java.util.Map;
/**
 * Cette classe implémente l'interface Comparator et permet de comparer les statistique par rapport au ratio du joueur courant
 * @author theodore
 */
public class ComparateurRatio implements Comparator<Jeu>{

    private Map<Jeu, Double> dico;

    public int compare(Jeu j1, Jeu j2){
        return this.dico.get(j1).compareTo(this.dico.get(j2));
    }

    public ComparateurRatio(Map<Jeu, Double> dico){
        this.dico = dico;
    }
}
