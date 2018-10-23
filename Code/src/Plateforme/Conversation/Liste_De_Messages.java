package Plateforme.Conversation;


import java.io.ByteArrayInputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Plateforme.Application.Utilisateur;
import Plateforme.Application.DuelSurLaToile;
import Plateforme.Connexion.ConnexionMySQL;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class Liste_De_Messages extends BorderPane {

    private Conversation app;

    private Utilisateur destinataire;

    private TextField mess;

    /**
     * Méthode qui gère l'affichage de la liste des messages
     * @param app l'application
     * @param destinataire le destinataire
     * @throws SQLException
     */
    public Liste_De_Messages(Conversation app, Utilisateur destinataire) throws SQLException {

        GridPane gp = new GridPane();

        this.app = app;
        this.destinataire = destinataire;

        List<Message> ls = new ArrayList<Message>();

        if(!this.app.getConnexion().getAmis(this.app.getPlateforme().getUtilisateur().getPseudoUt()).isEmpty()) {
            ls = this.app.getConnexion().recupMessage(this.app.getPlateforme().getUtilisateur().getPseudoUt(), destinataire.getPseudoUt());

            int i = 0;
            for(Message m : ls) {
                if(m.getExpediteur().equals(this.app.getPlateforme().getUtilisateur().getPseudoUt())) {
                    gp.add(new Label(m.getExpediteur()+" : "+m.getContenu()), 0, i);
                }
                else {
                    gp.add(new Label(this.destinataire+" : "+m.getContenu()), 0, i);
                }
                i+=1;
            }

            i=0;

            gp.setAlignment(Pos.CENTER);
            gp.setVgap(10);
            gp.setHgap(20);

            ScrollPane sp = new ScrollPane();
            sp.setContent(gp);
            sp.setVvalue(1.0);

            HBox saisie = new HBox();

            mess = new TextField();

            this.mess.setMinWidth(800.0);
            Button send = new Button("↵");
            Button refresh = new Button("⟳");
            
            send.setUserData("send");
            refresh.setUserData("refresh");
            
            send.setMaxWidth(Double.MAX_VALUE);
            refresh.setMaxWidth(Double.MAX_VALUE);
            
            send.setStyle("-fx-background-color:  #ffffff   ;");
            refresh.setStyle("-fx-background-color:  #ffffff   ;");
            
            saisie.getChildren().add(mess);
            saisie.getChildren().add(send);
            saisie.getChildren().add(refresh);

            ActionBoutonMessage act = new ActionBoutonMessage(this);
            send.setOnAction(act);
            refresh.setOnAction(act);

            saisie.setSpacing(10);
            saisie.setAlignment(Pos.CENTER);

            BorderPane.setMargin(saisie, new Insets(10,0,10,0));

            HBox c = new HBox();

            Image av = destinataire.getAvatarUt();
            ImageView avatar = new ImageView(av);
            avatar.setFitWidth(50);
            avatar.setPreserveRatio(true);
            
            
            c.getChildren().add(avatar);
            c.getChildren().add(new Label(destinataire.getPseudoUt()));

            c.setSpacing(10);

            c.setAlignment(Pos.CENTER);
            c.setPadding(new Insets(10,0,10,0));

            if(!destinataire.isActiveUt()){
                send.setDisable(true);
                refresh.setDisable(true);
                mess.setDisable(true);
            }

            this.setCenter(sp);
            this.setBottom(saisie);
            this.setTop(c);
        }
    }

    /**
     * Retourne le destinataire
     * @return le destinataire
     */
    public Utilisateur getDest() {
        return destinataire;
    }

    /**
     * Retourne le message
     * @return le message
     */
    public TextField getMess() {
        return this.mess;
    }

    /**
     * Retourne la conversation
     * @return la conversation
     */
    public Conversation getConv() {
        return this.app;
    }
}