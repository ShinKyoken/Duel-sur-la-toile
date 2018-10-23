package Plateforme.Connexion.MotDePasseOublie;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class SaisieNouveauMotDePasse extends BorderPane{

    private PasswordField mdp;

    /**
     * Méthode gérant l'affichage de la saisie du mot de passe
     * @param app
     */
    public SaisieNouveauMotDePasse(ApplicationMotDePasseOublie app) {

        VBox saisie = new VBox();

        Label instruction = new Label("Votre nouveau mot de passe");

        mdp = new PasswordField();

        Button valider = new Button("Valider");
        ActionSaisieNouveauMotDePasse act = new ActionSaisieNouveauMotDePasse(app);
        valider.setOnAction(act);

        saisie.getChildren().add(instruction);
        saisie.getChildren().add(mdp);
        saisie.getChildren().add(valider);

        saisie.setSpacing(10);
        saisie.setAlignment(Pos.CENTER);

        this.setPadding(new Insets(5,5,5,5));
        this.setCenter(saisie);
    }

    /**
     * Retourne le mot de passe
     * @return le mot de passe
     */
    public String getMDP() {
        return this.mdp.getText();
    }
}
