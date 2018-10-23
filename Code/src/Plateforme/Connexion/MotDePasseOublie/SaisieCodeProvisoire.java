package Plateforme.Connexion.MotDePasseOublie;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class SaisieCodeProvisoire extends BorderPane{


    private TextField codeP;

    /**
     * Méthode gérant l'affichage de la saisie du code provisoire
     * @param app l'application
     */
    public SaisieCodeProvisoire(ApplicationMotDePasseOublie app) {

        VBox saisie = new VBox();

        Label instruction = new Label("Votre code provisoire");

        this.codeP = new TextField();

        Button valider = new Button("Valider");
        ActionSaisieCodeProvisoire act = new ActionSaisieCodeProvisoire(app);
        valider.setOnAction(act);

        saisie.getChildren().add(instruction);
        saisie.getChildren().add(codeP);
        saisie.getChildren().add(valider);

        saisie.setSpacing(10);
        saisie.setAlignment(Pos.CENTER);

        this.setPadding(new Insets(5,5,5,5));
        this.setCenter(saisie);
    }

    /**
     * Retourne le code code provisoire
     * @return le code provisoire
     */
    public String getCodeProvisoire() {
        return this.codeP.getText();
    }
}
