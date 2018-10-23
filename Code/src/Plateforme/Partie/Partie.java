package Plateforme.Partie;

import Plateforme.Application.DuelSurLaToile;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class Partie extends BorderPane{

    private DuelSurLaToile app;
    private ListeInvitationPartie listeInvitationPartie;
    private ListePartieEnCours listePartieEnCours;

    /**
     * constructeur de Partie qui crée 2 scroolPane une pour la partie de droit(Invitation de partie) et de gauche(Partie en cours)
     * @param app
     */
    public Partie(DuelSurLaToile app){
        this.app=app;
        this.listeInvitationPartie=new ListeInvitationPartie(this.app);
        this.listePartieEnCours=new ListePartieEnCours(this.app);
        this.ajouteInterface();
    }

    /**
     * ajoute l'IHM necessaire pour l'affichage de l'application
     */
    public void ajouteInterface(){
        this.listeInvitationPartie=new ListeInvitationPartie(this.app);
        this.listePartieEnCours=new ListePartieEnCours(this.app);
        Label invitation = new Label("Invitations de Partie");
        invitation.setFont(new Font(20));
        Label partieEnCours = new Label("Partie en Cours");
        partieEnCours.setFont(new Font(20));
        BorderPane titre = new BorderPane();
        titre.setPadding(new Insets(10,10,10, 10));
        HBox hBox = new HBox();
        Label partie = new Label("Partie");
        partie.setFont(new Font(30));
        FlowPane inv = new FlowPane();
        inv.getChildren().add(invitation);
        inv.setAlignment(Pos.CENTER);
        hBox.getChildren().add(inv);
        FlowPane enCours = new FlowPane();
        enCours.getChildren().add(partieEnCours);
        enCours.setAlignment(Pos.CENTER);
        hBox.getChildren().add(enCours);
        hBox.setAlignment(Pos.CENTER);
        FlowPane nom=new FlowPane();
        nom.getChildren().add(partie);
        nom.setAlignment(Pos.CENTER);
        titre.setTop(nom);
        titre.setBottom(hBox);
        BorderPane.setAlignment(hBox,Pos.CENTER);
        BorderPane.setAlignment(titre, Pos.CENTER);
        this.setTop(titre);
        this.setLeft(this.listeInvitationPartie);
        this.setRight(this.listePartieEnCours);
        this.setPadding(new Insets(10,10,10, 10));
    }

    /**
     * met a jour l'IHM pour le visuelle de nimporte quel ordinateur qu'il ouvrira la plateforme
     */
    public void majIHM(){
        this.listeInvitationPartie.majIHM();
        this.listePartieEnCours.majIHM();
    }

    /**
     * met a jour par rapport a la base de donnée
     */
    public void maj(){
        this.listePartieEnCours.maj();
        this.listeInvitationPartie.maj();
        this.majIHM();
    }

    public ListeInvitationPartie getListeInvitationPartie() {
        return listeInvitationPartie;
    }

    public ListePartieEnCours getListePartieEnCours() {
        return listePartieEnCours;
    }
}
