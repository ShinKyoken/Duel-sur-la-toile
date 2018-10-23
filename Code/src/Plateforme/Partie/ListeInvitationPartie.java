package Plateforme.Partie;

import Plateforme.Application.DuelSurLaToile;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListeInvitationPartie extends ScrollPane {

    private DuelSurLaToile app;
    private List<InvitationPartie> liste;
    private VBox vBox;

    /**
     * constructeur de ListeInvitationPartie
     * @param app
     */
    public ListeInvitationPartie(DuelSurLaToile app) {
        this.app=app;
        try{
            this.liste=this.app.getConnexion().getInvitationsPartie(this.app.getUtilisateur(),this.app);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.vBox=new VBox();
        if(! this.liste.isEmpty()) {
            Collections.sort(this.liste);
            if(((DuelSurLaToile.longueur-40)/2)<500){
                this.ajoutePetiteInterface();
            }
            else {
                this.ajoute();
            }
        }
        this.setMinWidth((DuelSurLaToile.longueur-40)/2);
        this.setMaxWidth((DuelSurLaToile.longueur-40)/2);
        this.vBox.setAlignment(Pos.CENTER);
        this.vBox.setMinWidth((DuelSurLaToile.longueur-70)/2);
        this.vBox.setMinHeight((DuelSurLaToile.longueur-70)/2);
        this.vBox.setMaxWidth((DuelSurLaToile.longueur-70)/2);
        this.vBox.setMaxHeight((DuelSurLaToile.longueur-70)/2);
        this.vBox.setPadding(new Insets(10,10,10,10));
        this.setContent(this.vBox);
        this.vBox.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, null, null)));
    }

    public void setListe(List<InvitationPartie> liste){
        this.liste=liste;
    }

    /**
     * met a jour l'interface par rapport a la base de donnée
     */
    public void maj(){
        this.vBox=new VBox();
        try{
            this.liste=this.app.getConnexion().getInvitationsPartie(this.app.getUtilisateur(),this.app);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(! liste.isEmpty()) {
            Collections.sort(liste);
            if(((DuelSurLaToile.longueur-40)/2)<500){
                this.ajoutePetiteInterface();
            }
            else {
                this.ajoute();
            }
        }
        this.setContent(this.vBox);
    }

    /**
     * ajoute dans l'interface les differetes parties en cours dans un HBox qui lui meme est dans un VBox
     */
    public void ajoute(){
        HBox hBox = new HBox();
        for(int i=1; i<liste.size();i+=2){
            hBox.setAlignment(Pos.CENTER);
            hBox.setPadding(new Insets(10,10,10, 10));
            hBox.setSpacing(20);
            hBox.getChildren().addAll(liste.get(i-1),liste.get(i));
            this.vBox.getChildren().add(hBox);
            hBox=new HBox();
        }
        if(liste.size()%2!=0){
            hBox.setAlignment(Pos.CENTER);
            hBox.setPadding(new Insets(10,10,10, 10));
            hBox.setSpacing(20);
            hBox.getChildren().addAll(liste.get(liste.size()-1));
            this.vBox.getChildren().add(hBox);
        }
    }

    /**
     * pour des petites interfaces
     * ajoute dans l'interface les differetes parties en cours dans un HBox qui lui meme est dans un VBox
     */
    public void ajoutePetiteInterface(){
        for(int i=0; i<liste.size();i++){

            this.vBox.getChildren().add(liste.get(i));
            VBox.setMargin(liste.get(i), new Insets(10,10,10,10));
        }
        this.vBox.setSpacing(10);
    }

    public List<InvitationPartie> getListe() {
        return liste;
    }

    /**
     * met a jour l'IHM pour quel soit correcte sur n'importe pc
     */
    public void majIHM(){
        for(InvitationPartie invitationPartie : this.liste){
            invitationPartie.majIHM();
        }
        //this.setPadding(new Insets(0,((this.getWidth()-this.vBox.getWidth())/2)-19,0, ((this.getWidth()-this.vBox.getWidth())/2)-19));
    }
}
