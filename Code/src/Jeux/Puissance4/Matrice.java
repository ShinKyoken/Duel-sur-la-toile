package Jeux.Puissance4;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe permet le placement et la création du plateau du jeu Puissance 4.
 *
 */

public class Matrice{
	private int nbLignes;
	private int nbColonnes;
	private List<Integer> listeDesValeurs;
	private int valeurParDef;

	/**
	 * Le constructeur prend en parametre le nombre de ligne, le nombre de colonne et la valeur par defaut
	 * @param nbLignes
	 * @param nbColonnes
	 * @param valeurParDef
	 */

	public Matrice(int nbLignes, int nbColonnes, int valeurParDef) {
		this.nbLignes = nbLignes;
		this.nbColonnes = nbColonnes;
		this.valeurParDef = valeurParDef;
		this.listeDesValeurs = new ArrayList<>();
		for(int i = 0; i<(nbLignes*nbColonnes); i++) {
			this.listeDesValeurs.add(valeurParDef);
		}
	}

	/**
	 * Cette fonction previent si la matrice est remplit ou non
	 * @return
	 */

	public boolean matriceFull() {
		return !this.listeDesValeurs.contains(this.valeurParDef); //Si la liste ne contient plus aucune valeur par defaut
	}

	/**
	 * Cette fonction donne le nombre de lignes de la matrice.
	 * @return le nombre de lignes.
	 */

	public int getNbLignes() {
		return this.nbLignes;
	}

	/**
	 * Cette fonction donne le nombre de colonnes de la matrice
	 * @return le nombre de colonnes.
	 */

	public int getNbColonnes() {
		return this.nbColonnes;
	}

	/**
	 * Cette fonction donne la valeur aux coordonnées x,y de la matrice
	 * @param lig la ligne où se trouve la valeur
	 * @param col la colonne où se trouve la valeur
	 * @return la valeur à ces coordonnées.
	 */

	public Integer getVal(int lig, int col) {
		return this.listeDesValeurs.get((lig*this.nbColonnes)+col);
	}

	/**
	 * Cette fonction insere une valeur aux coordonnées x,y de la matrice
	 * @param lig la ligne où insérer la valeur
	 * @param col la colonne où insérer la valeur
	 * @param val la valeur à insérer.
	 */

	public void setVal(int lig, int col, int val) {
		this.listeDesValeurs.set((lig*this.nbColonnes)+col, val);
	}

	/**
	 * Cette fonction ressort la liste de valeur d'une certaine colonne
	 * @param i le numéro de la colonne.
	 * @return la liste des valeurs.
	 */
	public List<Integer> getColonne(int i){
		List<Integer> res = new ArrayList<>();
		for(int l = 0; l<this.getNbLignes(); l++) {
			res.add(this.getVal(l, i));
		}
		return res;
	}

	/**
	 * Cette fonction ressort la liste de valeur d'une certaine ligne
	 * @param i le numéro de la ligne.
	 * @return la liste des valeurs.
	 */
	public List<Integer> getLigne(int i){
		List<Integer> res = new ArrayList<>();
		for(int c = 0; c<this.getNbColonnes(); c++) {
			res.add(this.getVal(i, c));
		}
		return res;
	}

	/**
	 * Cette fonction ressort la liste de valeur d'une certaine diagonal gauche
	 * @param c numéro de la diagonale.
	 * @return les valeurs de cette diagonale.
	 */

	public List<Integer> getDiagonalGaucheColonne(int c){
		List<Integer> res = new ArrayList<>();
		for(int i = 0; i < this.nbLignes-(Math.abs(c-1)); i++) {
			res.add(this.getVal(i, c+i));
		}
		return res;
	}

	public List<Integer> getDiagonalGaucheLigne(int l){
		List<Integer> res = new ArrayList<>();
		for(int i = 0; i<Math.abs((this.nbColonnes-(Math.abs(l-1))));i++) {
			if(l!=0) {
				res.add(this.getVal(Math.abs((l+i)-2), i));
			}
			else {
				res.add(this.getVal((l+i), i));
			}
		}
		return res;
	}


	/**
	 * Cette fonction ressort la liste de valeur d'une certaine diagonal droite
	 * @param c numéro de la diagonale.
	 * @return les valeurs de cette diagonale.
	 */
	public List<Integer> getDiagonalDroiteColonne(int c){
		int val;
		List<Integer> res = new ArrayList<>();

		if(c>this.getNbColonnes()-2) {
			val = this.getNbLignes();
		}
		else {
			val = c+1;
		}
		for(int i = 0; i < val; i++){
			res.add(this.getVal(i, c-i));
		}
		return res;


	}

	public List<Integer> getDiagonalDroiteLigne(int l){
		List<Integer> res = new ArrayList<>();
		for(int i = 0; i<Math.abs((this.nbLignes-(Math.abs(l))));i++) {
			if(l!=0) {
				res.add(this.getVal((l+i), (this.nbLignes-i)));
			}
			else {
				res.add(this.getVal((l+i), (this.nbLignes-i)));
			}
		}
		return res;
	}

	public List<Integer> getLs(){
		return this.listeDesValeurs;
	}


	@Override
	public String toString() {
		int nbC = this.nbColonnes;
		int nbL = this.nbLignes;
		String res = "";
		for(int i = 0; i<nbL; i++) {
			for(int j = 0; j<nbC; j++) {
				res+=this.getVal(i,j);
			}
			res+="\n";
		}
		return res;
	}
}
