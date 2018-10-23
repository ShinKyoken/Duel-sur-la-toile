package Plateforme.Application;

import java.sql.SQLException;
import java.util.Optional;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class ActionMenuItemMenu implements EventHandler<ActionEvent>{

    private DuelSurLaToile app;

    /**
     * constructeur de ActionMenuItemMenu
     * @param app DuelSurLaToile
     */
    ActionMenuItemMenu(DuelSurLaToile app){
        this.app = app;
    }

    /**
     * gere les boutons du menu profil
     * @param a ActionEvent
     */
    @Override
    public void handle(ActionEvent a) {
        MenuItem mi = (MenuItem) a.getSource();
        if (mi.getUserData().equals("info")) {
            try {
                app.afficherProfil();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        else if(mi.getUserData().equals("invite")) {
            try {
                app.afficherInvitationAmiProfil();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        else if(mi.getUserData().equals("stat")) {
            try{
                app.afficherStatistiqueProfil();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
        
        else if(mi.getUserData().equals("deco")) {
            try {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("Déconnexion");
                confirm.setHeaderText("Déconnexion de la plateforme de jeux");
                confirm.setContentText("Êtes-vous sûr de vouloir quitter ?");

                ButtonType bouttonOui = new ButtonType("Oui");
                ButtonType bouttonNon = new ButtonType("Non");

                confirm.getButtonTypes().setAll(bouttonOui, bouttonNon);

                Optional<ButtonType> result = confirm.showAndWait();

                if(result.get().equals(bouttonOui)) {
                	app.getConnexion().changerConnexionUtilisateur(app.getUtilisateur().getId(), true);
                    app.getStage().close();
                    RunDuelSurLaToile run = new RunDuelSurLaToile();
                    run.start(new Stage());
                }

            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }

        }
    }

}
