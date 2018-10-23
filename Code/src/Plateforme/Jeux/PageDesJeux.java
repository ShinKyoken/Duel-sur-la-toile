package Plateforme.Jeux;

import Plateforme.Administration.ActionBoutonJeu;
import Plateforme.Application.DuelSurLaToile;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.SQLException;

/**
 * 
 * @author theodore
 */
public class PageDesJeux extends BorderPane{
    private ListeJeux ls;
    private VBox info;
    private Jeu jeuxSelectionner;
    private TextField rechercher;
    private DuelSurLaToile app;
    
    public PageDesJeux(DuelSurLaToile app) throws SQLException{
    	
    	this.app = app;
    	
        ls = new ListeJeux(app.getConnexion().getJeux(), this);

        BorderPane lsJeu = ListeDesJeux();

        lsJeu.setPrefWidth(350);

        this.setLeft(lsJeu);
        if (this.ls.size()>0) {
            this.setJeuxSelectionner(ls.get(0));
            info = infoJeu(this.getJeuxSelectionner());
            this.setCenter(info);
            BorderPane.setMargin(info, new Insets(10,10,10,10));
            BorderPane.setAlignment(info, Pos.CENTER);
        }



	}

    public BorderPane ListeDesJeux() {
        BorderPane bp = new BorderPane();

        HBox filtrage = new HBox();

        this.rechercher = new TextField();
        Button chercher = new Button("Rechercher");
        chercher.setStyle("-fx-background-color:   #ffffff  ;");
        chercher.setOnAction(new ActionChercherJeu(this));

        filtrage.getChildren().add(this.rechercher);
        filtrage.getChildren().add(chercher);

        bp.setTop(filtrage);
        bp.setCenter(ls);

        filtrage.setSpacing(10);

        BorderPane.setMargin(filtrage, new Insets(10,10,10,10));

        bp.setPadding(new Insets(10,10,10,10));

        return bp;
    }

    public VBox infoJeu(Jeu j) {

        VBox res = new VBox();

        ImageView image = new ImageView(j.getImage());
        image.setFitWidth(200);
        image.setFitHeight(200);

        Label nom = new Label(j.getNom());

        TextArea description = new TextArea(j.getDescrComplet());
        description.setEditable(false);
        description.setWrapText(true);

        Button jouer = new Button("Jouer !");
        jouer.setUserData(j);
        jouer.setStyle("-fx-background-color:   #ffffff  ;");
        ActionJouer act = new ActionJouer(this.app);
        jouer.setOnAction(act);

        res.getChildren().add(image);
        res.getChildren().add(nom);
        res.getChildren().add(description);
        res.getChildren().add(jouer);

        res.setSpacing(20);
        res.setAlignment(Pos.TOP_CENTER);

        res.setPadding(new Insets(10,10,10,10));

        return res;
    }

    public Jeu getJeuxSelectionner() {
        return jeuxSelectionner;
    }

    public void setJeuxSelectionner(Jeu jeuxSelectionner) {
        this.jeuxSelectionner = jeuxSelectionner;
    }

    public void majInfo() {
        info = this.infoJeu(this.getJeuxSelectionner());
        this.setCenter(info);
        BorderPane.setMargin(info, new Insets(10,10,10,10));
        BorderPane.setAlignment(info, Pos.CENTER);
    }
    
    public DuelSurLaToile getPlateforme(){
        return this.app;
    }
    
    public TextField getRechercher(){
        return this.rechercher;
    }
    
    public ListeJeux getListeJeux(){
        return this.ls;
    }
    
    public void setListeJeux(ListeJeux ls){
        this.ls = ls;
    }

    public void majListe(){
        BorderPane lsJeu = this.ListeDesJeux();

        lsJeu.setPrefWidth(350);
        this.setLeft(lsJeu);
    }

    public void maj(){

        this.majListe();
        if (this.ls.size()!=0){
            this.majInfo();
        }
    }
}
