package Jeux.Mastermind;

import java.io.IOException;
import java.sql.SQLException;

import org.json.simple.parser.ParseException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Classe permettant de creer des evenemments pour les bouttons de la page
 *
 */

public class ActionButton implements EventHandler<ActionEvent> {

	/**
	 * Varaible permettant de recuperer le plateau du mastermind
	 */
	private VuePlateau app;

	/**
	 * Constructeur prenant en parametre le plateau du mastermind
	 * 
	 * @param app
	 */
	ActionButton(VuePlateau app) {
		this.app = app;
	}

	/**
	 * Methode permettant d'attribuer une tache à un bouton
	 */
	@Override
	public void handle(ActionEvent a) {
		Button b = (Button) a.getSource();

		if (b.getUserData().equals("jouer")) {
			if (app.getCouleurSelect().contains(0)) { 
				System.out.println("erreur saisie");
			} else {
				try {
					app.getIHM().getJeu().etatPartie();
					if(app.getIHM().getJeu().getNbManche()<=3) {
						this.app.getIHM().getJeu().verifTours();
					}
					else {
						app.getIHM().getJeu().getConnexion().getInfoPlateforme().finDuJeu(app.getIHM().getJeu().getIDPartie());
						int vainceurPartie = app.getIHM().getJeu().getConnexion().gagnantPartie(app.getIHM().getJeu().getIDPartie());
						if (vainceurPartie!=0){
							Alert etat = new Alert(Alert.AlertType.INFORMATION);
							etat.setTitle("MasterMind");
							etat.setHeaderText("Fin de la partie");
							etat.setContentText("Le vainceur de cette partie est le joueur "+vainceurPartie);
							etat.show();
						}

						else if(vainceurPartie == 0) {
							Alert etat = new Alert(Alert.AlertType.INFORMATION);
							etat.setTitle("MasterMind");
							etat.setHeaderText("Fin de la partie");
							etat.setContentText("Egalité !");
							etat.show();
						}
						app.getIHM().getJeu().getplat().getBarre().bloquerBarre();
						app.getIHM().getJeu().getplat().getValider().setDisable(true);
						app.getIHM().getRight().getBoutonAbandonner().setDisable(true);
						app.getIHM().getJeu().getConnexion().getInfoPlateforme().finDuJeu(app.getIHM().getJeu().getIDPartie());
					}

				} catch (ClassNotFoundException | SQLException | IOException | InterruptedException | ParseException e) {
					e.printStackTrace();
				}

			}
		}
		else if(b.getUserData().equals("annuler")) {
			app.editLigne((app.getListeLigne().getChildren().size() - 2) - app.getIHM().getJeu().getNumTentative(),
					Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK);
			app.effacerCouleurSelectionner();
		}
		else if(b.getUserData().equals("abandonner")) {
			try {
				app.getIHM().getJeu().getThreadVerifAbandon().interrupt();
				app.getIHM().getJeu().getplat().getBarre().bloquerBarre();
				app.getIHM().getJeu().getplat().getValider().setDisable(true);
				app.getIHM().getJeu().setGagnant(app.getIHM().getJeu().setJoueurSuivant());
				app.getIHM().getJeu().getConnexion().setEtat(app.getIHM().getJeu(), app.getIHM().getJeu().getIDPartie());
				app.getIHM().getJeu().getConnexion().getInfoPlateforme().abandonnerJeu(app.getIHM().getJeu().getIDPartie());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
//		else if(b.getUserData().equals("quitter")) {
//			try {
//				Thread.currentThread().stop();
//				Thread.currentThread().destroy();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		else if(b.getUserData().equals("aide")) {
			try {
				new ApplicationAide().start(new Stage());
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}
