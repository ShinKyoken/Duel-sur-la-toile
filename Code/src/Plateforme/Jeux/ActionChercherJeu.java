/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Plateforme.Jeux;

import Plateforme.Application.Alerte;
import Plateforme.Profil.ListeStat;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 *
 * @author theodore
 */
public class ActionChercherJeu implements EventHandler<ActionEvent>{
    
    private PageDesJeux app;
    public ActionChercherJeu(PageDesJeux app){
        this.app = app;
    }
    
    @Override
    public void handle(ActionEvent a){
        try {
            List<Jeu> v = this.app.getPlateforme().getConnexion().rechercherJeux(this.app.getRechercher().getText());
            ListeJeux nLs = new ListeJeux(v, this.app);
            this.app.setListeJeux(nLs);
            this.app.majListe();
        } catch (SQLException e) {
            Alerte.creationAlerte(e);
        }
    }
}
