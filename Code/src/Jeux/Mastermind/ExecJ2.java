package Jeux.Mastermind;

import java.io.IOException;
import java.sql.SQLException;
import Plateforme.Connexion.ConnexionMySQL;

/**
 * Classe permettant de tester la partie multi joueur via la BD du mastermind
 *
 */
public class ExecJ2 {
	public static void main(String[] args) throws Exception {
		ConnexionMySQL co = new ConnexionMySQL();
		co.connecter();
		LeJeu g = new LeJeu();
		g.rejoindrePartieEnCours(19 ,2, 22);

	}
}
