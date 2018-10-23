package Plateforme.Application;

import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ActionBouttonMenu implements EventHandler<ActionEvent>{

    private DuelSurLaToile app;

    /**
     * constructeur de ActionBouttonMenu
     * @param app DuelSurLaToile
     */
    ActionBouttonMenu(DuelSurLaToile app){
        this.app = app;
    }

    /**
     *Gere les actions du menu
     * @param a
     */
    @Override
    public void handle(ActionEvent a) {
        Button b = (Button) a.getSource();

        if(b.getUserData().equals("jeux")) {
            try {
                app.afficherPageDesJeux();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if(b.getUserData().equals("conv")) {
            try {
                app.afficherConversation();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if(b.getUserData().equals("partie")) {
            try {
                app.afficherPartie();
                this.app.majIHM();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if(b.getUserData().equals("admin")) {
            try {
                app.afficherAdministrateur();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
