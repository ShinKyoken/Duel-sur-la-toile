package Jeux.Mastermind;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import Plateforme.Partage;
import Jeux.IJeu;


/**
 * Classe permettant de faire la liaison entre la plateforme et le jeu
 *
 */

public class LeJeu implements IJeu {


	/**
	 * Les données partager entre la plateforme et le jeu
	 */
	private Partage partage = null;

	/**
	 * Methode permettant de creer une nouvelle partie
	 */
	@Override
	public void creerPartie(int idPC, int idJeu, int idJoueur1, int idJoueur2, Object partage) throws Exception {
		this.partage = (Partage) partage;
		new CreerUnePartie();
		CreerUnePartie.main(null, idPC, idJeu, idJoueur1, idJoueur2);
	}

	/**
	 * Methode permettant de rejoindre une partie
	 * @throws Exception 
	 */
	@Override
	public void rejoindrePartieEnCours(int idPC, int idJeu, int idPartie) throws Exception {
		this.partage = (Partage) partage;
		new RejoindreUnePartie();
		RejoindreUnePartie.main(null, idPC, idJeu, idPartie);

	}


}
