package Jeux.Mastermind;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * Classe permettant de creer la partie gauche de l'IHM du mastermind
 *
 */
public class VuePartieGauche extends BorderPane {

	/**
	 * Zone où ce trouve l'avatar
	 */
	private VueZoneAvatar av;

	/**
	 * Label affichant le score du joueur de droite
	 */
	private Label score;
	
	private Label pseudo;

	VuePartieGauche(VueMastermind app,Image a, String ut) {

		VBox utilisateur = new VBox();
		av = new VueZoneAvatar(a);

		av.setEnjeu(true);

		score = new Label("0");
		
		pseudo = new Label(ut);
		
		utilisateur.getChildren().add(av);
		utilisateur.getChildren().add(pseudo);
		utilisateur.getChildren().add(score);
		utilisateur.setAlignment(Pos.CENTER);

		ActionButton act = new ActionButton(app.getPlat());

		Button aide = new Button("?");
		aide.setUserData("aide");
		aide.setOnAction(act);

		this.setTop(utilisateur);
		this.setBottom(aide);
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
	 * Methode permettant de recuperer le label avec le score du joueur de gauche
	 * @return
	 */
	public Label getScore() {
		return this.score;
	}
}
