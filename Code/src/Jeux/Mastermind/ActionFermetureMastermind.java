package Jeux.Mastermind;

import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

/**
 * Classe permettant de réaliser une action aprés la fermeture du mastermind
 *
 */
public class ActionFermetureMastermind implements EventHandler<WindowEvent>{
	
	private VueMastermind app;
	
	public ActionFermetureMastermind(VueMastermind app) {
		this.app = app;
	}
	
	@Override
	public void handle(WindowEvent a) {
		try {
			app.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
