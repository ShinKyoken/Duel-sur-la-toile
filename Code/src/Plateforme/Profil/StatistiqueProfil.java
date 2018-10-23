package Plateforme.Profil;

import Plateforme.Application.Alerte;
import Plateforme.Application.DuelSurLaToile;
import Plateforme.Application.Utilisateur;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.SQLException;

/**
 * Cette classe gère l'affichage des statistique et de l'historique de l'utilisateur courant
 * @author theodore
 */
public class StatistiqueProfil extends BorderPane{
    /**
     * la liste des amis de l'utilisateur
     * */
    private ListeStat listeStat;
    /**
     * la liste qui contient la vue de tout les historiquesPartie.
     */
    private ListeHistoriquePartie listeHistorique;
    /**
     * la zone de texte dans laquelle l'utilisateur entrera le pseudo du joueur rechecher
     */
    private javafx.scene.control.TextField rechercher;
    /**
     * les Vbox dans laquelle seront afficher les statiqtiques(panels) et l'historique(right).
     */
    private VBox panels, right;

    /**
     * L'utilisateur associé au profil
     */
    private Utilisateur utilisateur;

    /**
     * l'application
     * @param app
     */
    private DuelSurLaToile app;

    /**
     * Ce constructeur crée un BorderPane contenant deux Vbox. Celle de gauche contient les statistiques tandis que celle
     * de droite contient l'historique.
     * @param app 
     */
    public StatistiqueProfil(DuelSurLaToile app){
        this.app = app;
        this.utilisateur = app.getUtilisateur();
        try {
            this.listeStat = new ListeStat(this.app, app.getConnexion().getJeux(), app.getConnexion().getHistoriquePartie(this.utilisateur));
        }
        catch(SQLException e){
            Alerte.creationAlerte(e);
        }
        this.panels = new VBox();
        this.panels.getChildren().add(this.getStatistique());
        this.panels.getChildren().add(this.listeStat);
        this.panels.setMinWidth(DuelSurLaToile.longueur*0.48);
        this.panels.setMaxWidth(DuelSurLaToile.longueur*0.48);
        this.setLeft(panels);
        
        this.listeHistorique = new ListeHistoriquePartie(this.app, this.utilisateur);
        this.right = new VBox();
        this.right.getChildren().add(this.getHistorique());
        this.right.getChildren().add(this.listeHistorique);
        this.right.setMinWidth(DuelSurLaToile.longueur*0.48);
        this.right.setMaxWidth(DuelSurLaToile.longueur*0.48);
        this.setRight(right);
        
        this.setPadding(new Insets(30,10,30,10));
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

        ActionBoutonStat act = new ActionBoutonStat(this);
        chercher.setOnAction(act);
        az.setOnAction(act);
        parRatio.setOnAction(act);


        javafx.scene.control.Label titre = new javafx.scene.control.Label("Statistiques :");

        rechercher = new javafx.scene.control.TextField();

        top.getChildren().add(titre);
        top.getChildren().add(az);
        top.getChildren().add(parRatio);
        top.setAlignment(Pos.CENTER);
        top.setSpacing(5);

        center.getChildren().add(rechercher);
        center.getChildren().add(chercher);
        center.setAlignment(Pos.CENTER);
        center.setSpacing(5);

        bpListeAmi.setTop(top);
        bpListeAmi.setMargin(top, new Insets(5,5,5,5));
        bpListeAmi.setCenter(center);
        bpListeAmi.setBottom(listeStat);
        
        bpListeAmi.setMargin(center, new Insets(5,5,5,5));
        bpListeAmi.setMargin(listeStat, new Insets(5,5,5,5));

        return bpListeAmi;
    }

    public ListeStat getListeStat() {
        return listeStat;
    }

    public void setListeAmis(ListeStat val) {

        this.listeStat = val;
    }

    public TextField getRechercher() {
        return rechercher;
    }

    public VBox getPanels() {
        return panels;
    }

    public DuelSurLaToile getApp() {
        return app;
    }

    public void setListeStat(ListeStat listeStat) {
        this.listeStat = listeStat;
    }

    /**
     * Met à jour l'affichage
     */
    public void maj(){
        try{
            this.majHist();
            this.majStat();
        }
        catch(SQLException e){
            Alerte.creationAlerte(e);
        }
    }
    /**
     * Met à jour l'affichage des statistiques
     * @throws SQLException
     */
    public void majStat() throws SQLException {
        panels.getChildren().remove(1);
        panels.getChildren().add(this.getListeStat());
        this.setLeft(panels);
        this.setMargin(panels, new Insets(10, 10, 10, 10));
    }
    
    /**
     * @return un BorderPane contenant la liste des HistoriquePartie ainsi que les boutons permettant de trier cette liste.
     */
    public BorderPane getHistorique(){
        BorderPane bpListeAmi = new BorderPane();
        HBox top = new HBox();
        HBox center = new HBox();

        javafx.scene.control.Button jeu = new javafx.scene.control.Button("Jeu");
        jeu.setStyle("-fx-background-color:   #ffffff  ;");
        jeu.setUserData("az");
        javafx.scene.control.Button adversaire = new javafx.scene.control.Button("Adversaire");
        adversaire.setStyle("-fx-background-color:   #ffffff  ;");
        adversaire.setUserData("adversaire");
        javafx.scene.control.Button date = new javafx.scene.control.Button("Date");
        date.setStyle("-fx-background-color:   #ffffff  ;");
        date.setUserData("date");

        ActionBoutonHistorique act = new ActionBoutonHistorique(this);
        date.setOnAction(act);
        jeu.setOnAction(act);
        adversaire.setOnAction(act);


        javafx.scene.control.Label titre = new javafx.scene.control.Label("Historique :");


        top.getChildren().add(titre);
        top.getChildren().add(jeu);
        top.getChildren().add(adversaire);
        top.setAlignment(Pos.CENTER);
        top.setSpacing(5);

        center.getChildren().add(date);
        center.setAlignment(Pos.CENTER);
        center.setSpacing(5);

        bpListeAmi.setTop(top);
        bpListeAmi.setMargin(top, new Insets(5,5,5,5));
        bpListeAmi.setCenter(center);
        bpListeAmi.setMargin(center, new Insets(5,5,5,5));
        bpListeAmi.setBottom(this.listeHistorique);
        bpListeAmi.setMargin(this.listeHistorique, new Insets(5,5,5,5));
        
        return bpListeAmi;
    }
    
    public DuelSurLaToile getPlateforme(){
        return this.app;
    }
    
    public ListeHistoriquePartie getListeHistorique(){
        return this.listeHistorique;
    }
    
    /**
     * Met à jour l'affichage de l'historique
     * @throws SQLException
     */
    public void majHist() throws SQLException {
        this.right.getChildren().remove(1);
        this.right.getChildren().add(this.getListeHistorique());
        this.setRight(this.right);
        this.setMargin(this.right, new Insets(10, 10, 10, 10));
    }
}
