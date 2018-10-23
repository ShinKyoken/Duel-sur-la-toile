package Plateforme.Administration;

import Plateforme.Application.DuelSurLaToile;

import java.io.IOException;
import java.sql.SQLException;

import Plateforme.Connexion.ConnexionMySQL;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AdministrateurJeu extends BorderPane{

    private TableView lsJeu;
    private DuelSurLaToile app;
    private ListeJeux ls;
    private ConnexionMySQL co;

    /**
     * Méthode gérant l'affichage de la page administration gérant les jeux.
     * @param app l'application
     * @throws IOException
     */
    public AdministrateurJeu(DuelSurLaToile app) throws IOException, SQLException, ClassNotFoundException {

        this.app = app;

        this.co=new ConnexionMySQL();
        this.co.connecter();

        try {
            this.ls = new ListeJeux(this, app.getConnexion().getJeux());
            this.lsJeu = ls.getListeJeux();
        }
        catch(SQLException e){
            System.out.println("Erreur de Syntaxe SQL");
        }

        HBox top=new HBox();
        VBox panels = new VBox();
        HBox bot =new HBox();



        Button utilisateur=new Button("joueur");
        utilisateur.setUserData("user");
        utilisateur.setStyle("-fx-background-color: #ffffff ;");
        utilisateur.setMaxWidth(Double.MAX_VALUE);
        
        ActionBoutonJeu act = new ActionBoutonJeu(this);
        utilisateur.setOnAction(act);

        Button stats=new Button("Statistiques");
        stats.setUserData("stats");
        stats.setStyle("-fx-background-color: #ffffff ;");
        stats.setOnAction(act);
        stats.setMaxWidth(Double.MAX_VALUE);
        
        Button changeStatus=new Button("Changer status");
        changeStatus.setUserData("change");
        changeStatus.setStyle("-fx-background-color: #ffffff ;");
        changeStatus.setOnAction(act);
        changeStatus.setMaxWidth(Double.MAX_VALUE);

        Button modifier=new Button("Modifier");
        modifier.setUserData("modif");
        modifier.setStyle("-fx-background-color: #ffffff ;");
        modifier.setOnAction(act);
        modifier.setMaxWidth(Double.MAX_VALUE);
        
        Button ajouter=new Button("Ajouter");
        ajouter.setUserData("ajout");
        ajouter.setStyle("-fx-background-color: #ffffff ;");
        ajouter.setOnAction(act);
        ajouter.setMaxWidth(Double.MAX_VALUE);


        top.getChildren().addAll(utilisateur,stats);
        top.setSpacing(5.0);
        
        panels.getChildren().add(this.lsJeu);
        bot.getChildren().addAll(changeStatus,modifier,ajouter);
        bot.setSpacing(5.0);
        


        panels.setSpacing(50);

        this.setTop(top);
        this.setCenter(panels);
        this.setBottom(bot);

        BorderPane.setMargin(top, new Insets(10,10,10,10));
        BorderPane.setMargin(panels, new Insets(10,10,10,10));
        BorderPane.setMargin(bot, new Insets(10,10,10,10));


    }

    /**
     * Retourne l'application
     * @return
     */
    public DuelSurLaToile getApp(){
        return this.app;
    }

    /**
     * Retourne la liste des jeux.
     * @return
     */
    public ListeJeux getListeJeu() {
    	return this.ls;
    }

    public ConnexionMySQL getCo(){
        return this.co;
    }
    
}
