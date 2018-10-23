package Jeux.Mastermind;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * La classe Combinaison va permettre d'ajouter une combinaison de pions dans le
 * plateau du mastermind
 *
 */
public class Combinaison {
	/**
	 * Liste des pions
	 */
	List<Integer> combi;

	/**
	 * Taille d'une ligne du plateau
	 */
	int taille;

	/**
	 * Constructeur d'une combinaison prenant en parametre la taille de la
	 * combinaison
	 * 
	 * @param taille
	 */
	public Combinaison(int taille) {
		this.taille = taille;
		combi = new ArrayList<Integer>();
		for (int i = 0; i < taille; i++) {
			combi.add(0);
		}
	}

	/**
	 * Methode permettant de recuperer la taille de la combinaison
	 * 
	 * @return taille
	 */
	public int getTaille() {
		return this.taille;
	}

	/**
	 * Methode permettant de recuperer un pion de la combinaison à l'index i
	 * 
	 * @param i
	 * @return
	 */
	public Integer getPion(int i) {
		if (i >= this.getTaille()) {
			return null;
		}
		return this.combi.get(i);
	}

	/**
	 * Methode permettant de modifier un pion à l'index i
	 * 
	 * @param pion
	 * @param i
	 */
	public void setPion(int pion, int i) {
		if (i < getTaille()) {
			this.combi.set(i, pion);
		}
	}

	/**
	 * Permet de generer une combinaison aléatoire
	 * 
	 * @param nbCouleurs
	 */
	public void genere(int nbCouleurs) {
		for (int i = 0; i < getTaille(); i++) {
			Random r = new Random();
			int pion = r.nextInt(nbCouleurs) + 1;
			setPion(pion, i);
		}
	}

	/**
	 * Methode permettant à l'utilisateur de saisir une combinaison via la console
	 * 
	 * @param nbCouleurs
	 */
	public void saisirCombinaison(int nbCouleurs) {
		boolean ok = false;
		while (!ok) {
			System.out.println("Entrez votre combinaison, la valeur des pions séparés par des virgules");
			Scanner scan = new Scanner(System.in);
			String[] rep = scan.nextLine().split(",");
			if (rep.length != getTaille()) {
				System.out.println("le nombre de pions n'est pas correct");
			} else {
				ok = true;
				for (int i = 0; i < rep.length; i++) {
					try {
						int pion = Integer.parseInt(rep[i]);
						if (pion < 1 || pion > nbCouleurs) {
							System.out.println("le pion " + pion + " n'est pas correct");
							ok = false;
						} else {
							setPion(pion, i);
						}
					} catch (NumberFormatException error) {
						System.out.println("Le pion " + rep[i] + " n'est pas un entier");
						ok = false;
					}
				}
			}
			scan.close();
		}
	}

	public void saisirCombinaison(List<Integer> couleurs) {
		for (int i = 0; i < couleurs.size(); i++) {
			int pion = couleurs.get(i);
			setPion(pion, i);
		}
	}

	/**
	 * Cette methode compare deux combinaisons et en determine un couple comportant
	 * le nombre de bon et mauvais pions dans la combinaison mis en parametre
	 * 
	 * @param combRef
	 * @return
	 */
	public Couple<Integer> compare(Combinaison combRef) {
		int taille = getTaille();
		if (combRef.getTaille() != taille) {
			return null;
		}

		List<Integer> utilises = new ArrayList<Integer>();
		for (int i = 0; i < taille; i++) {
			utilises.add(0);
		}

		int nbBP = 0;
		for (int i = 0; i < taille; i++) {
			if (getPion(i) == combRef.getPion(i)) {
				nbBP += 1;
				utilises.set(i, 1);
			}
		}

		int nbMP = 0;
		for (int i = 0; i < taille; i++) {
			boolean trouve = false;
			int j = 0;
			while (j < taille && !trouve) {
				if (utilises.get(j) == 0 && utilises.get(i) != 1 && getPion(i) == combRef.getPion(j)) {
					trouve = true;
					nbMP += 1;
					utilises.set(j, 2);
				}
				j += 1;
			}
		}
		return new Couple<Integer>(nbBP, nbMP);
	}

	/**
	 * Methode permettant de recuperer la combinaison
	 * @return
	 */
	public List<Integer> getCombi() {
		return this.combi;
	}

	@Override
	public String toString() {
		String res = "|";
		for (int i = 0; i < getTaille(); i++) {
			res += getPion(i) + "|";
		}
		return res;
	}
}
