package Plateforme.Profil;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.Date;
/**
 * Cette classe contient toutes les informations associée à l'historique des parties:
 *  -les attributs
 *  -la méthode compareTo
 *  -un constructeur, des getters et setters
 *  -une méthode interfaceIHM
 * Les instances de cette classe sont créées lorsque l'utilisateur consulte son historique.
 * @author theodore, marie
 */
public class HistoriquePartie extends BorderPane implements Comparable<HistoriquePartie>{
    /**
     * Le nom du jeu
     */
    private String nom;
    /**
     * Le pseudo de l'adversaire de l'utilisateur courant
     */
    private String adversaire;
    /**
     * L'image associé au jeu
     */
    private ImageView image;
    /**
     * Le résultat de la partie
     */
    private String gagne;
    /**
     * Le score de l'utilisateur courant
     */
    private int scoreJoueur;
    /**
     * Le score de l'adversaire
     */
    private int scoreAdversaire;
    /**
     * La date de début de la partie
     */
    private Date date;

    /**
     * Ce constructeur contient tout les attributs HistoriquePartie en paramêtre à l'exception de "gagne" qui est 
     * défini en fonction des deux scores.
     * @param nom
     * @param adversaire
     * @param image
     * @param scoreAdversaire
     * @param scoreJoueur
     * @param date 
     */
    public HistoriquePartie(String nom, String adversaire, ImageView image, int scoreAdversaire, int scoreJoueur, Date date) {
        this.nom = nom;
        this.adversaire=adversaire;
        this.image=image;
        this.scoreAdversaire=scoreAdversaire;
        this.scoreJoueur=scoreJoueur;
        this.date=date;
        if(this.scoreJoueur>this.scoreAdversaire){
            gagne="Gagné";
        }
        else {
            if(this.scoreJoueur<this.scoreAdversaire){
                gagne="Perdu";
            }
            else {
                gagne="Egalité";
            }
        }
        this.interfaceIHM();
    }

    /**
     * methode qui permet de mettre l'interface de l'ihm pour les invitations parties
     */
    public void interfaceIHM(){
        BorderPane titre;
        VBox contenue;
        Label nom = new Label("Jeu : "+this.nom);
        Label joueurs = new Label("Vous VS "+this.adversaire);
        Label resultat = new Label(this.gagne+"\nVotre score : "+this.scoreJoueur+"\n"+
                    "Score de l'adversaire : "+this.scoreAdversaire);
        ImageView image = this.image;
        image.setFitWidth(100);
        image.setFitHeight(100);
        contenue=new VBox();
        contenue.getChildren().addAll(nom,joueurs);
        titre=new BorderPane();
        titre.setLeft(contenue);
        titre.setRight(image);
        this.setTop(titre);
        this.setBottom(resultat);
        this.setMargin(image, new Insets(0,5,5,5));
    }

    public String getNom() {
        return nom;
    }

    public String getGagne() {
        return gagne;
    }

    public ImageView getImage() {
        return image;
    }

    @Override
    public int compareTo(HistoriquePartie historiquePartie){
        return this.date.compareTo(historiquePartie.date);
    }

    public String getAdversaire() {
        return adversaire;
    }
    
    
}
