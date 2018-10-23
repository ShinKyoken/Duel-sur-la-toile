package Plateforme.Jeux;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.io.File;
import java.util.Optional;

import Plateforme.Application.DuelSurLaToile;
import java.sql.SQLException;
/**
 * Cette classe implémente EventHandler et gère le clic sur le bouton "Jouer" de la vue Jeu
 * @author erwan, theodore
 */
public class ActionJouer implements EventHandler<ActionEvent>{
    /**
     * L'application
     */
    private DuelSurLaToile app;
	
    public ActionJouer(DuelSurLaToile app){
	this.app = app;
    }

    /**
     * Cette fonction est apelée lors du clic du bouton "Jouer"
     * @param a 
     */
    public void handle(ActionEvent a){
        Button b = (Button) a.getSource();
        Jeu j = (Jeu) b.getUserData();
        String nf = j.getNom().replace(' ', '_');
        File jarFile = new File("./jar/"+nf+".jar");
        if(jarFile.exists()) {
            try{
                this.app.afficherInviterJeu(j);
            }
            catch(SQLException e){
                System.out.println("Erreur");
            }
        }
        else {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Jeu");
            alert.setHeaderText("Jeu non telecharger");
            alert.setContentText("Voulez-vous telecharger le jeu ?");

            ButtonType buttonTypeOui = new ButtonType("Oui");
            ButtonType buttonTypeNon = new ButtonType("Non");

            alert.getButtonTypes().setAll(buttonTypeOui, buttonTypeNon);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOui){
                app.getConnexion().getJar(j.getId(), nf);
                System.out.println("Telechargement terminé !");
        	}
        }
    }
}
