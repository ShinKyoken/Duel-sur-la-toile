package Jeux.Mastermind;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

/**
 * Classe permettant de creer la partie droite de l'IHM du mastermind
 *
 */
public class VuePartieDroite extends BorderPane {

	/**
	 * Zone où ce trouve l'avatar
	 */
	private VueZoneAvatar av;

	/**
	 * Label affichant le score du joueur de droite
	 */
	private Label score;

	/**
	 * L'IHM du mastermind
	 */
	private VueMastermind app;

	/**
	 * Le bouton abandonner
	 */
	private Button abandonner;
	
	private Label pseudo;
	
	/**
	 * Constructeur prenant en parametre l'avatar d'un joueur
	 * 
	 * @param a
	 */
	VuePartieDroite(VueMastermind app, Image a, String ut) {

		this.app = app;

		VBox utilisateur = new VBox();
		av = new VueZoneAvatar(a);

		ActionButton act = new ActionButton(app.getPlat());

		score = new Label("0");
		
		pseudo = new Label(ut);
		
		utilisateur.getChildren().add(av);
		utilisateur.getChildren().add(pseudo);
		utilisateur.getChildren().add(score);
		utilisateur.setAlignment(Pos.CENTER);

		VBox options = new VBox();

		Button annuler = new Button("Annuler combinaison\nsaisie");
		abandonner = new Button("Abandonner");
//		Button quitter = new Button("Quitter");

		annuler.setTextAlignment(TextAlignment.CENTER);

		annuler.setMaxWidth(Double.MAX_VALUE);
		abandonner.setMaxWidth(Double.MAX_VALUE);
//		quitter.setMaxWidth(Double.MAX_VALUE);

		annuler.setUserData("annuler");
		abandonner.setUserData("abandonner");
//		quitter.setUserData("quitter");

		annuler.setOnAction(act);
		abandonner.setOnAction(act);
//		quitter.setOnAction(act);

		options.getChildren().add(annuler);
		options.getChildren().add(abandonner);
//		options.getChildren().add(quitter);

		this.setTop(utilisateur);
		this.setBottom(options);
	}

	/**
	 * Methode permettant de recuperer la zone de où ce trouve l'avatar d'un joueur
	 * 
	 * @return
	 */
	public VueZoneAvatar getAvatar() {
		return this.av;
	}

	/**
	 * Methode permettant de recuperer le label avec le score du joueur de droite
	 * @return
	 */
	public Label getScore() {
		return this.score;
	}

	/**
	 * Methode permetant de recuperer l'IHM du mastermind
	 * @return
	 */
	public VueMastermind getIHM() {
		return this.app;
	}

	/**
	 * Methode permettant de recuperer le bouton abandonner
	 * @return
	 */
	public Button getBoutonAbandonner() {
		return this.abandonner;
	}
}
