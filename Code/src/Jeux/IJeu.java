package Jeux;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Interface necessaire pour les jeux present sur la plateforme
 *
 */
public interface IJeu {
	
	/**
	 * Methode permettant de rejoindre une partie en cours
	 * @param idJeu
	 * @param idPartie
	 * @throws SQLException
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws Exception 
	 */
	public void rejoindrePartieEnCours(int idPC, int idJeu, int idPartie) throws SQLException, FileNotFoundException, ClassNotFoundException, IOException, Exception;

	/**
	 * Methode permettant de creer une partie
	 * @param idJeu
	 * @param idJoueur1
	 * @param idJoueur2
	 * @param partage
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 * @throws Exception
	 */
	public void creerPartie(int idPC, int idJeu, int idJoueur1, int idJoueur2, Object partage) throws Exception;
}
