package Plateforme.Administration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

public class AjoutJeuAction implements EventHandler<ActionEvent> {
    /**
     * Classe permettant de définir les évênements qui surviennent lorsque l'on clique sur le bou
     */
    private AjoutJeu ajout;
    
    private File imageDuJeu;
    
    private File fichierJeu;
    
    /**
     *
     */

    public AjoutJeuAction (AjoutJeu ajout) {

        this.ajout = ajout;
    }

    @Override
	/**
	 * Méthode gérant les évènement de la page d'ajout d'un jeu.
	 */
    public void handle(ActionEvent a) {
    	Button b = (Button) a.getSource();
    	
    	if(b.getUserData().equals("annuler")) {
    		try {
				ajout.getApp().afficherAdministrateurJeu();
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
    	
    	else if(b.getUserData().equals("image")) {
    		FileChooser fc = new FileChooser();
			fc.setTitle("Choix de l'image");
			fc.setInitialDirectory(new File("../"));
			FileChooser.ExtensionFilter et = new FileChooser.ExtensionFilter("Image", "*.gif", "*.png", "*.jpeg" ,"*.jpg");
			fc.getExtensionFilters().add(et);
			File f = fc.showOpenDialog(null);
			if(f != null) {
				ajout.getImageS().setText("Image selectionnée !\n"+f.getName());
				this.imageDuJeu = f;
			}
			else {
				this.imageDuJeu = new File("./JEU-image.png");
			}
    	}
    	
    	else if(b.getUserData().equals("fichier")) {
    		FileChooser fc = new FileChooser();
			fc.setTitle("Choix du fichier du jeu");
			fc.setInitialDirectory(new File("../"));
			FileChooser.ExtensionFilter et = new FileChooser.ExtensionFilter("Jar", "*.jar", "*.JAR");
			fc.getExtensionFilters().add(et);
			File f = fc.showOpenDialog(null);
			if(f != null) {
				ajout.getFichierS().setText("Fichier selectionné !\n"+f.getName());
				this.fichierJeu = f;
			}
    	}
    	
    	else if(b.getUserData().equals("valider")) {
    		try {
				ajout.getIHM().getConnexion().ajoutJeu(
						ajout.getNom().getText(), 
						ajout.getDescription().getText(),
						this.fichierJeu,
						this.imageDuJeu,
						ajout.getType().getValue().getIdType());
				Alert al=new Alert(Alert.AlertType.INFORMATION);
				al.setTitle("Ajout d'un jeu");
				al.setHeaderText("Le jeu a bien été ajouté");
				al.show();
			} catch (SQLException | IOException e) {
				Alert error = new Alert(Alert.AlertType.ERROR);
				error.setTitle("Ajout d'un jeu");
				error.setHeaderText("Une erreur lors de l'ajout du jeu");
				error.setContentText("Il semble que vous avez fait une erreur\nlors du remplissage du formulaire");
				error.show();
			}
    	}
    }
}