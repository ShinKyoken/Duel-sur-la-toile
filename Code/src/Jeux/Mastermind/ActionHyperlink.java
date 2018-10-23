package Jeux.Mastermind;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;

/**
 * Classe permettant de naviger dans les differentes page de l'aide du mastermind
 *
 */
public class ActionHyperlink implements EventHandler<ActionEvent>{

	/**
	 * La page d'aide
	 */
	private ApplicationAide app;

	/**
	 * Constructeur du controleur prenant la page d'aide du mastermind en parametre
	 * @param app
	 */
	ActionHyperlink(ApplicationAide app){
		this.app = app;
	}

	@Override
	public void handle(ActionEvent a) {
		Hyperlink h = (Hyperlink) a.getSource();

		if(h.getUserData().equals("intro")) {
			app.afficherIntro();
		}

		else if(h.getUserData().equals("jouer")) {
			app.afficherJouerUnTours();
		}

		else if(h.getUserData().equals("partie")) {
			app.afficherLaPartie();
		}

		else if(h.getUserData().equals("autre")) {
			app.afficherAutre();
		}
	}

}
