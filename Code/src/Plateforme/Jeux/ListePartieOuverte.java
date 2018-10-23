package Plateforme.Jeux;

import Plateforme.Application.DuelSurLaToile;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.List;

public class ListePartieOuverte extends ScrollPane {

    private InviterPartie app;
    private List<PartieOuverte> liste;
    private VBox vBox;

    /**
     * constructeur de ListePartieOuverte
     * @param app
     */
    public ListePartieOuverte(InviterPartie app){
        this.app=app;
        try{
            this.liste=this.app.getPlateforme().getConnexion().getPartieOuverte(this.app.getJeu(), this.app.getPlateforme().getUtilisateur());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.vBox=new VBox();
        if(!(this.liste.isEmpty())) {
            this.ajoute();
        }
        this.setMinWidth((DuelSurLaToile.longueur-40)/2);
        this.setMaxWidth((DuelSurLaToile.longueur-40)/2);
        this.setPadding(new Insets(10,(DuelSurLaToile.longueur)/32,10, (DuelSurLaToile.longueur)/32));
        this.vBox.setAlignment(Pos.CENTER);
        this.setContent(this.vBox);
    }

    public void setListe(List<PartieOuverte> liste){
        this.liste=liste;
    }

    /**
     * Cette fonction met les
     */
    public void ajoute(){

        for (PartieOuverte partie: this.liste){
            BorderPane titre = new BorderPane();
            VBox contenue = new VBox();
            Label nom = new Label("Jeu : "+partie.getNomJeu());
            Label joueurs = new Label("Propriétaire : "+partie.getNomUt());
            ImageView image = partie.getImage();
            image.setFitWidth(100);
            image.setFitHeight(100);
            contenue.getChildren().addAll(nom,joueurs);
            Button rejoindre = new Button("Rejoindre");
            rejoindre.setUserData(partie);
            rejoindre.setOnAction(new ActionBoutonInviter(this.app));
            titre.setBottom(rejoindre);
            titre.setLeft(contenue);
            titre.setRight(image);
            partie.setTop(titre);
            partie.setMargin(image, new Insets(0,5,5,5));
            this.vBox.getChildren().add(partie);
        }
    }

    /**
     * met a jour l'interface par rapport a la base de donnée
     */
    public void maj(){
        this.vBox=new VBox();
        try{
            this.liste=this.app.getPlateforme().getConnexion().getPartieOuverte(this.app.getJeu(), this.app.getPlateforme().getUtilisateur());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        if(! liste.isEmpty()) {
            this.ajoute();
        }
        this.setContent(this.vBox);
    }

    public List<PartieOuverte> getListe() {
        return liste;
    }

    /**
     * met a jour l'IHM pour quel soit correcte sur n'importe pc
     */
    public void majIHM(){
        for(PartieOuverte partieEnCours : this.liste){
            
        }
        this.setPadding(new Insets(0,((this.getWidth()-this.vBox.getWidth())/2)-40,0, ((this.getWidth()-this.vBox.getWidth())/2)-40));
    }
}

