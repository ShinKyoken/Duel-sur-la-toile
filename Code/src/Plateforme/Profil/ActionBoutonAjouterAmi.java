package Plateforme.Profil;

import Plateforme.Application.Utilisateur;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
/**
 * Cette classe implémente EventHandler<ActionEvent>, elle contient la fonction handle associée aux boutons servant
 * dans l'interface d'ajout d'amis
 * @author theodore
 */
public class ActionBoutonAjouterAmi implements EventHandler<ActionEvent> {
    /**
     * le AjouterAmi auquel est associé l'ActionBoutonAjouterAmi
     */
    private InvitationAmiProfil ajouterAmi;

    public ActionBoutonAjouterAmi(InvitationAmiProfil ajouterAmi){
        this.ajouterAmi = ajouterAmi;
    }

    /**
     * Cette fonction est déclenchée lors de l'appuie d'un bouton de l'interface AjouterAmiProfil.
     * Si le bouton search est pressé, la liste des utilisateurs correspondant au critère de recherche sera affichée
     * à la place de la liste des utilisateur actuellement affichée.
     * Si le bouton A-Z est pressé, la liste est triée par ordre alphabétique (ordre de tri de base et unique actuellement).
     * Si un bouton Ajouté est cliqué, l'ami associé au bouton est ajouté à la liste d'amis de l'utilisateur.
     * @param a
     */
    @Override
    public void handle(ActionEvent a){
        Button b = (Button) a.getSource();
        if(b.getUserData().equals("az")) {
            List<Utilisateur> v;
            try {
                v = ajouterAmi.getConnexion().listeUtilisateurNonAmis(ajouterAmi.getPlateforme().getUtilisateur().getPseudoUt());
                Collections.sort(v);
                ListePersonne nLs = new ListePersonne(ajouterAmi, v);
                ajouterAmi.setListePersonne(nLs);
                ajouterAmi.majJoueur();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        else if(b.getUserData().equals("search")) {
            try {
                List<Utilisateur> v = ajouterAmi.getConnexion().rechercheUtilisateurNonAmis(ajouterAmi.getPlateforme().getUtilisateur().getPseudoUt(),ajouterAmi.getRechercherJoueur().getText());
                Collections.sort(v);
                ListePersonne nLs = new ListePersonne(ajouterAmi, v);
                ajouterAmi.setListePersonne(nLs);
                ajouterAmi.majJoueur();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        else {
            try {
                List<Utilisateur> v;
                ajouterAmi.getConnexion().ajouterAmis(ajouterAmi.getPlateforme().getUtilisateur().getId(), (int) b.getUserData());
                v = ajouterAmi.getConnexion().listeUtilisateurNonAmis(ajouterAmi.getPlateforme().getUtilisateur().getPseudoUt());
                ListePersonne nLs = new ListePersonne(ajouterAmi, v);
                ajouterAmi.setListePersonne(nLs);
                ajouterAmi.majJoueur();

                Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                confirmation.setTitle("Confirmation");
                confirmation.setHeaderText("Ajouter un ami");
                confirmation.setContentText("Ajout d'un ami reussi");
                confirmation.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
