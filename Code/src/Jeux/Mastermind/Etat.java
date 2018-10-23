package Jeux.Mastermind;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Plateforme.Connexion.ConnexionMySQL;

/**
 * Classe test pour la communication MySQL du jeu du mastermind
 *
 */
public class Etat {

	/**
	 * Variable permettant de recuperer une connexion MySQL
	 */
	private ConnexionMySQL comm;

	/**
	 * Constructeur permettant de ce connecter au serveur MySQL
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	Etat() throws FileNotFoundException, SQLException, IOException, ClassNotFoundException {
		comm = new ConnexionMySQL();
		comm.connecter();
	}

	/**
	 * Methode permettant de recuperer l'état d'une partie
	 * @param idPartie
	 * @return
	 * @throws SQLException
	 */
	public String[] getEtat(int idPartie) throws SQLException {
		PreparedStatement ps = comm.prepareStatement("SELECT numEtape, etatPartie FROM PARTIE WHERE idPa = ?");
		ps.setInt(1, idPartie);
		ResultSet res = ps.executeQuery();
		res.next();
		String[] vals = new String[3];
		vals[0] = res.getString(2);
		vals[1] = String.valueOf(idPartie);
		vals[2] = String.valueOf(res.getString(1));
		return vals;
	}

	/**
	 * Methode permettant de creer une nouvelle partie
	 * @param j
	 * @param idJeu
	 * @param idJ1
	 * @param idJ2
	 * @throws SQLException
	 */
	public void creerPartie(Mastermind j, int idJeu, int idJ1, int idJ2) throws SQLException {
		String etat = j.getEtat();

		PreparedStatement ps = comm.prepareStatement(
				"INSERT INTO PARTIE(idPa, debutPa, numEtape, etatPartie, idJeu, idUt1, score1, idUt2, score2) "
						+ "SELECT MAX(idPa)+1, NOW(), 0, ?, ?, ?, ?, ?, ? " + "FROM PARTIE;");

		ps.setString(1, etat);
		ps.setInt(2, idJeu);
		ps.setInt(3, idJ1);
		ps.setInt(4, 0);
		ps.setInt(5, idJ2);
		ps.setInt(6, 0);

		ps.executeUpdate();
	}

	/**
	 * Methode permettant de modifier l'état d'une partie en cours
	 * @param j
	 * @param idPartie
	 * @throws SQLException
	 */
	public void setEtat(Mastermind j, int idPartie) throws SQLException {
		String etat = j.getEtat();

		PreparedStatement ps = comm.prepareStatement("UPDATE PARTIE SET numEtape = numEtape + 1, etatPartie = ? WHERE idPa = ?;");

		ps.setString(1, etat);
		ps.setInt(2, idPartie);

		ps.executeUpdate();
	}

	/**
	 * Methode permettant de mettre à jour le score de la partie sur la base de donnée
	 * @param idPartie
	 * @param scoreJ1
	 * @param scoreJ2
	 * @throws SQLException
	 */
	public void majScore(int idPartie, int scoreJ1, int scoreJ2) throws SQLException {
		PreparedStatement ps = comm.prepareStatement("UPDATE PARTIE SET score1 = ?, score2 = ? WHERE idPa = ?");
		ps.setInt(1, scoreJ1);
		ps.setInt(2, scoreJ2);
		ps.setInt(3, idPartie);
		ps.executeUpdate();
	}
	
	/**
	 * Methode permettant de recuperer le gagnant de la partie
	 * @param idPartie
	 * @return
	 * @throws SQLException
	 * @throws ParseException
	 */
	public int gagnantPartie(int idPartie) throws SQLException, ParseException {
		PreparedStatement ps = comm.prepareStatement("SELECT etatPartie, score1, score2 FROM PARTIE WHERE idPa = ?");
		ps.setInt(1, idPartie);
		ResultSet res = ps.executeQuery();
		res.next();
		String etat = res.getString(1);
		int scoreJ1 = res.getInt(2);
		int scoreJ2 = res.getInt(3);
		JSONParser p = new JSONParser();
		JSONObject json = (JSONObject) p.parse(etat);
		JSONArray lsJ = (JSONArray) json.get("joueurs");
		List<Integer> player = new ArrayList<Integer>();

		for (int i = 0; i < lsJ.size(); i++) {
			player.add(((Long) lsJ.get(i)).intValue());
		}

		if(scoreJ1 > scoreJ2) {
			return player.get(0);
		}
		else if(scoreJ1 < scoreJ2) {
			return player.get(1);
		}
		return 0;
	}
	
	public ConnexionMySQL getInfoPlateforme() {
		return this.comm;
	}
}
