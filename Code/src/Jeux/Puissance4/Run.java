/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jeux.Puissance4;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import javafx.stage.Stage;

import Jeux.IJeu;

/**
 * Runs the program through the Jeu interface.
 * @author Tomoko
 */
public class Run implements IJeu {
    
    @Override
    public void rejoindrePartieEnCours(int idPC, int idJeu, int idPartie) throws SQLException, FileNotFoundException, ClassNotFoundException, IOException {
        String[] args = new String[2];
        args[0] = String.valueOf(idPC);
        args[1] = String.valueOf(idPartie);
        VuePuissance4 v = new VuePuissance4(args);
	v.start(new Stage());
    }
    
    @Override
    public void creerPartie(int idPC, int idJeu, int idJoueur1, int idJoueur2, Object partage) throws Exception {
        ConnectionManager.createGame(idPC, idJeu, idJoueur1, idJoueur2);
    }
}
