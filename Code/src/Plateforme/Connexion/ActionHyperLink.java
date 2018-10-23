package Plateforme.Connexion;


import Plateforme.Connexion.MotDePasseOublie.ApplicationMotDePasseOublie;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class ActionHyperLink implements EventHandler<ActionEvent>{

    private ApplicationLogin app;

    public ActionHyperLink(ApplicationLogin app) {
        this.app = app;
    }

    @Override
    /**
     * Méthode permettant de gérer l'évènement lorsque l'utilisateur clique sur l'hyperlien.
     */
    public void handle(ActionEvent a) {

        ApplicationMotDePasseOublie oublie = new ApplicationMotDePasseOublie();
        try {
            app.getStage().close();
            oublie.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

