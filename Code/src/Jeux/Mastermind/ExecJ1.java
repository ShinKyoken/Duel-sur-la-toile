package Jeux.Mastermind;

import java.io.IOException;
import java.sql.SQLException;

import Plateforme.Connexion.ConnexionMySQL;
import javafx.application.Platform;

/**
 * Classe permettant de tester la partie multi joueur via la BD du mastermind
 *
 */
public class ExecJ1 {

	public static void main(String[] args) throws Exception {
		ConnexionMySQL co = new ConnexionMySQL();
		co.connecter();
		LeJeu g = new LeJeu();
		
		g.rejoindrePartieEnCours(1 ,2, 27);

		
	}

}
