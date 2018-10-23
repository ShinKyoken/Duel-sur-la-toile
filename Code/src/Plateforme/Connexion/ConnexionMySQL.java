package Plateforme.Connexion;

import Plateforme.Application.DuelSurLaToile;
import Plateforme.Profil.HistoriquePartie;
import Plateforme.LancerJeu;
import Plateforme.Application.Alerte;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.Properties;

import Jeux.IJeu;
import Plateforme.Application.Utilisateur;
import Plateforme.Conversation.Message;
import Plateforme.Jeux.Jeu;
import Plateforme.Jeux.PartieOuverte;
import Plateforme.Jeux.TypeJeu;
import Plateforme.Profil.Invitation;
import javafx.application.Platform;
import javafx.scene.image.Image;
import Plateforme.Partie.*;
import javafx.scene.image.ImageView;

import java.sql.*;
import java.util.*;

public class ConnexionMySQL {
	private Connection mysql;
	private boolean connecte = false;

	/**
	 *Méthode qui permet de se connecter à l'application via une base de données.
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public ConnexionMySQL() throws ClassNotFoundException, IOException{
		Class.forName("com.mysql.jdbc.Driver"); // Verification de la presence du driver JDBC

		Properties props = new Properties();
		props.load(new FileInputStream("config.properties"));

		String user = props.getProperty("jdbc.username");
		String pass = props.getProperty("jdbc.password");
		String url = props.getProperty("jdbc.url");
	}

	/**
	 * Méthode qui permet de se connecter à une base de donnée qui a été configuré dans le fichier
	 * ./config.properties.
	 * @throws SQLException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void connecter() throws SQLException, FileNotFoundException, IOException {
		// CONNEXION CRYPTER
		//		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		//		encryptor.setPassword("DSLT");
		//
		//		Properties props = new EncryptableProperties(encryptor);

		//CONNEXION STANDARD
		Properties props = new Properties();
		props.load(new FileInputStream("./config.properties"));

		String user = props.getProperty("jdbc.username");
		String pass = props.getProperty("jdbc.password");
		String url = props.getProperty("jdbc.url");

		DriverManager.setLoginTimeout(5);
		this.mysql = DriverManager.getConnection(url, user, pass);
		this.connecte = true;
	}

	/**
	 * Méthode qui permet de se déconnecter de la base de données et d'indiquer l'utilisateur comme déconnecté.
	 * @throws SQLException
	 */
	public void close() throws SQLException {
		this.mysql.close();
		this.connecte = false;
	}

	/**
	 * Retourne si l'utilisateur est connecté ou non.
	 * @return
	 */
	public boolean isConnecte() {
		return this.connecte;
	}

	/**
	 * Méthode qui permet d'envoyer des fichers dans la base de données.
	 * @return
	 * @throws SQLException
	 */
	public Blob createBlob() throws SQLException {
		return this.mysql.createBlob();
	}

	/**
	 * Méthode qui retourne un Statement qui nous permettra de faire des requete.
	 * @return
	 * @throws SQLException
	 */
	public Statement createStatement() throws SQLException {
		return this.mysql.createStatement();
	}

	/**
	 * Méthode qui retourne un prepareStatement.
	 * @param requete
	 * @return un PrepareStatement
	 * @throws SQLException
	 */
	public PreparedStatement prepareStatement(String requete) throws SQLException {
		return this.mysql.prepareStatement(requete);
	}

	/**
	 * Affiche la table de la base de donnée dans le terminal.
	 * @param Table
	 * @throws SQLException
	 */
	public void seeAll(String Table) throws SQLException {
		//L'objet ResultSet contient le résultat de la requête SQL
		ResultSet result = createStatement().executeQuery("Select * from " + Table + ";");
		//On récupère les MetaData
		ResultSetMetaData resultMeta = result.getMetaData();
		System.out.println(resultMeta.getColumnCount() + " colonnes");
		for (int i = 1; i <= resultMeta.getColumnCount(); i++) {
			System.out.print("\t" + resultMeta.getColumnName(i).toUpperCase() + "\t *");
		}
		System.out.println("\n");
		while (result.next()) {
			for (int i = 1; i <= resultMeta.getColumnCount(); i++)
				System.out.print("\t" + result.getObject(i).toString() + "\t |");
			System.out.println("\n");
		}
		result.close();
		createStatement().close();
	}

	/**
	 * Retourne une liste contenant les pseudonymes des utilisateurs présent dans la base de données.
	 * @return une liste d'utilisateur
	 * @throws SQLException
	 */
	public List<String> getListeUser() throws SQLException {
		List<String> listeUsers = new ArrayList<String>();
		//L'objet ResultSet contient le résultat de la requête SQL
		ResultSet result = createStatement().executeQuery("Select * from UTILISATEUR;");
		//Ajout des pseudo dans une liste
		while (result.next()) {
			listeUsers.add(result.getObject(2).toString());
		}
		return listeUsers;
	}

	/**
	 * REtourne la liste de tout les utilisateur présent dans la base de données.
	 * @return une liste d'utilisateur
	 * @throws SQLException
	 */
	public List<Utilisateur> getListeUtilisateur() throws SQLException {
		List<Utilisateur> listUtilisateur = new ArrayList<>();
		PreparedStatement ps = this.prepareStatement("SELECT idUt, pseudoUt, emailUt, activeUt, avatarUt, nomRole, ratioUt FROM UTILISATEUR");
		ResultSet r = ps.executeQuery();
		while (r.next()) {
			listUtilisateur.add(this.nouvelUtilisateur(r));
		}
		return listUtilisateur;


	}

	/**
	 * Retourne un dictionnaire contenant ayant pour clé les pseudonyme des utilisateurs et comme valeur les mots de
	 * passes associés.
	 * @return un dictionnaire
	 * @throws SQLException
	 */
	public HashMap<String, String> getListeIDEtMDP() throws SQLException {
		HashMap<String, String> pseudoMDP = new HashMap<String, String>();
		//L'objet ResultSet contient le résultat de la requête SQL
		ResultSet result = createStatement().executeQuery("Select * from UTILISATEUR;");
		//Ajout des donnée dans le dictionnaire
		while (result.next()) {
			pseudoMDP.put(result.getObject(2).toString(), result.getObject(4).toString());
		}
		return pseudoMDP;
	}

	/**
	 * Retourne la liste des amis de l'utilisateur dont le pseudo a été passé en paramètre.
	 * @param pseudo
	 * @return une liste d'utilisateurs
	 * @throws SQLException
	 */
	public List<Utilisateur> getAmis(String pseudo) throws SQLException {
		List<Utilisateur> res = new ArrayList<Utilisateur>();
		PreparedStatement ps = this.prepareStatement("SELECT idUt, pseudoUt, emailUt, activeUt, avatarUt, nomRole, ratioUt FROM UTILISATEUR WHERE idUt IN (SELECT idUt1 FROM UTILISATEUR NATURAL JOIN ETREAMI WHERE pseudoUt = ?)");
		ps.setString(1, pseudo);
		ResultSet r = ps.executeQuery();
		while (r.next()) {
			res.add(this.nouvelUtilisateur(r));
		}
		return res;
	}

	/**
	 * Retourne la liste des utilisateurs non ami avec l'utilisateur dont le pseudo a été passé en paramètre.
	 * @param pseudo
	 * @return une liste d'utilisateur
	 * @throws SQLException
	 */
	public List<Utilisateur> listeUtilisateurNonAmis(String pseudo) throws SQLException {
		List<Utilisateur> res = new ArrayList<Utilisateur>();
		PreparedStatement ps = this.prepareStatement("SELECT idUt, pseudoUt, emailUt, activeUt, avatarUt, nomRole, ratioUt FROM UTILISATEUR WHERE idUt NOT IN (SELECT idUt FROM UTILISATEUR WHERE idUt IN (SELECT idUt1 FROM UTILISATEUR NATURAL JOIN ETREAMI WHERE pseudoUt = ?)) AND idUt NOT IN(SELECT idUt FROM UTILISATEUR WHERE pseudoUt = ?)");
		ps.setString(1, pseudo);
		ps.setString(2, pseudo);
		ResultSet r = ps.executeQuery();
		while (r.next()) {
			res.add(this.nouvelUtilisateur(r));
		}
		return res;
	}

	/**
	 * Retourne l'avatar de l'utilisateur dont le pseudo a été passé en paramètre.
	 * @param pseudo
	 * @return un byte
	 * @throws SQLException
	 */
	public byte[] getAvatarUtilisateur(String pseudo) throws SQLException {
		PreparedStatement ps = this.prepareStatement("SELECT avatarUt FROM UTILISATEUR WHERE pseudoUt = ?");
		ps.setString(1, pseudo);
		ResultSet r = ps.executeQuery();
		r.next();
		byte[] res = r.getBytes(1);
		return res;
	}

	/**
	 * Retourne un Resultset contenant le profil de l'utilisateur dont le profil a été passé en paramètre.
	 * @param pseudo
	 * @return un Resultset
	 * @throws SQLException
	 */
	public ResultSet getProfil(String pseudo) throws SQLException {
		PreparedStatement ps = this.prepareStatement("SELECT idUt, pseudoUt, emailUt, activeUt, avatarUt, nomRole, ratioUt FROM UTILISATEUR WHERE pseudoUt = ?");
		ps.setString(1, pseudo);
		ResultSet r = ps.executeQuery();
		r.next();
		return r;
	}

	/**
	 * Retourne l'utilisateur qui correspond à l'identifiant passé en paramètre.
	 * @param id
	 * @return un utilisateur
	 * @throws SQLException
	 */
	public Utilisateur chercherUtilisateur(int id) throws SQLException {
		PreparedStatement ps = this.prepareStatement("SELECT idUt, pseudoUt, emailUt, activeUt, avatarUt, nomRole, ratioUt FROM UTILISATEUR WHERE idUt = ?");
		ps.setInt(1, id);
		ResultSet r = ps.executeQuery();
		r.next();
		return this.nouvelUtilisateur(r);
	}

	/**
	 * Retourne l'utilisateur qui correspond au pseudo passé en paramètre
	 * @param pseudo
	 * @return un utilisateur
	 * @throws SQLException
	 */
	public Utilisateur chercherUtilisateur(String pseudo) throws SQLException {
		PreparedStatement ps = this.prepareStatement("SELECT idUt, pseudoUt, emailUt, activeUt, avatarUt, ratioUt, nomRole FROM UTILISATEUR WHERE pseudoUt = ?");
		ps.setString(1, pseudo);
		ResultSet r = ps.executeQuery();
		r.next();
		return this.nouvelUtilisateur(r);
	}

	/**
	 * Retourne une liste d'utilisateur dont le pseudo commence par le string passé en paramètre.
	 * @param search
	 * @return une liste d'utilisateur
	 * @throws SQLException
	 */
	public List<Utilisateur> rechercherUtilisateur(String search) throws SQLException {
		List<Utilisateur> res = new ArrayList<Utilisateur>();
		PreparedStatement ps = this.prepareStatement("SELECT idUt, pseudoUt, emailUt, activeUt, avatarUt, nomRole, ratioUt FROM UTILISATEUR WHERE pseudoUt LIKE ?");
		ps.setString(1, "%"+search + "%");
		ResultSet r = ps.executeQuery();
		while (r.next()) {
			res.add(this.nouvelUtilisateur(r));
		}
		return res;
	}

	/**
	 * Retourne la liste des utilisateurs qui sont amis avec l'utilisateur dont le pseudo a été passé en paramètre.
	 * Il faut assi que les utilisateurs de cette liste ait un pseudo qui commence par le paramètre search.
	 * @param pseudo
	 * @param search
	 * @return une liste d'utilisateur
	 * @throws SQLException
	 */
	public List<Utilisateur> rechercherAmis(String pseudo, String search) throws SQLException {
		List<Utilisateur> res = new ArrayList<Utilisateur>();
		PreparedStatement ps = this.prepareStatement("SELECT idUt, pseudoUt, emailUt, activeUt, avatarUt, nomRole, ratioUt FROM UTILISATEUR WHERE pseudoUt LIKE ? AND idUt IN (SELECT idUt1 FROM UTILISATEUR NATURAL JOIN ETREAMI WHERE pseudoUt = ?)");
		ps.setString(1, "%"+search + "%");
		ps.setString(2, pseudo);
		ResultSet r = ps.executeQuery();
		while (r.next()) {
			res.add(this.nouvelUtilisateur(r));
		}
		return res;
	}

	/**
	 * Retourne le mot de passe de l'utilisateur dont le pseudo a été passé en paramètre.
	 * @param pseudo
	 * @return un String
	 * @throws SQLException
	 */
	public String getMDP(String pseudo) throws SQLException {
		PreparedStatement ps = this.prepareStatement("Select mdpUt FROM UTILISATEUR WHERE pseudoUt = ?");
		ps.setString(1, pseudo);
		ResultSet r = ps.executeQuery();
		r.next();
		return r.getString(1);
	}

	/**
	 * Retourne l'e-mail de l'utilisateur dont le pseudo a été passé en paramètre.
	 * @param pseudo
	 * @return un String
	 * @throws SQLException
	 */
	public String getEmail(String pseudo) throws SQLException {
		PreparedStatement ps = this.prepareStatement("Select emailUt FROM UTILISATEUR WHERE pseudoUt = ?");
		ps.setString(1, pseudo);
		ResultSet r = ps.executeQuery();
		r.next();
		return r.getString(1);
	}

	/**
	 * Méthode permettant de modifier le mot de passe de l'utilisateur dont le pseudo a été passé en paramètre.
	 * @param pseudo
	 * @param mdp
	 * @throws SQLException
	 */
	public void modifierMDP(String pseudo, String mdp) throws SQLException {
		PreparedStatement ps = this.prepareStatement("UPDATE UTILISATEUR SET mdpUt = ? WHERE pseudoUt = ?");
		ps.setString(1, this.cryptPass(mdp));
		ps.setString(2, pseudo);
		ps.executeUpdate();
	}

	/**
	 * Méthode permettant de modifier l'e-mail de l'utilisateur dont le pseudo a été passé en paramètre.
	 * @param pseudo
	 * @param email
	 * @throws SQLException
	 */
	public void modifierEmail(String pseudo, String email) throws SQLException {
		PreparedStatement ps = this.prepareStatement("UPDATE UTILISATEUR SET emailUt = ? WHERE pseudoUt = ?");
		ps.setString(1, email);
		ps.setString(2, pseudo);
		ps.executeUpdate();
	}

	/**
	 * Méthode permettant de modifier l'avatar de l'utilisateur dont le pseudo a été passé en paramètre.
	 * @param pseudo
	 * @param image
	 * @throws SQLException
	 */
	public void modifierAvatar(String pseudo, byte[] image) throws SQLException {
		PreparedStatement ps = this.prepareStatement("UPDATE UTILISATEUR SET avatarUt = ? WHERE pseudoUt = ?");
		ps.setBytes(1, image);
		ps.setString(2, pseudo);
		ps.executeUpdate();
	}

	/**
	 * Retourne une liste des Utilisateurs qui ne sont pas amis avec l'utilisateur dont le pseudo a été passé
	 * en paramètre et dont le pseudo commence par la recherche.
	 * @param pseudo
	 * @param search
	 * @return une liste d'utilisateur
	 * @throws SQLException
	 */
	public List<Utilisateur> rechercheUtilisateurNonAmis(String pseudo, String search) throws SQLException {
		List<Utilisateur> res = new ArrayList<Utilisateur>();
		PreparedStatement ps = this.prepareStatement("SELECT idUt, pseudoUt, emailUt, activeUt, avatarUt, nomRole, ratioUt FROM UTILISATEUR WHERE pseudoUt LIKE ? AND idUt NOT IN (SELECT idUt FROM UTILISATEUR WHERE idUt IN (SELECT idUt1 FROM UTILISATEUR NATURAL JOIN ETREAMI WHERE pseudoUt = ?)) AND idUt NOT IN(SELECT idUt FROM UTILISATEUR WHERE pseudoUt = ?)");
		ps.setString(2, pseudo);
		ps.setString(1, "%"+search + "%");
		ps.setString(3, pseudo);
		ResultSet r = ps.executeQuery();
		while (r.next()) {
			res.add(this.nouvelUtilisateur(r));
		}
		return res;
	}

	/**
	 * Méthode permettant d'ajouter un ami.
	 * @param idUt
	 * @param idAmis
	 * @throws SQLException
	 */
	public void ajouterAmis(int idUt, int idAmis) throws SQLException {
		PreparedStatement ps = this.prepareStatement("INSERT INTO INVITATION VALUES(?, ?, 't', ?, ?)");
		PreparedStatement ps2 = this.prepareStatement("select max(idInv) from INVITATION");
		ResultSet rs = ps2.executeQuery();
		rs.next();
		ps.setInt(1, rs.getInt(1) + 1);
		ps.setDate(2, new Date(System.currentTimeMillis()));
		ps.setInt(3, idUt);
		ps.setInt(4, idAmis);
		ps.executeUpdate();

		Statement s = this.createStatement();
		s.executeUpdate("CALL completeEtreAmi()");
	}

	/**
	 * Méthode permettant de supprimer un ami.
	 * @param idUt
	 * @param idAmis
	 * @throws SQLException
	 */
	public void supprimerAmis(int idUt, int idAmis) throws SQLException {
		PreparedStatement ps = this.prepareStatement("CALL supprimerAmis(?, ?)");
		ps.setInt(1, idUt);
		ps.setInt(2, idAmis);
		ps.executeUpdate();
	}

	/**
	 * Retourne la liste des message qui ont été envoyé par un expediteur précis à un destinataire précis et inversement.
	 * @param expediteur
	 * @param destinataire
	 * @return une liste de message
	 * @throws SQLException
	 */
	public List<Message> recupMessage(String expediteur, String destinataire) throws SQLException {
		List<Message> val = new ArrayList<Message>();

		PreparedStatement ps = this.prepareStatement("SELECT m1.dateMsg, p1.pseudoUt, p2.pseudoUt, contenuMsg "
				+ "FROM MESSAGE m1 NATURAL JOIN UTILISATEUR p1, UTILISATEUR p2 "
				+ "WHERE (m1.idUt = (Select idUt FROM UTILISATEUR WHERE pseudoUt = ?) "
				+ "OR m1.idUt = (SELECT idUt FROM UTILISATEUR WHERE pseudoUt = ?)) "
				+ "AND (m1.idUt1 = (Select idUt FROM UTILISATEUR WHERE pseudoUt = ?) "
				+ "OR m1.idUt1 = (SELECT idUt FROM UTILISATEUR WHERE pseudoUt = ?)) "
				+ "AND p1.idUt = m1.idUt AND p2.idUt = m1.idUt1 AND p1.idUt != p2.idUt "
				+ "ORDER BY m1.dateMsg");
		ps.setString(1, expediteur);
		ps.setString(2, destinataire);
		ps.setString(3, expediteur);
		ps.setString(4, destinataire);
		ResultSet res = ps.executeQuery();

		while (res.next()) {
			val.add(new Message(res.getDate(1), res.getString(2), res.getString(3), res.getString(4)));
		}

		return val;
	}

	/**
	 * Méthode permettant d'envoyer un message.
	 * @param contenu
	 * @param idExpediteur
	 * @param idDestinataire
	 * @throws SQLException
	 */
	public void envoyerMessage(String contenu, int idExpediteur, int idDestinataire) throws SQLException {
		Statement s = this.createStatement();
		ResultSet res = s.executeQuery("SELECT MAX(idMsg) FROM MESSAGE");

		res.next();

		int i = res.getInt(1);

		PreparedStatement ps = this.prepareStatement("INSERT INTO MESSAGE VALUES(?, NOW(), ?, 't', ?, ?)");
		ps.setInt(1, i + 1);
		ps.setString(2, contenu);
		ps.setInt(3, idExpediteur);
		ps.setInt(4, idDestinataire);

		ps.executeUpdate();

	}

	/**
	 * Retourne la liste des jeux contenu dans la base données.
	 * @return une liste de jeux
	 * @throws SQLException
	 */
	public List<Jeu> getJeux() throws SQLException {
		List<Jeu> ls = new ArrayList<Jeu>();

		Statement s = this.createStatement();
		ResultSet res = s.executeQuery("SELECT nomJeu, regleJeu, image, nomTy, activeJeu, idJeu, ClasseLanceur, idTy FROM JEU NATURAL JOIN TYPEJEU");
		boolean activer;
		while (res.next()) {
			Image av = new Image(new ByteArrayInputStream(res.getBytes(3)));
			if (res.getString(5).equals("t")) {
				activer = true;
			} else {
				activer = false;
			}

			ls.add(new Jeu(res.getInt(6), res.getString(1), res.getString(4), res.getString(2), av, activer, res.getString(7), res.getInt(8)));

		}
		return ls;
	}

	/**
	 * Méthode permettant de donner un mot de passe provisoire à un utilisateur.
	 * @param pseudo
	 * @param code
	 * @throws SQLException
	 */
	public void ajoutMotDePasseProvisoire(String pseudo, String code) throws SQLException {
		PreparedStatement s = this.prepareStatement("INSERT INTO MOTDEPASSEOUBLIE VALUES(?, NOW(), ?)");
		s.setString(1, code);
		s.setString(2, pseudo);

		s.executeUpdate();
	}

	/**
	 * Méthode permettant de supprimer le mot de passe provisoire associé à un utilisateur.
	 * @param pseudo
	 * @throws SQLException
	 */
	public void suppressionMotDePasseProvisoire(String pseudo) throws SQLException {
		PreparedStatement s = this.prepareStatement("DELETE FROM MOTDEPASSEOUBLIE WHERE pseudoUtilisateur = ? ");
		s.setString(1, pseudo);

		s.executeUpdate();
	}

	/**
	 * Méthode permettant de savoir si le code passé en paramètre est le bon.
	 * @param pseudo
	 * @param code
	 * @return un booléen
	 * @throws SQLException
	 */
	public Boolean codeProvisoireCorrect(String pseudo, String code) throws SQLException {
		PreparedStatement s = this.prepareStatement("SELECT codeProvisoire FROM MOTDEPASSEOUBLIE WHERE pseudoUtilisateur = ?");
		s.setString(1, pseudo);

		ResultSet res = s.executeQuery();

		res.next();

		return res.getString(1).equals(code);
	}


	/**
	 * Retourne la liste des invitations reçue par l'utilisateur passé en parametre
	 *
	 * @param utilisateur
	 * @return une liste d'invitation
	 * @throws SQLException
	 */
	public List<Invitation> getInvitations(Utilisateur utilisateur) throws SQLException {
		List<Invitation> res = new ArrayList<>();
		PreparedStatement ps = this.prepareStatement("select idInv, dateInv, idUt, idUt1 from INVITATION where idUt1 = ?");
		ps.setInt(1, utilisateur.getId());
		ResultSet rs = ps.executeQuery();
		Utilisateur envoyeur, destinataire;
		while (rs.next()) {
			envoyeur = this.chercherUtilisateur(rs.getInt(3));
			destinataire = this.chercherUtilisateur(rs.getInt(4));
			Invitation v = new Invitation(rs.getInt(1), rs.getDate(2), envoyeur, destinataire);
			res.add(v);
		}
		return res;
	}

	/**
	 * Accepte l'invitation passer en paramêtre et la supprime de la base de donnée
	 * Les deux joueur devienne amis
	 *
	 * @param invit
	 * @throws SQLException
	 */
	public void accepterInvit(Invitation invit) throws SQLException {
		PreparedStatement ps = this.prepareStatement("INSERT INTO ETREAMI VALUES(?, ?)");
		ps.setInt(1, invit.getEnvoyeur().getId());
		ps.setInt(2, invit.getDestinataire().getId());
		ps.executeUpdate();
		Statement s = this.createStatement();
		s.executeUpdate("CALL completeEtreAmi()");
		s.executeUpdate("DELETE from INVITATION where idInv = " + invit.getId());
	}

	/**
	 * Refuse l'invitation passer en paramêtre et la supprime de la base de donnée
	 *
	 * @param invit
	 * @throws SQLException
	 */
	public void refuserInvit(Invitation invit) throws SQLException {
		Statement s = this.createStatement();
		s.executeUpdate("DELETE from INVITATION where idInv = " + invit.getId());
	}

        public void destactiveUtilisateur(int id) throws SQLException{

                PreparedStatement ps2 = this.prepareStatement("DELETE from INVITATION where idUt = ?");
		ps2.setInt(1, id);
		ps2.executeUpdate();
                PreparedStatement ps3 = this.prepareStatement("DELETE from INVITATION where idUt1 = ?");
		ps3.setInt(1, id);
		ps3.executeUpdate();
                PreparedStatement ps5 = this.prepareStatement("DELETE from INVITATIONPARTIE where idProprietaire = ? ");
		ps5.setInt(1, id);
		ps5.executeUpdate();
                PreparedStatement ps6 = this.prepareStatement("DELETE from INVITATIONPARTIE where idInviter = ?");
		ps6.setInt(1, id);
		ps6.executeUpdate();
                PreparedStatement ps8 = this.prepareStatement("UPDATE PARTIE SET numEtape = ?,score1 = ?, score2 = ?  where idUt1 = ?");
		ps8.setInt(1, -2);
                ps8.setInt(1, 0);
                ps8.setInt(1, 3);
                ps8.setInt(4, id);
		ps8.executeUpdate();
                PreparedStatement ps9 = this.prepareStatement("UPDATE PARTIE SET numEtape = ?,score2 = ?, score1 = ?  where idUt2 = ?");
		ps9.setInt(1, -2);
                ps9.setInt(1, 0);
                ps9.setInt(1, 3);
                ps9.setInt(4, id);
		ps9.executeUpdate();



        }

	/**
	 * Retourne la liste des invitationPartie de l'utilisateur passé en parametre
	 *
	 * @param utilisateur
	 * @return une liste d'invitationPartie
	 */
	public List<InvitationPartie> getInvitationsPartie(Utilisateur utilisateur, DuelSurLaToile duelSurLaToile) throws SQLException {
		List<InvitationPartie> res = new ArrayList<>();
		PreparedStatement ps = this.prepareStatement("select idInvitPartie, dateInvitPartie, pseudoUt, nomJeu, image "
				+ "from INVITATIONPARTIE NATURAL JOIN JEU NATURAL JOIN UTILISATEUR "
				+ "where idInviter=? and idProprietaire!=idInviter and idInviter!=idUt and idProprietaire=idUt");
		ps.setInt(1, utilisateur.getId());
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			byte[] elem = rs.getBytes(5);
			Image im = new Image(new ByteArrayInputStream(elem),120,120,false,false);
			ImageView image = new ImageView(im);
			res.add(new InvitationPartie(duelSurLaToile, rs.getInt(1), rs.getString(4), rs.getString(3), image, rs.getDate(2)));
		}
		return res;
	}

	/**
	 * Retourne la liste des partie en cours auquel participe l'utilisateur
	 *
	 * @param utilisateur
	 * @return une liste de partie en cours
	 */
	public List<PartieEnCours> getPartieEnCours(Utilisateur utilisateur, DuelSurLaToile duelSurLaToile) throws SQLException {
		List<PartieEnCours> res = new ArrayList<>();
		PreparedStatement ps = this.prepareStatement("Select idPa, debutPa, pseudoUt, score1, score2, nomJeu, image \n" +
				                                            "From PARTIE natural join JEU natural join UTILISATEUR\n" +
				                                            "Where numEtape>=0 and idUt1!=idUt and idUt1= ? And idUt2=idUt");
		ps.setInt(1, utilisateur.getId());
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			byte[] elem = rs.getBytes(7);
			Image im = new Image(new ByteArrayInputStream(elem),130,130,false,false);
			ImageView image = new ImageView(im);
			res.add(new PartieEnCours(duelSurLaToile, rs.getInt(1), rs.getString(6), rs.getString(3), image, rs.getInt(5), rs.getInt(4)));
		}
		ps = this.prepareStatement("Select idPa, debutPa, pseudoUt, score1, score2, nomJeu, image \n" +
				                          "From PARTIE natural join JEU natural join UTILISATEUR\n" +
				                          "Where numEtape>=0 and idUt1=idUt and idUt2= ? And idUt2!=idUt and idPa not in (\n" +
				                             "Select idPa\n" +
				                             "From PARTIE natural join JEU natural join UTILISATEUR\n" +
				                             "Where numEtape>=0 and idUt1!=idUt and idUt1= ? And idUt2=idUt\n" +
				                          ")");
		ps.setInt(1, utilisateur.getId());
		ps.setInt(2, utilisateur.getId());
        rs = ps.executeQuery();
		while (rs.next()) {
			byte[] elem = rs.getBytes(7);
			Image im = new Image(new ByteArrayInputStream(elem),130,130,false,false);
			ImageView image = new ImageView(im);
			res.add(new PartieEnCours(duelSurLaToile, rs.getInt(1), rs.getString(6), rs.getString(3), image, rs.getInt(5), rs.getInt(4)));
		}
		return res;
	}

	/**
	 * Retourne la liste des Partie auquel a participé l'utilisateur
	 *
	 * @param utilisateur
	 * @return une liste de partie
	 */
	public List<HistoriquePartie> getHistoriquePartie(Utilisateur utilisateur) throws SQLException{
		List<HistoriquePartie> res = new ArrayList<>();
		PreparedStatement ps = this.prepareStatement("select nomJeu, idUt1, idUt2, image, score1, score2, debutPa from" +
				" UTILISATEUR natural join PARTIE natural join JEU where idUt = ? and numEtape<0");
		ps.setInt(1, utilisateur.getId());
		ResultSet rs = ps.executeQuery();
		ImageView avatarjeu;
		String pseudo;
		while (rs.next()){
			if (rs.getInt(2)==utilisateur.getId()){
				pseudo = chercherUtilisateur(rs.getInt(3)).getPseudoUt();
			}
			else{
				pseudo = chercherUtilisateur(rs.getInt(2)).getPseudoUt();
			}
			avatarjeu = new ImageView(new Image(new ByteArrayInputStream(rs.getBytes(4))));
			res.add(new HistoriquePartie(rs.getString(1), pseudo, avatarjeu, rs.getInt(5), rs.getInt(6), rs.getDate(7)));
		}
		return res;


	}

	public String getEtat(int idPartie) throws SQLException {
		PreparedStatement ps = mysql.prepareStatement("SELECT etatPartie FROM PARTIE WHERE idPa = ?");
		ps.setInt(1, idPartie);
		ResultSet res = ps.executeQuery();
		res.next();
		return res.getString(1);
	}

	/**
	 * Supprime une invitation à une partie.
	 * @param id
	 * @throws SQLException
	 */
	public void refuserInvitPartie(Integer id) throws SQLException{
		PreparedStatement ps = this.prepareStatement("DELETE FROM INVITATIONPARTIE WHERE idInvitPartie=?");
		ps.setInt(1, id);
		ps.executeUpdate();
	}

	/**
	 * Methode permettant de disigner une partie comme totalement fini
	 * @param idPartie
	 * @throws SQLException
	 */
	public void finDuJeu(int idPartie) throws SQLException {
		PreparedStatement ps = this.prepareStatement("UPDATE PARTIE SET numEtape = -1 WHERE idPa = ?");
		ps.setInt(1, idPartie);
		ps.executeUpdate();
	}

	/**
	 * Methode permettant de designer une partie comme abandonner
	 * @param idPartie
	 * @throws SQLException
	 */
	public void abandonnerJeu(int idPartie) throws SQLException{
		PreparedStatement ps = this.prepareStatement("UPDATE PARTIE SET numEtape = -2 WHERE idPa = ?");
		ps.setInt(1, idPartie);
		ps.executeUpdate();
	}

	/**
	 * Méthode permettant d'abandonner toutes les parties pour un jeu donné.
	 * @param idJeu
	 * @throws SQLException
	 */
	public void abandonnerPartieJeuDesac(int idJeu) throws SQLException{
		PreparedStatement ps = this.prepareStatement("UPDATE PARTIE SET numEtape = -3 WHERE idJeu = ?");
		ps.setInt(1, idJeu);
		ps.executeUpdate();
	}

	/**
	 * Retourne le nom du jeu auquel on a été invité.
	 * @param idInv
	 * @return un String
	 * @throws SQLException
	 */
	public List<String> getJeuInvitationPartie(int idPa) throws SQLException {
		PreparedStatement ps = this.prepareStatement("SELECT nomJeu, ClasseLanceur FROM INVITATIONPARTIE NATURAL JOIN JEU WHERE idInvitPartie = ?");
		List<String> val = new ArrayList<String>();
		ps.setInt(1, idPa);
		ResultSet res = ps.executeQuery();
		res.next();
		val.add(res.getString(1));
		val.add(res.getString(2));
		return val;
	}
	
	public List<String> getJeuPartie(int idInv) throws SQLException {
		PreparedStatement ps = this.prepareStatement("SELECT nomJeu, ClasseLanceur FROM PARTIE NATURAL JOIN JEU WHERE idPa = ?");
		List<String> val = new ArrayList<String>();
		ps.setInt(1, idInv);
		ResultSet res = ps.executeQuery();
		res.next();
		val.add(res.getString(1));
		val.add(res.getString(2));
		return val;
	}

	/**
	 * Retourne la partie correspondant à l'identifiant passé en paramètre.
	 * @param idInv
	 * @return une InvitPartie
	 * @throws SQLException
	 */
	public InvitPartie getInvitPartieParID(int idInv) throws SQLException{
		PreparedStatement ps = this.prepareStatement("SELECT idInvitPartie, idProprietaire, idInviter, idJeu FROM INVITATIONPARTIE WHERE idInvitPartie = ?");
		ps.setInt(1, idInv);
		ResultSet res = ps.executeQuery();
		res.next();
		return new InvitPartie(res.getInt(1), res.getInt(2), res.getInt(3), res.getInt(4));
	}

	/**
	 * Méthode permettant de créer une partie.
	 * @param idInv
	 * @param app
	 * @throws Exception
	 */
	public void creationPartie(int idInv, DuelSurLaToile app) throws Exception{
		InvitPartie inv = this.getInvitPartieParID(idInv);
		List<String> val = this.getJeuInvitationPartie(idInv);
		IJeu j = (IJeu) LancerJeu.run(val.get(0), val.get(1));
		j.creerPartie(app.getUtilisateur().getId(), inv.getIdJeu(), inv.getIdProprietaire(), inv.getIdInviter(), null);
	}
	public void creationPartie2(int idPa, DuelSurLaToile app) throws Exception{
		PartieOuverte inv = this.getPartieParId(idPa);
		List<String> val = this.getJeuPartie(idPa);
		IJeu j = (IJeu) LancerJeu.run(val.get(0), val.get(1));
		j.creerPartie(app.getUtilisateur().getId(), inv.getIdJeu(), inv.getUt(), inv.getUt2(), null);
	}
	
	public PartieOuverte getPartieParId(int idPartie) throws SQLException{
		PreparedStatement ps = this.prepareStatement("SELECT idUt1, idUt2, idJeu FROM PARTIE WHERE idPa = ?");
		ps.setInt(1, idPartie);
		ResultSet res = ps.executeQuery();
		res.next();
		return new PartieOuverte(idPartie, res.getInt(3), res.getInt(1), res.getInt(2));
	}

	/**
	 * Méthode permettant de rejoindre une partie.
	 * @param idPartie
	 * @param app
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void rejoindrePartie(int idPartie, DuelSurLaToile app) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, IOException{
		PreparedStatement ps = this.prepareStatement("select idJeu, nomJeu, ClasseLanceur " +
				"from JEU natural join PARTIE " +
				"where idPa=?");
		ps.setInt(1, idPartie);
		ResultSet res = ps.executeQuery();

		res.next();

		IJeu j = (IJeu) LancerJeu.run(res.getString(2), res.getString(3));
		Platform.setImplicitExit(false);



		new Thread(() -> {
			Platform.runLater(() -> {
				try {
					j.rejoindrePartieEnCours(app.getUtilisateur().getId(), res.getInt(1), idPartie);
				} catch (ClassNotFoundException | SQLException | IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			});
		}).start();

	}

	/**
	 * Méthode prenant en paramètre une recherche sous forme de String. Elle permet de trouver la liste des jeux
	 * dont le nom commence par le String passé en paramètre.
	 * @param search
	 * @return une liste de jeux
	 * @throws SQLException
	 */
	public List<Jeu> rechercherJeux(String search) throws SQLException{
		List<Jeu> res = new ArrayList<Jeu>();
		PreparedStatement ps = this.prepareStatement("SELECT nomJeu, regleJeu, image, activeJeu, idJeu, ClasseLanceur, idTy,nomTy FROM JEU natural join TYPEJEU WHERE nomJeu LIKE ?");
		ps.setString(1, "%"+search + "%");
		ResultSet r = ps.executeQuery();
		Image image;
		boolean activer;
		while (r.next()) {
			image = new Image(new ByteArrayInputStream(r.getBytes(3)));
			if (r.getString(4).equals("t")) {
				activer = true;
			} else {
				activer = false;
			}

			Jeu v = new Jeu(r.getInt(5), r.getString(1),r.getString(8) , r.getString(2), image, activer, r.getString(6), r.getInt(7));
			res.add(v);
		}
		return res;
	}

	/**
	 * Méthode créant un utilisateur.
	 * @param r
	 * @return un utilisateur
	 */
	public Utilisateur nouvelUtilisateur(ResultSet r) {
		Utilisateur v = null;
		try {
			Boolean activer;
			if (r.getString(4).equals("t")) {
				activer = true;
			} else {
				activer = false;
			}
			Image avatarImage = new Image(new ByteArrayInputStream(r.getBytes(5)));
			v = new Utilisateur(r.getInt(1), r.getString(2), r.getString(3), activer, avatarImage, r.getString(6), r.getInt(7));

		} catch (SQLException e) {
			Alerte.creationAlerte(e);
		}
		return v;
	}

	/**
	 * Méthode permettant de changer l'activité du jeu.
	 * @param id
	 * @param act
	 * @throws SQLException
	 */
	public void changementActiviteJeu(int id, boolean act) throws SQLException {
		PreparedStatement ps = this.prepareStatement("UPDATE JEU SET activeJeu = ? WHERE idJeu = ?");
		if (act) {
			ps.setString(1, "t");
		} else {
			ps.setString(1, "f");
		}
		ps.setInt(2, id);

		ps.executeUpdate();
	}

	/**
	 * Retourne la liste des types de jeu.
	 * @return une liste de type de jeu
	 * @throws SQLException
	 */
	public List<TypeJeu> getTypeJeu() throws SQLException {
		Statement s = this.createStatement();
		ResultSet res = s.executeQuery("SELECT * FROM TYPEJEU");
		List<TypeJeu> val = new ArrayList<TypeJeu>();

		while (res.next()) {
			TypeJeu t = new TypeJeu(res.getInt(1), res.getString(2));
			val.add(t);
		}

		return val;
	}

	/**
	 * Méthode permettant l'ajout de jeux.
	 * @param nomJeu
	 * @param regleJeu
	 * @param jarJeu
	 * @param image
	 * @param idType
	 * @throws SQLException
	 * @throws IOException
	 */
	public void ajoutJeu(String nomJeu, String regleJeu, File jarJeu, File image, int idType) throws SQLException, IOException {
		PreparedStatement ps = this.prepareStatement("INSERT INTO JEU(idJeu, nomJeu, regleJeu, jarJeu, activeJeu, image, idTy) "
				+ "SELECT MAX(idJeu)+1, ?, ?, ?, ?, ?, ? FROM JEU");

		byte[] jar = Files.readAllBytes(Paths.get(jarJeu.getPath()));
		byte[] im = Files.readAllBytes(Paths.get(image.getPath()));

		ps.setString(1, nomJeu);
		ps.setString(2, regleJeu);
		ps.setBytes(3, jar);
		ps.setString(4, "f");
		ps.setBytes(5, im);
		ps.setInt(6, idType);

		ps.executeUpdate();
	}

	/**
	 * Méthode permettant la modification d'un jeu
	 * @param idJeu
	 * @param nomJeu
	 * @param regleJeu
	 * @param jarJeu
	 * @param image
	 * @param idType
	 * @throws SQLException
	 * @throws IOException
	 */
	public void modifierJeu(int idJeu, String nomJeu, String regleJeu, File jarJeu, File image, int idType) throws SQLException, IOException {
		if (jarJeu == null && image != null) {
			PreparedStatement ps = this.prepareStatement("UPDATE JEU SET nomJeu = ?, regleJeu = ?, image = ?, idTy = ? WHERE idJeu = ?");

			byte[] im = Files.readAllBytes(Paths.get(image.getPath()));

			ps.setString(1, nomJeu);
			ps.setString(2, regleJeu);
			ps.setBytes(3, im);
			ps.setInt(4, idType);
			ps.setInt(5, idJeu);
			ps.executeUpdate();

		} else if (image == null && jarJeu != null) {
			PreparedStatement ps = this.prepareStatement("UPDATE JEU SET nomJeu = ?, regleJeu = ?, jarJeu = ?, idTy = ? WHERE idJeu = ?");

			byte[] jar = Files.readAllBytes(Paths.get(jarJeu.getPath()));

			ps.setString(1, nomJeu);
			ps.setString(2, regleJeu);
			ps.setBytes(3, jar);
			ps.setInt(4, idType);
			ps.setInt(5, idJeu);
			ps.executeUpdate();
		} else if (image != null && jarJeu != null) {
			PreparedStatement ps = this.prepareStatement("UPDATE JEU SET nomJeu = ?, regleJeu = ?, jarJeu = ?, image = ?, idTy = ? WHERE idJeu = ?");

			byte[] jar = Files.readAllBytes(Paths.get(jarJeu.getPath()));
			byte[] im = Files.readAllBytes(Paths.get(image.getPath()));

			ps.setString(1, nomJeu);
			ps.setString(2, regleJeu);
			ps.setBytes(3, jar);
			ps.setBytes(4, im);
			ps.setInt(5, idType);
			ps.setInt(6, idJeu);
			ps.executeUpdate();
		} else if (image == null && jarJeu == null) {
			PreparedStatement ps = this.prepareStatement("UPDATE JEU SET nomJeu = ?, regleJeu = ?, idTy = ? WHERE idJeu = ?");

			System.out.println(idType + 1 + " " + idJeu);

			ps.setString(1, nomJeu);
			ps.setString(2, regleJeu);
			ps.setInt(3, idType);
			ps.setInt(4, idJeu);

			ps.executeUpdate();
		}
	}

	/**
	 * Méthode permettant de modifier un utilisateur.
	 * @param id
	 * @param pseudo
	 * @param mail
	 * @param active
	 * @param role
	 * @throws SQLException
	 */
	public void modifierUtilisateur(int id, String pseudo, String mail,String active,String role) throws SQLException{
                PreparedStatement ps = this.prepareStatement("UPDATE UTILISATEUR SET pseudoUt = ?, emailUt = ?, activeUt= ?, nomRole = ? where idUt = ?");
		ps.setString(1, pseudo);
		ps.setString(2, mail);
		ps.setString(3, active);
		ps.setString(4, role);
		ps.setInt(5, id);
		ps.executeUpdate();
	}

	/**
	 * Retourne le fichier jar du jeu.
	 * @param idJeu
	 * @param nomFic
	 */
	public void getJar(int idJeu, String nomFic){
		File file = new File ("jar/"+nomFic+".jar");
		try{
			FileOutputStream writer = new FileOutputStream (file);
			try{
				Blob b;
				try{
					PreparedStatement ps= mysql.prepareStatement("select jarJeu from JEU where idJeu=?");
					ps.setInt(1,idJeu);
					ResultSet rs=ps.executeQuery();
					rs.next();
					b=rs.getBlob(1);
					System.out.println(idJeu + " "+ nomFic);
					writer.write(b.getBytes(1,(int)b.length()));
				}catch (SQLException exception){
					System.out.println("Erreur SQL : " + exception.getMessage());
				}

				writer.close();
			}catch (IOException exception){
				System.out.println("Erreur lors de la lecture : " + exception.toString());
			}
		}catch (FileNotFoundException exception){
			System.out.println ("Le fichier n'a pas été trouvé");
		}

	}

	/**
	 * Retourne le nombre de partie pour tout les jeux (paramètre = null) ou pour un jeu donné
	 * @param jeu
	 * @return un int
	 * @throws SQLException
	 */
	public int getNbPartie(Jeu jeu) throws SQLException {

		ResultSet rs = null;
		if (jeu != null) {
			PreparedStatement ps = this.prepareStatement("SELECT COUNT(idPa) FROM PARTIE WHERE numEtape>-1 AND idJeu in (SELECT idJeu FROM JEU WHERE nomJeu = ? )");
			ps.setString(1, jeu.getNom());
			rs = ps.executeQuery();
			rs.next();
		} else {
			Statement ps = this.createStatement();
			rs = ps.executeQuery("SELECT COUNT(idPa) FROM PARTIE WHERE numEtape>-1");
			rs.next();
		}
		return rs.getInt(1);
	}

	/**
	 * Retourne le nombre d'utilisateur de la plateforme.
	 * @return un int
	 * @throws SQLException
	 */
	public int getNbUtilisateur() throws SQLException{
		Statement ps =this.createStatement();
		ResultSet rs=ps.executeQuery("SELECT COUNT(idUt) FROM UTILISATEUR");
		rs.next();
		return rs.getInt(1);
	}

	/**
	 * Retourne le nombre de joueurs bannis
	 * @return un int
	 * @throws SQLException
	 */
	public int getNbBannis() throws SQLException{
		Statement ps=this.createStatement();
		ResultSet rs=ps.executeQuery("SELECT COUNT(idUt) FROM UTILISATEUR WHERE activeUt='f'");
		rs.next();
		return rs.getInt(1);
	}

	/**
	 * Retourne le nombre de message.
	 * @return un int
	 * @throws SQLException
	 */
	public int getNbMessage() throws SQLException{
		PreparedStatement ps=this.prepareStatement("SELECT COUNT(idMsg) FROM MESSAGE");
		ResultSet rs=ps.executeQuery();
		rs.next();
		return rs.getInt(1);
	}

	/**
	 * Retourne la liste des parties ouvertes
	 * @return une liste des parties ouvertes
	 * @throws SQLException
	 */
	public List<PartieOuverte> getPartieOuverte(Jeu jeu, Utilisateur util) throws SQLException{
		List<PartieOuverte> res = new ArrayList<>();
		Statement s = this.createStatement();
		ResultSet rs = s.executeQuery("select idPa, idJeu, idUt1, nomJeu from PARTIE natural join JEU where idUt2=idUt1 and idJeu="+jeu.getId());
		ResultSet rs2;
		while(rs.next()) {
			Statement s2 = this.createStatement(); // Ici.
			rs2 = s2.executeQuery("select pseudoUt, avatarUt from UTILISATEUR where idUt="+rs.getInt(3));
			rs2.next();
			ImageView image =new ImageView(new Image(new ByteArrayInputStream(rs2.getBytes(2))));
			String pseudo = rs2.getString(1);
			if (rs.getInt(3)!=util.getId()) {
				res.add(new PartieOuverte(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), pseudo, image));
			}
		}
		System.out.println(res);
		return res;
	}

	public void rejoindrePartieOuverte(PartieOuverte partie, Utilisateur util) throws SQLException{
		Statement s = this.createStatement();
		s.executeUpdate("update PARTIE set idUt2 ="+util.getId()+",debutPa = NOW() where idPa ="+partie.getIdPartie());
	}


	public void nouvellePartieOuverte(int idUt, int idJeu) throws SQLException{
		Statement s = this.createStatement();
		ResultSet rs = s.executeQuery("select max(idPa) from PARTIE");
		rs.next();
		int id = rs.getInt(1)+1;
		s.executeUpdate("insert into PARTIE values ("+id+", NOW(), 0, NULL,"+idJeu+", "+idUt+", 0, "+idUt+",0)");
	}

	/**
	 * Méthode permettant de créer une invitation à une partie.
	 * @param idUt1
	 * @param idUt2
	 * @param idJeu
	 * @throws SQLException
	 */
	public void nouvelleInvitPartie(int idUt1, int idUt2, int idJeu) throws SQLException{
		Statement s = this.createStatement();
		ResultSet rs = s.executeQuery("select max(idInvitPartie) from INVITATIONPARTIE");
		rs.next();
		int idInv = rs.getInt(1)+1;
		s.executeUpdate("insert into INVITATIONPARTIE values ("+idInv+", "+idUt1+","+idUt2+","+idJeu+", NOW())");
	}

	/**
	 * Méthode permettant de changer l'état de connexion de l'utilisateur.
	 * @param idUt
	 * @param co
	 * @throws SQLException
	 */
	public void changerConnexionUtilisateur(int idUt, boolean co) throws SQLException {
		PreparedStatement ps = this.prepareStatement("UPDATE UTILISATEUR SET connecter = ? WHERE idUt = ?");

		ps.setBoolean(1, co);
		ps.setInt(2, idUt);

		ps.executeUpdate();
	}

	public int getNbUtilCo() throws SQLException {
		PreparedStatement ps = this.prepareStatement("SELECT COUNT(idUt) FROM UTILISATEUR WHERE connecter=true");
		ResultSet rs = ps.executeQuery();
		rs.next();
		return rs.getInt(1);
	}

	/**
	 * Méthode permettant de crypter le mot de passe.
	 * @param motDePasse
	 * @return un String
	 */
	public String cryptPass(String motDePasse) {
		/**
		 * Méthode prenant en paramètre un mot de passe sous forme de String.
		 * Elle retourne le mot de passe crypté sous forme de String.
		 */
		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] bytes = md.digest(motDePasse.getBytes());
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return generatedPassword;
	}




}
