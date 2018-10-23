package Jeux.Mastermind;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Classe permettant de creer l'affichage d'un nombre de pion pour l'IHM du
 * mastermind
 *
 */
public class VueNombrePions extends StackPane {

	/**
	 * Label portant un nombre de pions
	 */
	Label valeur;

	VueNombrePions() {
		Circle c = new Circle(15);

		c.setFill(Color.WHITE);
		valeur = new Label("0");

		StackPane.setAlignment(valeur, Pos.CENTER);
		this.getChildren().addAll(c, valeur);
	}

	/**
	 * Methode permettant de modifier le nombre de pions
	 * 
	 * @param val
	 */
	public void setVal(String val) {
		valeur.setText(val);
	}
}
