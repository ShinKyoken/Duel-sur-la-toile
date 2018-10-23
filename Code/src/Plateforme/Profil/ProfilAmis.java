/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Plateforme.Profil;

import Plateforme.Application.DuelSurLaToile;
import Plateforme.Application.Alerte;
import Plateforme.Application.Utilisateur;
import java.sql.SQLException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Cette classe hérite de BorderPane et affiche les informations et les statistiques d'un joueur
 * @author theodore
 */
public class ProfilAmis extends BorderPane{
    
    /**
     * L'application
     */
    private DuelSurLaToile app;
    /**
     * L'utilisateur dont on consulte les informations
     */
    private Utilisateur ami;
    /**
     * La VBox dans laquelle on rangera les statistique du joueur
     */
    private VBox panel;
    /**
     * L'avatar du joueur
     */
    private ImageView avatarInfo;
    /**
     * La liste des statistique du joueur
     */
    private ListeStat listeStat;
    /**
     * Le textField qui contient le paramêtre de la recherche.
     */
    private TextField rechercher;
    
    public ProfilAmis(DuelSurLaToile app, Utilisateur ami){
        this.app = app;
        this.ami = ami;
        this.panel = new VBox();
        try{
        this.listeStat = new ListeStat(this.getPlateforme(), app.getConnexion().getJeux(), app.getConnexion().getHistoriquePartie(this.ami));
        }
        catch(SQLException e){
            Alerte.creationAlerte(e);
        }
        this.panel.getChildren().add(this.listeStat);
        this.setTop(this.getInfo());
        this.setCenter(this.listeStat);
    }
    
    /**
     * 
     * @return un borderPane contenant les informations du joueur
     */
    public BorderPane getInfo() {
        BorderPane bpInfo = new BorderPane();
        VBox info = new VBox();
        javafx.scene.image.Image av = this.ami.getAvatarUt();
        this.avatarInfo = new ImageView(av);
        this.avatarInfo.setFitWidth(100);
        this.avatarInfo.setPreserveRatio(true);

        javafx.scene.control.Label pseudo = new javafx.scene.control.Label("Pseudo : "+this.ami.getPseudoUt());
        javafx.scene.control.Label email = new javafx.scene.control.Label("Email : "+this.ami.getEmailUt());
        javafx.scene.control.Label role = new javafx.scene.control.Label("Rôle : "+this.ami.isAdmin());
        javafx.scene.control.Label ratio = new javafx.scene.control.Label("Ratio : "+this.ami.getRatio()+" %");

        info.getChildren().add(pseudo);
        info.getChildren().add(email);
        info.getChildren().add(role);
        info.getChildren().add(ratio);

        info.setSpacing(10);
        info.setAlignment(Pos.TOP_CENTER);

        bpInfo.setLeft(this.avatarInfo);
        bpInfo.setCenter(info);

        BorderPane.setMargin(info, new Insets(0,0,0,10));

        return bpInfo;
    }
    
    public DuelSurLaToile getPlateforme(){
        return this.app;
    }
    
    /**
     * @return la liste des statistiques de l'utilisateur
     */
    public BorderPane getStatistique() {
        BorderPane bpListeAmi = new BorderPane();
        HBox top = new HBox();
        HBox center = new HBox();

        javafx.scene.control.Button az = new javafx.scene.control.Button("A-Z");
        az.setStyle("-fx-background-color:   #ffffff  ;");
        az.setUserData("az");
        javafx.scene.control.Button parRatio = new javafx.scene.control.Button("%");
        parRatio.setStyle("-fx-background-color:   #ffffff  ;");
        parRatio.setUserData("%");
        javafx.scene.control.Button chercher = new javafx.scene.control.Button("rechercher");
        chercher.setStyle("-fx-background-color:   #ffffff  ;");
        chercher.setUserData("search");

        ActionBoutonAmi act = new ActionBoutonAmi(this);
        chercher.setOnAction(act);
        az.setOnAction(act);
        parRatio.setOnAction(act);


        javafx.scene.control.Label titre = new javafx.scene.control.Label("Statistiques :");

        this.rechercher = new javafx.scene.control.TextField();

        top.getChildren().add(titre);
        top.getChildren().add(az);
        top.getChildren().add(parRatio);
        top.setAlignment(Pos.CENTER);
        top.setSpacing(5);

        center.getChildren().add(this.rechercher);
        center.getChildren().add(chercher);
        center.setAlignment(Pos.CENTER);
        center.setSpacing(5);

        bpListeAmi.setTop(top);
        bpListeAmi.setMargin(top, new Insets(10,10,10,10));
        bpListeAmi.setCenter(center);
        bpListeAmi.setBottom(listeStat);
        
        bpListeAmi.setMargin(center, new Insets(10,10,10,10));
        bpListeAmi.setMargin(listeStat, new Insets(10,10,10,10));

        return bpListeAmi;
    }

    public ListeStat getListeStat() {
        return listeStat;
    }
    
    public void setListeStat(ListeStat liste){
        this.listeStat = liste;
    }
    
    public TextField getRechercher(){
        return this.rechercher;
    }
    
    /**
     * Met à jour l'affichage
     * @throws SQLException
     */
    public void maj() throws SQLException {
        this.panel.getChildren().remove(1);
        this.panel.getChildren().add(getStatistique());
        this.setCenter(this.panel);
        this.setMargin(this.panel, new Insets(10, 10, 10, 10));
    }
}
