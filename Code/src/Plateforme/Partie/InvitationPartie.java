package Plateforme.Partie;

import Plateforme.Application.DuelSurLaToile;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.Date;


public class InvitationPartie extends BorderPane implements Comparable<InvitationPartie> {

    private int id;
    private String nom;
    private String proprietaire, inviter;
    private ImageView image;
    private Date date;
    private DuelSurLaToile duelSurLaToile;

    public InvitationPartie(DuelSurLaToile duelSurLaToile, int id, String nom, String prop, ImageView image, Date date){
        super();
        this.duelSurLaToile=duelSurLaToile;
        this.id=id;
        this.nom=nom;
        this.proprietaire=prop;
        this.image=image;
        this.date=date;
        this.interfaceIHM();
    }
    public InvitationPartie(DuelSurLaToile duelSurLaToile, int id, String nom, String prop, String invit, ImageView image, Date date){
        super();
        this.duelSurLaToile=duelSurLaToile;
        this.id=id;
        this.nom=nom;
        this.proprietaire=prop;
        this.inviter = invit;
        this.image=image;
        this.date=date;
        this.interfaceIHM();
    }

    public ImageView getImage() {
        return image;
    }

    public int getIdentifiant(){
        return this.id;
    }

    @Override
    public int compareTo(InvitationPartie invitationPartie){
        return this.date.compareTo(invitationPartie.date);
    }

    /**
     * methode qui permet de mettre l'interface de l'ihm pour les invitations parties
     */
    public void interfaceIHM(){
        VBox contenue;
        ActionBouttonInvitationPartie action = new ActionBouttonInvitationPartie(this.duelSurLaToile, this);
        Label nom = new Label("Jeu : "+this.nom);
        Label proprietaire = new Label("Partie de : "+this.proprietaire);
        Button accepter = new Button("Accepter");
        accepter.setStyle("-fx-background-color:   #ffffff  ;");
        accepter.setMinWidth(140);
        accepter.setMaxWidth(140);
        accepter.setUserData(this.id);
        Button refuser = new Button("Refuser");
        refuser.setStyle("-fx-background-color:   #ffffff  ;");
        refuser.setMinWidth(140);
        refuser.setMaxWidth(140);
        refuser.setUserData(this.id);
        javafx.scene.image.ImageView image = this.image;
        image.setFitWidth(120);
        image.setFitHeight(120);
        contenue=new VBox();
        contenue.getChildren().addAll(nom,proprietaire,accepter,refuser);
        contenue.setSpacing(5);
        contenue.setAlignment(Pos.CENTER_LEFT);
        contenue.setMaxWidth(refuser.getMaxWidth());
        this.setLeft(contenue);
        FlowPane fp=new FlowPane();
        fp.getChildren().add(image);
        fp.setMinWidth(140);
        fp.setMaxWidth(140);
        fp.setMinHeight(140);
        fp.setMaxHeight(140);
        this.setRight(image);
        accepter.setOnAction(action);
        refuser.setOnAction(action);
        BorderPane.setAlignment(contenue,Pos.CENTER);
        BorderPane.setMargin(contenue,new Insets(10,10,10,10));
        BorderPane.setMargin(image,new Insets(10,10,10,10));
        this.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
    }

    /**
     * met a jour l'IHM pour quel soit correcte sur n'importe pc
     */
    public void majIHM(){
        this.image.setFitWidth(this.getHeight()-10);
        this.image.setFitHeight(this.getHeight()-10);
    }
}
