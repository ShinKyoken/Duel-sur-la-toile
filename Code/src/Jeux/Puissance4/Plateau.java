package Jeux.Puissance4;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

/**
 * Cette classe crée le plateau du jeu.
 *
 */

public class Plateau {
	private List<Joueur> listeJ;
	private Matrice plat;
	private String j1,j2;
        private int id1, id2;
	private boolean finPartie = false;
        
        public Plateau() {
            plat = new Matrice(6,7,0);
        }
        
	/**
	 * Le constructeur prend le pseudo de deux joueurs
	 */
	public void initPlayers() {
		listeJ = Arrays.asList(new Joueur(this.j1, 1), new Joueur(this.j2, 2));
		listeJ.get(0).setJoue(true);
	}

	/**
	 * Cette fonction retourne la liste des joueurs
	 * @return la liste des joueurs.
	 */

	public List<Joueur> getListeJ() {
		return listeJ;
	}

	/**
	 * Cette fonction retourne le plateau de jeu.
	 * @return le plateau du jeu.
	 */

	public Matrice getPlat() {
		return plat;
	}

	/**
	 * Cette fonction retourne le joueur en partie.
	 * @return le joueur en cours.
	 */

	public Joueur getJoueurEnJeu() {
		for(Joueur p : listeJ) {
			if(p.getJoue()) return p;
		}
		return null;
	}

	/**
	 * Cette fonction désigne le joueur suivant.
	 */

	public void setJoueurSuivant() {
		if(listeJ.get(0).getJoue()) {
			listeJ.get(0).setJoue(false);
			listeJ.get(1).setJoue(true);
		}
		else {
			listeJ.get(0).setJoue(true);
			listeJ.get(1).setJoue(false);
		}
	}

        public void setJoueurSuivant(int bool) {
		listeJ.get(bool).setJoue(true);
		listeJ.get((bool + 1) % 2).setJoue(false);
	}
        
	/**
	 * Cette fonction permet de placer un pion dans le plateau
         * @param getY the row to place the token in.
	 */
	public void placerPion(int getY) {
		boolean continuer = true;
		while(continuer) {
			if(getY<this.plat.getNbColonnes()) {
				int X = 0;
				List<Integer> valCol = this.plat.getColonne(getY);
				for(int i = 0; i<valCol.size(); i++) {
					if(valCol.get((valCol.size()-1)-i)!=0) {
						X+=1;
					}
				}
				if(X<this.plat.getNbLignes()) {
					this.plat.setVal((valCol.size()-1)-X, getY, getJoueurEnJeu().getCouleur());
					continuer = false;
				}

			}
		}
	}

	/**
	 * Cette fonction détermine si le jeu est terminé ou non.
	 * @return si la manche est effectivement terminée.
	 */
	public boolean checkFinJeu() {
            int i = 0, j = 0, k = 1;
            do {
                int val = this.plat.getVal(i, j);
                if (val > 0) {
                    if (verifLigne(val, i, j)){return true;}
                    if (verifColumn(val, i, j)){return true;}
                    if (verifDiagonalDroit(val, i, j)){return true;}
                    if (verifDiagonalGauche(val, i, j)){return true;}
                }
                i = (i + 1) % this.getPlat().getNbLignes();
                j = (j + 1) % this.getPlat().getNbColonnes();
                k++;
            } while (k < 42);
            return false;
	}
        
        
        public boolean verifLigne(int val, int i, int j){
            boolean res;
            try{
                res = (val == this.plat.getVal(i - 1, j));
            }
            catch(IndexOutOfBoundsException e){return false;}
                        
            if(res){
                try{
                    res = (val == this.plat.getVal(i - 2, j));
                }
                catch(IndexOutOfBoundsException e){return false;}
            }                
            if(res){
                try{
                    res = (val == this.plat.getVal(i - 3, j));
                    return res;
                }                               
                catch(IndexOutOfBoundsException e){return false;}            
                }
           
            try{
                res = (val == this.plat.getVal(i + 1, j));
            }
            catch(IndexOutOfBoundsException e){return false;}
                        
            if(res){
                try {
                    res = (val == this.plat.getVal(i + 2, j));
                }            
                catch(IndexOutOfBoundsException e){return false;}
            }
            
            if(res){                
                try{
                    res = (val == this.plat.getVal(i + 3, j));
                }
                catch(IndexOutOfBoundsException e){return false;}
            }        
        return res;
        }
        
        public boolean verifColumn(int val, int i, int j){
            boolean res;
            try{
                res = (val == this.plat.getVal(i, j - 1));
            }
            catch(IndexOutOfBoundsException e){return false;}
            
            if(res){
                try{
                    res= (val == this.plat.getVal(i, j - 2));
                }
                catch(IndexOutOfBoundsException e){return false;}
            }
            
            if(res){
                try{
                    res = (val == this.plat.getVal(i, j - 3));
                    return res;
                }
                catch(IndexOutOfBoundsException e){return false;}
            }   
            
            try{
                res = (val == this.plat.getVal(i, j + 1));
            }
            catch(IndexOutOfBoundsException e){return false;}
            
            if(res){
                try{
                    res = (val == this.plat.getVal(i, j + 2));
                }
                catch(IndexOutOfBoundsException e){return false;}
            }
            
            if(res){
                try{
                    res = (val == this.plat.getVal(i, j + 3));
                }
                catch(IndexOutOfBoundsException e){return false;}
            }
        return res;
        }
        
        public boolean verifDiagonalDroit(int val, int i, int j){
            boolean res;
            try{
                res = (val == this.plat.getVal(i - 1, j - 1));
            }
            catch(IndexOutOfBoundsException e){return false;}
            
            if(res){
                try{
                    res = (val == this.plat.getVal(i - 2, j - 2));
                }
                catch(IndexOutOfBoundsException e){return false;}            
            }
            
            if(res){
                try{
                    res = (val == this.plat.getVal(i - 3, j - 3));
                    return res;
                }
                catch(IndexOutOfBoundsException e){return false;}
            }
            
            try{
                res = (val == this.plat.getVal(i - 1, j + 1));
            }
            catch(IndexOutOfBoundsException e){return false;}
            
            if(res){
                try{
                    res = (val == this.plat.getVal(i - 2, j + 2));
                }
                catch(IndexOutOfBoundsException e){return false;}
            }
            
            if(res){
                try{
                    res = (val == this.plat.getVal(i - 3, j + 3));
                }
                catch(IndexOutOfBoundsException e){return false;}
            }
            return res;
        }
        
        public boolean verifDiagonalGauche(int val, int i, int j){
            boolean res;
            try{
                res = (val == this.plat.getVal(i + 1, j - 1));
            }
            catch(IndexOutOfBoundsException e){return false;}
            
            if(res){
                try{
                    res = (val == this.plat.getVal(i + 2, j - 2));
                }
                catch(IndexOutOfBoundsException e){return false;}            
            }
            
            if(res){
                try{
                    res = (val == this.plat.getVal(i + 3, j - 3));
                    return res;
                }
                catch(IndexOutOfBoundsException e){return false;}
            }

            try{
                res = (val == this.plat.getVal(i + 1, j + 1));
            }
            catch(IndexOutOfBoundsException e){return false;}
            
            if(res){
                try{
                    res = (val == this.plat.getVal(i + 2, j + 2));
                }
                catch(IndexOutOfBoundsException e){return false;}
            }
            
            if(res){       
                try{
                    res = (val == this.plat.getVal(i + 2, j + 2));
                }
                catch(IndexOutOfBoundsException e){return false;}
            }
            return res;
        }
        
	/**
	 * Cette fonction permet de lancer une partie
         * @param x the row to place the token into.
	 */
	public void jouer(int x) {
		this.placerPion(x);
		this.getPlat();
		gagnant();
		if(!this.isFinPartie()) {
			this.setJoueurSuivant();
		}
	}

	public String gagnant() {
		String res = "";
		if(this.checkFinJeu()) {
			res = this.getJoueurEnJeu().getPseudo();
                        this.finPartie = true;
			
		}
		if(this.plat.matriceFull()) {
			res = "Draw!";
                        this.finPartie = true;
		}
		return res;
	}

        /**
         * Converts loaded matrice data into the Plateau's Matrice.
         * @param data the loaded data.
         * @throws IOException Failed to read the String.
         */
        public void setUpMap(String data) throws IOException {
            BufferedReader br = new BufferedReader(new StringReader(data));
            int i = 0;
            for(String line; (line = br.readLine()) != null;) {
                for (int j = 0; j < line.length() - 1; j++) {
                    this.plat.setVal(i, j, line.charAt(j) - '0');
                }
                i++;
            }
        }

	public boolean isFinPartie() {
		return finPartie;
	}

	public void setFinPartie(boolean finPartie) {
		this.finPartie = finPartie;
	}

	public String getJ1() {
		return j1;
	}

	public String getJ2() {
		return j2;
	}
        
        public void setJ1(String j) {
		this.j1 = j;
	}

	public void setJ2(String j) {
		this.j2 = j;
	}

        public int getId1() {
            return this.id1;
        }
        
        public int getId2() {
            return this.id2;
        }
        
        public void setId1(int id) {
            this.id1 = id;
        }
        
        public void setId2(int id) {
            this.id2 = id;
        }
}
