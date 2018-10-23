/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Plateforme.Jeux;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author theodore
 */
public class PartieOuverte extends BorderPane{
    private int idPartie;
    private int idJeu;
    private int idUt, idUt2;
    private String nomJeu;
    private String nomUt;
    private ImageView image;

    public PartieOuverte(int idPartie, int idJeu, int idUt, String nomJeu, String nomUt, ImageView image){
        this.idJeu = idJeu;
        this.idPartie = idPartie;
        this.idUt = idUt;
        this.nomUt = nomUt;
        this.nomJeu = nomJeu;
        this.image = image;
    }
    
    public PartieOuverte(int idPartie, int idJeu, int idUt, int idUt2){
        this.idJeu = idJeu;
        this.idPartie = idPartie;
        this.idUt = idUt;
        this.nomUt = nomUt;
        this.nomJeu = nomJeu;
        this.image = image;
        this.idUt2  =idUt2;
    }


    public ImageView getImage() {
        return image;
    }

    public int getIdJeu() {
        return idJeu;
    }

    public int getIdPartie() {
        return idPartie;
    }

    public String getNomJeu() {
        return nomJeu;
    }

    public String getNomUt() {
        return nomUt;
    }
    
    public int getUt() {
    	return this.idUt;
    }
    
    public int getUt2() {
    	return this.idUt2;
    }
}
