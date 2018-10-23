package Plateforme;
import Plateforme.Connexion.ConnexionMySQL;

public class Partage{
    private ConnexionMySQL mySQL;
    //private ChoixJeu choixJeu;
    public Partage(ConnexionMySQL mySQL){
	this.mySQL=mySQL;
	//this.choixJeu=choixJeu;
    }

    public ConnexionMySQL getMySQL(){return this.mySQL;}
    //public ChoixJeu getChoixJeu(){return this.choixJeu;}
}
