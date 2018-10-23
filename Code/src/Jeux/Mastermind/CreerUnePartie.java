package Jeux.Mastermind;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import org.json.simple.parser.ParseException;

/**
 * Classe permettant de creer une partie
 *
 */
public class CreerUnePartie {

	public static void main(String[] args, int idPC, int idJeu, int idJoueur1, int idJoueur2) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException, InterruptedException, ParseException {
		Mastermind m = new Mastermind(idJeu, 4, 6, 10, idJoueur1, idJoueur2,idPC);		
		m.getConnexion().creerPartie(m, idJeu, idJoueur1, idJoueur2);
	}
}
