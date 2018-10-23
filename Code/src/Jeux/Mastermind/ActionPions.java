package Jeux.Mastermind;

import java.util.HashMap;
import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Classe permettant de lancer une action lorsque l'ont clique sur un pion de la
 * barre de saisies
 *
 */
public class ActionPions implements EventHandler<MouseEvent> {

	/**
	 * Varaible correspondant à la barre de saisie du plateau du mastermind
	 */
	private VueBarreSaisie app;

	/**
	 * Constructeur prenant en parametre la barre de saisie
	 * 
	 * @param app
	 */
	ActionPions(VueBarreSaisie app) {
		this.app = app;
	}

	@Override
	public void handle(MouseEvent a) {
		Circle c = (Circle) a.getSource();

		HashMap<Integer, Color> IDColor = new HashMap<Integer, Color>();

		IDColor.put(0, Color.BLACK);
		IDColor.put(1, Color.RED);
		IDColor.put(2, Color.YELLOW);
		IDColor.put(3, Color.GREEN);
		IDColor.put(4, Color.BLUE);
		IDColor.put(5, Color.PURPLE);
		IDColor.put(6, Color.WHITE);

		List<Integer> couleurs = app.getPlateau().getCouleurSelect();

		for (int i = 0; i < couleurs.size(); i++) {
			if (couleurs.get(i) == 0) {
				app.getPlateau().getCouleurSelect().set(i, (Integer) c.getUserData());
				break;
			}

		}
		try {
			app.getPlateau().editLigne(
					(app.getPlateau().getListeLigne().getChildren().size() - 2) - app.getPlateau().getIHM().getJeu().getNumTentative(),
					IDColor.get(couleurs.get(0)), IDColor.get(couleurs.get(1)), IDColor.get(couleurs.get(2)),
					IDColor.get(couleurs.get(3)));
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}

}
