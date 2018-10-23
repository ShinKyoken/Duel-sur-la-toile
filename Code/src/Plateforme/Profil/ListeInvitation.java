package Plateforme.Profil;

import Plateforme.Application.DuelSurLaToile;
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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

/**
 * Cette gère l'affichage de la liste des invitations reçue par l'utilisateur courant.
 * @author theodore
 */
public class ListeInvitation extends ScrollPane {
    /**
     * La liste de tout les utilisateur de la plateforme
     */
    private List<Invitation> listeInvit;
    private InvitationAmiProfil app;

    /**
     * Ce constructeur crée un ScrollPane contenant la vue des Invitations reçue par l'utilisateur courant.
     * @param app
     * @param listeInvit 
     */
    public ListeInvitation(InvitationAmiProfil app, List<Invitation> listeInvit){
        this.app = app;
        this.listeInvit  =listeInvit;
        this.tri();
    }

    public List<Invitation> getListeInvit() {
        return listeInvit;
    }

    public void tri(){
        GridPane gp = new GridPane();
        int x = 0;
        int y = 0;
        for(int i = 0; i < listeInvit.size(); i++) {
            x+=1;
            BorderPane bp = new BorderPane();
            VBox boutton = new VBox();
            Image av = listeInvit.get(i).getEnvoyeur().getAvatarUt();
            ImageView avatar = new ImageView(av);
            avatar.setFitWidth(50);
            avatar.setPreserveRatio(true);
            Label pseudo = new Label(listeInvit.get(i).getEnvoyeur().getPseudoUt());
            Button accepter = new Button("Accepter");
            Button refuser = new Button("Refuser");
            accepter.setStyle("-fx-background-color:   #ffffff  ;");
            refuser.setStyle("-fx-background-color:   #ffffff  ;");
            accepter.setUserData(listeInvit.get(i));
            refuser.setUserData(listeInvit.get(i));

            accepter.setOnAction(new ActionBoutonInvitation(app));
            refuser.setOnAction(new ActionBoutonInvitation(app));

            boutton.getChildren().add(accepter);
            boutton.getChildren().add(refuser);
            boutton.setAlignment(Pos.CENTER_RIGHT);

            bp.setLeft(avatar);
            bp.setCenter(pseudo);
            bp.setRight(boutton);

            BorderPane.setMargin(pseudo, new Insets(0,0,0,10));
            BorderPane.setMargin(boutton, new Insets(0,0,0,10));

            bp.setBackground(new Background(new BackgroundFill(Color.rgb( 205, 205, 205 ), null, null)));
            gp.add(bp, x, y);
            if(x==2) {
                y+=1;
                x=0;
                bp.setPadding(new Insets(0,DuelSurLaToile.longueur*0.005,0,DuelSurLaToile.longueur*0.01));
            }
            else{
                bp.setPadding(new Insets(0,DuelSurLaToile.longueur*0.04,0,DuelSurLaToile.longueur*0.005));
            }

        }
        gp.setVgap(10);
        gp.setHgap(10);
        gp.setAlignment(Pos.CENTER);
        gp.setPadding(new Insets(10,0,10, 0));

        this.setContent(gp);
    }

    public void triDate(){
        Collections.sort(this.listeInvit);
        this.tri();
    }

    public void triAZ(){
        Collections.sort(this.listeInvit, new Comparator<Invitation>() {
            @Override
            public int compare(Invitation invitation, Invitation t1) {
                return invitation.getEnvoyeur().compareTo(t1.getEnvoyeur());
            }
        });
        this.tri();
    }
}
