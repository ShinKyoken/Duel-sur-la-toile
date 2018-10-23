/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Plateforme.Jeux;

import Plateforme.Application.DuelSurLaToile;
import Plateforme.Application.Utilisateur;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author theodore
 */
public class ListeAmisJeu extends ScrollPane{
    /**
     * la liste des amis de l'utilisateur
     */
    private List<Utilisateur> listeAmis;

    /**
     * Ce constructeur crée une ListeAmis(ScrollPane) et la remplie avec un GridPane lui même remplie avec BorderPane,
     * remplie avec un Label(pseudo), une Image(avatar) et une Vbox contenant un Button(supprimé).
     * @param profil
     * @param ls
     */
    public ListeAmisJeu(InviterPartie profil, List<Utilisateur> ls){
        this.listeAmis = ls;
        GridPane gp = new GridPane();
        int x = 0;
        int y = 0;
        for(int i = 0; i < ls.size(); i++) {
            x+=1;
            VBox boutton = new VBox();
            BorderPane bp = new BorderPane();
            Image av = ls.get(i).getAvatarUt();
            ImageView avatar = new ImageView(av);
            avatar.setFitWidth(50);
            avatar.setPreserveRatio(true);
            Label pseudo = new Label(ls.get(i).getPseudoUt());
            Button inviter = new Button("Inviter");
            inviter.setStyle("-fx-background-color:   #ffffff  ;");
            inviter.setUserData(ls.get(i).getId());

            inviter.setOnAction(new ActionBoutonInviter(profil));

            boutton.getChildren().add(inviter);
            boutton.setAlignment(Pos.CENTER_RIGHT);

            bp.setLeft(avatar);
            bp.setCenter(pseudo);
            bp.setRight(boutton);

            BorderPane.setMargin(boutton, new Insets(5,5,5,5));
            BorderPane.setMargin(pseudo, new Insets(5,5,5,5));
            bp.setBackground(new Background(new BackgroundFill(Color.rgb( 205, 205, 205 ), null, null)));
            gp.add(bp, x, y);
            if(x==3) {
                y+=1;
                x=0;
            }

        }
        gp.setAlignment(Pos.CENTER);
        gp.setVgap(5);
        gp.setHgap(5);
        gp.setPadding(new Insets(5,5,5,5));

        this.setContent(gp);
        this.setMinHeight(DuelSurLaToile.hauteur*0.6);
    }

    /**
     * @return le nombre d'amis de l'utilisateur
     */
    public int getNbAmis(){
        return this.listeAmis.size();
    }

    public List<Utilisateur> getListeAmis() {
        return listeAmis;
    }
    
    public void supprimer(int id){
        int asup = -1;
        for (int i=0;i<this.listeAmis.size();i++){
            if (this.listeAmis.get(i).getId()==id){
                asup = i;
            }
        }
        if (asup!=-1){
            this.listeAmis.remove(asup);
        }
    }
    
    
}
