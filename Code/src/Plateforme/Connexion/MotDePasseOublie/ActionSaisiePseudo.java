package Plateforme.Connexion.MotDePasseOublie;

import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;

public class ActionSaisiePseudo implements EventHandler<ActionEvent>{

    private ApplicationMotDePasseOublie app;

    public ActionSaisiePseudo(ApplicationMotDePasseOublie app) {

        this.app = app;
    }

    @Override
    /**
     * Méthode gérant les évènements lorsque l'on saisie un pseudo
     */
    public void handle(ActionEvent a) {

        String code = GenerationCodeProvisoire.genere();
        try {
            app.setPseudo();

            app.getConnexion().suppressionMotDePasseProvisoire(app.getPseudoSaisie());

            app.getConnexion().ajoutMotDePasseProvisoire(app.getPseudoSaisie(), code);

            Mail.send(app.getPseudoSaisie(), code, app.getConnexion().getEmail(app.getPseudoSaisie()));

            app.afficherSaisieCodeProvisoire();

            Alert error = new Alert(Alert.AlertType.INFORMATION);
            error.setTitle("Mot de passe oublié");
            error.setHeaderText("Generation de votre code provisoire réussie");
            error.setContentText("Votre code provisoire vous est envoyé par mail");
            error.showAndWait();

        } catch (SQLException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Mot de passe oublié");
            error.setHeaderText("Erreur de saisie");
            error.setContentText("Le pseudo entré est introuvable !");
            error.showAndWait();
        }
    }
}
