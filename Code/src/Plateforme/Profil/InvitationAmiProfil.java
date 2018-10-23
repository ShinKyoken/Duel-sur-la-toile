package Plateforme.Profil;

import Plateforme.Application.DuelSurLaToile;
import Plateforme.Application.Alerte;
import Plateforme.Connexion.ConnexionMySQL;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Cette classe gère l'affichage des Invitation et de la liste des utilisateur qui ne sont pas amis avec l'utilisateur
 * courant et permet d'inviter ces derniers et de répondre aux invitations reçues.
 * @author theodore
 */
public class InvitationAmiProfil extends BorderPane {

    /**
     * L'application qui hébergera le BorderPane créé ici
     */
    private DuelSurLaToile app;

    /**
     * la liste de tout les utilistateurs de la plateforme
     */
    private ListeInvitation listeInvit;
    /**
     * La VBox qui contiendra l'interface d'ajout d'ami
     */
    private VBox panelInvit, panelAjouter;
    /**
     * la liste de tout les utilistateurs de la plateforme
     */
    private ListePersonne lsPersonne;
    /**
     * Les deux TextField servant à rechercher une invitation ou un joueur à inviter
     */
    private TextField rechercherInvit, rechercherJoueur;

    /**
     * Crée un borderPane contenant la lsite des invitations reçue par l'utilisateur ainsi que les boutons permettant de les accepter ou de les refusée.
     * @param app
     */
    public InvitationAmiProfil(DuelSurLaToile app){
        this.app = app;
        try {
            this.listeInvit = new ListeInvitation(this, this.app.getConnexion().getInvitations(this.app.getUtilisateur()));
        }
        catch(SQLException e){
            System.out.println("Erreur de syntaxe SQL");
        }
        try {
            this.lsPersonne = new ListePersonne(this, app.getConnexion().listeUtilisateurNonAmis(app.getUtilisateur().getPseudoUt()));
        }
        catch(SQLException e){
            System.out.println("Erreur de Syntaxe SQL");
        }

        this.panelAjouter = new VBox();
        this.panelAjouter.getChildren().add(getListePersonne());
        this.panelAjouter.setMinWidth(DuelSurLaToile.longueur*0.5);
        this.panelAjouter.setMaxWidth(DuelSurLaToile.longueur*0.5);
        this.setLeft(this.panelAjouter);
        BorderPane.setMargin(this.panelAjouter, new Insets(10,0,0,10));
        
        this.panelInvit = new VBox();
        this.panelInvit.getChildren().add(getListeInvitation());
        this.panelInvit.setMinWidth(DuelSurLaToile.longueur*0.38);
        this.panelInvit.setMaxWidth(DuelSurLaToile.longueur*0.38);
        this.setRight(this.panelInvit);
        BorderPane.setMargin(this.panelInvit, new Insets(10,10,0,0));
        
        this.setPadding(new Insets(10));
    }
    
    /**
     * @return la liste de tout les utilisateurs de la plateforme
     */
    public BorderPane getListePersonne(){
        BorderPane bpListePersonne = new BorderPane();
        HBox top = new HBox();
        HBox center = new HBox();

        ActionBoutonAjouterAmi act = new ActionBoutonAjouterAmi(this);

        Button az = new Button("A-Z");
        az.setStyle("-fx-background-color:   #ffffff  ;");
        az.setUserData("az");
        az.setOnAction(act);
        Button enLigne = new Button("En ligne");
        enLigne.setStyle("-fx-background-color:   #ffffff  ;");
        Button chercher = new Button("chercher");
        chercher.setStyle("-fx-background-color:   #ffffff  ;");
        chercher.setUserData("search");
        chercher.setOnAction(act);
        Label titre = new Label("Chercher un amis");

        this.rechercherJoueur = new TextField();

        top.getChildren().add(titre);
        top.getChildren().add(az);
        top.getChildren().add(enLigne);
        top.setAlignment(Pos.CENTER);
        top.setSpacing(5);

        center.getChildren().add(this.rechercherJoueur);
        center.getChildren().add(chercher);
        center.setAlignment(Pos.CENTER);
        center.setSpacing(5);

        bpListePersonne.setTop(top);
        bpListePersonne.setCenter(center);
        bpListePersonne.setBottom(lsPersonne);

        BorderPane.setMargin(center, new Insets(10,0,0,0));
        BorderPane.setMargin(lsPersonne, new Insets(10,0,0,0));

        return bpListePersonne;
    }

    /**
     * @return la liste de toute les invitations reçue par l'utilisateur.
     */
    public BorderPane getListeInvitation(){
        BorderPane bpListePersonne = new BorderPane();
        HBox top = new HBox();
        HBox center = new HBox();

        ActionBoutonInvitation act = new ActionBoutonInvitation(this);

        Button az = new Button("A-Z");
        az.setStyle("-fx-background-color:   #ffffff  ;");
        az.setUserData("az");
        az.setOnAction(act);
        Button parDate  =new Button("Date");
        parDate.setStyle("-fx-background-color:   #ffffff  ;");
        parDate.setUserData("date");
        parDate.setOnAction(act);
        Label titre = new Label("Invitations");

        this.rechercherInvit = new TextField();
        Button chercher  =new Button("Rechercher");
        parDate.setStyle("-fx-background-color:   #ffffff  ;");
        parDate.setUserData("checherr");
        parDate.setOnAction(act);
        
        top.getChildren().add(titre);
        top.getChildren().add(az);
        top.getChildren().add(parDate);
        top.setAlignment(Pos.CENTER);
        top.setSpacing(5);

        center.getChildren().add(this.rechercherInvit);
        center.getChildren().add(chercher);
        center.setAlignment(Pos.CENTER);
        center.setSpacing(5);

        bpListePersonne.setTop(top);
        bpListePersonne.setCenter(center);
        bpListePersonne.setBottom(listeInvit);

        BorderPane.setMargin(center, new Insets(10,0,0,0));
        BorderPane.setMargin(listeInvit, new Insets(10,0,0,0));

        return bpListePersonne;
    }

    public ConnexionMySQL getConnexion() {
        return this.app.getConnexion();
    }

    public DuelSurLaToile getPlateforme() {
        return this.app;
    }

    public void setListeInvit(ListeInvitation val) {
        this.listeInvit = val;
    }

    /**
     * Met à jour la liste des invitations lorsque l'une d'elle est accetée ou refusée.
     * @throws SQLException
     */
    public void majInvit() throws SQLException {

        panelInvit.getChildren().remove(0);
        panelInvit.getChildren().add(getListeInvitation());

        this.setRight(panelInvit);

        BorderPane.setMargin(panelInvit, new Insets(10,0,0,10));
    }

    public TextField getRechercherJoueur() {
        return this.rechercherJoueur;
    }

    public void setListePersonne(ListePersonne val) {
        this.lsPersonne = val;
    }

    /**
     * Met à jour la liste de personne lors d'une recherche ou de l'ajout d'un ami.
     * @throws SQLException
     */
    public void majJoueur() throws SQLException {

        panelAjouter.getChildren().remove(0);
        panelAjouter.getChildren().add(0, this.getListePersonne());

        this.setLeft(panelAjouter);

        BorderPane.setMargin(panelAjouter, new Insets(10,0,0,10));
    }

    public TextField getRechercherInvit() {
        return this.rechercherInvit;
    }
    
    /**
     * Met à jour l'affichage
     */
    public void maj(){
        try{
            this.majInvit();
            this.majJoueur();
        }
        catch(SQLException e){
            Alerte.creationAlerte(e);
        }
    }

    public ListeInvitation getListeInvit() {
        return listeInvit;
    }

    public ListePersonne getLsPersonne() {
        return lsPersonne;
    }
}
