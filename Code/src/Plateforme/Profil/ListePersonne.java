package Plateforme.Profil;

import Plateforme.Application.DuelSurLaToile;
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

import java.io.ByteArrayInputStream;
import java.util.List;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

/**
 * Cette classe a pour but de créer un ScrollPane qui contient les informations de tout les utilisateur de la plateforme.
 */
public class ListePersonne extends ScrollPane {
    /**
     * La liste de tout les utilisateur de la plateforme
     */
    public List<Utilisateur> listeUtil;

    /**
     * Ce constructeur construit une ListePersonne(ScrollPane) remplie avec un GridPane
     * lui même rempli de BorderPane rempli avec un Label(pseudo), une Image(avatar) et une Vbox contenant un Button(supprimé).
     * @param app
     * @param ls
     */
    public ListePersonne(InvitationAmiProfil app, List<Utilisateur> ls){

        GridPane gp = new GridPane();

        int x = 0;
        int y = 0;
        for(int i = 0; i < ls.size(); i++) {
            x+=1;
            BorderPane bp = new BorderPane();
            VBox boutton = new VBox();
            Image av = ls.get(i).getAvatarUt();
            ImageView avatar = new ImageView(av);
            avatar.setFitWidth(50);
            avatar.setPreserveRatio(true);
            Label pseudo = new Label(ls.get(i).getPseudoUt());
            Button ajouter = new Button("Ajouter");
            ajouter.setStyle("-fx-background-color:   #ffffff  ;");
            ajouter.setUserData(ls.get(i).getId());

            ajouter.setOnAction(new ActionBoutonAjouterAmi(app));

            boutton.getChildren().add(ajouter);
            boutton.setAlignment(Pos.CENTER_RIGHT);

            bp.setLeft(avatar);
            bp.setCenter(pseudo);
            bp.setRight(boutton);

            BorderPane.setMargin(pseudo, new Insets(0,0,0,10));
            BorderPane.setMargin(boutton, new Insets(0,0,0,10));
            bp.setPadding(new Insets(5,5,5,5));
            bp.setBackground(new Background(new BackgroundFill(Color.rgb( 205, 205, 205 ), null, null)));
            gp.add(bp, x, y);
            if(x==3) {
                y+=1;
                x=0;
            }

        }
        gp.setVgap(10);
        gp.setHgap(27);
        gp.setAlignment(Pos.CENTER);
        gp.setPadding(new Insets(10,0,10, 0));

        this.setContent(gp);
        this.setMinWidth(DuelSurLaToile.longueur*0.58);
    }


    public List<Utilisateur> getListeUtil(){
        return this.listeUtil;
    }
}
