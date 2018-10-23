package Plateforme.Administration;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

public class ModifierJeuAction implements EventHandler<ActionEvent>{
	
	private ModifierJeu modif;
    
    private File imageDuJeu;
    
    private File fichierJeu;
	
    public ModifierJeuAction(ModifierJeu modif) {
    	this.modif = modif;
    	this.imageDuJeu = null;
    	this.fichierJeu = null;
    }
    
	@Override
	/**
	 * Méthode gérant les évènements de la page de modification
	 */
	public void handle(ActionEvent event) {
		Button b = (Button) event.getSource();
		
		if(b.getUserData().equals("annuler")) {
    		try {
				modif.getApp().afficherAdministrateurJeu();
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
				modif.getImageS().setText("Image selectionnée !\n"+f.getName());
				this.imageDuJeu = f;
			}
			else {
				this.imageDuJeu = null;
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
				modif.getFichierS().setText("Fichier selectionné !\n"+f.getName());
				this.fichierJeu = f;
			}
			else {
				this.fichierJeu = null;
			}
    	}
    	
    	else if(b.getUserData().equals("valider")) {
    		try {
    			System.out.println(this.fichierJeu);
    			System.out.println(modif.getIdJeu());
				modif.getApp().getConnexion().modifierJeu(modif.getIdJeu(), modif.getNom().getText(), modif.getDescription().getText(), this.fichierJeu, this.imageDuJeu, modif.getType().getSelectionModel().getSelectedIndex()+1);
				Alert al=new Alert(Alert.AlertType.INFORMATION);
				al.setTitle("Modification d'un jeu");
				al.setHeaderText("Le jeu à bien été modifié");
				al.show();
			} catch (SQLException | IOException e) {
				e.printStackTrace();
				Alert error = new Alert(Alert.AlertType.ERROR);
				error.setTitle("Modification d'un jeu");
				error.setHeaderText("Une erreur lors de la modification du jeu");
				error.setContentText("Il semble que vous avez fait une erreur\nlors du remplissage du formulaire");
				error.show();
			}
    	}
		
	}

}
