package Plateforme.Profil;

import Plateforme.Application.DuelSurLaToile;
import Plateforme.Jeux.Jeu;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

/**
 * Cette classe gère l'affichage de la liste des statiqtiques de l'utilisateur courant.
 * @author theodore
 */
public class ListeStat extends ScrollPane {
    /**
     * La liste des jeux présent sur la plateforme
     */
    private List<Jeu> listeJeux;
    /**
     * La liste des historiquepartie associé au joueur courant.
     */
    private List<HistoriquePartie> listePartie;
    /**
     * La page sur laquelle sera afichée la liste des statistique de l'utilisateur courant
     */
    private DuelSurLaToile app;
    /**
     * Les nombres de victoires, de défaites et d'égalité du joueur courant.
     */
    private int nbVictoire, nbdefaite, nbegalite;
    /**
     * Un dictionnaire dont les clés sont les jeux présent sur la plateforme et dont les valeurs sont le ratio de victoire
     * du joueur courant sur ce jeu.
     */
    private Map<Jeu, Double> listeRatio;

    /**
     * Ce constructeur crée un ScrollPane contenant la liste des vue des statistiques du joueur courant à partir des 
     * paramêtre suivnt :
     * @param app
     * @param jeux
     * @param listePartie 
     */
    public ListeStat(DuelSurLaToile app, List<Jeu> jeux, List<HistoriquePartie> listePartie){
        this.listeJeux = jeux;
        this.listePartie = listePartie;
        this.listeRatio = new HashMap<>();
        this.app = app;
        this.nbVictoire = 0;
        this.nbdefaite = 0;
        Double nbVic=0.0, nbdef=0.0, nbega=0.0, ratioJeu;
        GridPane gp = new GridPane();
        
        int x = 0;
        int y = 0;
        for (int i = 0; i < listeJeux.size(); i++) {
            x++;
            for (HistoriquePartie partie : listePartie) {
                if (partie.getNom().equals(listeJeux.get(i).getNom())) {
                    if (partie.getGagne().equals("Gagné")) {
                        nbVic++;
                    } else {
                        if (partie.getGagne().equals("Perdu")) {
                            nbdef++;
                        } else {
                            nbega++;
                        }
                    }
                }
            }
            ratioJeu = 0.0;
            if(nbdef + nbega + nbVic!=0) {
                ratioJeu = (nbVic+nbega/2) / (nbdef + nbega + nbVic);
            }
            this.listeRatio.put(this.listeJeux.get(i),ratioJeu);
            this.nbegalite += nbega;
            this.nbdefaite += nbdef;
            this.nbVictoire += nbVic;
            HBox stat = new HBox();
            Label victoire = new Label("Victoire : " + nbVic.intValue());
            Label egalite = new Label("Egalité : " + nbega.intValue());
            Label defaite = new Label("Défaite : " + nbdef.intValue());
            defaite.setPadding(new Insets(5,5,10,10));
            victoire.setPadding(new Insets(5,5,10,10));
            egalite.setPadding(new Insets(5,5,10,10));
            stat.getChildren().addAll(victoire, defaite, egalite);
            ratioJeu=ratioJeu*100;
            Label lratio = new Label( "Ratio : " + ratioJeu.intValue()+"%");
            BorderPane bp = new BorderPane();
            Image av = listeJeux.get(i).getImage();
            ImageView avatar = new ImageView(av);
            avatar.setFitWidth(50);
            avatar.setPreserveRatio(true);
            Label nom = new Label(listeJeux.get(i).getNom());
            bp.setBottom(stat);
            bp.setCenter(lratio);
            bp.setLeft(avatar);
            BorderPane.setMargin(avatar, new Insets(0,5,0,10));
            nom.setAlignment(Pos.CENTER);
            bp.setTop(nom);
            BorderPane.setMargin(nom, new Insets(5,5,10,10));
            bp.setBackground(new Background(new BackgroundFill(Color.rgb( 205, 205, 205 ), null, null)));

            gp.add(bp, x, y);
            if(x==2) {
                y+=1;
                x=0;
                bp.setPadding(new Insets(5,5,5,5));
            }
            else{
                bp.setPadding(new Insets(5,5,5,5));
            }
            nbVic=0.0;
            nbdef=0.0;
            nbega=0.0;
        }
        gp.setVgap(5);
        gp.setHgap(5);
        gp.setPadding(new Insets(5,0,5,0));
        this.setContent(gp);
    }

    public int getNbdefaite() {
        return nbdefaite;
    }

    public int getNbegalite() {
        return nbegalite;
    }

    public int getNbVictoire() {
        return nbVictoire;
    }

    public List<HistoriquePartie> getListePartie() {
        return listePartie;
    }

    public List<Jeu> getListeJeux() {
        return listeJeux;
    }

    public DuelSurLaToile getApp() {
        return app;
    }

    public Map<Jeu, Double> getListeRatio() {
        return listeRatio;
    }
}
