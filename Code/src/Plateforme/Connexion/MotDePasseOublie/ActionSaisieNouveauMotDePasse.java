package Plateforme.Connexion.MotDePasseOublie;

import Plateforme.Application.RunDuelSurLaToile;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.sql.SQLException;

public class ActionSaisieNouveauMotDePasse implements EventHandler<ActionEvent>{

    private ApplicationMotDePasseOublie app;

    public ActionSaisieNouveauMotDePasse(ApplicationMotDePasseOublie app) {
        this.app = app;
    }

    @Override
    /**
     * Méthode gérant les évènements lorque l'on saisie le nouveau mot de passe provisoire
     */
    public void handle(ActionEvent a) {

        try {
            app.getConnexion().modifierMDP(app.getPseudoSaisie(), app.Nouveaumdp());
            RunDuelSurLaToile reboot = new RunDuelSurLaToile();
            app.getStage().close();
            reboot.start(new Stage());


            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("Mot de passe oublié");
            info.setHeaderText("Mot de passe modifié");
            info.setContentText("Votre mot de passe a bien été changé !");
            info.showAndWait();

        } catch (SQLException | ClassNotFoundException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Mot de passe oublié");
            error.setHeaderText("Erreur interne");
            error.setContentText("Une erreur a fait son apparition, verifiez votre connexion internet");
            error.showAndWait();
        }
    }
}
