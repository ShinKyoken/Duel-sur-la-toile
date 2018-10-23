package Plateforme.Administration;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.io.IOException;
import java.sql.SQLException;

import Plateforme.Jeux.Jeu;

public class ActionBoutonJeu implements EventHandler<ActionEvent> {
    private AdministrateurJeu admin;

    /**
     * Constructeur
     * @param admin
     */
    public ActionBoutonJeu(AdministrateurJeu admin) {

        this.admin = admin;
    }

    @Override
    /**
     * Méthode permettant de gérer les différents évènements.
     */
    public void handle(ActionEvent a) {
        Button b=(Button)a.getSource();
        if (b.getUserData().equals("user")) {
            try {
                this.admin.getApp().afficherAdministrateur();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (b.getUserData().equals("stats")){
            try {
                this.admin.getApp().afficherAdministrateurStat();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        if(b.getUserData().equals("change")){
        	AdministrationJeu admJeu = new AdministrationJeu(admin.getListeJeu().getJeuSelectionner());
        	admJeu.changeActivable();
            Alert al=new Alert(Alert.AlertType.INFORMATION);
            al.setTitle("Changement d'état du jeu");
            al.setHeaderText("L'état du jeu a bien été changé");
            al.show();
        	if(!admJeu.getActivable()){
        	    int c=this.admin.getListeJeu().getJeuSelectionner().getId();
                try {
                    this.admin.getCo().abandonnerPartieJeuDesac(c);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        	try {
				admin.getApp().getConnexion().changementActiviteJeu(admin.getListeJeu().getJeuSelectionner().getId(), admin.getListeJeu().getJeuSelectionner().isActiver());;
				admin.getApp().afficherAdministrateurJeu();
        	} catch (SQLException | IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        if(b.getUserData().equals("modif")){
        	Jeu j = admin.getListeJeu().getJeuSelectionner();
        	try {
				admin.getApp().afficherModifierJeu(j.getId(), j.getType(), j.getNom(), j.getDescrComplet(), j.getClasseLanceur());
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
        if (b.getUserData().equals("ajout")){
            try {
				admin.getApp().afficherAjoutJeu();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
    }
}