package Plateforme.Application;

import Jeux.Mastermind.VueMastermind;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

public class ActionFermetureDSLT implements EventHandler<WindowEvent>{

	private DuelSurLaToile app;

	public ActionFermetureDSLT(DuelSurLaToile app) {
		this.app = app;
	}

	@Override
	public void handle(WindowEvent a) {
		try {
			Thread.currentThread().interrupt();
			app.getConnexion().changerConnexionUtilisateur(app.getUtilisateur().getId(), false);
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}


