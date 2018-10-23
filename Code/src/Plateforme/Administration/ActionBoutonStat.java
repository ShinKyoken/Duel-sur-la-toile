package Plateforme.Administration;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.io.IOException;
import java.sql.SQLException;

import Plateforme.Jeux.Jeu;

public class ActionBoutonStat implements EventHandler<ActionEvent> {
    private AdministrateurStat admin;


    /**
     * Constructeur
     *
     * @param admin
     */
    public ActionBoutonStat(AdministrateurStat admin) {

        this.admin = admin;
    }

    @Override
    /**
     * Méthode permettant de gérer les différents évènements.
     */
    public void handle(ActionEvent a) {
        Button b = (Button) a.getSource();
        if (b.getUserData().equals("user")) {
            try {
                this.admin.getApp().afficherAdministrateur();
                System.out.println("ça passe dans la boucle");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (b.getUserData().equals("game")) {
            try {
                this.admin.getApp().afficherAdministrateurJeu();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        if (b.getUserData().equals("ButtonSearch")) {
            try {
                if (this.admin.getCo().rechercherJeux(this.admin.getJeu()).size() == 1) {
                    String i = String.valueOf(this.admin.getCo().getNbPartie(this.admin.getCo().rechercherJeux(this.admin.getJeu()).get(0)));
                    this.admin.setStr(i);
                    this.admin.maj();

                }
                if (this.admin.getCo().rechercherJeux(this.admin.getJeu()).size() > 1) {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle("Attention !!!");
                    al.setHeaderText("Erreur de saisie");
                    al.setContentText("Il y a plusieurs jeu commençant par cette chaine de caractères.\n" +
                            "Veuillez affiner votre recherche");
                    al.showAndWait();
                }
                if (this.admin.getCo().rechercherJeux(this.admin.getJeu()).size() == 0) {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle("Attention !!!");
                    al.setHeaderText("Erreur de saisie");
                    al.setContentText("Il n'y a aucun jeu commençant par cette chaine de caractères.");
                    al.showAndWait();
                }
                if (b.getUserData().equals("reset1")) {
                    this.admin.setStr(String.valueOf(this.admin.getCo().getNbPartie(null)));
                    this.admin.maj();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        if (b.getUserData().equals("reset1")) {
            try {
                this.admin.setStr(String.valueOf(this.admin.getCo().getNbPartie(null)));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                this.admin.maj();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

