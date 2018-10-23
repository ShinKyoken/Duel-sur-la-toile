/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Plateforme.Jeux;

import Plateforme.Application.DuelSurLaToile;
import Plateforme.Profil.ActionBoutonProfil;
import Plateforme.Profil.ListeAmis;
import java.sql.SQLException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author theodore
 */
public class InviterPartie extends BorderPane{
    private Jeu jeu;
    private ListeAmisJeu lsAmis;
    private ListePartieOuverte lsOuverte;
    private DuelSurLaToile app;
    private TextField rechercher;
    
    public InviterPartie(DuelSurLaToile app, Jeu jeu){
        this.jeu = jeu;
        this.app = app;
        try {
            this.lsAmis = new ListeAmisJeu(this, app.getConnexion().getAmis(this.app.getUtilisateur().getPseudoUt()));
            this.setLeft(this.getListeAmi());
        }
        catch(SQLException e){
            System.out.println("Erreur de syntaxe SQL");
        }
        try{
            this.setRight(this.getListeOuverte());
        }
        catch(Exception e){
            
        }
    }
    
    /**
     * @return un borderPane contenant la liste des amis de l'utilisateur
     */
    public BorderPane getListeAmi(){
        BorderPane bpListeAmi = new BorderPane();
        HBox top = new HBox();
        HBox center = new HBox();

        javafx.scene.control.Button az = new javafx.scene.control.Button("A-Z");
        az.setUserData("az");
        az.setStyle("-fx-background-color:   #ffffff  ;");
        javafx.scene.control.Button enLigne = new javafx.scene.control.Button("En ligne");
        enLigne.setStyle("-fx-background-color:   #ffffff  ;");
        enLigne.setUserData("online");
        javafx.scene.control.Button chercher = new javafx.scene.control.Button("chercher");
        chercher.setStyle("-fx-background-color:   #ffffff  ;");
        chercher.setUserData("search");

        ActionBoutonInviter act = new ActionBoutonInviter(this);
        chercher.setOnAction(act);
        az.setOnAction(act);
        enLigne.setOnAction(act);


        javafx.scene.control.Label titre = new javafx.scene.control.Label("Liste Amis ("+this.lsAmis.getNbAmis()+")");

        this.rechercher = new javafx.scene.control.TextField();

        top.getChildren().add(titre);
        top.getChildren().add(az);
        top.getChildren().add(enLigne);
        top.setAlignment(Pos.CENTER);
        top.setSpacing(5);

        center.getChildren().add(this.rechercher);
        center.getChildren().add(chercher);
        center.setAlignment(Pos.CENTER);
        center.setSpacing(5);

        bpListeAmi.setTop(top);
        bpListeAmi.setCenter(center);
        bpListeAmi.setBottom(this.lsAmis);

        BorderPane.setMargin(center, new Insets(10,0,0,0));
        BorderPane.setMargin(this.lsAmis, new Insets(10,0,0,10));

        return bpListeAmi;
    }
    
    public BorderPane getListeOuverte(){
        this.lsOuverte = new ListePartieOuverte(this);
        BorderPane res = new BorderPane();
        Button creerOuverte = new Button("Creer une partie ouverte");
        creerOuverte.setStyle("-fx-background-color:   #ffffff  ;");
        creerOuverte.setOnAction(new ActionBoutonInviter(this));
        res.setCenter(this.lsOuverte);
        res.setTop(creerOuverte);
        return res;
    }
    
    public Jeu getJeu(){
        return this.jeu;
    }
    
    public DuelSurLaToile getPlateforme(){
        return this.app;
    }

    public ListeAmisJeu getLsAmis() {
        return lsAmis;
    }
    
    public void maj(){
        this.lsAmis = new ListeAmisJeu(this, this.lsAmis.getListeAmis());
        this.setLeft(this.getListeAmi());
    }
    
}
