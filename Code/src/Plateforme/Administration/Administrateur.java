package Plateforme.Administration;

import Plateforme.Application.DuelSurLaToile;
import Plateforme.Application.Utilisateur;
import Plateforme.Connexion.ConnexionMySQL;
import Plateforme.Profil.ListePersonne;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Administrateur extends BorderPane{

    private TableView lsPersonne;
    private DuelSurLaToile app;
    private TextField pseudo, mail;
    private Label id;
    private RadioButton admin,joueur;
    private CheckBox valid;
    
    public Administrateur(DuelSurLaToile app){
        this.app = app;
        
        try {
            this.lsPersonne = new ListeUtilisateur(this, app.getConnexion().getListeUtilisateur()).getListeUtil();
        }
        catch(SQLException e){
            System.out.println("Erreur de Syntaxe SQL");
        }

        VBox vTab = new VBox();
        HBox tab = new HBox();
        vTab.getChildren().addAll(textView(),buttonBot());
        tab.getChildren().addAll(vTab,this.lsPersonne);
        tab.setAlignment(Pos.CENTER);
        tab.setSpacing(75);
        this.setTop(buttonTop());
        this.setCenter(tab);

    }

    /**
     * Affichage des zone de saisie de texte.
     * @return l'interface de saisie de texte.
     */
    public VBox textView(){

        VBox user = new VBox();

        this.id = new Label("ID");
        this.id.setFont(Font.font("sanSerif",20));
        this.id.setMaxWidth(300);

        this.pseudo = new TextField();
        this.pseudo.setFont(Font.font("sanSerif",20));
        this.pseudo.setPromptText("Pseudo");
        this.pseudo.setMaxWidth(300);

        this.mail = new TextField();
        this.mail.setFont(Font.font("sanSerif",20));
        this.mail.setPromptText("Email");
        this.mail.setMaxWidth(300);

        ToggleGroup roleGroup =new ToggleGroup();

        this.admin = new RadioButton("Admin");
        this.admin.setToggleGroup(roleGroup);
        this.joueur = new RadioButton("Joueur");
        this.joueur.setToggleGroup(roleGroup);

        this.valid = new CheckBox("Etat");



        user.getChildren().addAll(this.id,this.pseudo,this.mail,this.admin,this.joueur,this.valid);

        user.setSpacing(10);







        return user;
    }

    /**
     * Cree les boutons permettant de se déplacer entre les pages d'administration
     * @return l'interface des boutons.
     */
    public HBox buttonTop(){
        HBox buttonTop = new HBox();

        Button jeu = new Button("Jeux");
        ActionBouton act=new ActionBouton(this);
        jeu.setOnAction(act);
        jeu.setUserData("game");
        jeu.setStyle("-fx-background-color: #ffffff ;");

        Button stat = new Button("Statistiques");
        stat.setOnAction(act);
        stat.setUserData("stats");
        stat.setStyle("-fx-background-color: #ffffff ;");
        buttonTop.getChildren().addAll(jeu,stat);
        buttonTop.setSpacing(20);
        buttonTop.setMinWidth(200);
        buttonTop.setPadding(new Insets(10,10,10,10));

        return buttonTop;
    }

    /**
     * Cree les boutons de gestion de joueurs.
     * @return l'interface de boutons.
     */
   public HBox buttonBot(){
       HBox buttonBot =new HBox();

       UpdateButton up = new UpdateButton(this);      
       Button MajButton=new Button("Mettre à jour");
       MajButton.setOnAction(up);
       MajButton.setStyle("-fx-background-color: #ffffff ;");
       buttonBot.getChildren().add(MajButton);
       buttonBot.setAlignment(Pos.BOTTOM_CENTER);
       buttonBot.setSpacing(20);
       buttonBot.setPadding(new Insets(10,10,10,10));
       return buttonBot;

   }

    /**
     * Retourne le TextField contenant le pseudo de l'utilisateur.
     * @return
     */
   public TextField getPseudoField(){
        return this.pseudo;
    }

    public Label getIdLabel(){
        return this.id;
    }

    /**
     * Retourne le TextField contenant le mail de l'utilisateur.
     * @return
     */
    public TextField getMailField(){
        return this.mail;
    }

    /**
     * Retourne le radioButton permettant de changer le status de l'utilisateur
     * @return
     */
    public RadioButton getadminButton(){
        return this.admin;
    }

    /**
     * Retourne le radioButton permettant de changer le status de l'utilisateur
     * @return
     */
    public RadioButton getJoueurButton(){
        return this.joueur;
    }

    public CheckBox getEtatBox(){
        return this.valid;
    }
    public void majAffichage(){
        try {
            this.lsPersonne = new ListeUtilisateur(this, app.getConnexion().getListeUtilisateur()).getListeUtil();
        }
        catch(SQLException e){
            System.out.println("Erreur de Syntaxe SQL");
        }

        VBox vTab = new VBox();
        HBox tab = new HBox();
        vTab.getChildren().addAll(textView(),buttonBot());
        tab.getChildren().addAll(vTab,this.lsPersonne);
        tab.setAlignment(Pos.CENTER);
        tab.setSpacing(75);
        this.setTop(buttonTop());
        this.setCenter(tab);
    }

    public DuelSurLaToile getApp(){
        return this.app;
    }
}
