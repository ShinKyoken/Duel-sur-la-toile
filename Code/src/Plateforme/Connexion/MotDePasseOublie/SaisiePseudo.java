package Plateforme.Connexion.MotDePasseOublie;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class SaisiePseudo extends BorderPane{

    private TextField pseudo;

    /**
     * Méthode qui permet de générer l'affichage de la saisie du pseudo.
     * @param app l'application
     */
    public SaisiePseudo(ApplicationMotDePasseOublie app) {

        VBox saisie = new VBox();

        Label instruction = new Label("Veuillez bien entrer votre pseudo\npour procéder à la recuperation\nde votre mot de passe");

        pseudo = new TextField();

        Button valider = new Button("Valider");
        ActionSaisiePseudo act = new ActionSaisiePseudo(app);
        valider.setOnAction(act);

        saisie.getChildren().add(instruction);
        saisie.getChildren().add(pseudo);
        saisie.getChildren().add(valider);

        saisie.setSpacing(10);
        saisie.setAlignment(Pos.CENTER);

        this.setPadding(new Insets(5,5,5,5));
        this.setCenter(saisie);
    }

    /**
     * Retourne le pseudo
     * @return le pseudo
     */
    public String getPseudo() {

        return this.pseudo.getText();
    }
}
