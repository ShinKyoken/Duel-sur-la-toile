package Plateforme.Application;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Alerte {

    /**
     * permet de creer une alerte qui préviendra l'utilisateur
     * On pourra personnalisé son alerte
     * @param e1
     * @param titre
     * @param contenue
     */
    public static void creationAlerte(Exception e1, String titre, String contenue){
        Alert alerte = new Alert(Alert.AlertType.ERROR);
        alerte.setHeight(Region.USE_PREF_SIZE);
        alerte.setTitle("Attention");
        alerte.setHeaderText(titre);
        alerte.setContentText(contenue);
        Label label = new Label("L'exception est :");

        Exception ex = new Exception(titre);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = e1.toString();

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alerte.getDialogPane().setExpandableContent(expContent);

        alerte.showAndWait();
    }

    /**
     * permet de creer une alerte qui préviendra l'utilisateur
     * Alerte de base créer pour ERREUR INTERNE
     * @param e1
     */
    public static void creationAlerte(Exception e1){
        Alerte.creationAlerte(e1,"ERREUR INTERNE","Une erreur interne a eu lieu.\nVeuillez réessayer ultérieurement.");
    }
}
