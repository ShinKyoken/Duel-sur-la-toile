package Plateforme.Jeux;


import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.List;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
/**
 * Cette classe gère la vue de la liste des jeux de la plateforme
 * @author theodore
 */
public class ListeJeux extends ScrollPane{
    /**
     * La liste des jeux de la plateforme.
     */
    private List<Jeu> listeJeux;

    /**
     * Crée un ScrollPane contenant les vues des jeux de la plateforme.
     * @param ls : la liste des jeux présent sur la plateforme.
     * @param app : l'application
     */
    public ListeJeux(List<Jeu> ls, PageDesJeux app){
        this.listeJeux = new ArrayList<>();
        GridPane gp = new GridPane();
        int x = 0;
        int y = 0;
        for(Jeu j : ls) {
            if (j.isActiver()){
                this.listeJeux.add(j);
                VBox jb = new VBox();

                Label nom = new Label(j.getNom());

                ImageView image = new ImageView(j.getImage());
                image.setFitWidth(100);
                image.setFitHeight(100);
                Label descr = new Label(j.getDescRapide());

                jb.getChildren().add(nom);
                jb.getChildren().add(image);
                jb.getChildren().add(descr);

                jb.setUserData(j);

                jb.setAlignment(Pos.CENTER);
                jb.setSpacing(10);
                jb.setPadding(new Insets(20,20,20,20));
                jb.setOnMouseClicked(new ActionJeu(app));
                jb.setBackground(new Background(new BackgroundFill(Color.rgb( 205, 205, 205 ), null, null)));
                if(x>1) {
                    x=0;
                    y+=1;
                }
                gp.add(jb, x, y);
                x+=1;
            }
        }
        gp.setVgap(5);
        gp.setHgap(5);
        gp.setPadding(new Insets(20,20,20,20));
        gp.setAlignment(Pos.CENTER);
        this.setContent(gp);
    }


    /**
     * La focntion get de List adaptée à cette classe.
     * @param i : un indice
     * @return le jeu associé à l'indice.
     */
    public Jeu get(int i){
        return this.listeJeux.get(i);
    }

    public int size(){
            return this.listeJeux.size();
    }
}
