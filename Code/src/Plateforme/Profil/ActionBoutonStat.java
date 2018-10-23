package Plateforme.Profil;

import Plateforme.Application.DuelSurLaToile;
import Plateforme.Application.Utilisateur;
import Plateforme.Jeux.Jeu;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.sql.SQLException;
import java.util.*;
/**
 * Cette classe implémente EventHandler et contient la fonction handle associée aux boutons servant à la consultation
 * de ses statistique par l'utilisateur courant.
 * @author theodore
 */
public class ActionBoutonStat implements EventHandler<ActionEvent> {
    /**
     * le profil auquel est associé l'ActionBoutonProfil
     */    
    private StatistiqueProfil profil;

    public ActionBoutonStat(StatistiqueProfil profil){
        this.profil = profil;
    }
    /**
     * Cette fonction est appelée à chaque fois qu'un boutons associé aux statistique de l'utilisateur courant est utilisé
     * Si c'est le boutons "search" elle filtrra la liste de statistique pour n'afficher que celle associée aux jeux dont 
     * le nom contient la chaine de caractère entrée dans le TextField.
     * Si c'est le bouton "A-Z", la fonction trie la liste de statistique dans l'ordre alphanumérique.
     * Si c'est le bouton "%", la fonction trie la liste de statistique en fonction du ratio de victoire.
     * @param a ActionEvent
     */
    @Override
    public void handle(ActionEvent a){
        Button b = (Button) a.getSource();

        if(b.getUserData().equals("search")) {
            try {
                List<Jeu> v = this.profil.getPlateforme().getConnexion().rechercherJeux(this.profil.getRechercher().getText());
                Collections.sort(v);
                ListeStat nLs = new ListeStat(this.profil.getPlateforme(), v, this.profil.getListeStat().getListePartie());
                this.profil.setListeStat(nLs);
                this.profil.majStat();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if(b.getUserData().equals("az")) {
            List<Jeu> v;
            try {
                v = this.profil.getApp().getConnexion().getJeux();
                Collections.sort(v);
                ListeStat nLs = new ListeStat(this.profil.getPlateforme(), v, this.profil.getListeStat().getListePartie());
                this.profil.setListeStat(nLs);
                this.profil.majStat();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        else{
            List<Jeu> dico;
            try {
                dico = (ArrayList) this.profil.getListeStat().getListeRatio().keySet();
                Collections.sort(dico, new ComparateurRatio(this.profil.getListeStat().getListeRatio()));
                ListeStat nLs = new ListeStat(this.profil.getPlateforme(), dico, this.profil.getListeStat().getListePartie());
                this.profil.setListeStat(nLs);
                this.profil.majStat();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
