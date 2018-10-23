package Plateforme.Administration;

import Plateforme.Jeux.Jeu;

public class AdministrationJeu {
        private Boolean activable;
        private Jeu jeu;

    /**
     * Le constructeur.
      * @param jeu un Jeu
     */
    public AdministrationJeu(Jeu jeu){
        this.jeu=jeu;
        this.activable = jeu.isActiver() ;
    }

    /**
     * Méthode permettant de changer le status du jeu.
     */
    public void changeActivable(){
        if (this.activable){
            jeu.setActiver(false);
        }
        else{
            jeu.setActiver(true);
        }
    }

    /**
     * Retourne si le jeu est activé ou non.
     * @return
     */
    public boolean getActivable(){
        return this.activable;
    }
}
