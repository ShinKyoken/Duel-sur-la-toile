package Plateforme.Profil;

import Plateforme.Application.Alerte;
import Plateforme.Application.DuelSurLaToile;
import Plateforme.Application.Utilisateur;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * Cette classe gère l'affichage de la liste des amis de l'utilisateur courant.
 * @author theodore
 */
public class ListeHistoriquePartie extends ScrollPane {

    private DuelSurLaToile app;
    private List<HistoriquePartie> liste;
    private Utilisateur util;

    /**
     * constructeur de ListeInvitationPartie
     * @param app
     */
    public ListeHistoriquePartie(DuelSurLaToile app, Utilisateur util){
        this.app = app;
        this.util = util;
        int x=0, y=0;
        try {
            this.liste = this.app.getConnexion().getHistoriquePartie(this.util);
        }
        catch(SQLException e){
            Alerte.creationAlerte(e);
        }
        Label historique = new Label("Historique");
        this.getChildren().add(historique);
        GridPane vBox = new GridPane();
        if(!(this.liste.isEmpty())) {
            for(HistoriquePartie historiquePartie : this.liste){
                x+=1;
                historiquePartie.setPadding(new Insets(5,10,5,10));
                historiquePartie.setBackground(new Background(new BackgroundFill(Color.rgb( 205, 205, 205 ), null, null)));
                vBox.add(historiquePartie,x,y);
                if(x==2) {
                    y+=1;
                    x=0;
                }
            }
        }
        else{
            vBox.add(new Label("Vous n'avez pas encore fait de partie"),1,1);
        }
        vBox.setVgap(5);
        vBox.setHgap(20);
        vBox.setPadding(new Insets(5));
        this.setContent(vBox);
    }

    public void setListe(List<HistoriquePartie> liste){
        this.liste=liste;
    }

    public List<HistoriquePartie> getListe() {
        return liste;
    }

    public void tri(){
        int x=0, y=0;
        Label historique = new Label("Historique");
        this.getChildren().add(historique);
        GridPane vBox = new GridPane();
        if(!(this.liste.isEmpty())) {
            for(HistoriquePartie historiquePartie : this.liste){
                x+=1;
                historiquePartie.setPadding(new Insets(5,10,5,10));
                historiquePartie.setBackground(new Background(new BackgroundFill(Color.rgb( 205, 205, 205 ), null, null)));
                vBox.add(historiquePartie,x,y);
                if(x==2) {
                    y+=1;
                    x=0;
                }
            }
        }
        else{
            vBox.add(new Label("Vous n'avez pas encore fait de partie"),1,1);
        }
        vBox.setVgap(5);
        vBox.setHgap(20);
        vBox.setPadding(new Insets(5));
        this.setContent(vBox);
    }
    
    public void triDate(){
        Collections.sort(this.liste);
        this.tri();
    }

    public void triJeu(){
        Collections.sort(this.liste, new ComparateurJeu());
        this.tri();
    }

    public void triAdversaire(){
        Collections.sort(this.liste, new ComparateurAdversaire());
        this.tri();
    }

}
