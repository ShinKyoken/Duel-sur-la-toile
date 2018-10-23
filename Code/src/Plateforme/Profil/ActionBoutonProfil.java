package Plateforme.Profil;

import Plateforme.Application.Alerte;
import Plateforme.Application.Utilisateur;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
/**
 * Cette fonction implémente EventHandler et contient la fonction handle associée aux bouton servant à supprimer
 * des utilisateurs de la liste d'amis de l'utilisateur courant.
 * @author theodore
 */
public class ActionBoutonProfil implements EventHandler<ActionEvent> {
    /**
     * le profil auquel est associé l'ActionBoutonProfil
     */
    private Profil profil;

    public ActionBoutonProfil(Profil profil){
        this.profil = profil;
    }

    /**
     * Cette fonction est déclenchée lors de l'appuie d'un bouton de l'interface Profil.
     * Si le bouton search est pressé, la liste des utilisateurs correspondant au critère de recherche sera affichée
     * à la place de la liste des utilisateur actuellement affichée.
     * Si le bouton A-Z est pressé, la liste est triée par ordre alphabétique (ordre de tri de base et unique actuellement).
     * Si un bouton supprimer est cliqué, l'ami associé au bouton est retiré de la liste d'amis de l'utilisateur.
     * @param a
     */
    @Override
    public void handle(ActionEvent a){
        Button b = (Button) a.getSource();

        if(b.getUserData().equals("search")) {
            try {
                List<Utilisateur> v = profil.getConnexion().rechercherAmis(profil.getPlateforme().getUtilisateur().getPseudoUt(),profil.getrechercher().getText());
                Collections.sort(v);
                ListeAmis nLs = new ListeAmis(profil, v);
                profil.setNouvelleListe(nLs);
                profil.maj();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if(b.getUserData().equals("az")) {
            List<Utilisateur> v;
            try {
                v = profil.getConnexion().getAmis(profil.getPlateforme().getUtilisateur().getPseudoUt());
                Collections.sort(v);
                ListeAmis nLs = new ListeAmis(profil, v);
                profil.setNouvelleListe(nLs);
                profil.maj();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        else if(b.getText().equals("Supprimer")){
            try {
                List<Utilisateur> v;
                profil.getConnexion().supprimerAmis(profil.getPlateforme().getUtilisateur().getId(), (int) b.getUserData());
                v = profil.getConnexion().getAmis(profil.getPlateforme().getUtilisateur().getPseudoUt());
                ListeAmis nLs = new ListeAmis(profil, v);
                profil.setNouvelleListe(nLs);
                profil.maj();

                Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                confirmation.setTitle("Confirmation");
                confirmation.setHeaderText("Suppression d'un ami");
                confirmation.setContentText("Suppression de l'ami réussi");
                confirmation.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else{
            try{
                this.profil.getPlateforme().afficherProfilAmi(this.profil.getPlateforme().getConnexion().chercherUtilisateur((int) b.getUserData()));
            }
            catch(SQLException e){
                Alerte.creationAlerte(e);
            }
        }
    }
}
