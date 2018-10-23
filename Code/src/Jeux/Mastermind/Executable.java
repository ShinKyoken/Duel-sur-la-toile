package Jeux.Mastermind;

import Plateforme.Partage;
import Plateforme.Connexion.ConnexionMySQL;

public class Executable {
	public static void main(String[] args)
			throws Exception {
		// TEST de la classe Joueur
		Joueur testJ = new Joueur(0, "joueur");
		testJ.setJoue(true);
		testJ.incrTentative();

		// TEST de la classe Couple
		Couple<Integer> c = new Couple<Integer>(2, 2);

		// TEST de la classe combinaison
		Combinaison comb = new Combinaison(4);
		Combinaison comb2 = new Combinaison(4);

		comb.genere(4);
		// comb.saisirCombinaison(4);
		// System.out.println(comb);

		comb2.genere(4);

		// TEST de la classe Mastermind
		// Mastermind partie = new Mastermind(4, 6, 10, 1, 2);
		// partie.jouer();

		// TEST communication SQL
		//		Etat et = new Etat();
		//		System.out.println(et.getEtat(1));

		// TEST liaison jeu -- plateforme
		ConnexionMySQL co = new ConnexionMySQL();
		co.connecter();
		LeJeu g = new LeJeu();

		Partage pa = new Partage(co);

		g.creerPartie(1, 2, 1, 19, pa);
		//g.rejoindrePartieEnCours(1 ,2, 3);


		//Application.launch(VueMastermind.class, "2");
		//g.creerPartie(2, 1, 2, pa);
	}
}
