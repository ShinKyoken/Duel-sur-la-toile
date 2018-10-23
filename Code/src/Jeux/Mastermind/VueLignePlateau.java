package Jeux.Mastermind;

import java.util.Arrays;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Classe permettant la creation d'une ligne dans le plateau du mastermind
 *
 */
public class VueLignePlateau extends HBox {

	/**
	 * Liste de pions sur une ligne du plateau du mastermind
	 */
	List<Circle> pions;

	/**
	 * Constructeur de la classe pour la creation d'une ligne de pions dans le
	 * plateau du mastermind
	 */
	VueLignePlateau() {

		this.pions = Arrays.asList(new Circle(10), new Circle(10), new Circle(10), new Circle(10));
		for (Circle c : this.pions) {
			this.getChildren().add(c);
		}
		this.setSpacing(30);
		this.setPadding(new Insets(10, 10, 10, 10));
		this.setMaxWidth(180);

		this.setAlignment(Pos.CENTER);
		this.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
	}

	/**
	 * Constructeur de la classe où l'ont precise les couleurs des pions lors de
	 * leurs creation
	 * 
	 * @param c1
	 * @param c2
	 * @param c3
	 * @param c4
	 */
	VueLignePlateau(Color c1, Color c2, Color c3, Color c4) {

		Circle ce = new Circle(10);
		ce.setFill(c1);
		Circle ce2 = new Circle(10);
		ce2.setFill(c2);
		Circle ce3 = new Circle(10);
		ce3.setFill(c3);
		Circle ce4 = new Circle(10);
		ce4.setFill(c4);

		this.pions = Arrays.asList(ce, ce2, ce3, ce4);
		for (Circle c : this.pions) {
			this.getChildren().add(c);
		}

		this.setSpacing(30);
		this.setPadding(new Insets(10, 10, 10, 10));
		this.setMaxWidth(180);

		this.setAlignment(Pos.CENTER);
		this.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
	}

	/**
	 * Methode permettant de recuperer la liste des pions d'une ligne du plateau
	 * 
	 * @return
	 */
	public List<Circle> getListePion() {
		return this.pions;
	}

}
