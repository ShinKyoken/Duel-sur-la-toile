package Plateforme.Connexion.MotDePasseOublie;

import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;

public class ActionSaisieCodeProvisoire implements EventHandler<ActionEvent>{

    private ApplicationMotDePasseOublie app;


    public ActionSaisieCodeProvisoire(ApplicationMotDePasseOublie app) {
        this.app = app;
    }

    @Override
    /**
     * Méthode permettant de gérer les évènement lors de la saisie du code provisoire
     */
    public void handle(ActionEvent a) {
        try {
            if(app.getConnexion().codeProvisoireCorrect(app.getPseudoSaisie(), app.getCodeP())) {
                app.afficherSaisieNMDP();
                app.getConnexion().suppressionMotDePasseProvisoire(app.getPseudoSaisie());
                Alert b = new Alert(Alert.AlertType.INFORMATION);
                b.setTitle("Mot de passe oublié");
                b.setHeaderText("Code provisoire valide");
                b.setContentText("Vous pouvez maintenant saisir\nvotre nouveau mot de passe");
                b.showAndWait();
            }
            else{
                System.out.println("Doit y avoir une erreur dans la condition, ou alors tu as juste mal tapé ton code provisoire ;)");
                }
            }
         catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
