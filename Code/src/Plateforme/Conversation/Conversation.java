package Plateforme.Conversation;
import java.sql.SQLException;

import Plateforme.Application.DuelSurLaToile;
import Plateforme.Application.DuelSurLaToile;
import Plateforme.Connexion.ConnexionMySQL;
import Plateforme.Application.Utilisateur;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;


public class Conversation extends BorderPane{

    private DuelSurLaToile app;

    private Utilisateur destinataire;

    /**
     * Méthode permettant de générer l'affichage de l'onglet de conversation
     * @param app l'application
     * @throws SQLException
     */
    public Conversation(DuelSurLaToile app) throws SQLException {

        this.app = app;

        if (!this.app.getConnexion().getAmis(app.getUtilisateur().getPseudoUt()).isEmpty()) {
            destinataire = this.app.getConnexion().getAmis(app.getUtilisateur().getPseudoUt()).get(0);
        }


        BorderPane bp = new BorderPane();
        Liste_De_Contacts ls = new Liste_De_Contacts(this, this.getConnexion().getAmis(app.getUtilisateur().getPseudoUt()));
        Liste_De_Messages lsmail = new Liste_De_Messages(this, destinataire);

        ls.setMinWidth(200);
        ls.setPrefHeight(app.getScene().getHeight()-10);
        lsmail.setPrefHeight(app.getScene().getHeight()-10);

        BorderPane.setMargin(lsmail, new Insets(0,5,0,5));
        BorderPane.setMargin(bp, new Insets(0,5,0,5));

        bp.setCenter(lsmail);
        bp.setLeft(ls);

        this.setCenter(bp);
        this.setPadding(new Insets(20,10,10,10));
    }

    /**
     * Retourne la connexion
     * @return la connexion
     */
    public ConnexionMySQL getConnexion() {
        /**
         * Méthode ne prenant pas de paramètre et qui retourne la Connexion.
         */
        return app.getConnexion();
    }

    /**
     * Retourne la plateforme
     * @return la plateforme
     */
    public DuelSurLaToile getPlateforme() {
        return this.app;
    }

    /**
     * Modifie le destinataire
     * @param val le destinataire
     */
    public void setDest(Utilisateur val) {

        this.destinataire = val;
    }

    /**
     * Méthode qui met à jour l'affichage de l'onglet conversation
     * @throws SQLException
     */
    public void maj() throws SQLException {

        BorderPane bp = new BorderPane();
        Liste_De_Contacts ls = new Liste_De_Contacts(this, this.getConnexion().getAmis(app.getUtilisateur().getPseudoUt()));
        Liste_De_Messages lsmail = new Liste_De_Messages(this, destinataire);

        ls.setMinWidth(200);
        ls.setPrefHeight(app.getScene().getHeight()-10);
        lsmail.setPrefHeight(app.getScene().getHeight()-10);

        BorderPane.setMargin(lsmail, new Insets(0,5,0,5));
        BorderPane.setMargin(bp, new Insets(0,5,0,5));

        bp.setCenter(lsmail);
        bp.setLeft(ls);

        this.setCenter(bp);
        this.setPadding(new Insets(20,10,10,10));
    }
}
