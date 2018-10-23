package Plateforme.Conversation;


import java.io.ByteArrayInputStream;
import java.util.List;
import Plateforme.Connexion.ConnexionMySQL;
import Plateforme.Jeux.ActionJeu;
import Plateforme.Application.Utilisateur;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Liste_De_Contacts extends ScrollPane{

    /**
     * Méthode permettant de gérer l'affichage de la liste des contacts
     * @param app l'application
     * @param lsContact la liste d'utilisateurs
     */
    public Liste_De_Contacts(Conversation app, List<Utilisateur> lsContact) {

        VBox lsContactV = new VBox();
        for(int i = 0; i < lsContact.size(); i++) {
            HBox c = new HBox();

            Image av = lsContact.get(i).getAvatarUt();
            ImageView avatar = new ImageView(av);
            avatar.setFitWidth(50);
            avatar.setPreserveRatio(true);

            c.getChildren().add(avatar);
            c.getChildren().add(new Label(lsContact.get(i).getPseudoUt()));

            c.setUserData(lsContact.get(i));

            c.setOnMouseClicked(new ActionContact(app));
            c.setSpacing(10);
            
            c.setAlignment(Pos.CENTER);
            c.setPadding(new Insets(20,20,20,20));
            
            c.setBackground(new Background(new BackgroundFill(Color.rgb( 205, 205, 205 ), null, null)));
            
            lsContactV.getChildren().add(c);
        }

        lsContactV.setSpacing(10);
        lsContactV.setPadding(new Insets(5, 5, 5, 5));
        this.setContent(lsContactV);
    }
}
