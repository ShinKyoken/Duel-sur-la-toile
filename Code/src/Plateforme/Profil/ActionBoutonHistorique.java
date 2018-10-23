/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Plateforme.Profil;

import java.sql.SQLException;
import Plateforme.Application.Alerte;
import java.util.Collections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 * Cette classe implémente EventHandler et contien la fonction handle qui gère les boutons de tri de l'historique.
 * @author theodore
 */
public class ActionBoutonHistorique implements EventHandler<ActionEvent>{
    
    private StatistiqueProfil stat;
    
    public ActionBoutonHistorique(StatistiqueProfil stat){
        this.stat = stat;
    }

    /**
     * Cette fonction est appélée lors d'un clic sur les boutons servant au tri de l'historique.
     * On peut le trier par date, par adversaire ou par jeu.
     * @param a 
     */
    @Override
    public void handle(ActionEvent a) {
        Button b = (Button) a.getSource();
        if(b.getUserData().equals("date")){
            this.stat.getListeHistorique().triDate();
            try{
                this.stat.majHist();
            }
            catch(SQLException e){
                Alerte.creationAlerte(e);
            }
        }
        else if(b.getUserData().equals("adversaire")){
            this.stat.getListeHistorique().triAdversaire();
            try{
                this.stat.majHist();
            }
            catch(SQLException e){
                Alerte.creationAlerte(e);
            }
        }
        else if(b.getUserData().equals("az")){
            this.stat.getListeHistorique().triJeu();
            try{
                this.stat.majHist();
            }
            catch(SQLException e){
                Alerte.creationAlerte(e);
            }
        }
    }
}
