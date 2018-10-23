package Plateforme.Conversation;


import java.sql.SQLException;

import Plateforme.Application.Utilisateur;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class ActionContact implements EventHandler <MouseEvent>{

    private Conversation app;

    public ActionContact(Conversation app) {
        this.app = app;
    }

    /**
     * Méthode gérant les évènement lorsque l'on clique sur un contact
     * @param a un MouseEvent
     */
    @Override
    public void handle(MouseEvent a) {

        HBox h = (HBox) a.getSource();
        app.setDest((Utilisateur) h.getUserData());
        try {
            app.maj();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
