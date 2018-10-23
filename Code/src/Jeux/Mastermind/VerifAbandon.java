package Jeux.Mastermind;

import java.sql.SQLException;

import javafx.application.Platform;
import javafx.scene.control.Alert;

/**
 * Classe de verification d'abandon durant une partie de mastermind
 *
 */
public class VerifAbandon implements Runnable{

	/**
	 * Le mastermind
	 */
	private Mastermind j;

	/**
	 * L'identifiant de la partie en cours
	 */
	private int idPartie;
	
	private int nbApparition;

	/**
	 * Constructeur de la classe prenant en parametre le jeu du mastermind, et l'identifiant de la partie
	 * @param j
	 * @param idPartie
	 */
	VerifAbandon(Mastermind j, int idPartie){
		this.j = j;
		this.idPartie = idPartie;
		this.nbApparition = 0;
	}

	/**
	 * Methode permetant de lancer la classe sur un autre thread
	 */
	@Override
	public void run() {
		
		Boolean continuer = true;
		while(continuer) {

			try {
				String[] val = j.getConnexion().getEtat(idPartie);
				int numEtape = Integer.parseInt(val[2]);
				if(numEtape == -2) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							j.getplat().getBarre().bloquerBarre();
							j.getplat().getValider().setDisable(true);
							j.getplat().getIHM().getRight().getBoutonAbandonner().setDisable(true);
							j.finPartie();
							j.setEtat(val[0]);				
						}
					});
					
					if(nbApparition < 1) {
						nbApparition+=1;
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								Alert ab = new Alert(Alert.AlertType.INFORMATION);
								ab.setTitle("Mastermind");
								ab.setHeaderText("Abandon");
								ab.setContentText("La partie a été abandonner !");
								ab.show();
							}
						});
					}
					

					Thread.currentThread().interrupt();
					continuer = false;
					break;
				}
				Thread.sleep(1000); 
			} catch (SQLException | InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

}
