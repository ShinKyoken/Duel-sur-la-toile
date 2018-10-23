package Plateforme.Jeux;

import java.io.File;

import javafx.scene.image.Image;
/**
 * Cette classe contient tout les attributs nécéssaire à Jeu ainsi que les getters et setters asociés.
 * Elle implémente aussi l'interface Comparable
 * @author 
 */
public class Jeu implements Comparable<Jeu>{
    /**
     * L'identifiant unique du jeu
     */
    private int idJeu;
    /**
     * Le nom du jeu
     */
    private String nom;
    /**
     * Une description rapide du jeu
     */
    private String descRapide;
    /**
     * La description complête du jeu
     */
    private String descComplete;
    /**
     * L'image du jeu
     */
    private Image image;
    /**
     * Un booléen indiquant si le jeu est activé
     */
    private boolean activer;
    private String ClasseLanceur;
    private int typeJeu;
    
    /**
     * Ce constructeur défini tout les attributs de jeu grâce aux informations passées en paramêtre.
     * @param idJeu
     * @param nom
     * @param descRapide
     * @param descrComplet
     * @param image
     * @param activer
     * @param ClasseLanceur
     * @param type 
     */
    public Jeu(int idJeu, String nom, String descRapide, String descrComplet, Image image, boolean activer, 
    		String ClasseLanceur, int type){
        this.idJeu = idJeu;
    	this.nom = nom;
        this.descRapide = descRapide;
        this.descComplete = descrComplet;
        this.image = image;
        this.activer = activer;
        this.ClasseLanceur = ClasseLanceur;
        this.typeJeu = type;
    }

	public int getId() {
    	return this.idJeu;
    }
	
	public int getType() {
		return this.typeJeu;
	}
	
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescRapide() {
        return descRapide;
    }

    public void setDescrRapide(String descrRapide) {
        this.descRapide = descrRapide;
    }

    public String getDescrComplet() {
        return descComplete;
    }

    public void setDescrComplet(String descrComplet) {
        this.descComplete = descrComplet;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
    
    public String getClasseLanceur() {
    	return this.ClasseLanceur;
    }

    /**
     * Compare deux jeux en fonction de leur id
     * @param j2
     * @return un int indiquant, grâce à son signe,  quel jeu est le plus grand
     */
    @Override
    public int compareTo(Jeu j2){
        return this.idJeu - j2.getId();
    }
    
    public boolean isActiver(){
        return this.activer;
    }

    public void setActiver(boolean bool){
        this.activer=bool;
    }
}
