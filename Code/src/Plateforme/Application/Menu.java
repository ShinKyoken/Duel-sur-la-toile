package Plateforme.Application;

import Plateforme.Jeux.*;
import Plateforme.Connexion.*;
import Plateforme.Conversation.*;
import Plateforme.Administration.*;
import Plateforme.Partie.*;
import Plateforme.Profil.*;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

public class Menu extends HBox{

    private Scene s;
	private DuelSurLaToile app;

    /**
     * constructeur de Menu
     * @param app
     * @param s
     */
    public Menu(DuelSurLaToile app, Scene s) {
        this.app=app;
        this.s=s;
        System.out.print(app.getUtilisateur().isAdmin());
        this.creationMenu();
	}

    /**
     * Permet de créer le menu de bas de la plateforme et ajoute un boutton si neccessaire pour l'adiministrateur
     */
	public void creationMenu() {
        Button jeux = new Button("Jeux");
		jeux.setUserData("jeux");
		jeux.setStyle("-fx-background-color:   #ffffff  ;");

		Button conversation = new Button("Conversation");
		conversation.setUserData("conv");
		conversation.setStyle("-fx-background-color:   #ffffff  ;");

		Button partie = new Button("Partie");
		partie.setUserData("partie");
		partie.setStyle("-fx-background-color:   #ffffff  ;");

		MenuItem infoPerso = new MenuItem("Profil");
		infoPerso.setUserData("info");

		MenuItem statistique = new MenuItem("Statistique");
		statistique.setUserData("stat");

		MenuItem invitation = new MenuItem("Invitation");
		invitation.setUserData("invite");

		MenuItem deconnexion = new MenuItem("Déconnexion");
		deconnexion.setUserData("deco");

		MenuButton profile = new MenuButton("Profil", null, infoPerso, statistique, invitation, deconnexion);
		profile.setUserData("profile");
		profile.setStyle("-fx-background-color:   #ffffff  ;");

		this.getChildren().add(jeux);
		this.getChildren().add(profile);
		this.getChildren().add(conversation);
		this.getChildren().add(partie);

		jeux.setMinWidth(s.getWidth()/4);
		profile.setMinWidth(s.getWidth()/4);
		conversation.setMinWidth(s.getWidth()/4);
		partie.setMinWidth(s.getWidth()/4);

		ActionBouttonMenu actBoutton = new ActionBouttonMenu(app);
		ActionMenuItemMenu actMenuItem = new ActionMenuItemMenu(app);

		int taille = 4, reduire=18;

		if (app.getUtilisateur().isAdmin().equals("Admin")){
			Button admin = new Button("Administration");
			admin.setUserData("admin");
			admin.setStyle("-fx-background-color:   #ffffff  ;");
			this.getChildren().add(admin);
			taille=5;
			reduire=21;
			admin.setOnAction(actBoutton);
			admin.setMinWidth((s.getWidth()-reduire)/taille);
		}

		jeux.setMinWidth((s.getWidth()-reduire)/taille);
		profile.setMinWidth((s.getWidth()-reduire)/taille);
		conversation.setMinWidth((s.getWidth()-reduire)/taille);
		partie.setMinWidth((s.getWidth()-reduire)/taille);

		jeux.setOnAction(actBoutton);
		conversation.setOnAction(actBoutton);
		partie.setOnAction(actBoutton);

		infoPerso.setOnAction(actMenuItem);
		statistique.setOnAction(actMenuItem);
		invitation.setOnAction(actMenuItem);
		deconnexion.setOnAction(actMenuItem);

		this.setSpacing(3);
		this.setPadding(new Insets(3,3,3,3));
		this.setMinWidth(s.getWidth());
		this.setMinHeight(15.0);
    }

}
