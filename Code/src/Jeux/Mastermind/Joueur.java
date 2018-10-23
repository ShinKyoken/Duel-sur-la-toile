package Jeux.Mastermind;

/**
 * Une classe permettant de creer un joueur Ce joueur est composé : 
 * - D'un pseudo 
 * - Si c'est son tour ou non 
 * - Son nombre de tentative 
 * - D'un identifiant
 *
 */

public class Joueur {

	/**
	 * Chaine de caractere correspondant au pseudo du joueur
	 */
	private String pseudo;

	/**
	 * Boolean correspondant si c'est le tours du joueur ou non
	 */
	private boolean joue;

	/**
	 * Un entier correspondant au nombre de tentative réaliser par l'utilisateur
	 */
	private int nbtentative;

	/**
	 * Identifiant du joueur
	 */

	private int id;

	/**
	 * Constructeur de la classe joueur
	 * 
	 * @param pseudo
	 * @param id
	 */
	public Joueur(int id, String pseudo) {
		this.id = id;
		this.pseudo = pseudo;
		this.nbtentative = 0;
	}

	/**
	 * Methode permettant de recuperer le nombre de tentative du joueur
	 * 
	 * @return nbtentative
	 */
	public int getTentative() {
		return this.nbtentative;
	}

	/**
	 * Methode permettant d'incrementer le nombre de tentative
	 */
	public void incrTentative() {
		this.nbtentative += 1;
	}

	/**
	 * Methode permettant de recuperer le pseudo du joueur
	 * 
	 * @return pseudo
	 */
	public String getPseudo() {
		return this.pseudo;
	}

	/**
	 * Methode indiquant si c'est le tours du joueur ou non
	 * 
	 * @return joue
	 */
	public boolean getJoue() {
		return this.joue;
	}

	/**
	 * Methode permettant d'indiquer si c'est le tours du joueur ou non
	 * 
	 * @param val
	 */
	public void setJoue(boolean val) {
		this.joue = val;
	}

	/**
	 * Methode permettant de recuperer l'identifiant du joueur
	 * 
	 * @return
	 */
	public int getID() {
		return this.id;
	}

	@Override
	public String toString() {
		return "(pseudo : " + this.pseudo + ")";
	}
}
