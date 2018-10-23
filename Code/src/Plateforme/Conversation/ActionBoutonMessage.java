package Plateforme.Conversation;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ActionBoutonMessage implements EventHandler<ActionEvent>{

    private Liste_De_Messages app;

    ActionBoutonMessage(Liste_De_Messages app){
        this.app = app;
    }

    @Override
    /**
     * Méthode gérant les évènement de la page de conversation
     */
    public void handle(ActionEvent a) {
        Button b = (Button) a.getSource();

        if(b.getUserData().equals("send")) {
            try {
                app.getConv().getConnexion().envoyerMessage(app.getMess().getText(), app.getConv().getPlateforme().getUtilisateur().getId(), app.getDest().getId());
                app.getMess().clear();
                app.getConv().maj();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(b.getUserData().equals("refresh")) {
            try {
                app.getConv().getConnexion().recupMessage(app.getConv().getPlateforme().getUtilisateur().getPseudoUt(), app.getDest().getPseudoUt());
                app.getConv().maj();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }

}
