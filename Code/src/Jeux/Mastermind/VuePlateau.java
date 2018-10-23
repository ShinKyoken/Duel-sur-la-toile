package Jeux.Mastermind;

import java.util.Arrays;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Methode permettant d'implementer le plateau du mastermind à l'IHM du jeu
 *
 */
public class VuePlateau extends BorderPane {

	/**
	 * Liste des lignes du plateau
	 */
	private VBox lsLigne;

	/**
	 * Le plateau
	 */
	private Group plateau;

	/**
	 * Le support du plateau
	 */
	private Rectangle rect;

	/**
	 * Liste des couleurs selectionner via la barre de saisie
	 */
	private List<Integer> lsCouleurSelect;

	/**
	 * VBox correspondant aux nombre de bon pions
	 */
	private VBox bonPions;

	/**
	 * VBox correspondant aux nombre de mauvais pions
	 */
	private VBox mauvaisPions;

	/**
	 * Page principale du jeu
	 */
	private VueMastermind app;

	/**
	 * Le bouton valider
	 */
	private Button valider;

	/**
	 * La barre de saisie
	 */
	private VueBarreSaisie barre;

	/**
	 * Constructeur du plateau prenant en parametre la page principale du jeu
	 * 
	 * @param app
	 */
	VuePlateau(VueMastermind app) {
		this.app = app;
		this.lsCouleurSelect = Arrays.asList(0, 0, 0, 0);

		BorderPane top = new BorderPane();

		rect = new Rectangle(300, 500);

		rect.setArcWidth(25);
		rect.setArcHeight(25);
		rect.setFill(Color.rgb(80, 41, 0));

		plateau = new Group();
		lsLigne = new VBox();
		plateau.getChildren().addAll(rect);
		for (int i = 0; i < 10; i++) {
			VueLignePlateau l = new VueLignePlateau();
			lsLigne.getChildren().add(l);
		}

		barre = new VueBarreSaisie(this);

		lsLigne.getChildren().add(barre);

		lsLigne.setAlignment(Pos.CENTER);
		lsLigne.setPadding(new Insets(5, 0, 0, 5));
		lsLigne.setSpacing(4);

		plateau.getChildren().add(lsLigne);

		top.setLeft(new Label("Pion bien placé"));
		top.setCenter(new Label("MASTERMIND"));
		top.setRight(new Label("Pion mal placé"));

		bonPions = getnbPions();
		mauvaisPions = new VBox();

		valider = new Button("VALIDER");

		valider.setUserData("jouer");

		valider.setOnAction(new ActionButton(this));

		mauvaisPions.getChildren().add(getnbPions());
		mauvaisPions.getChildren().add(valider);

		this.setTop(top);
		this.setCenter(plateau);
		this.setLeft(bonPions);
		this.setRight(mauvaisPions);

		BorderPane.setMargin(bonPions, new Insets(0, 0, 0, 50));
	}

	/**
	 * Generation d'une VBox avec le nombre de pions bon ou mauvais pour chaque
	 * ligne du plateau
	 * 
	 * @return
	 */
	public VBox getnbPions() {
		VBox res = new VBox();

		for (int i = 0; i < 10; i++) {
			res.getChildren().add(new VueNombrePions());
		}

		res.setSpacing(15);

		res.setPadding(new Insets(55, 10, 10, 10));

		res.setMaxWidth(10);

		return res;
	}

	/**
	 * Methode permettant de changer la couleur des pions d'une ligne
	 * 
	 * @param c1
	 * @param c2
	 * @param c3
	 * @param c4
	 */
	public void editLigne(int i, Color c1, Color c2, Color c3, Color c4) {
		lsLigne.getChildren().set(i, new VueLignePlateau(c1, c2, c3, c4));
		plateau.getChildren().set(1, lsLigne);
	}

	/**
	 * Methode permettant de recuperer la page principal du mastermind
	 * 
	 * @return
	 */
	public VueMastermind getIHM() {
		return this.app;
	}

	/**
	 * Methode permettant de recuperer les couleurs selectionnées
	 * 
	 * @return
	 */
	public List<Integer> getCouleurSelect() {
		return this.lsCouleurSelect;
	}

	/**
	 * Methode permettant d'éditer la liste des couleurs selectionnée
	 * 
	 * @param val
	 */
	public void setCouleurSelect(List<Integer> val) {
		this.lsCouleurSelect = val;
	}

	/**
	 * Methode permettant de recuperer la liste des lignes de pions du plateau
	 * 
	 * @return
	 */
	public VBox getListeLigne() {
		return this.lsLigne;
	}

	/**
	 * Methode permettant de recuperer les valeur de bon pions pour chaque ligne
	 * 
	 * @return
	 */
	public VBox getValeurLigneBonPions() {
		return this.bonPions;
	}

	/**
	 * Methode permettant de recuperer les valeur de mauvais pions pour chaque ligne
	 * 
	 * @return
	 */
	public VBox getValeurLigneMauvaisPions() {
		return this.mauvaisPions;
	}

	/**
	 * Methode permettant de recuperer le bouton valider
	 * @return
	 */
	public Button getValider() {
		return this.valider;
	}

	/**
	 * Methode permettant de recuperer la barre de saisie
	 * @return
	 */
	public VueBarreSaisie getBarre() {
		return this.barre;
	}
	/**
	 * Remet à zero les valeurs des bon et mauvais pions du plateau
	 */
	public void resetValeurPions() {
		this.mauvaisPions.getChildren().set(0, getnbPions());
		this.bonPions = this.getnbPions();
		this.setLeft(bonPions);
		this.setRight(mauvaisPions);
		BorderPane.setMargin(bonPions, new Insets(0, 0, 0, 50));
	}

	/**
	 * Methode permettant d'effacer les couleurs selectionner
	 */
	public void effacerCouleurSelectionner() {
		this.lsCouleurSelect = Arrays.asList(0,0,0,0);
	}
}
