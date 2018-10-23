package Jeux.Mastermind;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import Plateforme.Application.Utilisateur;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Classe permettant de tester l'IHM du mastermind
 *
 */
public class VueMastermind extends Application {

	/**
	 * Le jeu du mastermind
	 */
	private Mastermind jeu;

	/**
	 * La partie gauche de l'IHM
	 */
	private VuePartieGauche left;

	/**
	 * La partie droite de l'IHM
	 */
	private VuePartieDroite right;

	/**
	 * Le plateau du mastermind
	 */
	private VuePlateau plateau;
	
	private Utilisateur ut1;
	
	private Utilisateur ut2;
	
	private Etat et;
	
	private BorderPane fin;
	
	private static String[] param;
	
	public VueMastermind(String[] args) throws Exception {
		param = args;
	}
	
	/**
	 * Methode permettant de creer la scene de l'IHM du mastermind
	 * 
	 * @return
	 */
	public Scene s() {
		fin = new BorderPane();

		plateau = new VuePlateau(this);

		File fichier = new File("./avatar_defaut.png");

		left = new VuePartieGauche(this, new Image(fichier.toURI().toString()), "utilisateur");
		right = new VuePartieDroite(this, new Image(fichier.toURI().toString()), "utilisateur");

		fin.setCenter(plateau);
		fin.setLeft(left);
		fin.setRight(right);

		BorderPane.setMargin(plateau, new Insets(0, 10, 0, 10));
		BorderPane.setMargin(left, new Insets(10, 10, 10, 10));
		BorderPane.setMargin(right, new Insets(10, 10, 10, 10));

		return new Scene(fin, 800, 600);
	}

	@Override
	public void start(Stage a) throws Exception {
		
		et = new Etat();
		
		ActionFermetureMastermind act = new ActionFermetureMastermind(this);

		a.setTitle("Mastermind");
		a.setScene(s());
		a.show();
		a.setResizable(false);
		a.setOnCloseRequest(act);
				
		if(param[0].equals("creer")) {
			jeu = new Mastermind(this, Integer.parseInt(param[2]), 4, 6, 10, Integer.parseInt(param[3]), Integer.parseInt(param[4]), Integer.parseInt(param[1]));
		}
		else if(param[0].equals("rejoindre")){
			
			int id = Integer.parseInt(param[4]);
			jeu = new Mastermind(this, Integer.parseInt(param[3]), param[2], Integer.parseInt(param[1]),id);
			
			JSONParser p = new JSONParser();
			JSONObject res = (JSONObject) p.parse(param[2]);
			
			JSONArray lsJ = (JSONArray) res.get("joueurs");
			List<Integer> player = new ArrayList<Integer>();

			for (int i = 0; i < lsJ.size(); i++) {
				player.add(((Long) lsJ.get(i)).intValue());
			}
			
			ut1 = et.getInfoPlateforme().chercherUtilisateur(player.get(0));
			ut2 = et.getInfoPlateforme().chercherUtilisateur(player.get(1));
						
			left = new VuePartieGauche(this, plateau.getIHM().getUt1().getAvatarUt(), ut1.getPseudoUt());
			right = new VuePartieDroite(this, plateau.getIHM().getUt2().getAvatarUt(), ut2.getPseudoUt());
			
			fin.setLeft(left);
			fin.setRight(right);
		}
		
		

	}

	/**
	 * Methode permettant de recuperer le jeu du mastermind
	 * 
	 * @return
	 */
	public Mastermind getJeu() {
		return jeu;
	}

	/**
	 * Methode permettant de recuperer la partie gauche de l'IHM
	 * 
	 * @return
	 */
	public VuePartieGauche getLeft() {
		return left;
	}

	/**
	 * Methode permettant de recuperer la partie droite de l'IHM
	 * 
	 * @return
	 */
	public VuePartieDroite getRight() {
		return right;
	}

	/**
	 * Methode permettant de recuperer le plateau du jeu
	 * @return
	 */
	public VuePlateau getPlat() {
		return this.plateau;
	}
	
	public Utilisateur getUt1() {
		return this.ut1;
	}
	
	public Utilisateur getUt2() {
		return this.ut2;
	}
}
