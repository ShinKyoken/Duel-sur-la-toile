package Plateforme.Connexion.MotDePasseOublie;

import java.sql.SQLException;

import Plateforme.Connexion.ConnexionMySQL;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ApplicationMotDePasseOublie extends Application{


    private Stage a;

    private ConnexionMySQL mysql;

    private Scene windows;

    private SaisiePseudo ps;

    private SaisieCodeProvisoire cp;

    private SaisieNouveauMotDePasse nmdp;

    private String pseudo;

    /**
     * Méthode permettant de paraméter l'affichage
     * @param a le Stage
     * @throws Exception
     */
    @Override
    public void start(Stage a) throws Exception {

        mysql = new ConnexionMySQL();
        mysql.connecter();

        VBox bp = new VBox();
        windows = new Scene(bp, 300,100);

        this.ps = new SaisiePseudo(this);
        this.cp = new  SaisieCodeProvisoire(this);
        this.nmdp = new SaisieNouveauMotDePasse(this);

        bp.getChildren().add(ps);

        this.a = a;

        a.setTitle("Mot de passe oublié");
        a.setScene(windows);
        a.setResizable(false);
        a.show();

    }

    /**
     * Retourne la connexion
     * @return
     */
    public ConnexionMySQL getConnexion() {
        return this.mysql;
    }

    /**
     * Retourne le Stage
     * @return
     */
    public Stage getStage() {
        return a;
    }

    /**
     * Modifie qui permet de changer le pseudo
     */
    public void setPseudo() {
        this.pseudo = this.ps.getPseudo();
    }

    /**
     * Retourne le pseudo
     * @return
     */
    public String getPseudoSaisie() {
        return pseudo;
    }

    /**
     * Retourne le code provisoire
     * @return le code provisoire
     */
    public String getCodeP() {
        return this.cp.getCodeProvisoire();
    }

    /**
     * Retourne le nouveau mot de passe
     * @return le nouveau mot de passe
     */
    public String Nouveaumdp() {
        return this.nmdp.getMDP();
    }

    /**
     *Affiche la saisie du code provisoire
     */
    public void afficherSaisieCodeProvisoire() {
        VBox fp = ((VBox) windows.getRoot());
        fp.getChildren().remove(0);
        fp.getChildren().add(cp);
    }

    /**
     *Affiche la saisie du mot de passe
     */
    public void afficherSaisieNMDP(){
        VBox fp = ((VBox) windows.getRoot());
        fp.getChildren().remove(0);
        fp.getChildren().add(nmdp);
    }
}