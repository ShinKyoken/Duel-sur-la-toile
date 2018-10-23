package Jeux.Mastermind;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Classe du mastermind
 *
 */
public class Mastermind {

	/**
	 * Un entier correspondant à la taille du plateau du mastermind
	 */
	private int taille;

	/**
	 * Le nombre de couleur autoriser
	 */
	private int nbCouleurs;

	/**
	 * Le nombre de tentative réaliser
	 */
	private int nbTentative;

	/**
	 * Une liste de tentative
	 */
	private List<Couple> tentative;

	/**
	 * Une liste de joueur
	 */
	private List<Integer> listeJ;

	/**
	 * Une combinaison
	 */
	private Combinaison comb;

	/**
	 * La combinaison de reference
	 */
	private Combinaison combRef;

	/**
	 * Variable indiquant le joueur en jeu
	 */
	private int joueurEnJeu;

	/**
	 * Entier indiquant le numéro de la derniere tentative
	 */
	private int numTentative;

	/**
	 * Variable indiquant le joueur gagnant
	 */
	private int joueurGagnant;

	/**
	 * Grille du jeu durant la partie
	 */
	private List<List<Integer>> grille;

	/**
	 * Score du joueur 1
	 */
	private int scoreJ1;

	/**
	 * Score du joueur 2
	 */
	private int scoreJ2;

	/**
	 * IHM du mastermind
	 */
	private VueMastermind app;

	/**
	 * Identifiant du jeu
	 */
	private int idJeu;

	/**
	 * Identifiant du joueur 1
	 */
	private int idJ1;

	/**
	 * Identifiant du joueur 2
	 */
	private int idJ2;

	/**
	 * Identifiant du joueur sur le PC
	 */
	private int idPC;

	/**
	 * Identifiant de la partie
	 */
	private int idPartie;

	/**
	 * Le thread de verification de tours
	 */
	private Thread t;

	/**
	 * Le thread de verification d'abandon
	 */
	private Thread u;

	/**
	 * Nombre de manche sur la partie actuel
	 */
	private int numManche;

	/**
	 * Connexion à la base de donnée
	 */
	private Etat et;

	/**
	 * Constructeur du jeu
	 * @param idJeu
	 * @param tailleCombinaison
	 * @param nbCouleurs
	 * @param nbTentatives
	 * @param J1
	 * @param J2
	 * @param idPC
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	public Mastermind(int idJeu, int tailleCombinaison, int nbCouleurs, int nbTentatives, int J1, int J2, int idPC) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException, InterruptedException, ParseException {
		this.taille = tailleCombinaison;
		this.nbCouleurs = nbCouleurs;
		this.nbTentative = nbTentatives;
		this.tentative = new ArrayList<Couple>();
		this.comb = new Combinaison(tailleCombinaison);
		this.comb.genere(nbCouleurs);
		this.combRef = comb;
		this.idJ1 = J1;
		this.idJ2 = J2;
		this.listeJ = Arrays.asList(this.idJ1, this.idJ2);
		this.joueurEnJeu = this.idJ1;
		this.joueurGagnant = 0;
		this.numTentative = 0;
		this.grille = new ArrayList<List<Integer>>();
		this.idJeu = idJeu;
		this.idPC = idPC;
		this.numManche = 1;
		this.et = new Etat();
	}

	/**
	 * Constructeur du mastermind en version IHM
	 * @param app
	 * @param idJeu
	 * @param tailleCombinaison
	 * @param nbCouleurs
	 * @param nbTentatives
	 * @param J1
	 * @param J2
	 * @param idPC
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	public Mastermind(VueMastermind app, int idJeu, int tailleCombinaison, int nbCouleurs, int nbTentatives, int J1, int J2, int idPC) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException, InterruptedException, ParseException {
		this.taille = tailleCombinaison;
		this.nbCouleurs = nbCouleurs;
		this.nbTentative = nbTentatives;
		this.tentative = new ArrayList<Couple>();
		this.comb = new Combinaison(tailleCombinaison);
		this.comb.genere(nbCouleurs);
		this.combRef = comb;
		this.idJ1 = J1;
		this.idJ2 = J2;
		this.listeJ = Arrays.asList(this.idJ1, this.idJ2);
		this.joueurEnJeu = this.idJ1;
		this.joueurGagnant = 0;
		this.numTentative = 0;
		this.grille = new ArrayList<List<Integer>>();
		this.app = app;
		this.idJeu = idJeu;
		this.idPC = idPC;
		this.numManche = 1;
		this.et = new Etat();
		verifAbandon();
		if(this.idPC != this.joueurEnJeu) {
			this.app.getPlat().getValider().setDisable(true);
			this.app.getPlat().getBarre().bloquerBarre();
		}
		verifTours();
	}

	/**
	 * Constructeur du mastermind en version IHM prenant en parametre une partie
	 * deja en cours
	 * @param app
	 * @param idJeu
	 * @param json
	 * @param idPC
	 * @param idPartie
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	Mastermind(VueMastermind app, int idJeu, String json, int idPC, int idPartie) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException, InterruptedException, ParseException {
		this.app = app;
		this.idJeu = idJeu;
		this.idPC = idPC;
		this.idPartie = idPartie;
		this.tentative = new ArrayList<Couple>();
		this.grille = new ArrayList<List<Integer>>();
		this.et = new Etat();
		setEtat(json);
		verifAbandon();
		if(this.idPC != this.joueurEnJeu) {
			this.app.getPlat().getValider().setDisable(true);
			this.app.getPlat().getBarre().bloquerBarre();

		}
		verifTours();
	}

	/**
	 * Methode permettant de creer une sauvegarde de la partie en cours
	 * 
	 * @return
	 */
	public String getEtat() {
		JSONObject res = new JSONObject();
		res.put("joueurCourant", this.joueurEnJeu);
		res.put("joueurGagnant", this.joueurGagnant);
		res.put("taille", this.taille);
		res.put("numManche", this.numManche);
		res.put("numTour", this.numTentative);
		res.put("nombreTentativeMax", this.getNbTentatives());
		res.put("joueurs", this.listeJ);
		res.put("scoreJ1", this.scoreJ1);
		res.put("scoreJ2", this.scoreJ2);

		JSONArray gr = new JSONArray();

		for (int i = 0; i < this.tentative.size(); i++) {
			List<List<Integer>> tentativeLs = new ArrayList<List<Integer>>();
			Couple bm = (Couple) this.getUneTentative(i).getVal2();
			List<Integer> valueBM = new ArrayList<Integer>();

			valueBM.add((Integer) bm.getVal1());
			valueBM.add((Integer) bm.getVal2());

			tentativeLs.add(((Combinaison) this.getUneTentative(i).getVal1()).getCombi());
			tentativeLs.add(valueBM);
			gr.add(tentativeLs);
		}

		JSONArray combRef = new JSONArray();
		combRef.add(this.combRef.getCombi());
		res.put("grille", gr);
		res.put("combRef", combRef);
		return res.toString();
	}

	/**
	 * Methode permettant d'implementer une sauvegarde dans la partie
	 * 
	 * @param json
	 */
	public void setEtat(String json) {
		JSONParser p = new JSONParser();
		try {
			this.tentative = new ArrayList<Couple>();
			this.grille = new ArrayList<List<Integer>>();

			JSONObject res = (JSONObject) p.parse(json);
			this.joueurEnJeu = ((Long) res.get("joueurCourant")).intValue();
			this.joueurGagnant = 0;
			this.taille = ((Long) res.get("taille")).intValue();
			this.numTentative = ((Long) res.get("numTour")).intValue();
			this.nbTentative = ((Long) res.get("nombreTentativeMax")).intValue();
			this.scoreJ1 = ((Long) res.get("scoreJ1")).intValue();
			this.scoreJ2 = ((Long) res.get("scoreJ2")).intValue();
			this.numManche = ((Long) res.get("numManche")).intValue();

			Combinaison comb = new Combinaison(this.taille);
			JSONArray combR = (JSONArray) res.get("combRef");

			List<Integer> x = new ArrayList<Integer>();

			for (int i = 0; i < combR.size(); i++) {
				JSONArray pions = (JSONArray) combR.get(i);
				for (int j = 0; j < pions.size(); j++) {
					x.add(((Long) pions.get(j)).intValue());
				}

			}

			JSONArray lsJ = (JSONArray) res.get("joueurs");
			List<Integer> player = new ArrayList<Integer>();

			for (int i = 0; i < lsJ.size(); i++) {
				player.add(((Long) lsJ.get(i)).intValue());
			}

			this.listeJ = player;
			this.idJ1 = player.get(0);
			this.idJ2 = player.get(1);

			List<Integer> couleurs = new ArrayList<Integer>();
			comb.saisirCombinaison(x);
			this.combRef = comb;

			List<Integer> pList = new ArrayList<Integer>();
			List<Integer> vList = new ArrayList<Integer>();

			JSONArray gr = (JSONArray) res.get("grille");

			for (int i = 0; i < gr.size(); i++) {
				JSONArray tent = (JSONArray) gr.get(i);

				JSONArray lsPions = (JSONArray) tent.get(0);
				JSONArray bm = (JSONArray) tent.get(1);

				for (int j = 0; j < lsPions.size(); j++) {
					pList.add(((Long) lsPions.get(j)).intValue());
				}

				for (int j = 0; j < bm.size(); j++) {
					vList.add(((Long) bm.get(j)).intValue());
				}

				Combinaison c = new Combinaison(4);
				c.saisirCombinaison(pList);

				Couple coupleBM = new Couple(vList.get(0), vList.get(1));

				Couple coupleTentative = new Couple(c, coupleBM);

				this.tentative.add(coupleTentative);

				pList = new ArrayList<Integer>();
				vList = new ArrayList<Integer>();

			}

			for (int i = 0; i < this.numTentative; i++) {

				Couple dataTent = (Couple) this.tentative.get(i);
				Couple infoBonMauvais = (Couple) dataTent.getVal2();

				HashMap<Integer, Color> IDColor = new HashMap<Integer, Color>();

				IDColor.put(0, Color.BLACK);
				IDColor.put(1, Color.RED);
				IDColor.put(2, Color.YELLOW);
				IDColor.put(3, Color.GREEN);
				IDColor.put(4, Color.BLUE);
				IDColor.put(5, Color.PURPLE);
				IDColor.put(6, Color.WHITE);

				Combinaison color = (Combinaison) this.tentative.get(i).getVal1();
				List<Integer> colorList = color.getCombi();


				app.getPlat().editLigne((this.nbTentative - i) - 1, IDColor.get(colorList.get(0)),
						IDColor.get(colorList.get(1)), IDColor.get(colorList.get(2)), IDColor.get(colorList.get(3)));

				VBox mauvaisPoint = (VBox) app.getPlat().getValeurLigneMauvaisPions().getChildren().get(0);

				VueNombrePions vm = new VueNombrePions();
				vm.setVal(String.valueOf(infoBonMauvais.getVal2()));

				mauvaisPoint.getChildren().set((getNbTentatives() - 1) - i, vm);

				app.getPlat().getValeurLigneMauvaisPions().getChildren().set(0, mauvaisPoint);

				VueNombrePions vb = new VueNombrePions();
				vb.setVal(String.valueOf(infoBonMauvais.getVal1()));

				app.getPlat().getValeurLigneBonPions().getChildren().set((getNbTentatives() - 1) - i, vb);
			}

			if (getJoueurEnJeu() == 1) {
				app.getPlat().getIHM().getLeft().getAvatar().setEnjeu(true);
				app.getPlat().getIHM().getRight().getAvatar().setEnjeu(false);
			} else {
				app.getPlat().getIHM().getLeft().getAvatar().setEnjeu(false);
				app.getPlat().getIHM().getRight().getAvatar().setEnjeu(true);
			}

			app.getPlat().getIHM().getLeft().getScore().setText(String.valueOf(this.scoreJ1));
			app.getPlat().getIHM().getRight().getScore().setText(String.valueOf(this.scoreJ2));
		} catch (ParseException e) {
			System.out.println(e);
		}
	}

	/**
	 * Methode permettant de recuperer la liste de joueur en partie
	 * 
	 * @return
	 */
	public List<Integer> getListeJ() {
		return listeJ;
	}

	/**
	 * Methode permettant de recuperer le joueur en jeu
	 * 
	 * @return
	 */
	public int getJoueurEnJeu() {
		return this.joueurEnJeu;
	}

	/**
	 * Methode permettant d'attribuer le tours au prochain joueur
	 */
	public int setJoueurSuivant() {
		if (this.getJoueurEnJeu() == this.idJ1) {
			return this.idJ2;
		}
		return this.idJ1;

	}

	/**
	 * Methode permettant de recuperer le nombre de tentative maximum
	 * 
	 * @return
	 */
	public int getNbTentatives() {
		return this.nbTentative;
	}

	/**
	 * Methode permettant de recuperer la taille du plateau
	 * 
	 * @return
	 */
	public int getTailleJeu() {
		return this.taille;
	}

	/**
	 * Methode permettant de recuperer le nombre de couleur autoriser
	 * 
	 * @return
	 */
	public int getNbCouleurs() {
		return this.nbCouleurs;
	}

	/**
	 * Methode permettant de recuperer la combinaison de reference
	 * 
	 * @return
	 */
	public Combinaison getCombRef() {
		return this.combRef;
	}

	/**
	 * Methode permettant de recuperer le nombre de tentative realiser durant la
	 * partie
	 * 
	 * @return
	 */
	public int getNumTentative() {
		return this.tentative.size();
	}

	/**
	 * Methode permettant de recuperer une methode à l'index i
	 * 
	 * @param i
	 * @return
	 */
	public Couple getUneTentative(int i) {
		if (i < 0 || i > getNumTentative()) {
			return null;
		}
		return this.tentative.get(i);
	}

	public Couple nouvelleTentative(List<Integer> couleurs) {
		if (getNumTentative() >= getNbTentatives()) {
			return null;
		}
		this.comb = new Combinaison(getTailleJeu());
		comb.saisirCombinaison(couleurs);
		Couple res = comb.compare(combRef);
		this.tentative.add(new Couple(comb, res));
		this.numTentative += 1;
		this.grille.add(couleurs);
		return res;
	}

	/**
	 * Methode permettant de designer un gagnant
	 * 
	 * @param v
	 */
	public void setGagnant(int v) {
		this.joueurGagnant = v;
	}

	/**
	 * Methode permettant d'avertir sur l'etat de la partie
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws FileNotFoundException 
	 * @throws ParseException 
	 * @throws InterruptedException 
	 */
	public void etatPartie() throws FileNotFoundException, ClassNotFoundException, SQLException, IOException, InterruptedException, ParseException {
		if(this.numManche <= 3) {
			if (!app.getPlat().getCouleurSelect().contains(0)) {
				boolean fin = false;

				Couple<Integer> res = nouvelleTentative(app.getPlat().getCouleurSelect());
				List<Integer> val = Arrays.asList(0, 0, 0, 0);
				app.getPlat().setCouleurSelect(val);

				Couple<Integer> c = (Couple<Integer>) (getUneTentative(getNumTentative() - 1).getVal2());

				VueNombrePions vb = new VueNombrePions();
				vb.setVal(String.valueOf(c.getVal1()));

				app.getPlat().getValeurLigneBonPions().getChildren().set(getNbTentatives() - getNumTentative(), vb);

				VueNombrePions vm = new VueNombrePions();
				vm.setVal(String.valueOf(c.getVal2()));

				VBox mauvaisPoints = (VBox) app.getPlat().getValeurLigneMauvaisPions().getChildren().get(0);

				mauvaisPoints.getChildren().set(getNbTentatives() - getNumTentative(), vm);

				app.getPlat().getValeurLigneMauvaisPions().getChildren().set(0, mauvaisPoints);

				if (aGagne() != 0) {
					Alert etat = new Alert(Alert.AlertType.INFORMATION);
					etat.setTitle("MasterMind");
					etat.setHeaderText("Felicitation !");
					etat.setContentText("Felicitation ! Vous avez gagné la manche!");
					etat.show();
					fin = true;
					if (this.getJoueurEnJeu() == this.idJ1) {
						this.scoreJ1 += 1;
					} else {
						this.scoreJ2 += 1;
					}
					this.joueurGagnant = this.getJoueurEnJeu();
					app.getPlat().getIHM().getLeft().getScore().setText(String.valueOf(this.scoreJ1));
					app.getPlat().getIHM().getRight().getScore().setText(String.valueOf(this.scoreJ2));
				}

				else if (getNumTentative() == app.getPlat().getListeLigne().getChildren().size() - 1) {
					res = nouvelleTentative(app.getPlat().getCouleurSelect());
					if (res == null) {
						Alert etat = new Alert(Alert.AlertType.INFORMATION);
						etat.setTitle("MasterMind");
						etat.setHeaderText("Egalité !");
						etat.setContentText("Il n'y a pas de gagnant ni perdant");
						etat.show();
						fin = true;
						this.joueurGagnant = -1;
					}
				}


				else {
					this.joueurEnJeu = setJoueurSuivant();
					if (getJoueurEnJeu() == this.idJ1) {
						app.getPlat().getIHM().getLeft().getAvatar().setEnjeu(true);
						app.getPlat().getIHM().getRight().getAvatar().setEnjeu(false);
					} else {
						app.getPlat().getIHM().getLeft().getAvatar().setEnjeu(false);
						app.getPlat().getIHM().getRight().getAvatar().setEnjeu(true);
					}
				}

				this.getConnexion().majScore(this.idPartie, this.scoreJ1, this.scoreJ2);

				if (fin) {
					this.finPartie();	
				}

			}

			String[] val = this.getConnexion().getEtat(idPartie);
			if(Integer.parseInt(val[2])>=0) {
				this.getConnexion().setEtat(this, this.idJeu);
			}

		}
		else {
			getplat().getBarre().bloquerBarre();
			getplat().getValider().setDisable(true);

		}

	}

	public void finPartie() {
		if(this.joueurGagnant==0) {
			this.joueurGagnant = 0;
		}

		VueNombrePions vmR = new VueNombrePions();

		for (int i = 0; i < app.getPlat().getListeLigne().getChildren().size() - 1; i++) {
			app.getPlat().getListeLigne().getChildren().set(i, new VueLignePlateau());
		}

		app.getPlat().resetValeurPions();

		this.numTentative = 0;
		this.tentative = new ArrayList<Couple>();

		Combinaison comb = new Combinaison(4);
		comb.genere(4);
		this.combRef = comb;

		app.getPlat().getIHM().getLeft().getAvatar().setEnjeu(true);
		app.getPlat().getIHM().getRight().getAvatar().setEnjeu(false);

		this.numManche += 1;
	}

	public int aGagne() {
		Couple res = (Couple) this.getUneTentative(this.numTentative - 1).getVal2();
		if ((int) res.getVal1() == this.getTailleJeu()) {
			this.joueurGagnant = this.getJoueurEnJeu();
			return this.getJoueurEnJeu();
		}
		return 0;
	}

	/**
	 * Methode permettant de recuperer le score du joueur 1
	 * @return
	 */
	public int getScoreJ1() {
		return this.scoreJ1;
	}

	/**
	 * Methode permettant de recuperer le score du joueur 2
	 * @return
	 */
	public int getScoreJ2() {
		return this.scoreJ2;
	}

	/**
	 * Methode permettant de recuperer le plateau du jeu
	 * @return
	 */
	public VuePlateau getplat() {
		return this.app.getPlat();
	}

	public void verifTours() throws FileNotFoundException, ClassNotFoundException, SQLException, IOException, InterruptedException, ParseException {
		if(this.idPC != this.joueurEnJeu) {
			this.app.getPlat().getValider().setDisable(true);
			this.app.getPlat().getBarre().bloquerBarre();
			t = new Thread(new VerifTours(this, this.idPartie, this.idPC));
			t.start();
		}
	}

	public void verifAbandon() {
		u = new Thread(new VerifAbandon(this, this.idPartie));
		u.start();
	}

	/**
	 * Methode permettant de recuperer le thread de verification de tours du mastermind
	 * @return
	 */
	public Thread getThreadVerifTours() {
		return this.t;
	}

	/**
	 * Methode permettant de recuperer le thread de verification d'abandon du mastermind
	 * @return
	 */
	public Thread getThreadVerifAbandon() {
		return this.u;
	}

	/**
	 * Methode permettant de recuperer l'identifiant du joueur qui a choisis de rejoindre la partie
	 * @return
	 */
	public int getIDPC() {
		return this.idPC;
	}

	/**
	 * Methode permettant de recuperer l'identifiant de la partie
	 * @return
	 */
	public int getIDPartie() {
		return this.idPartie;
	}

	/**
	 * Methode permettant de recuperer le nombre de manche realiser dans cette partie
	 * @return
	 */
	public int getNbManche() {
		return this.numManche;
	}

	/**
	 * Methode permettant de recuperer la connexion à la base de donnée
	 * @return
	 */
	public Etat getConnexion() {
		return this.et;
	}

}
