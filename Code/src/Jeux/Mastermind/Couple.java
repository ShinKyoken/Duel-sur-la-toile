package Jeux.Mastermind;

/**
 * Cette classe permet de creer un couple de nombre de pion bien placer et mal
 * placer sur une ligne du plateau
 *
 * @param <T>
 */
public class Couple<T> {
	/**
	 * Premiere valeur du couple
	 */
	private T val1;

	/**
	 * Seconde valeur du couple
	 */
	private T val2;

	/**
	 * Constructeur de la classe couple comportant une premier et seconde valeur
	 * 
	 * @param v1
	 * @param v2
	 */
	public Couple(T v1, T v2) {
		this.val1 = v1;
		this.val2 = v2;
	}

	/**
	 * Methode permettant de recuperer la premiere valeur
	 * 
	 * @return val1
	 */
	public T getVal1() {
		return val1;
	}

	/**
	 * Methode permettant de recuperer la seconde valeur
	 * 
	 * @return val2
	 */
	public T getVal2() {
		return val2;
	}

	@Override
	public String toString() {
		return this.val1 + " " + this.val2;
	}

}
