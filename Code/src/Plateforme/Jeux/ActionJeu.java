package Plateforme.Jeux;

import Plateforme.Jeux.Jeu;
import Plateforme.Jeux.PageDesJeux;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * Cette classe implémente EventHandler et gère les actions déclenchées par les clics de la souris dans l'IHM Jeu.
 * @author theodore
 */
public class ActionJeu implements EventHandler<MouseEvent>{
    /**
     * La page auquel cette classe est associé
     */
    private PageDesJeux app;

    public ActionJeu(PageDesJeux app) {
        this.app = app;
    }
 
    /**
     * Cette focntion est appelée lors d'un clic sur un des jeu présenté sur la page
     * Elle modifie le jeu sélectionner pour donner plus d'information sur le jeu sélectionner
     * et permettre de lançer une partie
     * @param a 
     */
    @Override
    public void handle(MouseEvent a) {
        VBox vb = (VBox) a.getSource();
        app.setJeuxSelectionner((Jeu) vb.getUserData());
        app.majInfo();
    }
}
