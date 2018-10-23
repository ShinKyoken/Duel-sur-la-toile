package Plateforme.Application;

import Plateforme.Connexion.ApplicationLogin;
import Plateforme.Connexion.ApplicationRegister;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ActionBouttonRun implements EventHandler<ActionEvent> {

    private RunDuelSurLaToile app;

    /**
     * contructeur de ActionBouttonRun
     * @param app RunDuelSurLaToile
     */
    public ActionBouttonRun(RunDuelSurLaToile app){
        this.app=app;
    }

    /**
     * méthode qui permettra de switch entre sois la fenêtre Connexion ou la fenêtre Inscription
     * Si tout se passe bien, sinon on crée une alerte ERREUR
     * @param e
     */
    @Override
    public void handle(ActionEvent e) {
        Button b = (Button) e.getSource(); //Recuperation de tout les boutons de l'IHM
        if(b.getText().equals("Connectez-vous !")){ //Si on appuie sur le bouton "connectez vous !" on affiche dans le terminal le nom du bouton
            app.getStage().close();
            try {
                new ApplicationLogin().start(new Stage());
            } catch (ClassNotFoundException | SQLException | IOException e1) {
                Alerte.creationAlerte(e1);
            }

        }
        else if(b.getText().equals("Pas encore de compte ?")) {
            app.getStage().close();
            try {
                new ApplicationRegister().start(new Stage());
            } catch (ClassNotFoundException | SQLException | IOException e1) {
                Alerte.creationAlerte(e1);
            }
        }
    }

}
