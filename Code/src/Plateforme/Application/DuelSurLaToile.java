package Plateforme.Application;

import Plateforme.Profil.ListeHistoriquePartie;
import Plateforme.Jeux.*;
import Plateforme.Connexion.*;
import Plateforme.Conversation.*;
import Plateforme.Administration.*;
import Plateforme.Partie.*;
import Plateforme.Profil.*;


import Plateforme.Profil.Profil;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import Jeux.Mastermind.ActionFermetureMastermind;

public class DuelSurLaToile extends Application{
    private static Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    public static int hauteur= (int)(dimension.getHeight()/1.25);
    public static int longueur= (int)(dimension.getWidth()/1.1);

    /**
     * Il s'agit du stage de l'application Duel Sur La Toile
     */
    private Stage stage;


    /**
     * Il s'agit de la scene qu'on changera au fur et à mesure de la plateforme
     * pour passer entre les différents pages du menu
     */
    private Scene windows;

    /**
     * Il s'agit du menu pour changer les différentes scenes
     * Il y aura 2 menus : un pour joueur et un pour admin
     * Le code se trouve dans le même package
     */
    private Menu menu;

    /**
     * Il s'agit de la page des jeux qui correspond à l'acceuil de l'application avec une liste de jeux
     * Le code se trouve dans Plateforme.Jeux
     */
    private PageDesJeux pageDesJeux;

    /**
     * Il s'agit de la connexion pour se connecter à la plateforme à la BD
     * Le code se trouve dans Plateforme.Connexion
     */
    private ConnexionMySQL mysql;

    /**
     * Il s'agit de l'utilisateur qui se connect à la plateforme
     * L'utilisateur est utile pour savoir si l'utilisteur est encore connecter et
     * Le code se trouve dans le même package
     */
    private Utilisateur utilisateur;

    /**
     * Il s'agit de la scene pour le profil du joueur
     * Le code se trouve dans Plateforme.Profil
     */
    private Profil profil;

    /**
     * Il s'agit de la scene pour la conversation
     * Le code se trouve dans Plateforme.Conversation
     */
    private Conversation conversation;


    /**
     * Il s'agit de la scene pour statistique du joueur
     * Le code se trouve dans Plateforme.Profil
     */
    private StatistiqueProfil statistiqueProfil;

    /**
     * Il s'agit de la scene pour les invitations ami du profil
     * Le code se trouve dans Plateforme.Profil
     */
    private InvitationAmiProfil invitationAmiProfil;

    /**
     * Il s'agit de la scene pour voir les parties en cours et les invitations de partie
     * Le code se trouve dans Plateforme.Partie
     */
    private Partie partie;

    /**
     * Il s'agit de la scene pour visualiser la page réserver pour l'administrateur (la partie gérer joueur)
     * Le code se trouve dans Plateforme.Administrateur
     */
    private Administrateur administrateur;

    /**
     * Il s'agit de la scene pour visualiser la page réserver pour l'administrateur (la partie gérer jeu)
     * Le code se trouve dans Plateforme.Administrateur
     */
    private AdministrateurJeu admin;

    /**
     *Il s'agit de la scene pour visualiser la page réserver pour l'administrateur (la partie gérer jeu)
     *Le code se trouve dans Plateforme.Administrateur
     */
    private AdministrateurStat adminStat;
    /**
     * Il s'agit de la scnène servant à inviter d'autres joueurs à jouer ou à rejoindre une partie ouverte.
     */
    private InviterPartie invitPartie;
    
    private AjoutJeu addJeu;
    
    private ModifierJeu modifJeu;
    
    private ProfilAmis profilAmis;
    
    /**
     * 
     * @param args
     */
	public static void main(String[] args) {
		launch(args);
	}

	/**
     * @throws Exception
     */
	@Override
	public void start(Stage a) throws Exception {
		ActionFermetureDSLT act = new ActionFermetureDSLT(this);
		
		this.mysql = new ConnexionMySQL();
		this.mysql.connecter();

		VBox bp = new VBox();
        bp.setBackground(new Background(new BackgroundFill(Color.rgb( 205, 205, 205 ), null, null)));
		this.windows = new Scene(bp, DuelSurLaToile.longueur,DuelSurLaToile.hauteur*0.9);

		this.pageDesJeux = new PageDesJeux(this);
		this.partie = new Partie(this);
		this.menu = new Menu(this,this.windows);
		this.profil = new Profil(this);
		this.pageDesJeux = new PageDesJeux(this);
		this.statistiqueProfil = new StatistiqueProfil(this);
		this.invitationAmiProfil = new InvitationAmiProfil(this);
		this.administrateur = new Administrateur(this);
		this.admin = new AdministrateurJeu(this);
		this.adminStat = new AdministrateurStat(this);
		this.conversation = new Conversation(this);
		this.addJeu = new AjoutJeu(this);
        this.partie = new Partie(this);

		bp.getChildren().add(this.menu);
		bp.getChildren().add(this.pageDesJeux);
		this.stage = a ;
		this.stage.setTitle("Duel Sur La Toile");
		this.stage.setScene(windows);
		this.stage.setResizable(false);
		this.stage.show();
		this.stage.setOnCloseRequest(act);
	}

    /**
     * getter
     * @return utilisateur
     */
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    /**
     * setter
     * @param utilisateur
     */
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    /**
     *
     * @return stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Affiche la page du profil de l'utilisateur
     * @throws SQLException
     */
    public void afficherProfil() throws SQLException{
        this.profil.maj();
        VBox fp = ((VBox) windows.getRoot());
        fp.getChildren().remove(1);
        fp.getChildren().add(profil);
    }

    /**
     * Affiche la page pour afficher les parties en cours et les invitations de parties
     * @throws SQLException
     */
    public void afficherPartie() throws SQLException{
        this.partie.maj();
        VBox fp = ((VBox) windows.getRoot());
        fp.getChildren().remove(1);
        fp.getChildren().add(partie);
    }

    /**
     * Affiche la page pour afficher les invitations amis que l'utilisateur a recu
     * @throws SQLException
     */
    public void afficherInvitationAmiProfil() throws SQLException{
        this.invitationAmiProfil.maj();
        VBox fp = ((VBox) windows.getRoot());
        fp.getChildren().remove(1);
        fp.getChildren().add(invitationAmiProfil);
    }

    /**
     * Affiche la page pour afficher statistique de l'utilisateur
     * @throws SQLException
     */
    public void afficherStatistiqueProfil() throws SQLException{
        this.statistiqueProfil.maj();
        VBox fp = ((VBox) windows.getRoot());
        fp.getChildren().remove(1);
        fp.getChildren().add(statistiqueProfil);
    }

    /**
     * Affiche la page pour afficher les conversations de l'utilisateur
     * @throws SQLException
     */
    public void afficherConversation() throws SQLException{
        this.conversation.maj();
        VBox fp = ((VBox) windows.getRoot());
        fp.getChildren().remove(1);
        fp.getChildren().add(conversation);
    }

    /**
     * Affiche la page pour voir tous les jeux
     * @throws SQLException
     */
    public void afficherPageDesJeux() throws SQLException{
        this.pageDesJeux.maj();
        VBox fp = ((VBox) windows.getRoot());
        fp.getChildren().remove(1);
        fp.getChildren().add(pageDesJeux);
    }

    /**
     * Affiche la page pour voir la page de l'administrateur
     * @throws SQLException
     */
    public void afficherAdministrateur() throws SQLException{
        this.administrateur.majAffichage();
        VBox fp = ((VBox) windows.getRoot());
        fp.getChildren().remove(1);
        fp.getChildren().add(administrateur);
    }

    public void afficherAdministrateurJeu() throws SQLException, IOException, ClassNotFoundException {
        this.admin = new AdministrateurJeu(this);
        VBox fp = ((VBox) windows.getRoot());
        fp.getChildren().remove(1);
        fp.getChildren().add(admin);
    }

    public void afficherAdministrateurStat() throws SQLException,IOException{
        this.adminStat=new AdministrateurStat(this);
        VBox fp = ((VBox) windows.getRoot());
        fp.getChildren().remove(1);
        fp.getChildren().add(adminStat);
    }
    
    public void afficherAjoutJeu() throws SQLException {
    	this.addJeu = new AjoutJeu(this);
    	VBox fp = ((VBox) windows.getRoot());
        fp.getChildren().remove(1);
        fp.getChildren().add(addJeu);
    }
    
    public void afficherModifierJeu(int idJeu, int idType, String nomVal, String descriptionVal, String classeVal) throws SQLException {
    	this.modifJeu = new ModifierJeu(this, idJeu, idType, nomVal, descriptionVal, classeVal);
    	VBox fp = ((VBox) windows.getRoot());
        fp.getChildren().remove(1);
        fp.getChildren().add(modifJeu);
    }
    
    public void afficherInviterJeu(Jeu jeu) throws SQLException {
    	this.invitPartie = new InviterPartie(this, jeu);
    	VBox fp = ((VBox) windows.getRoot());
        fp.getChildren().remove(1);
        fp.getChildren().add(this.invitPartie);
    }
    
    public void afficherProfilAmi(Utilisateur util) throws SQLException {
    	this.profilAmis = new ProfilAmis(this, util);
    	VBox fp = ((VBox) windows.getRoot());
        fp.getChildren().remove(1);
        fp.getChildren().add(this.profilAmis);
    }
    
    /**
     * getter
     * @return ConnexionMySQL
     */
	public ConnexionMySQL getConnexion() {
		return this.mysql;
	}

    /**
     * getter
     * @return scene
     */
	public Scene getScene() {
		return windows;
	}

    public Partie getPartie() {
        return partie;
    }

    public void majIHM(){
	    this.partie.majIHM();
    }
}
