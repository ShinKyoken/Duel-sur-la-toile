package Plateforme.Partie;

import Plateforme.Application.DuelSurLaToile;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;


public class PartieEnCours extends BorderPane implements Comparable<PartieEnCours>{
    private String nom;
    private String adversaire;
    private ImageView image;
    private int id;
    private int scoreAdversaire, scoreJoueur;
    private DuelSurLaToile duelSurLaToile;

    /**
     * constructeur de Partie en cours
     * @param duelSurLaToile
     * @param id
     * @param nom
     * @param prop
     * @param image
     * @param scoreAdversaire
     * @param scoreJoueur
     */
    public PartieEnCours(DuelSurLaToile duelSurLaToile, int id, String nom, String prop, ImageView image, int scoreAdversaire, int scoreJoueur){
        this.nom=nom;
        this.adversaire=prop;
        this.image=image;
        this.scoreAdversaire=scoreAdversaire;
        this.scoreJoueur=scoreJoueur;
        this.id=id;
        this.duelSurLaToile=duelSurLaToile;
        this.interfaceIHM();
    }

    @Override
    public String toString(){
        return this.nom+" "+this.id+" "+this.adversaire;
    }

    public ImageView getImage() {
        return image;
    }

    public int getIdentifiant(){
        return this.id;
    }

    @Override
    public int compareTo(PartieEnCours partieEnCours){
        return this.nom.compareTo(partieEnCours.nom);
    }

     /**
      * methode qui permet de mettre l'interface de l'ihm pour les invitations parties
     */
     public void interfaceIHM(){
         VBox contenue;
         ActionBouttonPartieEnCours action = new ActionBouttonPartieEnCours(this.duelSurLaToile, this);
         Label nom = new Label("Jeu : "+this.nom);
         Label joueurs = new Label("Vous  VS  "+this.adversaire);
         Label resultat = new Label(this.scoreJoueur+"  VS  "+this.scoreAdversaire);
         Button accepter = new Button("Rejoindre");
         accepter.setStyle("-fx-background-color:   #ffffff  ;");
         accepter.setMinWidth(135);
         accepter.setMaxWidth(135);
         accepter.setUserData(this.id);
         Button refuser = new Button("Abandonner");
         refuser.setStyle("-fx-background-color:   #ffffff  ;");
         refuser.setMinWidth(135);
         refuser.setMaxWidth(135);
         refuser.setUserData(this.id);
         image.setFitWidth(130);
         image.setFitHeight(130);
         contenue=new VBox();
         contenue.getChildren().addAll(nom,joueurs,resultat,accepter,refuser);
         contenue.setSpacing(5);
         contenue.setAlignment(Pos.CENTER_LEFT);
         contenue.setMaxWidth(refuser.getMaxWidth());
         this.setLeft(contenue);
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
