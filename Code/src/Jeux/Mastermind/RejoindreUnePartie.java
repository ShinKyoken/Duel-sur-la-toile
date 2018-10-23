package Jeux.Mastermind;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Classe permettant de rejoindre une partie
 *
 */
public class RejoindreUnePartie {
	public static void main(String[] args, int idPC, int idJeu, int idPartie) throws Exception {
		Etat et = new Etat();
		String[] sauvegarde = et.getEtat(idPartie);
		String[] param = new String[6];
		
		param[0] = "rejoindre";
		param[1] = String.valueOf(idPC);
		param[2] = sauvegarde[0];
		param[3] = String.valueOf(idPartie);
		param[4] = sauvegarde[1];
		
		if(Integer.parseInt(sauvegarde[2])>=0) {
			VueMastermind app = new VueMastermind(param);
			app.start(new Stage());
		}
	}
}
