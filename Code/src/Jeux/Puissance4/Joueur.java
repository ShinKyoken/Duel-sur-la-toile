package Jeux.Puissance4;

/**
 * Cette classe permet d'introduire les infos du joueur dans le jeu.
 *
 */

public class Joueur {
	private String pseudo;
	private int couleur;
	private boolean joue;

	/**
	 * Un constructeur qui prend en parametre le pseudo et la couleur du joueur
	 * @param pseudo
	 * @param couleur
	 */

	public Joueur(String pseudo, int couleur) {
		this.pseudo = pseudo;
		this.couleur = couleur;
	}

	/**
	 * Fonction pour recuperer la couleur du joueur
	 * @return
	 */

	public int getCouleur() {
		return couleur;
	}

	/**
	 * Fonction pour recuperer le pseudo du joueur
	 * @return
	 */

	public String getPseudo() {
		return this.pseudo;
	}

	/**
	 * Fonction pour recuperer si le joueur est en jeu ou non
	 * @return
	 */

	public boolean getJoue() {
		return this.joue;
	}

	/**
	 * Fonction qui change l'état "joue"
	 * @param val
	 */

	public void setJoue(boolean val) {
		this.joue = val;
	}

	@Override
	public String toString() {
		return "(pseudo : "+this.pseudo+" , couleur : "+this.couleur+" )";
	}
}
