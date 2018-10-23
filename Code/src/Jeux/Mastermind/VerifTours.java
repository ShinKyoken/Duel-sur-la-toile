package Jeux.Mastermind;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.application.Platform;
import javafx.scene.control.Alert;

/**
 * Classe permettant de recuperer l'etat de la partie quand on a passé son tours
 *
 */
public class VerifTours implements Runnable{

	/**
	 * Entier indiquant l'identifiant de la partie en cours
	 */
	private int idPartie;

	/**
	 * Entier indiquand l'identifiant du joueur qui a passé son tours
	 */
	private int idJoueur;

	/**
	 * Le mastermind
	 */
	private Mastermind j;

	/**
	 * Constructeur de la classe prenant en parametre le jeu du mastermind, l'identifiant de la partie, et l'identifiant du joueur
	 * @param j
	 * @param idPartie
	 * @param idJoueur
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	VerifTours(Mastermind j, int idPartie, int idJoueur) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException, InterruptedException, ParseException{
		this.idPartie = idPartie;
		this.idJoueur = idJoueur;
		this.j = j;
	}

	/**
	 * Methode permetant de lancer la classe sur un autre thread
	 */
	@Override
	public void run() {
		Boolean continuer = true;
		int nbApparition = 0;
		while(continuer) {

			try {
				String[] val = j.getConnexion().getEtat(idPartie);
				JSONParser p = new JSONParser();
				JSONObject res = (JSONObject) p.parse(val[0]);
				int idEnJeu = ((Long) res.get("joueurCourant")).intValue();
				int numEtape = Integer.parseInt(val[2]);
				int gagnant = ((Long) res.get("joueurGagnant")).intValue();

				if(idJoueur == idEnJeu) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							j.getplat().getBarre().debloquerBarre();
							j.getplat().getValider().setDisable(false);
							j.finPartie();
							j.setEtat(val[0]);
						}
					});

					Thread.currentThread().interrupt();
					continuer = false;
					break;
				}


				if(gagnant > 0 && nbApparition < 1) {
					nbApparition+=1;
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							if(j.getIDPC() != gagnant) {
								Alert etat = new Alert(Alert.AlertType.INFORMATION);
								etat.setTitle("MasterMind");
								etat.setHeaderText("Dommage !");
								etat.setContentText("Dommage ! Le joueur " + gagnant + " a gagné la manche !");
								etat.show();
							}

						}
					});

				}
				
				if(gagnant == -1 && nbApparition < 1) {
					nbApparition+=1;
					if(Integer.parseInt(j.getConnexion().getEtat(j.getIDPartie())[2])%10==0) {
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
									Alert etat = new Alert(Alert.AlertType.INFORMATION);
									etat.setTitle("MasterMind");
									etat.setHeaderText("Egalité !");
									etat.setContentText("Il n'y a pas de gagnant ni perdant");
									etat.show();

							}
						});
					}
					

				}


				if(numEtape < 0) {
					int vainceur = j.getConnexion().gagnantPartie(j.getIDPartie());
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							if(j.getIDPC() != gagnant) {
								Alert etat = new Alert(Alert.AlertType.INFORMATION);
								etat.setTitle("MasterMind");
								etat.setHeaderText("Fin de la partie");
								if(vainceur != 0) {
									etat.setContentText("Le vainceur de cette partie est le joueur "+vainceur);
								}
								else {
									etat.setContentText("Egalité !");
								}
								etat.show();
							}

						}
					});
					j.getplat().getIHM().getRight().getBoutonAbandonner().setDisable(true);
					Thread.currentThread().interrupt();
					continuer = false;
					break;
				}
				Thread.sleep(1000); 
			} catch (SQLException | ParseException | InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

}

