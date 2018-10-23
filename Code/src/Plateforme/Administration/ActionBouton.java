package Plateforme.Administration;

import Plateforme.Connexion.MotDePasseOublie.ApplicationMotDePasseOublie;
import Plateforme.Connexion.MotDePasseOublie.GenerationCodeProvisoire;
import Plateforme.Connexion.MotDePasseOublie.Mail;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class ActionBouton implements EventHandler<ActionEvent> {
    /**
     * Classe permettant de définir les évênements qui surviennent lorsque l'on clique sur les boutons présents sur la page Administrateur qui gère les joueurs
     * Classe permettant de définir les évênements qui surviennent lorsque l'on clique sur les boutons présents sur la page Administrateur qui gère les joueurs
     */
    private Administrateur admin;
    /**
     *
     */

    public ActionBouton(Administrateur admin) {

        this.admin = admin;
    }

    @Override
    public void handle(ActionEvent a) {
        /**
         * Méthode prenant comme parametre un ActionEvent et qui permet de définir les évênements
         */
        Button b=(Button)a.getSource();
        if (b.getUserData().equals("game")) {
            try {
                this.admin.getApp().afficherAdministrateurJeu();
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (b.getUserData().equals("stats")){
            try {
                this.admin.getApp().afficherAdministrateurStat();
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }

        }
        if(b.getUserData().equals("change")){

        }
        if(b.getUserData().equals("modif")){

        }
        if (b.getUserData().equals("ajout")){

        }
    }
}
