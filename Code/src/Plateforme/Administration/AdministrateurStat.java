package Plateforme.Administration;

import Plateforme.Application.DuelSurLaToile;

import java.io.IOException;
import java.sql.SQLException;

import Plateforme.Connexion.ConnexionMySQL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import Plateforme.Connexion.ConnexionMySQL;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AdministrateurStat extends BorderPane{

    private ListView<String> stat;
    private ListView<String> valeur;
    private Button utilisateur;
    private Button jeux;
    private TextField choixJeu;
    private Label recherche;
    private Button boutonRecherche;
    private Button reset;
    private HBox panels;
    private DuelSurLaToile app;
    private ConnexionMySQL co;
    private String str;

    /**
     * Méthode gérant l'affichage de la page d'administration gérant les stats.
     * @param app l'application
     * @throws IOException
     * @throws SQLException
     */
    public AdministrateurStat(DuelSurLaToile app) throws SQLException{

        this.app=app;

        try{
            this.co=new ConnexionMySQL();
            this.co.connecter();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        this.str=String.valueOf(this.co.getNbPartie(null));
        this.stat = new ListView<String>();
        ObservableList<String> items = FXCollections.observableArrayList(
                "Statistiques", "Nombre de partie en cours","Nombre d'utilisateur inscrit","Nbre d'utilisateur sur la plateforme", "Nombre d'utilisateur bannis", "Nombre de message envoyé");
        this.stat.setItems(items);
        this.stat.setMaxWidth(2000);

        this.valeur = new ListView<String>();
        ObservableList<String> items2 = FXCollections.observableArrayList(
                "Statistiques",this.str,String.valueOf(co.getNbUtilisateur()),String.valueOf(co.getNbUtilCo()),String.valueOf(co.getNbBannis()),String.valueOf(co.getNbMessage()));
        this.valeur.setItems(items2);
        this.valeur.setMaxWidth(1000);

        HBox top=new HBox();
        panels = new HBox();
        HBox bot=new HBox();
       
        panels.getChildren().addAll(this.stat,this.valeur);
        panels.setAlignment(Pos.CENTER);
        panels.setMaxWidth(3000);
        
        
        ActionBoutonStat act = new ActionBoutonStat(this);
        this.utilisateur=new Button("joueur");
        this.utilisateur.setUserData("user");
        this.utilisateur.setOnAction(act);
        this.utilisateur.setStyle("-fx-background-color: #ffffff ;");
        this.utilisateur.setMaxWidth(Double.MAX_VALUE);

        this.reset=new Button("reset");
        this.reset.setUserData("reset1");
        this.reset.setOnAction(act);
        this.reset.setStyle("-fx-background-color: #ffffff ;");
        this.reset.setMaxWidth(Double.MAX_VALUE);

        this.jeux=new Button("Jeux");
        this.jeux.setUserData("game");
        this.jeux.setOnAction(act);
        this.jeux.setStyle("-fx-background-color: #ffffff ;");
        this.jeux.setMaxWidth(Double.MAX_VALUE);

        this.boutonRecherche=new Button("Rechercher pour le jeu");
        this.boutonRecherche.setUserData("ButtonSearch");
        this.boutonRecherche.setOnAction(act);
        this.boutonRecherche.setStyle("-fx-background-color: #ffffff ;");
        this.boutonRecherche.setMaxWidth(Double.MAX_VALUE);

        this.choixJeu=new TextField();

        this.recherche=new Label("Rechercher : ");

        top.getChildren().addAll(utilisateur,jeux);
        
        bot.getChildren().addAll(this.recherche,this.choixJeu,this.boutonRecherche,this.reset);

        this.setTop(top);
        top.setSpacing(5.0);

        this.setCenter(panels);

        this.setBottom(bot);
        bot.setSpacing(5.0);
    }

    /**
     * Retourne l'application.
     * @return
     */
    public DuelSurLaToile getApp(){
        return this.app;
    }

    /**
     * Retourne la connexion.
     * @return
     */
    public ConnexionMySQL getCo(){return this.co;}

    /**
     * Retourne le nom du jeu.
     * @return
     */
    public String getJeu(){return this.choixJeu.getText();}

    /**
     * Modifie la valeur du nombre de partie en cours.
     * @param str un String
     */
    public void setStr(String str) {
        this.str = str;
    }

    public void maj() throws SQLException {
        this.valeur = new ListView<String>();
        ObservableList<String> items2 = FXCollections.observableArrayList(
                "Statistiques",this.str,String.valueOf(co.getNbUtilisateur()),String.valueOf(co.getNbUtilCo()),String.valueOf(co.getNbBannis()),String.valueOf(co.getNbMessage()));
        this.valeur.setItems(items2);
        this.valeur.setMaxWidth(1000);
        panels.getChildren().clear();
        panels.getChildren().addAll(this.stat,this.valeur);
        this.setCenter(panels);

    }
}
