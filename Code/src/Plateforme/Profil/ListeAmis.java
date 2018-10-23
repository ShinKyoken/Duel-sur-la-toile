package Plateforme.Profil;

import Plateforme.Application.Utilisateur;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.List;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

/**
 * Cette classe a pour but de créer un ScrollPane contenant les informations de tout les amis du l'utilisateur.
 */
public class ListeAmis extends ScrollPane{
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
    public ListeAmis(Profil profil, List<Utilisateur> ls){
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
            Button supprimer = new Button("Supprimer");
            supprimer.setStyle("-fx-background-color:   #ffffff  ;");
            supprimer.setUserData(ls.get(i).getId());
            Button stat = new Button("Statistique");
            stat.setStyle("-fx-background-color:   #ffffff  ;");
            stat.setUserData(ls.get(i).getId());

            supprimer.setOnAction(new ActionBoutonProfil(profil));
            stat.setOnAction(new ActionBoutonProfil(profil));

            boutton.getChildren().add(supprimer);
            boutton.getChildren().add(stat);
            boutton.setAlignment(Pos.CENTER_RIGHT);
            boutton.setSpacing(5);

            bp.setLeft(avatar);
            bp.setCenter(pseudo);
            bp.setRight(boutton);

            BorderPane.setMargin(boutton, new Insets(5,5,5,5));
            BorderPane.setMargin(pseudo, new Insets(5,5,5,5));
            bp.setBackground(new Background(new BackgroundFill(Color.rgb( 205, 205, 205 ), null, null)));
            bp.setPadding(new Insets(10,10,10,10));
            gp.add(bp, x, y);
            if(x==2) {
                y+=1;
                x=0;
            }

        }
        gp.setVgap(10);
        gp.setHgap(10);
        gp.setAlignment(Pos.CENTER);
        gp.setPadding(new Insets(10,10,10,10));

        this.setContent(gp);
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
}
