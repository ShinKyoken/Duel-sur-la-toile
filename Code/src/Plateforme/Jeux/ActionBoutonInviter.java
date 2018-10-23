/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Plateforme.Jeux;

import Plateforme.Application.Alerte;
import Plateforme.Application.DuelSurLaToile;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

/**
 *
 * @author theodore
 */
public class ActionBoutonInviter implements EventHandler<ActionEvent> {
    
    private InviterPartie app;

    public ActionBoutonInviter(InviterPartie app) {
        this.app = app;
    }
    
    @Override
    public void handle(ActionEvent a){
        Button b = (Button) a.getTarget();
        if (b.getText().equals("Creer une partie ouverte")) {
            try {
                this.app.getPlateforme().getConnexion().nouvellePartieOuverte(this.app.getPlateforme().getUtilisateur().getId(),
                        this.app.getJeu().getId());
            }
            catch(SQLException e){
                Alerte.creationAlerte(e);
            }
        }
        else if (b.getText().equals("Rejoindre")){
            try {
                this.app.getPlateforme().getConnexion().rejoindrePartieOuverte((PartieOuverte) b.getUserData(), this.app.getPlateforme().getUtilisateur());
                int idPartie =((PartieOuverte) b.getUserData()).getIdPartie();
                this.app.getPlateforme().getConnexion().creationPartie2(idPartie, this.app.getPlateforme());
                //this.app.getPlateforme().getConnexion().refuserInvitPartie(idPartie);
                this.app.getPlateforme().afficherPartie();
            }
            catch(SQLException e){
                Alerte.creationAlerte(e);
            }
            catch(Exception e){
                Alerte.creationAlerte(e);
            }

        }
        else {
            int idInviter = (int) b.getUserData();
            try {
                this.app.getPlateforme().getConnexion().nouvelleInvitPartie(this.app.getPlateforme().getUtilisateur().getId(),
                        idInviter, this.app.getJeu().getId());
            } catch (SQLException e) {
                System.out.println(e);
            }
            this.app.getLsAmis().supprimer(idInviter);
            this.app.maj();
        }
    }
    
}
