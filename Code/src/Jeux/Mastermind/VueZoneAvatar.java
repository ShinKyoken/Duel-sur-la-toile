package Jeux.Mastermind;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Classe permettant de creer une zone d'avatar pour l'IHM du mastermind
 *
 */
public class VueZoneAvatar extends StackPane {

	/**
	 * Le rectangle où ce trouve l'avatar
	 */
	private Rectangle rect;

	/**
	 * Constructeur de la classe prenant un avatar et une couleur de fond
	 * 
	 * @param avatar
	 * @param c
	 */
	VueZoneAvatar(Image avatar) {
		ImageView picture = new ImageView(avatar);
		picture.setFitWidth(50);
		picture.setPreserveRatio(true);

		rect = new Rectangle(75, 75);

		rect.setFill(Color.RED);

		StackPane.setAlignment(picture, Pos.TOP_CENTER);
		StackPane.setMargin(picture, new Insets(10, 0, 0, 0));
		StackPane.setAlignment(rect, Pos.TOP_CENTER);

		this.getChildren().addAll(rect, picture);
	}

	/**
	 * Chagement de couleur du rectangle pour designer le tour du joueur
	 * 
	 * @param b
	 */
	public void setEnjeu(boolean b) {
		if (b) {
			rect.setFill(Color.GREEN);
		} else {
			rect.setFill(Color.RED);
		}
	}
}
