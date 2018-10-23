package Jeux.Mastermind;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Classe permettant de creer une barre de saisie
 *
 */
public class VueBarreSaisie extends HBox {

	/**
	 * Varaible permettant de recuperer le plateau du mastermind
	 */
	private VuePlateau app;

	/**
	 * Constructeur prenant en parametre le plateau du mastermind
	 * 
	 * @param app
	 */
	VueBarreSaisie(VuePlateau app) {
		this.app = app;

		Circle c1 = new Circle(10);
		Circle c2 = new Circle(10);
		Circle c3 = new Circle(10);
		Circle c4 = new Circle(10);
		Circle c5 = new Circle(10);
		Circle c6 = new Circle(10);

		ActionPions act = new ActionPions(this);

		c1.setFill(Color.RED);
		c1.setUserData(1);
		c1.setOnMouseClicked(act);

		c2.setFill(Color.YELLOW);
		c2.setUserData(2);
		c2.setOnMouseClicked(act);

		c3.setFill(Color.GREEN);
		c3.setUserData(3);
		c3.setOnMouseClicked(act);

		c4.setFill(Color.BLUE);
		c4.setUserData(4);
		c4.setOnMouseClicked(act);

		c5.setFill(Color.PURPLE);
		c5.setUserData(5);
		c5.setOnMouseClicked(act);

		c6.setFill(Color.WHITE);
		c6.setUserData(6);
		c6.setOnMouseClicked(act);

		this.getChildren().addAll(c1, c2, c3, c4, c5, c6);

		this.setSpacing(30);

		this.setAlignment(Pos.CENTER);
		this.setPadding(new Insets(10, 10, 10, 10));
		this.setMaxWidth(180);
		this.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, null, null)));
	}

	/**
	 * Methode permettant de bloquer la barre de saisie
	 */
	public void bloquerBarre() {
		this.getChildren().clear();
		Circle c1 = new Circle(10);
		Circle c2 = new Circle(10);
		Circle c3 = new Circle(10);
		Circle c4 = new Circle(10);
		Circle c5 = new Circle(10);
		Circle c6 = new Circle(10);

		c1.setFill(Color.GREY);

		c2.setFill(Color.GREY);

		c3.setFill(Color.GREY);

		c4.setFill(Color.GREY);

		c5.setFill(Color.GREY);

		c6.setFill(Color.GREY);

		this.getChildren().addAll(c1, c2, c3, c4, c5, c6);

		this.setSpacing(30);

		this.setAlignment(Pos.CENTER);
		this.setPadding(new Insets(10, 10, 10, 10));
		this.setMaxWidth(180);
		this.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, null, null)));
	}

	/**
	 * Methode permettant de debloquer la barre de saisie
	 */
	public void debloquerBarre() {
		this.getChildren().clear();
		Circle c1 = new Circle(10);
		Circle c2 = new Circle(10);
		Circle c3 = new Circle(10);
		Circle c4 = new Circle(10);
		Circle c5 = new Circle(10);
		Circle c6 = new Circle(10);

		ActionPions act = new ActionPions(this);

		c1.setFill(Color.RED);
		c1.setUserData(1);
		c1.setOnMouseClicked(act);

		c2.setFill(Color.YELLOW);
		c2.setUserData(2);
		c2.setOnMouseClicked(act);

		c3.setFill(Color.GREEN);
		c3.setUserData(3);
		c3.setOnMouseClicked(act);

		c4.setFill(Color.BLUE);
		c4.setUserData(4);
		c4.setOnMouseClicked(act);

		c5.setFill(Color.PURPLE);
		c5.setUserData(5);
		c5.setOnMouseClicked(act);

		c6.setFill(Color.WHITE);
		c6.setUserData(6);
		c6.setOnMouseClicked(act);

		this.getChildren().addAll(c1, c2, c3, c4, c5, c6);

		this.setSpacing(30);

		this.setAlignment(Pos.CENTER);
		this.setPadding(new Insets(10, 10, 10, 10));
		this.setMaxWidth(180);
		this.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, null, null)));
	}

	/**
	 * Methode permettant de recuperer le plateau
	 * @return
	 */
	public VuePlateau getPlateau() {
		return this.app;
	}
}
