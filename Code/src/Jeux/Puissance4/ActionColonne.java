package Jeux.Puissance4;

import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Region;

public class ActionColonne implements EventHandler<ActionEvent>{

	private VuePuissance4 app;

	public ActionColonne(VuePuissance4 app){
		this.app = app;
	}

	@Override
	public void handle(ActionEvent arg0) {
		Button b = (Button) arg0.getSource();

		Alert error = new Alert(AlertType.ERROR);
		error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		error.setTitle("ERROR");
		error.setHeaderText("The row is full.");
		error.setContentText("You can't insert the token in the row.");

		if((int) b.getUserData()-1>this.app.getGame().getPlat().getNbColonnes() || (int) b.getUserData()-1<0) {
			error.showAndWait();
		}

		else if(!this.app.getGame().getPlat().getColonne((int) b.getUserData()-1).contains(0)) {
			error.showAndWait();
		}

		else {
			app.getGame().jouer((int) b.getUserData()-1);
                        try {
                            app.getCM().sendStatus();
                        }
                        catch(SQLException|IOException e) {
                            e.printStackTrace();
                        }
			app.maj();
		}
	}
}