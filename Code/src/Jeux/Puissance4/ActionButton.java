package Jeux.Puissance4;

import java.util.Optional;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

public class ActionButton implements EventHandler<ActionEvent>{

	private VuePuissance4 app;

	ActionButton(VuePuissance4 app){
		this.app = app;
	}

	@Override
	public void handle(ActionEvent ev) {
		Button b = (Button) ev.getSource();

                if (b.getUserData().equals("newgame")) {
		    this.app.getResultatPartie().setText("");
		    this.app.recommencer();
		    this.app.maj();
                }
                else if (b.getUserData().equals("surrender")) {
                    Alert prompt = new Alert(AlertType.CONFIRMATION);
                    prompt.setTitle("Surrender?");
                    prompt.setHeaderText("You have chosen to surrender.");
                    prompt.setContentText("Do you really want to surrender?");
                    Optional<ButtonType> res = prompt.showAndWait();
                    if (res.get() == ButtonType.OK) {
                        this.app.getGame().setJoueurSuivant();
                        this.app.getGame().setFinPartie(true);
                        this.app.getResultatPartie();
                        this.app.win(this.app.getGame().getJoueurEnJeu().getPseudo());
                    }
                }
                else if (b.getUserData().equals("leave")) {
                    System.err.println("EEEEEEEE");
                    Platform.exit();
                }
	}
}
